package com.stip.net.imessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PingMessage;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.AbstractWebSocketSession;

import com.lottery.net.utils.Constants;
import com.lottery.net.utils.JsonUtil;
import com.stip.net.job.AbstractJob;
import com.stip.net.job.ScoreLoginJob;
import com.stip.net.job.TigerHallInfoJob;
import com.stip.net.job.TigerLoginJob;
import com.stip.net.job.TigerPutJob;
import com.stip.net.job.TigerQueryMyPutJob;
import com.stip.net.job.TigerRoomEnterJob;
import com.stip.net.job.TigerRoomInfoJob;
import com.stip.net.job.TigerRoomLeaveJob;
import com.stip.net.main.MainData;
import com.stip.net.main.MsgCode;
import com.stip.net.service.AccountService;
import com.stip.net.service.GameService;
import com.stip.net.service.LotteryUserService;
import com.stip.net.service.RedisService;
import com.stip.net.service.TigerService;
import com.stip.net.service.UserService;

import net.sf.json.JSONObject;

/**
 * Socket处理器
 * 
 * @author cja
 * @Date 20181110
 */
@Component
public class IMWebSocketHandler implements WebSocketHandler {
	protected final Logger _log = LoggerFactory.getLogger(this.getClass());
	public static final Map<String, WebSocketSession> userSocketSessionMap;
	@Resource
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	@Autowired
	public UserService userService;
	@Autowired
	private TigerService tigerService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private LotteryUserService lotteryUserService;
	@Autowired
	private GameService gameService;
	@Autowired
    private RedisService redisService;

	static {
		userSocketSessionMap = new ConcurrentHashMap<String, WebSocketSession>();
	}

	/**
	 * 建立连接后
	 */
	public void afterConnectionEstablished(WebSocketSession session) {
		String uid = session.getAttributes().get("userId").toString();
		if (userSocketSessionMap.get(uid) == null) {
			userSocketSessionMap.put(uid, session);
		} else {
			userSocketSessionMap.remove(uid);
			userSocketSessionMap.put(uid, session);
		}
		Object gameType = session.getAttributes().get("gameType");
		if(gameType==null){
			return;
		}
		switch (gameType.toString()) {
		case Constants.TIGER_TYPE: {
			// 推送龙虎斗奖池余额
			String balance = MainData.poolBalance.get(Constants.TIGER_TYPE);
			if (balance == null) {
				balance = accountService.getBalance(Constants.account_tiger);
				MainData.poolBalance.put(Constants.TIGER_TYPE, balance);
			}
			if (balance != null) {
				Map<String, Object> map = new HashMap<>();
				map.put("balance", balance);
				String tJson = JsonUtil.buildJson(MsgCode.TIGER_POOL_BALANCE, map);
				sendMessageToUser(uid, new TextMessage(tJson));
			}
		}
			break;
		case Constants.DICE_TYPE: {
			// 推送骰子奖池余额
			String balance = MainData.poolBalance.get(Constants.DICE_TYPE);
			if (balance == null) {
				balance = accountService.getBalance(Constants.account_dice);
				MainData.poolBalance.put(Constants.DICE_TYPE, balance);
			}
			if (balance != null) {
				Map<String, Object> map = new HashMap<>();
				map.put("balance", balance);
				String tJson = JsonUtil.buildJson(MsgCode.DICE_POOL_BALANCE, map);
				sendMessageToUser(uid, new TextMessage(tJson));
			}
		}
			break;
		default:
			break;
		}
	}

