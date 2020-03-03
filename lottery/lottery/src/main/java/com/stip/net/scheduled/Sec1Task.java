package com.stip.net.scheduled;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import com.lottery.net.utils.Constants;
import com.lottery.net.utils.DBCache;
import com.lottery.net.utils.JsonUtil;
import com.stip.net.entity.DBDiceBetting;
import com.stip.net.entity.EosFlow;
import com.stip.net.entity.tiger.TigerConfirm;
import com.stip.net.entity.tiger.TigerPutRecord;
import com.stip.net.entity.tiger.TigerPutRecordUpdater;
import com.stip.net.entity.tiger.TigerRoom;
import com.stip.net.entity.tiger.TigerTimeState;
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
import com.stip.net.vo.Message;

import net.sf.json.JSONObject;

@Component
public class Sec1Task {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private IMWebSocketHandler handler;
	@Autowired
	private AccountService accountService;
	@Autowired
	private LotteryService lotteryService;
	@Autowired
	private TigerService tigerService;
	@Autowired
	private LotteryUserService lotteryUserService;
	@Autowired
	private GameService gameService;
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	public Message msg;

	@Scheduled(cron = "*/1 * * * * ?")
	public void execute() {
		sendMessageToDiceUser();
		sendTigerRoomDjs();
		scanTigerConfirm();
	}

