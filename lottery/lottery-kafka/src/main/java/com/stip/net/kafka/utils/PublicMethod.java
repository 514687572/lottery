package com.stip.net.kafka.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.web.socket.TextMessage;

import com.google.gson.GsonBuilder;
import com.lottery.net.utils.CoinUtil;
import com.lottery.net.utils.Constants;
import com.lottery.net.utils.GrnerateUUID;
import com.lottery.net.utils.JsonUtil;
import com.lottery.net.utils.TimeUtils;
import com.stip.net.entity.EosFlow;
import com.stip.net.entity.LotteryRecords;
import com.stip.net.entity.LotteryUser;
import com.stip.net.entity.LotteryUserScore;
import com.stip.net.entity.UserBetRecords;
import com.stip.net.entity.UserBetScoreRecords;
import com.stip.net.entity.UserTransactionScore;
import com.stip.net.example.UserTransactionScoreExample;
import com.stip.net.example.UserTransactionScoreExample.Criteria;
import com.stip.net.imessage.IMWebSocketHandler;
import com.stip.net.main.MainData;
import com.stip.net.main.MsgCode;
import com.stip.net.service.AccountService;
import com.stip.net.service.GameService;
import com.stip.net.service.LotteryUserService;
import com.stip.net.service.UserBetScoreRecordsService;
import com.stip.net.service.UserBetService;
import com.stip.net.service.UserTransactionScoreService;
import com.stip.net.vo.Message;

import io.eblock.eos4j.api.vo.account.Account;
import net.sf.json.JSONObject;

public class PublicMethod {

	private PublicMethod() {
		super();
	}

	/**
	 * 插入EOS流水
	 */
	public static void insertTransaction(EosFlow ef, GameService gameService, Logger _log) {
		try {
			gameService.insertEosFlow(ef);
		} catch (Exception e) {
			_log.error("EOS流水记录插入失败：" + ef);
		}
	}

	/**
	 * 新用户奖励 新用户及邀请者各得0.1EOS 新用户额外获得0.1EOS
	 */
	public static void newUser(String userName, LotteryUserService lotteryUserService, AccountService accountService,
			Logger logger, Integer roomId, Long qid, String gameType, IMWebSocketHandler handler,
			GameService gameService) {
		long count = gameService.countMyPut(userName);
		if (count > 1) {
			return;
		}
		LotteryUser lotteryUser = new LotteryUser();
		lotteryUser.setUserName(userName);
		lotteryUser = lotteryUserService.getLotteryByInviter(lotteryUser);
		// 转账
		if (null != lotteryUser) {
			String inviter = lotteryUser.getUserName();
			// 双方各得0.1EOS
			try {
				// 邀请者获得0.1EOS
				BigDecimal b1 = new BigDecimal(Constants.REWARD);
				String result1 = CoinUtil.formatMoney(b1);
				accountService.plusBalance(inviter, result1);
				// 流水记录
				EosFlow ef_inviter = new EosFlow(inviter, gameType, 1, 2, roomId, qid, b1, userName);
				insertTransaction(ef_inviter, gameService, logger);

				// 投注者获得0.1EOS，再额外获得0.1EOS
				BigDecimal b2 = b1.add(b1);
				String result2 = CoinUtil.formatMoney(b2);
				accountService.plusBalance(userName, result2);
				// 流水记录
				EosFlow ef_putter = new EosFlow(userName, gameType, 1, 3, roomId, qid, b2, null);
				insertTransaction(ef_putter, gameService, logger);
			} catch (InterruptedException e) {
				logger.error("新用户奖励转账出错 邀请者:{},下注者:{}", inviter, userName);
			}
			// 推送消息给用户
			putMessageToInviter(handler, inviter, logger);
		}
	}

	/**
	 * 邀请奖励发放
	 *
	 * @param betAmount
	 *            下注总金额
	 * @param userName
	 *            玩家
	 */
	public static void invitationAward(BigDecimal betAmount, String userName, LotteryUserService lotteryUserService,
			AccountService accountService, Logger logger, Integer roomId, Long qid, String gameType,
			GameService gameService, IMWebSocketHandler handler) {
		LotteryUser lotteryUser = new LotteryUser();
		lotteryUser.setUserName(userName);
		lotteryUser = lotteryUserService.getLotteryByInviter(lotteryUser);
		// 转账
		if (null != lotteryUser) {
			String inviter = lotteryUser.getUserName();
			// 比例
			BigDecimal proportion = new BigDecimal(Constants.PROPORTION).multiply(betAmount);
			try {
				// 格式化小数 始终保持4位
				String result = new DecimalFormat("0.0000")
						.format(proportion.setScale(4, BigDecimal.ROUND_HALF_DOWN).doubleValue()).toString();
				accountService.plusBalance(inviter, result);

				EosFlow ef = new EosFlow(inviter, gameType, 1, 4, roomId, qid, proportion, userName);
				insertTransaction(ef, gameService, logger);
			} catch (InterruptedException e) {
				logger.error("转账出错了");
			}
			// 推送消息给用户
			putMessageToInviter(handler, inviter, logger);
		}
	}