	/**
	 * 消息处理，在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理
	 */
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		if (message instanceof TextMessage) {
			if (!message.isLast()) {
				return;
			}
			if(message.getPayload()!=null&&"ping".equals(message.getPayload().toString())) {
				PingMessage pingMessage=new PingMessage();
				handlePingMessage(session,pingMessage);
				
				return;
			}

			// 客户端上行消息
			JSONObject up = JSONObject.fromObject(message.getPayload());
//			System.out.println("客户端上行：" + up);
			int code = up.getInt("code");
			JSONObject upData = up.getJSONObject("data");
			String userId = "" + session.getAttributes().get("userId");

			AbstractJob job = null;
			switch (code) {
			case 1: {// 测试消息
				System.out.println("上行消息：" + upData);
				Map<String, Object> map = new HashMap<>();
				map.put("下行消息1", "萨芬地方");
				map.put("下行2", "阿狸萨芬的");
				String json = JsonUtil.buildJson(1, map);
				sendMessageToUser(userId, new TextMessage(json));
			}
				break;
			case MsgCode.TIGER_LOGIN: {// 龙虎斗，用户登录
				job = new TigerLoginJob();
				job.runImpl(this, userId, upData, accountService, userService, lotteryUserService,redisService);
			}
				break;
			case MsgCode.TIGER_ROOM_ENTER: {// 龙虎斗，进入房间
				job = new TigerRoomEnterJob();
				job.runImpl(this, userId, upData, tigerService);
			}
				break;
			case MsgCode.TIGER_ROOM_LEAVE: {// 龙虎斗，离开房间
				job = new TigerRoomLeaveJob();
				job.runImpl(this, userId, upData);
			}
				break;
			case MsgCode.TIGER_PUT: {// 龙虎斗，投注
				job = new TigerPutJob();
				job.runImpl(this, userId, upData, tigerService, accountService, threadPoolTaskExecutor, gameService, redisService);
			}
				break;
			case MsgCode.TIGER_ROOM_INFO: {// 龙虎斗，投注
				job = new TigerRoomInfoJob();
				job.runImpl(this, userId, upData, tigerService);
			}
				break;
			case MsgCode.TIGER_MY_PUT_QUERY: {// 龙虎斗，我的投注分页查询
				job = new TigerQueryMyPutJob();
				job.runImpl(this, userId, upData, tigerService);
			}
				break;
			case MsgCode.TIGER_HALL_INFO: {// 龙虎斗大厅
				job = new TigerHallInfoJob();
				job.runImpl(this, userId, upData);
			}
			case MsgCode.SCORE_LOGIN: {// 积分登录处理
				job = new ScoreLoginJob();
				job.runImpl(this, userId, upData);
			}
				break;
			default: {
//					Message msg = new Gson().fromJson(message.getPayload().toString(), Message.class);
//					msg.setDate(new Date());
//					sendMessageToUser(msg.getTo(), new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
			}
				break;
			}
		}else if (message instanceof PongMessage) {
			handlePongMessage(session, (PongMessage) message);
		}else if (message instanceof PingMessage) {
			handlePingMessage(session, (PingMessage) message);
		}else {
			throw new IllegalStateException("Unexpected WebSocket message type: " + message);
		}
	}

	/**
	 * 消息传输错误处理
	 */
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (!session.isOpen()) {
			Iterator<Entry<String, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
			// 移除Socket会话
			while (it.hasNext()) {
				Entry<String, WebSocketSession> entry = it.next();
				if (entry.getValue().getId().equals(session.getId())) {
					userSocketSessionMap.remove(entry.getKey());
					_log.info("Socket会话已经移除:用户ID" + entry.getKey());
					break;
				}
			}
		}
	}

	/**
	 * 关闭连接后
	 */
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)  {
		_log.info("Websocket:" + session.getId() + "已经关闭"+closeStatus);
		
		Iterator<Entry<String, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		// 移除Socket会话
		while (it.hasNext()) {
			Entry<String, WebSocketSession> entry = it.next();
			if (entry.getValue().getId().equals(session.getId())) {
				userSocketSessionMap.remove(entry.getKey());
				_log.info("Socket会话已经移除:用户ID" + entry.getKey());
				break;
			}
		}
		// 龙虎斗房间移除玩家
		String userId = "" + session.getAttributes().get("userId");
		MainData.removeUserInRoom(userId);
	}

	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 广播给某个游戏的所有用户
	 */
	public synchronized void broadcastByGame(final TextMessage message, final String gameStr) {
		Iterator<Entry<String, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		while (it.hasNext()) {
			final Entry<String, WebSocketSession> entry = it.next();
			String key = entry.getKey();
			try {
				WebSocketSession session = entry.getValue();
				if (!gameStr.equals(session.getAttributes().get("gameType"))) {
					continue;
				}
				if (session.isOpen()) {
					synchronized (session) {
						if (session.isOpen()) {
							WebSocketSession value = userSocketSessionMap.get(key);
							if (null != value) {
								session.sendMessage(message);
							}
						}
					}
				}
			} catch (IOException e) {
				userSocketSessionMap.remove(key);
			}
		}
	}

	/**
	 * 给某个用户发送消息
	 */
	public void sendMessageToUser(String userId, TextMessage message) {
		final AbstractWebSocketSession<?> session = (AbstractWebSocketSession<?>) userSocketSessionMap.get(userId);
		if (session != null && session.isOpen()) {
			synchronized (session) {
				try {
					session.sendMessage(message);
				} catch (IOException e) {
					userSocketSessionMap.remove(userId);
				}
			}
		}
	}

	/**
	 * 给某些用户发送消息
	 */
	public synchronized void broadcastToSome(Collection<String> heads, TextMessage message) {
		List<String> users = new ArrayList<>(heads);
		for (String userId : users) {
			final AbstractWebSocketSession<?> session = (AbstractWebSocketSession<?>) userSocketSessionMap.get(userId);
			if (session != null && session.isOpen()) {
				synchronized (session) {
					try {
						session.sendMessage(message);
					} catch (IOException e) {
						userSocketSessionMap.remove(userId);
					}
				}
			}
		}
	}

	/**
	 * 错误提示
	 */
	public void sendNoticeToUser(String userId, int noticeCode) {
		Map<String, Object> map = new HashMap<>();
		map.put("err", noticeCode);
		String json = JsonUtil.buildJson(MsgCode.NOTICE, map);
		sendMessageToUser(userId, new TextMessage(json));
	}

	public void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
		_log.info("pong----------------------------------");
	}

	public void handlePingMessage(final WebSocketSession session, PingMessage message) throws Exception {
		PongMessage pongMessage=new PongMessage();
		session.sendMessage(pongMessage);
		_log.info("ping----------------------------------");
	}
}