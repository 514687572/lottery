package com.stip.net.kafka.consumer;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import com.google.gson.GsonBuilder;
import com.lottery.net.utils.Constants;
import com.lottery.net.utils.DBCache;
import com.lottery.net.utils.DateUtils;
import com.stip.net.entity.DBDiceBetting;
import com.stip.net.entity.DBDiceBettingScore;
import com.stip.net.entity.LotteryUserScore;
import com.stip.net.entity.UserTransactionScore;
import com.stip.net.imessage.IMWebSocketHandler;
import com.stip.net.kafka.dto.SampleMessage;
import com.stip.net.kafka.utils.PublicMethod;
import com.stip.net.service.LotteryService;
import com.stip.net.service.LotteryUserService;
import com.stip.net.service.RedisService;
import com.stip.net.service.UserTransactionScoreService;
import com.stip.net.vo.Message;

import net.sf.json.JSONObject;

/**
 * 骰子开奖消息
 * 
 * @author SuperdRream
 *
 */
@Component
public class OPenDiceConsumer implements MessageListener<String, SampleMessage> {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private IMWebSocketHandler handler;
	@Autowired
	private RedisService redisService;
	@Autowired
    private LotteryUserService lotteryUserService;
	@Autowired
	private LotteryService lotteryService;
	@Autowired
	private UserTransactionScoreService userTransactionScoreRecordsService;
	public Message msg;

