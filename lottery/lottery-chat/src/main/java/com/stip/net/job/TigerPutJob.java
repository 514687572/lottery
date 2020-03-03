package com.stip.net.job;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.socket.TextMessage;

import com.lottery.net.utils.CoinUtil;
import com.lottery.net.utils.Constants;
import com.lottery.net.utils.JsonUtil;
import com.stip.net.entity.EosFlow;
import com.stip.net.entity.tiger.TigerLatePutRecord;
import com.stip.net.entity.tiger.TigerOption;
import com.stip.net.entity.tiger.TigerPutRecord;
import com.stip.net.entity.tiger.TigerRoom;
import com.stip.net.entity.tiger.TigerTimeState;
import com.stip.net.imessage.IMWebSocketHandler;
import com.stip.net.main.MainData;
import com.stip.net.main.MsgCode;
import com.stip.net.main.Notice;
import com.stip.net.service.AccountService;
import com.stip.net.service.GameService;
import com.stip.net.service.RedisService;
import com.stip.net.service.TigerService;

import io.eblock.eos4j.api.vo.account.Account;
import net.sf.json.JSONObject;

/**
 * 玩家投注（龙虎斗）
 */
public class TigerPutJob extends AbstractJob {

	@Override
	public void runImpl(IMWebSocketHandler handler, String userId, JSONObject params, Object... object) {
		TigerService tigerService = (TigerService) object[0];
		AccountService accountService = (AccountService) object[1];
		ThreadPoolTaskExecutor threadPoolTaskExecutor = (ThreadPoolTaskExecutor) object[2];
		GameService gameService = (GameService) object[3];
		RedisService redisService = (RedisService) object[4];

		int type = params.getInt("type");// 0 scatter投注，1 私钥投注
		int roomId = params.getInt("roomId");// 房间id
		int optId = params.getInt("opt");// 选择哪一门，参考TigerOption枚举
		String moneyStr = params.getString("money");// 投注金额
		BigDecimal money = new BigDecimal(moneyStr);

		String txId = null;// 区块链transaction_id
		Account account;// 用户账户

		TigerOption opt = TigerOption.getEnumById(optId);
		TigerRoom room = MainData.getRoomById(roomId);

		if (type == 0) {// scatter登录投注
			// 正常逻辑
			txId = params.getString("txId");
			account = null;

			// 判断是否允许投注的变量
			boolean isOk = true;
			// 金额小数判断
			int pointIndex = moneyStr.indexOf(".");
			if (isOk && pointIndex != -1 && pointIndex + 1 < moneyStr.length() - 1) {// 多于一位小数
				_log.error("money point error!" + moneyStr);
				isOk = false;
			}
			// 单笔限额
			if (isOk && (money.compareTo(new BigDecimal("0.1")) < 0 || money.compareTo(new BigDecimal(100)) > 0)) {
				_log.error("money too small or big!" + moneyStr);
				isOk = false;
			}
			// 房间不存在
			if (room == null) {
				_log.error("room is NULL! id:" + roomId);
				isOk = false;
			}
			// 不是投注时间
			if (room.getState() != TigerTimeState.put.getId()) {
				_log.error("not tiger put time!");
				isOk = false;
			}
			if (!isOk) {
				// 添加延迟投注记录
				int qid = -1;
				if (room != null) {
					qid = room.getQid();
				}
				TigerLatePutRecord lateRecord = new TigerLatePutRecord(roomId, qid, userId, txId, optId, money, type);
				tigerService.saveLateRecord(lateRecord);
				// 响应客户端
				handler.sendNoticeToUser(userId, Notice.PUT_MONEY_WILL_RETURN);
				return;
			}
		} else {// 私钥登录投注
			int pointIndex = moneyStr.indexOf(".");
			if (pointIndex != -1 && pointIndex + 1 < moneyStr.length() - 1) {// 多于一位小数
				_log.error("money point error!" + moneyStr);
				handler.sendNoticeToUser(userId, Notice.PUT_MONEY_ERROR);
				return;
			}
			// 单笔限额
			if (money.compareTo(new BigDecimal("0.1")) < 0 || money.compareTo(new BigDecimal(100)) > 0) {
				_log.error("money too small or big!" + moneyStr);
				handler.sendNoticeToUser(userId, Notice.PUT_MONEY_ERROR);
				return;
			}
			if (room == null) {
				_log.error("room is NULL! roomId:" + roomId);
				handler.sendNoticeToUser(userId, Notice.ROOM_NOT_FOUND);
				return;
			}
			if (room.getType() != 0) {// 不是EOS房间
				_log.error("roomId error! Not EOS room! roomId:" + roomId);
				handler.sendNoticeToUser(userId, Notice.ROOM_NOT_FOUND);
				return;
			}
			if (room.getState() != TigerTimeState.put.getId()) {// 不是投注时间
				_log.error("not tiger put time!");
				handler.sendNoticeToUser(userId, Notice.NOT_PUT_TIME);
				return;
			}
			// 奖池最大赔付判断
			String poolBalanceInCache = MainData.poolBalance.get(Constants.TIGER_TYPE);
			if (poolBalanceInCache != null) {
				String poolBalanceStr = CoinUtil.getFromQuantity(poolBalanceInCache);
				// 奖池余额
				BigDecimal poolBalance = new BigDecimal(poolBalanceStr);
				// 本房间最大赔付金额
				BigDecimal maxPeiMoney = room.getMaxPeiMoney();
				// 本次投注中奖金额
				BigDecimal thisPeiMoney = money.multiply(new BigDecimal(opt.getPei()).divide(new BigDecimal(1000))).add(money);
				if (thisPeiMoney.add(maxPeiMoney).multiply(new BigDecimal(5)).compareTo(poolBalance) > 0) {
					_log.error("pool balance not enough, forbid this put! putMoney:" + moneyStr + ", poolBalance:" + poolBalance);
					handler.sendNoticeToUser(userId, Notice.PUT_MONEY_ERROR);
					return;
				}
			}
			int qid = room.getQid();
			account = accountService.getAccount(userId);
			if (account == null) {
				_log.error("account not found! userId: " + userId);
				handler.sendNoticeToUser(userId, Notice.USER_NOT_FOUND);
				return;
			}
			// 私钥
			String privateKey = redisService.getCache(userId).toString();
			if (privateKey == null) {
				_log.error("can't find private key from redis! username: " + userId);
				handler.sendNoticeToUser(userId, Notice.USER_NOT_FOUND);
				return;
			}
			String quantity = account.getCoreLiquidBalance();
			String balance = CoinUtil.getFromQuantity(quantity);
			if (new BigDecimal(balance).compareTo(money) < 0) {
				_log.error("用户余额不足！");
				handler.sendNoticeToUser(userId, Notice.BALANCE_NOT_ENOUGH);
				return;
			}
			Map<String, String> remark = new HashMap<>();
			remark.put("gameType", Constants.TIGER_TYPE);
			remark.put("userName", userId);

			txId = accountService.minusBalanceToTiger(userId, money, privateKey,
					JSONObject.fromObject(remark).toString());
			if (txId == null) {
				_log.error("投注扣款失败！ userId: " + userId + ", money: " + moneyStr);
				handler.sendNoticeToUser(userId, Notice.PUT_ERROR);
				return;
			} else {// 扣款成功
				_log.debug("用户私钥投注，扣款成功！ txId: " + txId);
				// 流水记录
				EosFlow ef = new EosFlow(userId, Constants.GAME_TYPE_TIGER, 0, 0, roomId, Long.valueOf(qid), money,
						null);
				try {
					gameService.insertEosFlow(ef);
				} catch (Exception e) {
					_log.error("EOS流水记录插入失败：" + ef);
				}
			}
		}

		// 修改该房间的对应投注项金额
		switch (opt) {
		case long0:
			room.setPut_long0(room.getPut_long0().add(money));
			break;
		case long1:
			room.setPut_long1(room.getPut_long1().add(money));
			break;
		case long2:
			room.setPut_long2(room.getPut_long2().add(money));
			break;
		case hu0:
			room.setPut_hu0(room.getPut_hu0().add(money));
			break;
		case hu1:
			room.setPut_hu1(room.getPut_hu1().add(money));
			break;
		case hu2:
			room.setPut_hu2(room.getPut_hu2().add(money));
			break;
		case he:
			room.setPut_he(room.getPut_he().add(money));
			break;
		default:
			break;
		}
		// 添加投注记录
		int qid = room.getQid();
		TigerPutRecord record = new TigerPutRecord(roomId, qid, userId, txId, optId, money, type);
		tigerService.saveRecord(record);

		// 异步响应客户端
		threadPoolTaskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				// 响应客户端，发给投注人，投注人投注金额变动
				Map<String, Object> map1 = new HashMap<>();
				map1.put("roomId", roomId);
				map1.put("opt", optId);
				map1.put("total", room.getMoneyByOpt(opt));
				map1.put("self", tigerService.getMyPutMoney(userId, roomId, qid, optId));
				String json1 = JsonUtil.buildJson(MsgCode.TIGER_PUT, map1);
				handler.sendMessageToUser(userId, new TextMessage(json1));
				// 响应客户端，发给此房间所有人，此投注项总金额变动
				Map<String, Object> map2 = new HashMap<>();
				map2.put("roomId", roomId);
				map2.put("opt", optId);
				map2.put("total", room.getMoneyByOpt(opt));
				map2.put("pei", opt.getPei());
				String json2 = JsonUtil.buildJson(MsgCode.TIGER_ROOM_MONEY_CHANGE, map2);
				handler.broadcastToSome(room.getHeads(), new TextMessage(json2));
				// 响应客户端，发给此房间所有人，投注直播
				Map<String, Object> map3 = new HashMap<>();
				map3.put("roomId", roomId);
				map3.put("type", 0);// 0表示投注
				map3.put("userId", userId);
				map3.put("qid", qid);
				map3.put("money", money.toPlainString());
				map3.put("time", record.getPutTimeSpt());
				String json3 = JsonUtil.buildJson(MsgCode.TIGER_PUT_SHOW, map3);
				handler.broadcastToSome(room.getHeads(), new TextMessage(json3));
				// 响应客户端，奖池金额
				String poolBalance = accountService.getBalance(Constants.account_tiger);
				if (poolBalance != null) {
					MainData.poolBalance.put(Constants.TIGER_TYPE, poolBalance);
					Map<String, Object> map4 = new HashMap<>();
					map4.put("balance", poolBalance);
					String json4 = JsonUtil.buildJson(MsgCode.TIGER_POOL_BALANCE, map4);
					handler.broadcastToSome(room.getHeads(), new TextMessage(json4));
				}
				// 响应客户端，用户余额
				if (type == 1) {
					String myBalance = accountService.getBalance(userId);
					if (myBalance != null) {
						Map<String, Object> map5 = new HashMap<>();
						map5.put("balance", myBalance);
						String json5 = JsonUtil.buildJson(MsgCode.TIGER_USER_BALANCE, map5);
						handler.sendMessageToUser(userId, new TextMessage(json5));
					}
				}
			}
		});
	}
}