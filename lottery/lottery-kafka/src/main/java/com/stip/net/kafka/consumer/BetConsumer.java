package com.stip.net.kafka.consumer;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;

import com.google.gson.GsonBuilder;
import com.lottery.net.utils.CoinUtil;
import com.lottery.net.utils.Constants;
import com.lottery.net.utils.DBCache;
import com.stip.net.entity.DBDiceBetting;
import com.stip.net.entity.LotteryConfirm;
import com.stip.net.entity.LotteryRecordGroup;
import com.stip.net.entity.UserBetRecords;
import com.stip.net.entity.tiger.TigerConfirm;
import com.stip.net.entity.tiger.TigerLatePutRecord;
import com.stip.net.entity.tiger.TigerPutRecord;
import com.stip.net.entity.tiger.TigerPutRecordUpdater;
import com.stip.net.imessage.IMWebSocketHandler;
import com.stip.net.kafka.dto.SampleMessage;
import com.stip.net.kafka.utils.PublicMethod;
import com.stip.net.main.MainData;
import com.stip.net.service.AccountService;
import com.stip.net.service.ConfirmService;
import com.stip.net.service.GameService;
import com.stip.net.service.LotteryUserService;
import com.stip.net.service.RedisService;
import com.stip.net.service.TigerService;
import com.stip.net.service.UserBetService;
import com.stip.net.vo.Message;

import net.sf.json.JSONObject;

/**
 *
 * @Title:下注确认消息
 * @date：2018年11月14日-下午1:33:32
 * @author：cja
 *
 */
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, Exception.class})
@Component
public class BetConsumer implements MessageListener<String, SampleMessage> {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserBetService userBetService;
	@Autowired
	private ConfirmService confirmService;
	@Autowired
	private TigerService tigerService;
	@Autowired
	private IMWebSocketHandler handler;
	@Autowired
	private RedisService redisService;
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	@Autowired
	private LotteryUserService lotteryUserService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private GameService gameService;

