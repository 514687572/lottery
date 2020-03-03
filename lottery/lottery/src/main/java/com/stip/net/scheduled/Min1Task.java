package com.stip.net.scheduled;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import com.google.gson.GsonBuilder;
import com.lottery.net.utils.CoinUtil;
import com.lottery.net.utils.Constants;
import com.lottery.net.utils.JsonUtil;
import com.stip.net.entity.DBDiceBetting;
import com.stip.net.entity.EosFlow;
import com.stip.net.entity.TopFlow;
import com.stip.net.entity.UserBetRecords;
import com.stip.net.entity.tiger.TigerHistory;
import com.stip.net.entity.tiger.TigerLatePutRecord;
import com.stip.net.entity.tiger.TigerOption;
import com.stip.net.entity.tiger.TigerPutRecord;
import com.stip.net.entity.tiger.TigerPutRecordUpdater;
import com.stip.net.imessage.IMWebSocketHandler;
import com.stip.net.kafka.utils.PublicMethod;
import com.stip.net.main.MainData;
import com.stip.net.main.MsgCode;
import com.stip.net.main.Notice;
import com.stip.net.service.AccountService;
import com.stip.net.service.GameService;
import com.stip.net.service.LotteryService;
import com.stip.net.service.LotteryUserService;
import com.stip.net.service.TigerService;
import com.stip.net.service.UserBetService;
import com.stip.net.vo.Message;

import net.sf.json.JSONObject;

@Component
public class Min1Task {
	protected final Logger _log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TigerService tigerService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private LotteryService lotteryService;
	@Autowired
	private LotteryUserService lotteryUserService;
	@Autowired
	private UserBetService userBetService;
	@Autowired
	private IMWebSocketHandler handler;
	@Autowired
	private GameService gameService;
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Scheduled(cron = "0 0/1 * * * ?")
	public void execute() {
		updatePoolBalance();
		updateScorePoolBalance();
		openAndSendLateTiger();
		sendTopCoin();
		returnLatePutMoney();
		saveAll();
		sendBlockErrorDice();
		sendBlockErrorTiger();
	}

