package com.stip.net.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import com.lottery.net.utils.DBCache;
import com.lottery.net.utils.DateUtils;
import com.lottery.net.utils.JsonUtil;
import com.stip.net.entity.DBDiceBetting;
import com.stip.net.entity.DBDiceBettingScore;
import com.stip.net.entity.EosFlow;
import com.stip.net.entity.LotteryUserScore;
import com.stip.net.entity.UserTransactionScore;
import com.stip.net.imessage.IMWebSocketHandler;
import com.stip.net.kafka.utils.PublicMethod;
import com.stip.net.main.MainData;
import com.stip.net.main.MsgCode;
import com.stip.net.service.AccountService;
import com.stip.net.service.GameService;
import com.stip.net.service.LotteryService;
import com.stip.net.service.LotteryUserService;
import com.stip.net.service.RedisService;
import com.stip.net.service.UserTransactionScoreService;

import io.eblock.eos4j.api.vo.account.Account;
import net.sf.json.JSONObject;

@Scope("request")
@RequestMapping("/dice")
@RestController
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, Exception.class })
public class DiceController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private LotteryService lotteryService;
	@Autowired
	private SessionLocaleResolver localeResolver;
	@Autowired
	private IMWebSocketHandler handler;
	@Autowired
	private AccountService accountService;
	@Autowired
	private LotteryUserService lotteryUserService;
	@Autowired
	private GameService gameService;
	@Autowired
    private RedisService redisService;
	@Autowired
	private UserTransactionScoreService userTransactionScoreRecordsService;

	/**
	 * 骰子积分投注
	 *
	 * @param type
	 *            投注类型
	 * @param termnumber
	 *            期号
	 * @param forecast
	 *            预测数字
	 * @param bettingScore
	 *            投注金额
	 * @param betTime
	 *            下注时间
	 */
	@PostMapping("/getDiceBettingScore")
	public Map<String, Object> getDiceBettingScore(@RequestParam int type, @RequestParam long termnumber,
			@RequestParam long forecast, @RequestParam String bettingScore, @RequestParam String betTime,
			HttpServletRequest request) {
		Locale locale = localeResolver.resolveLocale(request);
		Map<String, Object> response = new HashMap<String, Object>(1);
		response.put("status", false);
		// 验证登录
		LotteryUserScore userInSession = (LotteryUserScore) request.getSession().getAttribute(Constants.USER_IN_SESSION);
		if (userInSession == null) {
			response.put("msg", gameService.getMessage("notLogin", locale));
			return response;
		}
		if (Double.parseDouble(bettingScore) < 0.1 || Double.parseDouble(bettingScore) > 100) {
			response.put("msg", gameService.getMessage("dice111", locale));
			return response;
		}
		if (forecast < 0 || forecast > 99) {
			response.put("msg", gameService.getMessage("dice112", locale));
			return response;
		}
		// 根据用户发送下注时间，把期号向后延迟
		int sendMiao = DateUtils.dateDiffSec(DateUtils.parseDate(betTime).getTime(), System.currentTimeMillis());
		if (sendMiao > 0) {
			termnumber = termnumber + (sendMiao * 2);
		}

		long id = userInSession.getId();
		// 用户信息
		LotteryUserScore lotteryUserScore = lotteryUserService.getLotteryUserScoreId(id);
		if (lotteryUserScore == null) {
			response.put("msg", gameService.getMessage("errorUserInfo", locale));
			return response;
		}
		//判断是否在投注状态
		DBDiceBettingScore diceCache = DBCache.getInstance().getDiceBettingAccountScoreId(id);
		if(diceCache != null){
			response.put("msg", gameService.getMessage("dice107", locale));
			return response;
		}
		//判断余额
		if(lotteryUserScore.getScore().compareTo(new BigDecimal(bettingScore)) < 0){
			response.put("msg", gameService.getMessage("dice113", locale));
			return response;
		}
		// 扣除积分
		boolean success = lotteryUserService.minusScoreToDice(id, new BigDecimal(bettingScore));
		if (success) {
			DBDiceBettingScore diceBettingScore=new DBDiceBettingScore();
			String betScore = new DecimalFormat("0.0000").format(Double.parseDouble(bettingScore));
			diceBettingScore.setBettingScore(betScore);
			diceBettingScore.setForecast(forecast);
			diceBettingScore.setUid(id);
			diceBettingScore.setType(type);
			diceBettingScore.setUser(userInSession.getUserId());
			diceBettingScore.setTermnumber(termnumber);
			DBCache.getInstance().getDiceBettingScores().add(diceBettingScore);
			response.put("termnumber", termnumber);
			response.put("msg", gameService.getMessage("dice116", locale));
			// 新用户奖励
			PublicMethod.newUserScore(Constants.DICE_TYPE,lotteryUserScore, lotteryUserService, userTransactionScoreRecordsService, "3", null, termnumber, handler, logger);
			// 发送佣金
			PublicMethod.invitationAwardScoreUser(Constants.DICE_TYPE,new BigDecimal(bettingScore), lotteryUserScore,
					lotteryUserService, userTransactionScoreRecordsService, Constants.GAME_TYPE_DICE, null, termnumber,
					handler, logger);
			// 投注积分流水
			UserTransactionScore record = new UserTransactionScore(id, Constants.GAME_TYPE_DICE, null, termnumber, "0",
					"0", new BigDecimal(bettingScore), null);
			PublicMethod.insertTransactionScore(record, userTransactionScoreRecordsService, logger);
			// 响应用户余额
			PublicMethod.sendScoreUserBalance(id, Constants.DICE_TYPE, lotteryUserService, handler);
			// 广播奖池余额
			PublicMethod.sendPoolBalance(Constants.DICE_TYPE, handler);
		} else {
			response.put("msg", gameService.getMessage("dice115", locale));
			return response;
		}
		response.put("status", true);
		return response;
	}

	/**
	 * 骰子eos投注
	 *
	 * @param account
	 *            用户名
	 * @param type
	 *            投注类型
	 * @param termnumber
	 *            期号
	 * @param forecast
	 *            预测数字
	 * @param bettingEOS
	 *            投注金额
	 * @param transaction_id
	 *            交易地址
	 * @param betTime
	 *            下注时间
	 * @return
	 */
	@PostMapping("/getDiceBetting")
	public Map<String, Object> getDiceBetting(@RequestParam String account, @RequestParam int type,
			@RequestParam long termnumber, @RequestParam long forecast, @RequestParam String bettingEOS,
			@RequestParam String betTime, @RequestParam int isPrivate, HttpServletRequest request) {
		Locale locale = localeResolver.resolveLocale(request);
		Map<String, Object> response = new HashMap<String, Object>(1);
		if (Double.parseDouble(bettingEOS) < 0.1 || Double.parseDouble(bettingEOS) > 100) {
			response.put("err", gameService.getMessage("dice111", locale));
			return response;
		}
		if (forecast < 0 || forecast > 99) {
			response.put("err", gameService.getMessage("dice112", locale));
			return response;
		}
		// 根据用户发送下注时间，把期号向后延迟
		int sendMiao = DateUtils.dateDiffSec(DateUtils.parseDate(betTime).getTime(), System.currentTimeMillis());
		if (sendMiao > 0) {
			termnumber = termnumber + (sendMiao * 2);
		}
		// scatter投注的交易地址
		String transaction_id = request.getParameter("transaction_id");
		// 查询缓存中是否存在
		DBDiceBetting diceCache = DBCache.getInstance().getDiceBettingAccount(account);
		if (diceCache != null && diceCache.getState() != 3) {// 还在投注状态
			response.put("err", gameService.getMessage("dice107", locale));
			return response;
		} else if (diceCache != null && diceCache.getState() == 3) {// 已收到扣款确认消息
																	// scatter登录
			// 根据交易地址查询投注信息
			DBDiceBetting dice = DBCache.getInstance().getDiceBettingTransaction_id(transaction_id);
			if (dice != null) {// 修改投注信息
				dice.setType(type);
				dice.setTermnumber(termnumber);
				dice.setForecast(forecast);
				dice.setBettingEOS(bettingEOS);
				response.put("termnumber", termnumber);
				response.put("msg", gameService.getMessage("dice116", locale));
			} else {// 创建投注信息
				DBDiceBetting dbDice = new DBDiceBetting();
				dbDice.setTransaction_id(transaction_id);
				dbDice.setTermnumber(termnumber);
				dbDice.setAccount(account);
				dbDice.setType(type);
				dbDice.setForecast(forecast);
				dbDice.setBettingEOS(bettingEOS);
				DBCache.getInstance().getDiceBettings().add(dbDice);
				response.put("termnumber", termnumber);
				response.put("msg", gameService.getMessage("dice116", locale));
			}
		} else if (diceCache == null) {// 正常投注状态
			DBDiceBetting dbDice = new DBDiceBetting();
			// 判断是否是私钥登录，如果是私钥登录则扣款
			if (isPrivate == 1) {
				try {
					// 查询用户信息
					Account acc = accountService.getAccount(account);
					if (acc == null) {
						response.put("msg", gameService.getMessage("dice114", locale));
						return response;
					}
					String balance = acc.getCoreLiquidBalance().split(" ")[0];
					// 判断余额
					if (new BigDecimal(balance).compareTo(new BigDecimal(bettingEOS)) < 0) {
						response.put("msg", gameService.getMessage("dice113", locale));
						return response;
					}
					String result = new DecimalFormat("0.0000").format(Double.parseDouble(bettingEOS)).toString();
					Map<String, String> remark = new HashMap<>();
					remark.put("gameType", Constants.DICE_TYPE);
					remark.put("userName", account);
					String privateKey = redisService.getCache(account).toString();
					long time = System.currentTimeMillis();
					// 扣款操作
					String id = accountService.minusBalanceToDice(account, result, privateKey,
							JSONObject.fromObject(remark).toString());
					long time1 = System.currentTimeMillis();
					// 判断扣款是否成功
					if (!StringUtils.isBlank(id)) {
						// 扣钱需要多少秒
						int miao = DateUtils.dateDiffSec(time, time1);
						// 期号根据扣钱需要多少秒向后延迟
						termnumber = termnumber + miao * 2;
						DBDiceBetting Cache = DBCache.getInstance().getDiceBettingAccount(account);
						if (Cache == null) {
							dbDice.setTransaction_id(id);
							dbDice.setTermnumber(termnumber);
							dbDice.setAccount(account);
							dbDice.setType(type);
							dbDice.setForecast(forecast);
							dbDice.setBettingEOS(bettingEOS);
							DBCache.getInstance().getDiceBettings().add(dbDice);
						} else {// 扣钱确认消息已收到
							Cache.setBettingEOS(bettingEOS);
							Cache.setTermnumber(termnumber);
							Cache.setType(type);
							Cache.setForecast(forecast);
						}
						response.put("msg", gameService.getMessage("dice116", locale));
						response.put("termnumber", termnumber);
						response.put("balance", new BigDecimal(balance).subtract(new BigDecimal(bettingEOS)) + " EOS");
						response.put("top", accountService.getTopBalance(account));
						// 推送骰子奖池余额
						String poolBalance = accountService.getBalance(Constants.account_dice);
						if (poolBalance != null) {
							MainData.poolBalance.put(Constants.DICE_TYPE, poolBalance);
							Map<String, Object> dmap = new HashMap<>();
							dmap.put("balance", poolBalance);
							String dJson = JsonUtil.buildJson(MsgCode.DICE_POOL_BALANCE, dmap);
							handler.broadcastByGame(new TextMessage(dJson), Constants.DICE_TYPE);
						}
					} else {
						response.put("err", gameService.getMessage("dice115", locale));
						return response;
					}
				} catch (InterruptedException e) {
					response.put("err", gameService.getMessage("dice115", locale));
					return response;
				}
			} else {// scatter登录
				dbDice.setTransaction_id(transaction_id);
				dbDice.setTermnumber(termnumber);
				dbDice.setAccount(account);
				dbDice.setType(type);
				dbDice.setForecast(forecast);
				dbDice.setBettingEOS(bettingEOS);
				DBCache.getInstance().getDiceBettings().add(dbDice);
				response.put("termnumber", termnumber);
				response.put("msg", gameService.getMessage("dice116", locale));
			}
			// 流水记录
			EosFlow ef = new EosFlow(account, Constants.GAME_TYPE_DICE, 0, 0, null, termnumber, new BigDecimal(bettingEOS), null);
			PublicMethod.insertTransaction(ef, gameService, logger);
		}
		return response;
	}

	/**
	 * 查询EOS用户的投注记录
	 *
	 * @param account
	 * @return
	 */
	@GetMapping("getAccountDice")
	public Map<String, Object> getAccountDice(@RequestParam String account, @RequestParam int begin,
			@RequestParam int limit) {
		Map<String, Object> response = new HashMap<String, Object>(1);
		List<DBDiceBetting> dbdices = lotteryService.getAccountDice(account, begin, limit);
		response.put("accountDice", dbdices);
		return response;
	}

	/**
	 * 查询积分用户的投注记录
	 *
	 * @param account
	 * @return
	 */
	@GetMapping("getAccountDiceScore")
	public Map<String, Object> getAccountDiceScore(@RequestParam int begin, @RequestParam int limit, HttpServletRequest request) {
		Map<String, Object> response = new HashMap<String, Object>(1);
		Locale locale = localeResolver.resolveLocale(request);
		// 验证登录
		LotteryUserScore userInSession = (LotteryUserScore) request.getSession().getAttribute(Constants.USER_IN_SESSION);
		if (userInSession == null) {
			response.put("msg", gameService.getMessage("notLogin", locale));
			return response;
		}
		List<DBDiceBettingScore> dbdices = lotteryService.getAccountDiceScore(userInSession.getId(), begin, limit);
		response.put("accountDice", dbdices);
		return response;
	}

	@GetMapping("/getDBCache")
	public Map<String,Object> getDBCache() {
		Map<String, Object> response = new HashMap<String, Object>(1);
		response.put("GOS:", DBCache.getInstance().getDiceBettingScores());
		response.put("EOS:", DBCache.getInstance().getDiceBettings());
		return response;
	}

	@GetMapping("/removeDBCache")
	public String removeDBCache(@RequestParam String type) {
		if(StringUtils.isBlank(type)){
			DBCache.getInstance().getDiceBettings().removeAll(DBCache.getInstance().getDiceBettings());
		}else{
			DBCache.getInstance().getDiceBettingScores().removeAll(DBCache.getInstance().getDiceBettingScores());
		}
		return "ok";
	}

}
