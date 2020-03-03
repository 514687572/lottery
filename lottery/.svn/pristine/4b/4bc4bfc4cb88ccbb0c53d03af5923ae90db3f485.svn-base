package com.stip.net.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.socket.TextMessage;

import com.lottery.net.utils.Constants;
import com.lottery.net.utils.JsonUtil;
import com.stip.net.entity.LotteryUserScore;
import com.stip.net.entity.UserTransactionScore;
import com.stip.net.entity.tiger.TigerOption;
import com.stip.net.entity.tiger.TigerPutRecord2;
import com.stip.net.entity.tiger.TigerRoom;
import com.stip.net.entity.tiger.TigerTimeState;
import com.stip.net.imessage.IMWebSocketHandler;
import com.stip.net.kafka.utils.PublicMethod;
import com.stip.net.main.MainData;
import com.stip.net.main.MsgCode;
import com.stip.net.service.GameService;
import com.stip.net.service.LotteryUserService;
import com.stip.net.service.TigerService;
import com.stip.net.service.UserTransactionScoreService;

@Scope("request")
@RequestMapping("/tiger")
@RestController
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, Exception.class })
public class TigerController {
	private final Logger _log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IMWebSocketHandler handler;
	@Autowired
	private GameService gameService;
	@Autowired
	private TigerService tigerService;
	@Autowired
	private SessionLocaleResolver localeResolver;
	@Autowired
	private LotteryUserService lotteryUserService;
	@Autowired
	private UserTransactionScoreService userTransactionScoreRecordsService;