	/**
	 * 账户变更推送
	 */
	private static void putMessageToInviter(IMWebSocketHandler handler, String userName, Logger logger) {
		try {
			Message msg = new Message();
			msg.setDate(new Date());
			msg.setFrom("invitation");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("invitation", userName);
			msg.setText(jsonObject.toString());
			String bulder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg);
			// handler.sendMessageToUser(userName,new TextMessage(bulder));
			handler.broadcastByGame(new TextMessage(bulder), Constants.LOTTERY_TYPE);
		} catch (Exception e) {
			logger.error("消息推送失败");
		}
	}

	/**
	 * 大乐透，发送开奖信息
	 */
	public static void senPrizeMsg(LotteryRecords records, Date date, UserBetService userBetService,
			UserBetScoreRecordsService userBetScoreService, AccountService accountService, Logger logger,
			LotteryUserService lotteryUserService, IMWebSocketHandler handler, GameService gameService,
			UserTransactionScoreService userTransactionScoreService) {
		Map<String, String> map = null;
		List<UserBetRecords> list = userBetService.getBetRecordsByTime(date, records);
		List<UserBetScoreRecords> list1 = userBetScoreService.getBetRecordsByTime(date, records);
		map = new HashMap<String, String>(list1.size() + list.size());
		if (null != list1 && list1.size() > 0) {// 积分用户开奖
			for (UserBetScoreRecords ubsr : list1) {
				double prize = 0;
				if ("1".equals(ubsr.getHighClass())) {// 高级模式
					if (ubsr.getNoteNum() == 1) {
						prize = ubsr.getNoteMoney() * 9.8;
					} else if (ubsr.getNoteNum() == 2) {
						prize = ubsr.getNoteMoney() * 98;
					} else if (ubsr.getNoteNum() == 3) {
						prize = ubsr.getNoteMoney() * 980;
					} else if (ubsr.getNoteNum() == 4) {
						prize = ubsr.getNoteMoney() * 9800;
					} else if (ubsr.getNoteNum() == 5) {
						prize = ubsr.getNoteMoney() * 98000;
					}
				} else {
					if (StringUtils.isNotBlank(ubsr.getLotteryOne())) {
						prize = ubsr.getNoteMoney() * 9.8;
					} else {
						prize = ubsr.getNoteMoney() * 2 * 0.98;
					}
				}
				BigDecimal b = new BigDecimal(prize);
				String result = new DecimalFormat("0.0000")
						.format(b.setScale(4, BigDecimal.ROUND_HALF_DOWN).doubleValue()).toString();
				boolean success = lotteryUserService.plusScoreFromLottery(ubsr.getUserId().longValue(),
						new BigDecimal(result));
				if (success) {
					// 插入明细
					UserTransactionScore record = new UserTransactionScore(ubsr.getUserId(),
							Constants.GAME_TYPE_LOTTERY, null, ubsr.getBetNum(), "1", "1", new BigDecimal(result),
							null);
					insertTransactionScore(record, userTransactionScoreService, logger);
				} else {
					logger.error("给积分用户发奖失败");
				}
				ubsr.setLotteryBonus(b.setScale(4, BigDecimal.ROUND_HALF_DOWN).doubleValue());
				ubsr.setUpdateTime(new Date());
				userBetScoreService.updateByPrimaryKey(ubsr);
				LotteryUserScore lotteryUserScore = lotteryUserService.getLotteryUserScoreId(ubsr.getUserId());
				if (map.get(lotteryUserScore.getUserId()) == null) {
					map.put(lotteryUserScore.getUserId(),
							b.setScale(4, BigDecimal.ROUND_HALF_DOWN).doubleValue() + "GOS");
				} else {
					String score = map.get(lotteryUserScore.getUserId());
					map.put(lotteryUserScore.getUserId(), Double.parseDouble(StringUtils.remove(score, "GOS"))
							+ b.setScale(4, BigDecimal.ROUND_HALF_DOWN).doubleValue() + "GOS");
				}
			}

		} else if (null != list && list.size() > 0) {// eos用户开奖
			for (UserBetRecords rec : list) {
				double prize = 0;
				if ("1".equals(rec.getHighClass())) {// 高级模式
					if (rec.getNoteNum() == 1) {
						prize = rec.getNoteMoney() * 9.8;
					} else if (rec.getNoteNum() == 2) {
						prize = rec.getNoteMoney() * 98;
					} else if (rec.getNoteNum() == 3) {
						prize = rec.getNoteMoney() * 980;
					} else if (rec.getNoteNum() == 4) {
						prize = rec.getNoteMoney() * 9800;
					} else if (rec.getNoteNum() == 5) {
						prize = rec.getNoteMoney() * 98000;
					}
				} else {
					if (StringUtils.isNotBlank(rec.getLotteryOne())) {
						prize = rec.getNoteMoney() * 9.8;
					} else {
						prize = rec.getNoteMoney() * 2 * 0.98;
					}
				}

				BigDecimal b = new BigDecimal(prize);
				try {
					String result = new DecimalFormat("0.0000")
							.format(b.setScale(4, BigDecimal.ROUND_HALF_DOWN).doubleValue()).toString();
					accountService.plusBalance(rec.getUserName(), result);

					EosFlow ef = new EosFlow(rec.getUserName(), Constants.GAME_TYPE_LOTTERY, 1, 1, null,
							rec.getBetNum(), b, null);
					insertTransaction(ef, gameService, logger);
				} catch (InterruptedException e) {
					logger.error("转账出错了");
				}
				rec.setLotteryBonus(b.setScale(4, BigDecimal.ROUND_HALF_DOWN).doubleValue());
				userBetService.updateByPrimaryKey(rec);

				if (map.get(rec.getUserName()) == null) {
					map.put(rec.getUserName(), b.setScale(4, BigDecimal.ROUND_HALF_DOWN).doubleValue() + "EOS");
				} else {
					String banlance=StringUtils.remove(map.get(rec.getUserName()),"EOS");
					map.put(rec.getUserName(),new DecimalFormat("0.0000").format(b.add(new BigDecimal(banlance)).doubleValue()) + "EOS");
				}
			}
		} else {
			return;
		}

		Iterator<Entry<String, String>> it = map.entrySet().iterator();

		JSONObject jsonObject = null;
		Message msg = null;
		while (it.hasNext()) {
			final Entry<String, String> entry = it.next();

			jsonObject = new JSONObject();
			jsonObject.put("pri", entry.getValue().toString());
			jsonObject.put("num", records.getRecordsId().toString());
			jsonObject.put("time", new Date());

			msg = new Message();
			msg.setDate(new Date());
			msg.setFrom("prizeMsg");
			msg.setFromName(entry.getKey().toString());
			msg.setText(jsonObject.toString());
			String bulder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg);
			handler.broadcastByGame(new TextMessage(bulder), Constants.LOTTERY_TYPE);
		}
	}

	/**
	 * 新用户奖励（新用户及邀请者各得0.1积分 新用户额外获得0.1积分）
	 * 
	 * @param user
	 *            积分投注用户
	 * @param gameType
	 *            游戏类型
	 * @param roomId
	 *            房间号
	 * @param qid
	 *            期号
	 */
	public static void newUserScore(String gameStr, LotteryUserScore user, LotteryUserService lotteryUserService,
			UserTransactionScoreService userTransactionScoreRecordsService, String gameType, Integer roomId, Long qid,
			IMWebSocketHandler handler, Logger logger) {
		// 获取邀请者
		long uid = user.getId();
		LotteryUserScore inviter = lotteryUserService.getScoreInviterById(uid);
		if (inviter == null) {
			return;
		}
		long inviterId = inviter.getId();
		// 判断是不是新用户
		UserTransactionScoreExample example = new UserTransactionScoreExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(uid);
		int count = userTransactionScoreRecordsService.countByExample(example);
		if (count > 1) {
			return;
		}
		// 投注者获得0.2积分
		lotteryUserService.plusScoreFromDice(uid, new BigDecimal("0.2"));
		// 插入明细
		UserTransactionScore record2 = new UserTransactionScore(uid, gameType, null, qid, "1", "3",
				new BigDecimal("0.2"), null);
		insertTransactionScore(record2, userTransactionScoreRecordsService, logger);

		// 邀请者获得0.1积分
		lotteryUserService.plusScoreFromDice(inviterId, new BigDecimal("0.1"));
		// 插入明细
		UserTransactionScore record1 = new UserTransactionScore(inviterId, gameType, null, qid, "1", "2",
				new BigDecimal("0.1"), uid);
		insertTransactionScore(record1, userTransactionScoreRecordsService, logger);
		// 响应邀请者用户余额
		sendScoreUserBalance(inviterId, gameStr, lotteryUserService, handler);

		// 广播奖池余额
		sendPoolBalance(gameStr, handler);
	}

	/**
	 * 邀请奖励（给积分用户发送佣金）
	 * 
	 * @param betAmount
	 *            投注积分
	 * @param user
	 *            积分投注用户
	 * @param gameType
	 *            游戏类型
	 * @param roomId
	 *            房间号
	 * @param qid
	 *            期号
	 */
	public static void invitationAwardScoreUser(String gameStr, BigDecimal betAmount, LotteryUserScore user,
			LotteryUserService lotteryUserService, UserTransactionScoreService userTransactionScoreRecordsService,
			String gameType, Integer roomId, Long qid, IMWebSocketHandler handler, Logger logger) {
		// 获取邀请者
		LotteryUserScore inviter = lotteryUserService.getScoreInviterById(user.getId());
		if (inviter == null) {
			return;
		}
		long inviterId = inviter.getId();
		// 比例
		BigDecimal proportion = new BigDecimal(Constants.PROPORTION).multiply(betAmount);
		lotteryUserService.plusScoreFromDice(inviterId, proportion);
		// 插入明细
		UserTransactionScore record = new UserTransactionScore(inviterId, gameType, null, qid, "1", "4", proportion,
				user.getId());
		insertTransactionScore(record, userTransactionScoreRecordsService, logger);
		// 响应邀请者用户余额
		sendScoreUserBalance(inviterId, gameStr, lotteryUserService, handler);
		// 广播奖池余额
		PublicMethod.sendPoolBalance(gameStr, handler);
	}

	/**
	 * 插入积分用户流水
	 */
	public static void insertTransactionScore(UserTransactionScore record, UserTransactionScoreService service,
			Logger _log) {
		try {
			Date date = new Date();
			record.setCreateTime(date);
			record.setSerialNumber(TimeUtils.dateToString(date, "yyyyMMddHHmmss") + GrnerateUUID.getAtomicCounter());
			service.insert(record);
		} catch (Exception e) {
			_log.error("积分流水记录插入失败：" + record);
		}
	}

	/**
	 * 响应积分用户余额
	 */
	public static void sendScoreUserBalance(long id, String gameStr, LotteryUserService lotteryUserService,
			IMWebSocketHandler handler) {
		LotteryUserScore user = lotteryUserService.getLotteryUserScoreId(id);
		Map<String, Object> map = new HashMap<>();
		map.put("score", user.getScore());
		String json = JsonUtil.buildJson(MsgCode.SCORE_USER_BALANCE, map);
		handler.sendMessageToUser(user.getUserId(), new TextMessage(json));
	}

	/**
	 * 响应积分奖池余额
	 */
	public static void sendPoolBalance(String gameStr, IMWebSocketHandler handler) {
		Map<String, Object> dmap = new HashMap<>();
		BigDecimal balance = MainData.scorePoolBalance.get(gameStr);
		dmap.put("score", balance);
		dmap.put("game", gameStr);
		String dJson = JsonUtil.buildJson(MsgCode.SCORE_POOL_BALANCE, dmap);
		handler.broadcastByGame(new TextMessage(dJson), gameStr);
	}

	/**
	 * 响应积分用户充值信息
	 */
	public static void sendScoreUserPay(long id, LotteryUserService lotteryUserService, IMWebSocketHandler handler) {
		LotteryUserScore user = lotteryUserService.getLotteryUserScoreId(id);
		Map<String, Object> map = new HashMap<>();
		map.put("score", user.getScore());
		String json = JsonUtil.buildJson(MsgCode.SCORE_USER_PAY, map);
		handler.sendMessageToUser(user.getUserId(), new TextMessage(json));
	}
	
	/**
	 * 响应EOS用户余额
	 * @param ddb
	 */
	public static void sendBalance(String account, AccountService accountService,IMWebSocketHandler handler){
		Map<String, Object> map2 = new HashMap<>();
		Account user = accountService.getAccount(account);
		String balance = user.getCoreLiquidBalance();
		map2.put("balance", balance);
		map2.put("top", accountService.getTopBalance(account));
		String json1 = JsonUtil.buildJson(MsgCode.TIGER_USER_BALANCE, map2);
		handler.sendMessageToUser(account, new TextMessage(json1));
	}
	
}