	public void test(JSONObject jsonObject) {
		// 业务逻辑
		JSONObject data = openbetDice(jsonObject);
		msg = new Message();
		msg.setDate(new Date());
		msg.setFrom("diceBlock");
		msg.setFromName("");
		msg.setText(data + "");
		String bulder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg);
		handler.broadcastByGame(new TextMessage(bulder), Constants.DICE_TYPE);
	}

	@Override
	public void onMessage(ConsumerRecord<String, SampleMessage> data) {
		JSONObject jsonObject = JSONObject.fromObject(data.value());
		String block_num = jsonObject.get("block_num").toString();
		int i = Integer.parseInt(block_num);

		Object object = redisService.getCache("block_num_dice");
		if (null != object) {
			int lastNum = Integer.parseInt(object.toString());
			if (i > lastNum) {
				redisService.setCache("block_num_dice", block_num);
			} else {
				return;
			}
		} else {
			redisService.setCache("block_num_dice", block_num);
		}
		// 业务逻辑
		JSONObject datajson = openbetDice(jsonObject);
		msg = new Message();
		msg.setDate(new Date());
		msg.setFrom("diceBlock");
		msg.setFromName("");
		msg.setText(datajson + "");
		String bulder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg);
		handler.broadcastByGame(new TextMessage(bulder), Constants.DICE_TYPE);
	}

	private JSONObject openbetDice(JSONObject jsonObject) {
		// 区块号
		String blocknum = jsonObject.get("block_num").toString();
		long termnumber = Long.parseLong(blocknum) - 10;
		// 区块时间
		JSONObject time = (JSONObject) jsonObject.get("header");
		String date1 = time.getString("timestamp");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		df2.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date = null;
		try {
			date = df2.parse(date1);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			return jsonObject;
		}
		String hash = jsonObject.get("id").toString();
		// 中奖号码
		String prizenum = "";
		for (int a = hash.length() - 1; a >= 0; a--) {
			char hashdata = hash.charAt(a);
			if (Character.isDigit(hashdata)) {
				String to = prizenum;
				prizenum = hashdata + to;
				if (prizenum.length() == 2) {
					break;
				}
			}
		}
		time.put("timestamp", DateUtils.getCurrentTime(date.getTime()));
		List<DBDiceBetting> dicebetting = DBCache.getInstance().getDiceBettingsTerm(termnumber);
		List<DBDiceBettingScore> dBDiceBettingScore = DBCache.getInstance().getDiceBettingsTermScore(termnumber);
		getUserScore(hash, prizenum, date, dBDiceBettingScore);
		getUserEOS(hash, prizenum, date, dicebetting);
		return jsonObject;
	}

	/**
	 * eos用户开奖
	 * 
	 * @param hash
	 * @param prizenum
	 * @param date
	 * @param dBDiceBettingScore
	 */
	private void getUserEOS(String hash, String prizenum, Date date, List<DBDiceBetting> dicebetting) {
		// eos用户
		if (dicebetting != null && dicebetting.size() > 0) {
			for (DBDiceBetting db : dicebetting) {
				db.setHash(hash);// 存入开奖hash值
				db.setPrizenumber(prizenum);
				db.setTime(date);
				switch (db.getType()) {
				case Constants.DICE_GREATER_THAN: {// 大于
					if (getGreaterThan(prizenum, db.getForecast())) {
						// 胜率
						double win = (99 - db.getForecast()) * 0.01;
						getPrize(win, db);
					}
					break;
				}
				case Constants.DICE_LESS_THAN: {// 小于
					if (getLessThan(prizenum, db.getForecast())) {
						// 胜率
						double win = db.getForecast() * 0.01;
						getPrize(win, db);
					}
					break;
				}
				case Constants.DICE_MAX: {// 大
					if (getMax(prizenum, db.getForecast())) {
						// 胜率
						double win = 0.45;
						getPrize(win, db);
					}
					break;
				}
				case Constants.DICE_SMALL: {// 小
					if (getSmall(prizenum, db.getForecast())) {
						// 胜率
						double win = 0.45;
						getPrize(win, db);
					}
					break;
				}
				case Constants.DICE_COUPLET: {// 对子
					if (getCouplet(prizenum, db.getForecast())) {
						// 胜率
						double win = 0.1;
						getPrize(win, db);
					}
					break;
				}
				default:
					break;
				}
			}
		}
	}

	/**
	 * 积分用户开奖
	 * 
	 * @param hash
	 * @param prizenum
	 * @param date
	 * @param dBDiceBettingScore
	 */
	private void getUserScore(String hash, String prizenum, Date date, List<DBDiceBettingScore> dBDiceBettingScore) {
		if (dBDiceBettingScore != null && dBDiceBettingScore.size() > 0) {
			for (DBDiceBettingScore dbs : dBDiceBettingScore) {
				dbs.setHash(hash);
				dbs.setPrizenumber(prizenum);
				dbs.setTime(date);
				switch (dbs.getType()) {
				case Constants.DICE_GREATER_THAN: {// 大于
					if (getGreaterThan(prizenum, dbs.getForecast())) {
						// 胜率
						double win = (99 - dbs.getForecast()) * 0.01;
						getPrizeScore(win, dbs);
					}
					openDiceMessage(dbs);
					break;
				}
				case Constants.DICE_LESS_THAN: {// 小于
					if (getLessThan(prizenum, dbs.getForecast())) {
						// 胜率
						double win = dbs.getForecast() * 0.01;
						getPrizeScore(win, dbs);
					}
					openDiceMessage(dbs);
					break;
				}
				case Constants.DICE_MAX: {// 大
					if (getMax(prizenum, dbs.getForecast())) {
						// 胜率
						double win = 0.45;
						getPrizeScore(win, dbs);
					}
					openDiceMessage(dbs);
					break;
				}
				case Constants.DICE_SMALL: {// 小
					if (getSmall(prizenum, dbs.getForecast())) {
						// 胜率
						double win = 0.45;
						getPrizeScore(win, dbs);
					}
					openDiceMessage(dbs);
					break;
				}
				case Constants.DICE_COUPLET: {// 对子
					if (getCouplet(prizenum, dbs.getForecast())) {
						// 胜率
						double win = 0.1;
						getPrizeScore(win, dbs);
					}
					openDiceMessage(dbs);
					break;
				}
				default:
					break;
				}
			}
			// 广播奖池余额
			PublicMethod.sendPoolBalance(Constants.DICE_TYPE, handler);
			// 清除缓存
			DBCache.getInstance().getDiceBettingScores().removeAll(dBDiceBettingScore);
		}
	}

	/**
	 * 大于（胜率：（99-预测数）%）
	 */
	private boolean getGreaterThan(String prizenum, long forecast) {
		if (Long.parseLong(prizenum) > forecast) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 小于（胜率：预测数%）
	 */
	private boolean getLessThan(String prizenum, long forecast) {
		if (Long.parseLong(prizenum) < forecast) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 大（胜率：45%）
	 */
	private boolean getMax(String prizenum, long forecast) {
		if (!isCouplet(prizenum)) {
			if (Long.parseLong(prizenum) >= 50) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 小（胜率：45%）
	 */
	private boolean getSmall(String prizenum, long forecast) {
		if (!isCouplet(prizenum)) {
			if (Long.parseLong(prizenum) <= 49) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 对子(胜率：10%)
	 */
	private boolean getCouplet(String prizenum, long forecast) {
		if (isCouplet(prizenum)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否对子
	 * 
	 * @param prize
	 * @return
	 */
	private boolean isCouplet(String prize) {
		String[] data = { "00", "11", "22", "33", "44", "55", "66", "77", "88", "99" };
		for (String str : data) {
			if (str.equals(prize)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 计算eos用户奖金(收取2%手续费)
	 * 
	 * @return
	 */
	private void getPrize(double win, DBDiceBetting dicebetting) {
		dicebetting.setState(1);
		// 赔率
		double odds = Double.parseDouble(new DecimalFormat("0.00").format(1 / win * 0.98));
		dicebetting.setOdds(odds + "");
		// 奖金
		String prize = new DecimalFormat("0.0000").format(Double.parseDouble(dicebetting.getBettingEOS()) * odds);
		dicebetting.setPrizeEOS(prize);
	}

	/**
	 * 计算积分用户奖金(收取2%手续费)
	 * 
	 * @return
	 */
	private void getPrizeScore(double win, DBDiceBettingScore dBDiceBettingScore) {
		dBDiceBettingScore.setState(1);
		// 赔率
		double odds = Double.parseDouble(new DecimalFormat("0.00").format(1 / win * 0.98));
		dBDiceBettingScore.setOdds(odds + "");
		// 奖金
		String prize = new DecimalFormat("0.0000")
				.format(Double.parseDouble(dBDiceBettingScore.getBettingScore()) * odds);
		dBDiceBettingScore.setPrizeScore(prize);
	}
	
	/**
	 * 积分用户开奖发奖信息
	 * @param dBDiceBettingScore
	 */
	private void openDiceMessage(DBDiceBettingScore dBDiceBettingScore) {
		// 判断是否中奖
		if (dBDiceBettingScore.getState() == 1) {
			boolean success = lotteryUserService.plusScoreFromDice(dBDiceBettingScore.getUid(),
					new BigDecimal(dBDiceBettingScore.getPrizeScore()));
			if (!success) {
				dBDiceBettingScore.setState(2);
			}
			msg = new Message();
			msg.setDate(new Date());
			msg.setFrom("diceAllPrizeScore");
			msg.setFromName("");
			dBDiceBettingScore.setTermnumber(dBDiceBettingScore.getTermnumber() + 10);
			msg.setText(JSONObject.fromObject(dBDiceBettingScore) + "");
			String bulder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg);
			// 展示中奖消息
			handler.broadcastByGame(new TextMessage(bulder), Constants.DICE_TYPE);
			// 插入明细
			UserTransactionScore record = new UserTransactionScore(dBDiceBettingScore.getUid(), Constants.GAME_TYPE_DICE,
					null, dBDiceBettingScore.getTermnumber(), "1", "1",
					new BigDecimal(dBDiceBettingScore.getPrizeScore()), null);
			PublicMethod.insertTransactionScore(record, userTransactionScoreRecordsService, logger);
		}
		lotteryService.addDBDiceBettingScore(dBDiceBettingScore);
		String prize = new DecimalFormat("0.0000").format(Double.parseDouble(dBDiceBettingScore.getBettingScore()));
		dBDiceBettingScore.setBettingScore(prize);
		msg = new Message();
		msg.setDate(new Date());
		msg.setFrom("diceUserPrizeScore");
		msg.setFromName("");
		msg.setText(JSONObject.fromObject(dBDiceBettingScore) + "");
		String bulder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg);
		LotteryUserScore user = lotteryUserService.getLotteryUserScoreId(dBDiceBettingScore.getUid());
		// 给用户发送中奖消息
		handler.sendMessageToUser(user.getUserId(), new TextMessage(bulder));
		// 响应用户余额
		PublicMethod.sendScoreUserBalance(user.getId(), Constants.DICE_TYPE , lotteryUserService, handler);
	}
}