	/**
	 * 积分投注
	 *
	 * @param roomId
	 *            房间号
	 * @param optId
	 *            投注项，参见TigerOption
	 * @param termnumber
	 *            期号
	 * @param forecast
	 *            预测数字
	 * @param bettingScore
	 *            投注金额
	 * @param betTime
	 *            下注时间
	 */
	@PostMapping("/put")
	public Map<String, Object> put(@RequestParam int roomId, @RequestParam int optId, @RequestParam String money,
			HttpServletRequest request) {
		Locale locale = localeResolver.resolveLocale(request);
		Map<String, Object> response = new HashMap<String, Object>(1);

		// 验证登录
		LotteryUserScore userInSession = (LotteryUserScore) request.getSession()
				.getAttribute(Constants.USER_IN_SESSION);
		if (userInSession == null) {
			response.put("msg", gameService.getMessage("notLogin", locale));
			return response;
		}
		// 投注项
		TigerOption opt = TigerOption.getEnumById(optId);
		if (opt == null) {
			_log.error("tiger option not found! optId:" + optId);
			response.put("msg", gameService.getMessage("dice112", locale));
			return response;
		}
		// 金额合法性判断
		int pointIndex = money.indexOf(".");
		if (pointIndex != -1 && pointIndex + 1 < money.length() - 1) {// 多于一位小数
			_log.error("money point error:" + money);
			response.put("msg", gameService.getMessage("dice111", locale));
			return response;
		}
		BigDecimal score = new BigDecimal(money);
		// 单笔限额
		if (score.compareTo(new BigDecimal("0.1")) < 0 || score.compareTo(new BigDecimal(100)) > 0) {
			_log.error("score too big or too small::" + money);
			response.put("msg", gameService.getMessage("dice111", locale));
			return response;
		}
		TigerRoom room = MainData.getRoomById(roomId);
		if (room == null) {
			_log.error("room not found! roomId:" + roomId);
			response.put("msg", gameService.getMessage("TODO,房间不存在", locale));
			return response;
		}
		if (room.getType() != 1) {// 不是积分房间
			_log.error("roomId error! Not Score room! roomId:" + roomId);
			response.put("msg", gameService.getMessage("TODO,房间错误", locale));
			return response;
		}
		if (room.getState() != TigerTimeState.put.getId()) {// 不是投注时间
			_log.error("not tiger put time!");
			response.put("msg", gameService.getMessage("TODO,非投注时间", locale));
			return response;
		}
		// 奖池最大赔付判断
		BigDecimal poolBalance = MainData.scorePoolBalance.get(Constants.TIGER_TYPE);
		// 本房间最大赔付金额
		BigDecimal maxPeiScore = room.getMaxPeiMoney();
		// 本次投注中奖金额
		BigDecimal thisPeiScore = score.multiply(new BigDecimal(opt.getPei()).divide(new BigDecimal(1000))).add(score);
		if (thisPeiScore.add(maxPeiScore).multiply(new BigDecimal(5)).compareTo(poolBalance) > 0) {
			_log.error("score pool balance not enough, cancel this put!");
			response.put("msg", gameService.getMessage("dice111", locale));
			return response;
		}
		long uid = userInSession.getId();
		String userId = userInSession.getUserId();
		// 用户信息
		LotteryUserScore lotteryUserScore = lotteryUserService.getLotteryUserScoreId(userInSession.getId());
		if (lotteryUserScore == null) {
			response.put("msg", gameService.getMessage("errorUserInfo", locale));
			return response;
		}
		// 判断余额
		if (lotteryUserScore.getScore().compareTo(score) < 0) {
			response.put("msg", gameService.getMessage("dice113", locale));
			return response;
		}
		// 扣除积分
		boolean success = lotteryUserService.minusScoreToTiger(lotteryUserScore.getId(), score);
		if (!success) {
			response.put("err", gameService.getMessage("dice115", locale));
			return response;
		}
		// 修改该房间的对应投注项金额
		switch (opt) {
		case long0:
			room.setPut_long0(room.getPut_long0().add(score));
			break;
		case long1:
			room.setPut_long1(room.getPut_long1().add(score));
			break;
		case long2:
			room.setPut_long2(room.getPut_long2().add(score));
			break;
		case hu0:
			room.setPut_hu0(room.getPut_hu0().add(score));
			break;
		case hu1:
			room.setPut_hu1(room.getPut_hu1().add(score));
			break;
		case hu2:
			room.setPut_hu2(room.getPut_hu2().add(score));
			break;
		case he:
			room.setPut_he(room.getPut_he().add(score));
			break;
		default:
			break;
		}
		// 创建投注记录，并存数据库
		int qid = room.getQid();
		TigerPutRecord2 record = new TigerPutRecord2(roomId, qid, uid, userId, optId, score);
		tigerService.saveRecord2(record);
		// 投注积分流水
		UserTransactionScore flow = new UserTransactionScore(uid, Constants.GAME_TYPE_TIGER, roomId, Long.valueOf(qid), "0", "0",
				score, null);
		PublicMethod.insertTransactionScore(flow, userTransactionScoreRecordsService, _log);
		// 新用户奖励
		PublicMethod.newUserScore(Constants.TIGER_TYPE, lotteryUserScore, lotteryUserService, userTransactionScoreRecordsService, "3", roomId,
				Long.valueOf(qid), handler, _log);
		// 发送佣金
		PublicMethod.invitationAwardScoreUser(Constants.TIGER_TYPE, score, lotteryUserScore, lotteryUserService,
				userTransactionScoreRecordsService, Constants.GAME_TYPE_TIGER, roomId, Long.valueOf(qid), handler, _log);
		// 响应用户余额
		PublicMethod.sendScoreUserBalance(uid, Constants.TIGER_TYPE, lotteryUserService, handler);
		// 广播奖池余额
		PublicMethod.sendPoolBalance(Constants.TIGER_TYPE, handler);

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
		map3.put("money", score.toPlainString());
		map3.put("time", System.currentTimeMillis());
		String json3 = JsonUtil.buildJson(MsgCode.TIGER_PUT_SHOW, map3);
		handler.broadcastToSome(room.getHeads(), new TextMessage(json3));
		// HTTP响应，下注成功
		response.put("msg", gameService.getMessage("dice116", locale));
		return response;
	}

	@GetMapping("/my")
	public Map<String, Object> queryMyRecords(HttpServletRequest request) {
		Locale locale = localeResolver.resolveLocale(request);
		Map<String, Object> response = new HashMap<String, Object>(1);

		// 验证登录
		LotteryUserScore userInSession = (LotteryUserScore) request.getSession()
				.getAttribute(Constants.USER_IN_SESSION);
		if (userInSession == null) {
			response.put("msg", gameService.getMessage("notLogin", locale));
			return response;
		}

		long time = 0;
		String ts = request.getParameter("time");
		if (ts != null) {
			time = Long.valueOf(ts);
		}
		long uid = userInSession.getId();
		int pageSize = 10;
		List<TigerPutRecord2> records = tigerService.getMyRecord2(uid, pageSize, time);
		// 给客户端响应
		List<Map<String, Object>> list = new ArrayList<>();
		for (TigerPutRecord2 record : records) {
			Map<String, Object> map = new HashMap<>();
			map.put("roomId", record.getRoomId());
			map.put("qid", record.getQid());
			map.put("time", record.getOpenTimeSpt());
			map.put("opt", record.getOpt());
			map.put("put", record.getPutMoney());
			map.put("gain", record.getGainMoney());
			list.add(map);
		}
		response.put("list", list);
		return response;
	}
}