	public void onMessage(ConsumerRecord<String, SampleMessage> data) {
		JSONObject jsonObject = JSONObject.fromObject(data.value());
		String transactionId = jsonObject.getString("transaction_id");
		String quantity = jsonObject.getString("quantity");
		long block_time = jsonObject.getLong("block_time");
		JSONObject memo = JSONObject.fromObject(jsonObject.get("memo"));
		String userName=memo.getString("userName");
		String gameType=memo.getString("gameType");
		BigDecimal realMoney =null;
		
		if (!quantity.endsWith("EOS")) {
			logger.error("收到错误消息" + jsonObject.toString());
			return;
		}else {
			logger.error("收到消息" + jsonObject.toString());
			realMoney = new BigDecimal(CoinUtil.getFromQuantity(quantity));
		}

		
		if (StringUtils.isEmpty(transactionId)) {
			logger.error("更新下注状态消息错误" + jsonObject.toString());
			return;
		}
		
		if(Constants.LOTTERY_TYPE.equals(gameType)) {// 大乐透
			String sid=memo.getString("id");// 大乐透特有

			UserBetRecords record = new UserBetRecords();
			record.setLotteryStatus("1");
			record.setTransactionId(transactionId);
			int count=userBetService.updateByBetRecordsExample(record, sid);
			
//			List<UserBetRecords> list=userBetService.getUserBetHistry(1,userName , 1, sid);
			
			if (count > 0) {
				LotteryRecordGroup group = userBetService.getRecordsBySid(sid);
				UserBetRecords main = group.getMain();
				List<UserBetRecords> list = group.getList();
				if (main != null && list.size() > 0) {
					double realDouble = realMoney.doubleValue();
					double bigDouble = main.getNoteMoney();
					if (realDouble - bigDouble < -0.001 || realDouble - bigDouble > 0.001) {
						logger.error("投注金额与BetConsumer金额不一致！");
						bigDouble  = realDouble;
						double smallDouble = bigDouble / list.size();
						for (UserBetRecords r : list) {
							r.setNoteMoney(smallDouble);
							userBetService.updateByPrimaryKey(r);
						}
						main.setNoteMoney(bigDouble);
						userBetService.updateByPrimaryKey(main);
					}

					BigDecimal bigNoteMoney = new BigDecimal(bigDouble);
					double dvalue = bigNoteMoney.setScale(4, BigDecimal.ROUND_HALF_DOWN).doubleValue();
					redisService.setNx(main.getBetNum().toString(), "0", 60 * 2 * 1000l);
					double newLong = Double.parseDouble(
							redisService.getCache(main.getBetNum().toString()).toString()) + dvalue;
					redisService.setCache(main.getBetNum().toString(), newLong);
					threadPoolTaskExecutor.execute(new Runnable() {
						public void run() {
							sendBetRecords(userName, new DecimalFormat("0.0000").format(dvalue) + "EOS",
									main.getCreateTime(), main.getBetNum().toString());
							// 新用户奖励
							PublicMethod.newUser(userName, lotteryUserService, accountService, logger, null,
									main.getBetNum(), Constants.GAME_TYPE_LOTTERY, handler, gameService);
							// 邀请奖励发放
							PublicMethod.invitationAward(bigNoteMoney, userName, lotteryUserService, accountService, logger, null,
									main.getBetNum(), Constants.GAME_TYPE_LOTTERY, gameService, handler);
						}
					});
				}
			} else {
				LotteryConfirm confirm=new LotteryConfirm();
				confirm.setConfirmId(sid);
				confirm.setGameType(gameType);
				confirm.setTransactionId(transactionId);
				confirm.setUserName(userName);
				
				confirmService.addConfirm(confirm);
			}
		}else if(Constants.DICE_TYPE.equals(gameType)) {// 骰子
			DBDiceBetting dice = DBCache.getInstance().getDiceBettingTransaction_id(transactionId);
			if (dice != null) {  
				if(dice.getBettingEOS() != null){
					// 如果确认金额比投注金额小，以确认金额为准
					if (realMoney.compareTo(new BigDecimal(dice.getBettingEOS())) < 0) {
						dice.setBettingEOS(realMoney+"");
						logger.error("投注金额与BetConsumer金额不一致！");
					}
					dice.setDice_state(1);
				}else{
					logger.error("重复收到扣款确认消息"+jsonObject.toString());
				}
			}else{//扣款消息先到
				DBDiceBetting ddb=new DBDiceBetting();
				ddb.setAccount(userName);
				ddb.setState(3);
				ddb.setDice_state(1);
				ddb.setTransaction_id(transactionId);
				DBCache.getInstance().getDiceBettings().add(ddb);
			}
		} else if (Constants.TIGER_TYPE.equals(gameType)) {// 龙虎斗
			TigerPutRecord record = tigerService.getRecordByTxId(transactionId);
			if (record != null) {
				TigerPutRecordUpdater updater = new TigerPutRecordUpdater();
				updater.setTxId(record.getTxId());
				updater.setTxStatus(1);
				// 如果确认金额比投注金额小，以确认金额为准
				if (record.getPutMoney().compareTo(realMoney) > 0) {
					updater.setPutMoney(realMoney);
					logger.error("投注金额与BetConsumer金额不一致！");
				}
				tigerService.updateRecord(updater);
				threadPoolTaskExecutor.execute(new Runnable() {
					public void run() {
						// 新用户奖励
						PublicMethod.newUser(userName, lotteryUserService, accountService, logger, record.getRoomId(),
								(long) record.getQid(), Constants.GAME_TYPE_TIGER, handler,	gameService);
						// 邀请奖励发放
						PublicMethod.invitationAward(record.getPutMoney(), userName, lotteryUserService, accountService,
								logger, record.getRoomId(), (long) record.getQid(), Constants.GAME_TYPE_TIGER,
								gameService, handler);
					}
				});
			} else {
				TigerLatePutRecord lateRecord = tigerService.getLateRecordByTxId(transactionId);
				if (lateRecord != null) {
					lateRecord.setTxStatus(1);
					// 如果确认金额比投注金额小，以确认金额为准
					if (lateRecord.getPutMoney().compareTo(realMoney) > 0) {
						lateRecord.setPutMoney(realMoney);
					}
					tigerService.updateLateRecord(lateRecord);
				} else {
					// 走到这里，说明这里的消息比客户端投注还快
					List<TigerConfirm> confirms = MainData.tigerConfirms;
					if (!confirms.contains(confirms)) {
						synchronized (confirms) {
							confirms.add(new TigerConfirm(transactionId, userName, realMoney, block_time));
						}
						logger.error("add tiger confirm to cache: " + transactionId);
					}
				}
			}
		}
	}

	/**
	 * 发送用户下注记录
	 */
	public void sendBetRecords(String userName,String count,Date date,String num) {
		Message msg = new Message();
		JSONObject obj=new JSONObject();
		obj.put("userName", userName);
		obj.put("count",count);
		obj.put("date", date);
		obj.put("num", num);
		BigDecimal b = new BigDecimal(redisService.getCache(num).toString());
		obj.put("limit", b.setScale(4,BigDecimal.ROUND_HALF_DOWN).doubleValue());
		msg.setDate(new Date());
		msg.setFrom("bet");
		msg.setFromName("");
		msg.setText(obj.toString());
		String bulder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg);
		handler.broadcastByGame(new TextMessage(bulder), Constants.LOTTERY_TYPE);
	}
}