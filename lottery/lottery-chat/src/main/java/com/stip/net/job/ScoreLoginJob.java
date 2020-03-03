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

import net.sf.json.JSONObject;

/**
 * 玩家登录（登录顶掉游客的userId）
 */
public class ScoreLoginJob extends AbstractJob {

	@Override
	public void runImpl(IMWebSocketHandler handler, String userId, JSONObject params, Object... object) {
		String loginUserId = params.getString("loginUserId");// 积分登录标识，手机号或邮箱
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
		String json = JsonUtil.buildJson(MsgCode.SCORE_LOGIN, map);
		handler.sendMessageToUser(loginUserId, new TextMessage(json));
	}
}