	/**
	 * 骰子，给用户发送信息
	 */
	private void sendMessageToDiceUser() {
		try {
			List<DBDiceBetting> dicebetting=DBCache.getInstance().getDiceAwardPrizes();
			if (dicebetting != null && dicebetting.size() > 0) {
				// 中奖用户列表
				List<DBDiceBetting> dicePeize = new ArrayList<>();
				for (DBDiceBetting ddb : dicebetting) {
					// 判断transaction_id是否存在
					if(!accountService.checkTxId(ddb.getTransaction_id())){
						ddb.setDice_state(3);
						continue;
					}
					// 判断用户是否中奖
					if (ddb.getState() == 1) {
						dicePeize.add(ddb);
						if (!accountService.plusBalanceFromDice(ddb.getAccount(), ddb.getPrizeEOS())) {
							logger.error("骰子给用户" + ddb.getAccount() + "发奖" + ddb.getPrizeEOS() + "失败");
							ddb.setState(2);
							Map<String, Object> map2 = new HashMap<>();
							map2.put("balance", Notice.Award_prizes_ERROR);
							String json2 = JsonUtil.buildJson(MsgCode.NOTICE, map2);
							handler.sendMessageToUser(ddb.getAccount(), new TextMessage(json2));
						} else {
							// 流水记录
							EosFlow ef = new EosFlow(ddb.getAccount(), Constants.GAME_TYPE_DICE, 1, 1, null, ddb.getTermnumber(), new BigDecimal(ddb.getPrizeEOS()), null);
							PublicMethod.insertTransaction(ef, gameService, logger);
							//响应用户余额
							PublicMethod.sendBalance(ddb.getAccount(), accountService, handler);
						}
					}
					String prize = new DecimalFormat("0.0000").format(Double.parseDouble(ddb.getBettingEOS()));
					ddb.setBettingEOS(prize);
					msg = new Message();
					msg.setDate(new Date());
					msg.setFrom("diceUserPrize");
					msg.setFromName("");
					msg.setText(JSONObject.fromObject(ddb) + "");
					String bulder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg);
					// 给用户发送中奖消息
					handler.sendMessageToUser(ddb.getAccount(), new TextMessage(bulder));
					// 新用户奖励
					PublicMethod.newUser(ddb.getAccount(), lotteryUserService, accountService, logger, null, 
							ddb.getTermnumber(), Constants.GAME_TYPE_DICE,handler,gameService);
					// 邀请奖励发放
					PublicMethod.invitationAward(new BigDecimal(ddb.getBettingEOS()), ddb.getAccount(),
							lotteryUserService, accountService, logger, null, ddb.getTermnumber(),
							Constants.GAME_TYPE_DICE,gameService,handler);
				}
				lotteryService.addDBDiceBetting(dicebetting);
				if (dicePeize.size() > 0) {
					Collections.sort(dicePeize);
					msg = new Message();
					msg.setDate(new Date());
					msg.setFrom("diceAllPrize");
					msg.setFromName("");
					dicePeize.get(0).setTermnumber(dicePeize.get(0).getTermnumber()+10);
					msg.setText(JSONObject.fromObject(dicePeize.get(0)) + "");
					String bulder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg);
					// 展示中奖消息
					handler.broadcastByGame(new TextMessage(bulder), Constants.DICE_TYPE);
					// 推送骰子奖池余额
					String poolBalance = accountService.getBalance(Constants.account_dice);
		        	if (poolBalance != null) {
		        		MainData.poolBalance.put(Constants.DICE_TYPE, poolBalance);
		        		Map<String, Object> dmap = new HashMap<>();
						dmap.put("balance", poolBalance);
						String dJson = JsonUtil.buildJson(MsgCode.DICE_POOL_BALANCE, dmap);
						handler.broadcastByGame(new TextMessage(dJson), Constants.DICE_TYPE);
		        	}
				}
				DBCache.getInstance().getDiceBettings().removeAll(dicebetting);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 龙虎斗，推送房间倒计时
	 */
	private void sendTigerRoomDjs() {
		try {
			for (TigerRoom room : MainData.tigerRooms) {
				long now = System.currentTimeMillis();
				long lastUpdate = room.getLastUpdate();
				long djs = 0;
				long period = 0;
				TigerTimeState state = TigerTimeState.getEnumById(room.getState());
				switch (state) {
				case put:
					period = Constants.TIGER_PUT_TIME;
					break;
				case open:
					period = Constants.TIGER_OPEN_TIME;
					break;
				default:
					break;
				}
				djs = lastUpdate + period - now;
				if (djs < 0) {
					djs = 0;
				}
				// 通知客户端
				Map<String, Object> map = new HashMap<>();
				map.put("roomId", room.getId());
				map.put("djs", djs / 1000);
				String json = JsonUtil.buildJson(MsgCode.TIGER_ROOM_DJS, map);
				handler.broadcastToSome(room.getHeads(), new TextMessage(json));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 扫描龙虎斗投注确认缓存
	 */
	private void scanTigerConfirm() {
		try {
			List<TigerConfirm> confirms = MainData.tigerConfirms;
			if (confirms.size() == 0) {
				return;
			}
			List<TigerConfirm> useConfirms = new ArrayList<>(confirms);
			for (Iterator<TigerConfirm> it = useConfirms.iterator(); it.hasNext();) {
				TigerConfirm confirm = it.next();
				String txId = confirm.getTxId();
				TigerPutRecord record = tigerService.getRecordByTxId(txId);
				if (record == null) {
					it.remove();
					continue;
				}
				TigerPutRecordUpdater updater = new TigerPutRecordUpdater();
				updater.setTxId(record.getTxId());
				updater.setTxStatus(1);
				if (confirm.getPutMoney().compareTo(record.getPutMoney()) < 0) {
					updater.setPutMoney(confirm.getPutMoney());
				}
				tigerService.updateRecord(updater);

				threadPoolTaskExecutor.execute(new Runnable() {
					public void run() {
						// 新用户奖励
						PublicMethod.newUser(record.getUserId(), lotteryUserService, accountService, logger,
								record.getRoomId(), (long)record.getQid(),Constants.GAME_TYPE_TIGER,handler,gameService);
						// 邀请奖励发放
						PublicMethod.invitationAward(record.getPutMoney(), record.getUserId(), lotteryUserService,
								accountService, logger, record.getRoomId(), (long)record.getQid(),Constants.GAME_TYPE_TIGER,gameService,handler);
					}
				});
			}
			// 从缓存中删除
			synchronized (confirms) {
				confirms.removeAll(useConfirms);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}