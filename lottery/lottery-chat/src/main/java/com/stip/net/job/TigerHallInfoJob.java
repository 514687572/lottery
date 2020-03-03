package com.stip.net.job;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.socket.TextMessage;

import com.lottery.net.utils.Constants;
import com.lottery.net.utils.JsonUtil;
import com.stip.net.entity.tiger.TigerRoom;
import com.stip.net.imessage.IMWebSocketHandler;
import com.stip.net.main.MainData;
import com.stip.net.main.MsgCode;

import net.sf.json.JSONObject;

/**
 * 龙虎斗，房间大厅信息
 */
public class TigerHallInfoJob extends AbstractJob {

	@Override
	public void runImpl(IMWebSocketHandler handler, String userId, JSONObject params, Object... object) {
		// 龙虎斗EOS奖池余额
		String balance = MainData.poolBalance.get(Constants.TIGER_TYPE);
		// 龙虎斗积分奖池余额
		BigDecimal score = MainData.scorePoolBalance.get(Constants.TIGER_TYPE);
		// 给客户端响应
		Map<String, Object> map = new HashMap<>();
		map.put("balance", balance);
		map.put("score", score);
		List<Map<String, Object>> rooms = new ArrayList<>();
		map.put("rooms", rooms);
		for (TigerRoom room : MainData.tigerRooms) {
			Map<String, Object> mmp = new HashMap<>();
			mmp.put("id", room.getId());
			mmp.put("type", room.getType());
			mmp.put("num", room.getHeads().size());
			mmp.put("road", room.getRoads());
			rooms.add(mmp);
		}
		String json = JsonUtil.buildJson(MsgCode.TIGER_HALL_INFO, map);
		handler.sendMessageToUser(userId, new TextMessage(json));
	}
}