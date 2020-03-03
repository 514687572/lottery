package com.stip.net.job;

import java.util.HashMap;
import java.util.Set;

import org.springframework.web.socket.TextMessage;

import com.lottery.net.utils.JsonUtil;
import com.stip.net.entity.tiger.TigerRoom;
import com.stip.net.imessage.IMWebSocketHandler;
import com.stip.net.main.MainData;
import com.stip.net.main.MsgCode;
import com.stip.net.main.Notice;

import net.sf.json.JSONObject;

/**
 * 龙虎斗，玩家离开房间
 */
public class TigerRoomLeaveJob extends AbstractJob {

	@Override
	public void runImpl(IMWebSocketHandler handler, String userId, JSONObject params, Object... object) {
		int roomId = params.getInt("roomId");// 房间id
		TigerRoom room = MainData.getRoomById(roomId);
		if (room == null) {
			_log.error("room is NULL! id:" + roomId);
			handler.sendNoticeToUser(userId, Notice.ROOM_NOT_FOUND);
			return;
		}
		// 房间移除此用户
		Set<String> heads = room.getHeads();
		synchronized (heads) {
			heads.remove(userId);
		}
		// 给客户端响应
		String json = JsonUtil.buildJson(MsgCode.TIGER_ROOM_LEAVE, new HashMap<>());
		handler.sendMessageToUser(userId, new TextMessage(json));
	}
}