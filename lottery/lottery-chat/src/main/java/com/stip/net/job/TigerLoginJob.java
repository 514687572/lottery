package com.stip.net.job;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.lottery.net.utils.JsonUtil;
import com.stip.net.entity.tiger.TigerRoom;
import com.stip.net.imessage.IMWebSocketHandler;
import com.stip.net.main.MainData;
import com.stip.net.main.MsgCode;
import com.stip.net.main.Notice;
import com.stip.net.service.AccountService;
import com.stip.net.service.LotteryUserService;
import com.stip.net.service.RedisService;
import com.stip.net.service.UserService;

import io.eblock.eos4j.api.vo.account.Account;
import io.eblock.eos4j.api.vo.account.CpuLimit;
import net.sf.json.JSONObject;

/**
 * 玩家登录（登录顶掉游客的userId）
 */
public class TigerLoginJob extends AbstractJob {

	@Override
	public void runImpl(IMWebSocketHandler handler, String userId, JSONObject params, Object... object) {
		AccountService accountService = (AccountService) object[0];
		UserService userService = (UserService) object[1];
		LotteryUserService lotteryUserService = (LotteryUserService) object[2];
		RedisService redisService = (RedisService) object[3];
		// 0为普通登录，1为私钥登录
		int type = params.getInt("type");
		String loginUserId = null;// 登录用户的真实userId
		String publicKey = null;
		String privateKey = null;
		String userCode = null;// 邀请码
		if (type == 0) {
			publicKey = params.getString("publicKey");
			loginUserId = params.getString("loginUserId");
		} else {// 私钥登录
			privateKey = params.getString("privateKey");
			publicKey = accountService.getPublicKey(privateKey);
			if (publicKey == null) {
				handler.sendNoticeToUser(userId, Notice.USER_NOT_FOUND);
				return;
			}
			loginUserId = accountService.getUserName(publicKey);
			if (loginUserId == null) {
				handler.sendNoticeToUser(userId, Notice.USER_NOT_FOUND);
				return;
			}
			// 将用户私钥存入redis缓存
			redisService.setCache(loginUserId, privateKey);
		}
		Account account = accountService.getAccount(loginUserId);
		if (account == null) {
			handler.sendNoticeToUser(userId, Notice.USER_NOT_FOUND);
			return;
		}
		// 用户余额
		String balance = account.getCoreLiquidBalance();
		CpuLimit cpu = account.getCpuLimit();

		// 新用户 绑定关系
		if (params.get("userCode") != null && !"".equals(params.getString("userCode"))) {
			userCode = params.getString("userCode");
		}
		boolean b = userService.addUserInfo(loginUserId, publicKey);
		if (b && userCode != null) {
			lotteryUserService.updateRelatioin(loginUserId, userCode);
		}

		Map<String, WebSocketSession> sessionMap = IMWebSocketHandler.userSocketSessionMap;
		WebSocketSession session = sessionMap.get(userId);
		if (session != null) {
			sessionMap.remove(userId);
			session.getAttributes().put("userId", loginUserId);
			sessionMap.put(loginUserId, session);
		}
		// 如果游客在龙虎斗房间，那么替换userId
		TigerRoom room = MainData.removeUserInRoom(userId);
		if (room != null) {
			Set<String> heads = room.getHeads();
			synchronized (heads) {
				heads.add(loginUserId);
			}
		}
		// 给客户端响应
		Map<String, Object> map = new HashMap<>();
		map.put("type", type);
		map.put("loginUserId", loginUserId);
		map.put("publicKey", publicKey);
		map.put("cpu", cpu);
		String json = JsonUtil.buildJson(MsgCode.TIGER_LOGIN, map);
		handler.sendMessageToUser(loginUserId, new TextMessage(json));
		// 响应客户端，用户余额
		Map<String, Object> map2 = new HashMap<>();
		map2.put("balance", balance);
		map2.put("top", accountService.getTopBalance(loginUserId));
		String json2 = JsonUtil.buildJson(MsgCode.TIGER_USER_BALANCE, map2);
		handler.sendMessageToUser(loginUserId, new TextMessage(json2));
	}
}