	/**
	 * 定时存储游戏数据
	 */
	private void saveAll() {
		try {
			_log.info("save All start..................................");
			tigerService.saveAllRooms(MainData.tigerRooms);
			tigerService.saveAllConfirms(MainData.tigerConfirms);
			_log.info("save All over..................................");
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
	}

	/**
	 * 更新奖池金额缓存
	 */
	private void updatePoolBalance() {
		try {
			String tigerBalance = accountService.getBalance(Constants.account_tiger);
			if (tigerBalance != null) {
				MainData.poolBalance.put(Constants.TIGER_TYPE, tigerBalance);
			}
			String diceBalance = accountService.getBalance(Constants.account_dice);
			if (diceBalance != null) {
				MainData.poolBalance.put(Constants.DICE_TYPE, diceBalance);
			}
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
	}

	/**
	 * 更新积分奖池金额缓存
	 */
	private void updateScorePoolBalance() {
		try {
			Map<String, BigDecimal> map = MainData.scorePoolBalance;
			map.put(Constants.LOTTERY_TYPE, gameService.getPoolScore(Constants.VK_LOTTERY_POOL_SCORE));
			map.put(Constants.TIGER_TYPE, gameService.getPoolScore(Constants.VK_TIGER_POOL_SCORE));
			map.put(Constants.DICE_TYPE, gameService.getPoolScore(Constants.VK_DICE_POOL_SCORE));
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
	}

	/**
	 * 龙虎斗，补发投注过于延迟而错过开奖的奖励
	 */
	private void openAndSendLateTiger() {
		try {
			long expireTime = 2L * 60 * 1000;// 指定超时时间为两分钟
			// 查询超过2分钟仍未开奖的投注记录
			List<TigerPutRecord> records = tigerService.getExpiredRecordsToOpen(System.currentTimeMillis(), expireTime);
			if (records.size() == 0) {
				threadPoolTaskExecutor.execute(new Runnable() {
					@Override
					public void run() {
						sendLateTiger();
					}
				});
				return;
			}
			threadPoolTaskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					// 更新开奖标记和开奖时间
					for (int i = 0; i < records.size(); i++) {
						TigerPutRecord record = records.get(i);
						TigerPutRecordUpdater updater = new TigerPutRecordUpdater();
						updater.setTxId(record.getTxId());

						int qid = record.getQid();
						int roomId = record.getRoomId();
						TigerHistory th = tigerService.getTigerHistory(qid, roomId);
						if (th == null) {
							_log.error("open expird tiger record failed! history not found! qid: " + qid + ", roomId: "
									+ roomId);
							continue;
						}
						// 使得每条记录开奖时间不同，便于查询
						updater.setOpenTimeSpt(th.getOpenTimeSpt() + i + 200);
						List<Integer> goodOpts = MainData.getGoodOpts(th.getLp(), th.getHp());
						// 更新中奖金额
						if (goodOpts.contains(record.getOpt())) {
							updater.setStatus(2);
							TigerOption opt = TigerOption.getEnumById(record.getOpt());
							BigDecimal gainMoney = record.getPutMoney().multiply(new BigDecimal(opt.getPei()))
									.divide(new BigDecimal(1000));
							updater.setGainMoney(gainMoney);
						} else {
							updater.setStatus(1);
						}
						// 更新到数据库
						tigerService.updateRecord(updater);
					}
					// 扫描并发奖
					sendLateTiger();
				}
			});
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
	}

	/**
	 * 扫描历史悠久而未发奖的记录并发奖
	 */
	private void sendLateTiger() {
		long expireTime = 2L * 60 * 1000;// 指定超时时间为两分钟
		// 查询超过2分钟，应该发奖而未发奖的投注记录
		List<TigerPutRecord> luckyRecords = tigerService.getExpiredRecordsToSend(System.currentTimeMillis(),
				expireTime);
		if (luckyRecords.size() == 0) {
			return;
		}
		for (TigerPutRecord record : luckyRecords) {
			TigerPutRecordUpdater updater = new TigerPutRecordUpdater();
			String txId = record.getTxId();
			updater.setTxId(txId);
			// 如果区块链上查不到此txId
			if (!accountService.checkTxId(txId)) {
				updater.setStatus(-2);
				tigerService.updateRecord(updater);
				continue;
			}

			String userId = record.getUserId();
			BigDecimal putMoney = record.getPutMoney();
			BigDecimal gainMoney = record.getGainMoney();
			// 发奖转账
			boolean success = accountService.plusBalanceFromTiger(userId, putMoney.add(gainMoney));
			if (success) {
				_log.error("发奖成功！ userId: " + userId + ", money: " + putMoney.add(gainMoney));
				updater.setStatus(3);
				tigerService.updateRecord(updater);
				// 流水记录
				EosFlow ef = new EosFlow(userId, Constants.GAME_TYPE_TIGER, 1, 1, record.getRoomId(), Long.valueOf(record.getQid()), putMoney.add(gainMoney), null);
				PublicMethod.insertTransaction(ef, gameService, _log);
			} else {
				_log.error("发奖失败！ txId: " + record.getTxId());
				updater.setStatus(-1);
				tigerService.updateRecord(updater);
			}
		}
		// 更新奖池缓存
		String poolBalance = accountService.getBalance(Constants.account_tiger);
		if (poolBalance != null) {
			MainData.poolBalance.put(Constants.TIGER_TYPE, poolBalance);
		}
	}

	/**
	 * 玩家投注后，给他发代币
	 */
	private void sendTopCoin() {
		if ("".length() == 0) {// 此功能暂不开放
			return;
		}
		try {
			List<TigerPutRecord> tigerRecords = tigerService.getRecordsToSendTop();
			// 查询骰子出未发代币的投注记录
			List<DBDiceBetting> diceRecords = lotteryService.getDiceTopState();
			// 查询出未发代币的投注记录
			List<UserBetRecords> lotteryRecords = userBetService.getUserScripIssued();
			if (tigerRecords.size() + diceRecords.size() + lotteryRecords.size() == 0) {
				return;
			}
			// 投注EOS返TOP币的比例
			String per = CoinUtil.getTopPer(MainData.totalTop);
			if (per == null) {
				_log.error("can't sum total top!");
				return;
			}
			BigDecimal top_per = new BigDecimal(per);

			threadPoolTaskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					// 龙虎斗发代币
					for (TigerPutRecord record : tigerRecords) {
						TigerPutRecordUpdater updater = new TigerPutRecordUpdater();
						updater.setTxId(record.getTxId());

						String userId = record.getUserId();
						BigDecimal putMoney = record.getPutMoney();
						BigDecimal topMoney = putMoney.multiply(top_per);
						boolean success = accountService.transFromTop(userId, topMoney);
						if (success) {
							updater.setTopStatus(1);
							tigerService.updateRecord(updater);
							// 记录TOP流水
							tigerService.insertTopFlow(new TopFlow(userId, Constants.GAME_TYPE_TIGER, putMoney, per,
									topMoney, System.currentTimeMillis()));
						}
					}
					// 骰子发代币
					for (DBDiceBetting record : diceRecords) {
						String account = record.getAccount();
						String bettingEOS = record.getBettingEOS();
						BigDecimal topMoney = new BigDecimal(bettingEOS).multiply(top_per);
						boolean success = accountService.transFromTop(account, topMoney);
						if (success) {
							// 标记投注记录为已发代币
							record.setTopStatus(1);
							// 将此记录更新到数据库
							lotteryService.updateDiceTopState(record);
							// 记录TOP流水
							tigerService.insertTopFlow(new TopFlow(account, Constants.GAME_TYPE_DICE,
									new BigDecimal(bettingEOS), per, topMoney, System.currentTimeMillis()));
						}
					}
					// 大乐透发代币
					for (UserBetRecords record : lotteryRecords) {
						String username = record.getUserName();
						Double noteMoney = record.getNoteMoney();
						BigDecimal topMoney = new BigDecimal(noteMoney).multiply(top_per);
						boolean success = accountService.transFromTop(username, topMoney);
						UserBetRecords newRecord = new UserBetRecords();
						newRecord.setBetId(record.getBetId());
						if (success) {
							// 标记投注记录为已发代币
							newRecord.setUserId(1L);
							// 将此记录更新到数据库
							userBetService.updateLotteryBetScripIssued(newRecord);
							// 记录TOP流水
							tigerService.insertTopFlow(new TopFlow(username, Constants.GAME_TYPE_LOTTERY,
									new BigDecimal(noteMoney), per, topMoney, System.currentTimeMillis()));
						}
					}
				}
			});
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
	}

	/**
	 * 退还延迟投注的扣款
	 */
	private void returnLatePutMoney() {
		try {
			List<TigerLatePutRecord> records = tigerService.getLateRecordsToReturn();
			if (records.size() == 0) {
				return;
			}
			for (TigerLatePutRecord record : records) {
				String txId = record.getTxId();
				// 如果区块链上查不到此txId
				if (!accountService.checkTxId(txId)) {
					record.setStatus(-2);
					tigerService.updateLateRecord(record);
					continue;
				}

				String userId = record.getUserId();
				BigDecimal putMoney = record.getPutMoney();
				// 退款
				boolean success = accountService.plusBalanceFromTiger(userId, putMoney);
				if (success) {
					_log.error("退款成功！ userId: " + userId + ", money: " + putMoney);
					record.setStatus(1);
					tigerService.updateLateRecord(record);
					// 流水记录
					EosFlow ef = new EosFlow(userId, Constants.GAME_TYPE_TIGER, 1, 5, record.getRoomId(), Long.valueOf(record.getQid()), putMoney, null);
					PublicMethod.insertTransaction(ef, gameService, _log);
				} else {
					_log.error("发奖失败！ txId: " + record.getTxId());
					record.setStatus(-1);
					tigerService.updateLateRecord(record);
				}
			}
			// 更新奖池缓存
			String poolBalance = accountService.getBalance(Constants.account_tiger);
			if (poolBalance != null) {
				MainData.poolBalance.put(Constants.TIGER_TYPE, poolBalance);
			}
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
	}

	/**
	 *  骰子，补发交易地址未查到的投注记录
	 */
	private void sendBlockErrorDice(){
		try {
			//骰子，未查到交易地址的列表
			List<DBDiceBetting> dices=lotteryService.getLateDice();
			for (DBDiceBetting ddb : dices) {
				if(accountService.checkTxId(ddb.getTransaction_id())){
					lotteryService.updateDiceState(ddb.getId());
					// 判断用户是否中奖
					if (ddb.getState() == 1) {
						if (!accountService.plusBalanceFromDice(ddb.getAccount(), ddb.getPrizeEOS())) {
							_log.error("骰子给用户" + ddb.getAccount() + "发奖" + ddb.getPrizeEOS() + "失败");
							ddb.setState(2);
							Map<String, Object> map2 = new HashMap<>();
							map2.put("balance", Notice.Award_prizes_ERROR);
							String json2 = JsonUtil.buildJson(MsgCode.NOTICE, map2);
							handler.sendMessageToUser(ddb.getAccount(), new TextMessage(json2));
						} else {
							// 流水记录
							EosFlow ef = new EosFlow(ddb.getAccount(), Constants.GAME_TYPE_DICE, 1, 1, null, ddb.getTermnumber(), new BigDecimal(ddb.getPrizeEOS()), null);
							PublicMethod.insertTransaction(ef, gameService, _log);
							//响应用户余额
							PublicMethod.sendBalance(ddb.getAccount(), accountService, handler);
						}
					}
					String prize = new DecimalFormat("0.0000").format(Double.parseDouble(ddb.getBettingEOS()));
					ddb.setBettingEOS(prize);
					Message msg = new Message();
					msg.setDate(new Date());
					msg.setFrom("diceUserPrize");
					msg.setFromName("");
					msg.setText(JSONObject.fromObject(ddb) + "");
					String bulder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg);
					// 给用户发送中奖消息
					handler.sendMessageToUser(ddb.getAccount(), new TextMessage(bulder));
					// 新用户奖励
					PublicMethod.newUser(ddb.getAccount(), lotteryUserService, accountService, _log, null, 
							ddb.getTermnumber(), Constants.GAME_TYPE_DICE,handler,gameService);
					// 邀请奖励发放
					PublicMethod.invitationAward(new BigDecimal(ddb.getBettingEOS()), ddb.getAccount(),
							lotteryUserService, accountService, _log, null, ddb.getTermnumber(),
							Constants.GAME_TYPE_DICE,gameService,handler);
				}
			}
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
	}

	/**
	 *  龙虎斗，补发交易地址未查到的投注记录
	 */
	private void sendBlockErrorTiger(){
		try {
			// 龙虎斗，未查到交易地址的投注记录
			List<TigerPutRecord> records = tigerService.getBlockErrorRecord();
			if (records.size() == 0) {
				return;
			}
			for (TigerPutRecord record : records) {
				String txId = record.getTxId();
				// 如果区块链上查不到此txId
				if (!accountService.checkTxId(record.getTxId())) {
					continue;
				}

				String userId = record.getUserId();
				BigDecimal putMoney = record.getPutMoney();
				BigDecimal gainMoney = record.getGainMoney();
				// 发奖转账
				boolean success = accountService.plusBalanceFromTiger(userId, putMoney.add(gainMoney));
				if (success) {
					_log.error("发奖成功！ userId: " + userId + ", money: " + putMoney.add(gainMoney));
					TigerPutRecordUpdater updater = new TigerPutRecordUpdater();
					updater.setTxId(txId);
					updater.setStatus(3);
					tigerService.updateRecord(updater);
					// 流水记录
					EosFlow ef = new EosFlow(userId, Constants.GAME_TYPE_TIGER, 1, 1, record.getRoomId(), Long.valueOf(record.getQid()), putMoney.add(gainMoney), null);
					PublicMethod.insertTransaction(ef, gameService, _log);
				}
			}
			// 更新奖池缓存
			String poolBalance = accountService.getBalance(Constants.account_tiger);
			if (poolBalance != null) {
				MainData.poolBalance.put(Constants.TIGER_TYPE, poolBalance);
			}
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
	}
}