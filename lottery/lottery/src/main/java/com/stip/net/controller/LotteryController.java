package com.stip.net.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottery.net.utils.MD5Utils;
import com.stip.net.entity.*;
import io.eblock.eos4j.Rpc;
import io.eblock.eos4j.api.vo.transaction.Transaction;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.socket.TextMessage;

import com.google.gson.GsonBuilder;
import com.lottery.net.utils.Constants;
import com.stip.net.dto.BetDto;
import com.stip.net.dto.HightBetDto;
import com.stip.net.imessage.IMWebSocketHandler;
import com.stip.net.kafka.utils.PublicMethod;
import com.stip.net.service.AccountService;
import com.stip.net.service.GameService;
import com.stip.net.service.LotteryService;
import com.stip.net.service.LotteryUserService;
import com.stip.net.service.RedisService;
import com.stip.net.service.SysDictService;
import com.stip.net.service.UserBetScoreRecordsService;
import com.stip.net.service.UserBetService;
import com.stip.net.service.UserTransactionScoreService;
import com.stip.net.vo.Message;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import net.sf.json.JSONObject;

@Scope("request")
@RequestMapping("/lottery")
@RestController
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, Exception.class})
public class LotteryController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private LotteryService lotteryService;
    @Autowired
    private UserBetService userBetService;
    @Autowired
    private SessionLocaleResolver localeResolver;
    @Autowired
    private LotteryUserService lotteryUserService;
    @Autowired
    private IMWebSocketHandler handler;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private GameService gameService;
    @Autowired
    private UserBetScoreRecordsService userBetScoreRecordsService;
    @Autowired
	private UserTransactionScoreService userTransactionScoreRecordsService;

    /**
     * 语言切换 通过session
     *
     * @param request
     * @param type    切换语种 1中文 2英文
     */
    @GetMapping("/updLocale")
    public void updLocale(HttpServletRequest request, HttpServletResponse response, String type) {
        if ("1".equals(type)) {
            localeResolver.setLocale(request, response, Locale.SIMPLIFIED_CHINESE);
        } else {
            localeResolver.setLocale(request, response, Locale.US);
        }
    }

    /**
     * 博彩开关 是否开启 检查账号冻结状态
     *
     * @param request
     */
    @GetMapping("/checkUserStatus")
    public Map<String, Object> checkUserStatus(HttpServletRequest request) {
        String gameType = request.getParameter("gameType");
        if ("tiger".equals(gameType)) {
            return checkByType(Constants.DICT_TYPE_SWITCH_TIGER, request);
        } else if ("dice".equals(gameType)) {
            return checkByType(Constants.DICT_TYPE_SWITCH_DICE, request);
        } else {
            return checkByType(Constants.DICT_TYPE_SWITCH, request);
        }
    }

    /**
     * 根据不同的游戏，判断是否可以投注
     */
    private Map<String, Object> checkByType(String type, HttpServletRequest request) {
        Map<String, Object> jsonResult = new HashMap<String, Object>(1);
        jsonResult.put("status", Boolean.FALSE);
        String userName = request.getParameter("userName");
        String loginType = request.getParameter("loginType");
        Locale locale = localeResolver.resolveLocale(request);
        List<SysDict> sysdictByType = sysDictService.getSysdictByType(type);
        if (CollectionUtils.isEmpty(sysdictByType) || !Constants.GENERAL_ONE.equals(sysdictByType.get(0).getValue())) {
            jsonResult.put("msg", gameService.getMessage("gameClosed", locale));
            return jsonResult;
        }
        //	判断是否是积分用户
        if(!loginType.equals("3")){
        	if (StringUtils.isBlank(userName)) {
        		jsonResult.put("msg", gameService.getMessage("notLogin", locale));
        		return jsonResult;
        	}
        	// 判断用户是否是合约用户
          	if(!accountService.get_code_hash(userName)){
          		jsonResult.put("msg", gameService.getMessage("contract", locale));
                return jsonResult;
          	}
            boolean userStatus = lotteryUserService.getUserStatus(userName);
            if (userStatus) {
                jsonResult.put("status", Boolean.TRUE);
                return jsonResult;
            }
        }else {
        	// 验证登录
    		LotteryUserScore userInSession = (LotteryUserScore) request.getSession().getAttribute(Constants.USER_IN_SESSION);
    		if (userInSession == null) {
    			jsonResult.put("msg", gameService.getMessage("notLogin", locale));
    			return jsonResult;
    		}
        	if (lotteryUserService.getUserScoreStatus(userInSession.getUserId())) {
        		jsonResult.put("status", Boolean.TRUE);
        		return jsonResult;
        	}
        }
        jsonResult.put("msg", gameService.getMessage("ecxeption", locale));
        return jsonResult;
    }

    /**
     * eos用户下注
     *
     * @param request
     * @return
     * @throws InterruptedException
     */
    @PostMapping("/goBet.do")
    public Map<String, Object> goBet(HttpServletRequest request) {
        Map<String, Object> jsonResult = new HashMap<String, Object>(1);
        Date date = new Date();
        int recordCount = 0;
        String isPrivate = request.getParameter("isPrivate");
        String userName = request.getParameter("userName").toString();
        String model = request.getParameter("model").toString();
        String myBets = request.getParameter("perBets").toString();
        String sid = request.getParameter("sid").toString();

        List<UserBetRecords> list = new ArrayList<UserBetRecords>();
        double longBets = 0;

        Locale locale = localeResolver.resolveLocale(request);

        try {
            if (!"".equals(myBets) && null != myBets) {
                double f = Double.parseDouble(myBets);
                BigDecimal b = new BigDecimal(f);
                longBets = b.setScale(4, BigDecimal.ROUND_HALF_DOWN).doubleValue();
            }
        } catch (NumberFormatException e) {
            jsonResult.put("status", "false");
            jsonResult.put("msg", gameService.getMessage("betFail", locale));

            return jsonResult;
        }

        LotteryRecords lotteryRecords = lotteryService.getNotPrizeByTime("DESC");

        if ("0".equals(model)) {// 简单模式
            String large = request.getParameter("large").toString();
            String single = request.getParameter("single").toString();
            String baseNum = request.getParameter("baseNum");
            BetDto betDto = new BetDto();

            betDto.setDate(date);
            betDto.setLongBets(longBets);
            betDto.setModel(model);
            betDto.setUserName(userName);
            betDto.setLarge(large);
            betDto.setSingle(single);
            betDto.setBaseNum(baseNum);

            recordCount = userBetService.goBet(lotteryRecords, betDto, list, sid);

            jsonResult.put("status", "true");
            jsonResult.put("msg", gameService.getMessage("betSuccess", locale));

        } else {
            HightBetDto hightBetDto = new HightBetDto();
            String firstNums = request.getParameter("firstNums").toString();
            String starRank = request.getParameter("starRank").toString();
            String secondNums = request.getParameter("secondNums");
            String thirdNums = request.getParameter("thirdNums");
            String forthNums = request.getParameter("forthNums");
            String fifthNums = request.getParameter("fifthNums");

            hightBetDto.setDate(date);
            hightBetDto.setFifthNums(fifthNums);
            hightBetDto.setFirstNums(firstNums);
            hightBetDto.setForthNums(forthNums);
            hightBetDto.setLongBets(longBets);
            hightBetDto.setModel(model);
            hightBetDto.setSecondNums(secondNums);
            hightBetDto.setStarRank(starRank);
            hightBetDto.setThirdNums(thirdNums);
            hightBetDto.setUserName(userName);

            try {
                recordCount = userBetService.goBetHight(lotteryRecords, hightBetDto, sid);
            } catch (Exception e) {
                jsonResult.put("status", "false");
                jsonResult.put("msg", gameService.getMessage("betFail", locale));
            }

            jsonResult.put("status", "true");
            jsonResult.put("msg", gameService.getMessage("betSuccess", locale));
        }

        double cm = 0;
        if ("0".equals(model)) {
        	cm = longBets * (list.size() - 1);
        } else {
        	cm = longBets * recordCount;
        }
        BigDecimal a = new BigDecimal(cm);
        String result = new DecimalFormat("0.0000").format(a.setScale(4, BigDecimal.ROUND_HALF_DOWN).doubleValue()).toString();
        if (recordCount > 0 && null != isPrivate) {
            Object pk = redisService.getCache(userName);
            String priKey = "";

            if (null != pk) {
                priKey = pk.toString();
            } else {
                jsonResult.put("loginStatus", "未登录或者登录信息过期，请重新登录。");
            }

            try {
                Map<String, String> remark = new HashMap<>();
                remark.put("gameType", Constants.LOTTERY_TYPE);
                remark.put("id", sid);
                remark.put("userName", userName);
                boolean payStatus = accountService.minusBalance(userName, result, priKey, JSONObject.fromObject(remark).toString());

                if (!payStatus) {
                    jsonResult.put("status", "false");
                    jsonResult.put("msg", gameService.getMessage("betFail", locale));
                }
            } catch (InterruptedException e) {
                logger.error("下注扣款失败");
                jsonResult.put("status", "false");
                jsonResult.put("msg", gameService.getMessage("betFail", locale));
            }
        }
        // 流水记录
        EosFlow ef = new EosFlow(userName, Constants.GAME_TYPE_LOTTERY, 0, 0, null, lotteryRecords.getRecordsId(), new BigDecimal(result), null);
        PublicMethod.insertTransaction(ef, gameService, logger);

        return jsonResult;
    }

    /**
     * 积分用户下注
     */
    @PostMapping("/goBetScore.do")
    public Map<String, Object> goBetScore(HttpServletRequest request) {
    	Locale locale = localeResolver.resolveLocale(request);
        Map<String, Object> jsonResult = new HashMap<String, Object>(1);
        jsonResult.put("status", "false");
        // 验证登录
        LotteryUserScore userInSession = (LotteryUserScore) request.getSession().getAttribute(Constants.USER_IN_SESSION);
		if (userInSession == null) {
        	jsonResult.put("msg", gameService.getMessage("notLogin", locale));
        	return jsonResult;
        }
        Date date = new Date();
        String model = request.getParameter("model").toString();// 简单模式
        String myBets = request.getParameter("perBets").toString();//投注金额
        String score = request.getParameter("score").toString();//投注总额
        List<UserBetScoreRecords> list = new ArrayList<UserBetScoreRecords>();
        double longBets = 0;

		String userId = userInSession.getUserId();
		// 用户信息
		LotteryUserScore lotteryUserScore = lotteryUserService.getLotteryUserScoreId(userInSession.getId());
		if (lotteryUserScore == null) {
			jsonResult.put("msg", gameService.getMessage("errorUserInfo", locale));
			return jsonResult;
		}
		BigDecimal score1=new BigDecimal(score);
		// 判断积分
		if (lotteryUserScore.getScore().compareTo(score1) < 0) {
			jsonResult.put("msg", gameService.getMessage("dice113", locale));
			return jsonResult;
		}
        try {
            if (!"".equals(myBets) && null != myBets) {
                double f = Double.parseDouble(myBets);
                BigDecimal b = new BigDecimal(f);
                longBets = b.setScale(4, BigDecimal.ROUND_HALF_DOWN).doubleValue();
            }
        } catch (NumberFormatException e) {
            jsonResult.put("msg", gameService.getMessage("betFail", locale));
            return jsonResult;
        }
        // 扣除积分
      	boolean n = lotteryUserService.minusScoreToLottery(lotteryUserScore.getId(), score1);
      	if (!n) {
      		jsonResult.put("msg", gameService.getMessage("betFail", locale));
            return jsonResult;
      	}
        LotteryRecords lotteryRecords = lotteryService.getNotPrizeByTime("DESC");
        if ("0".equals(model)) {// 简单模式
            String large = request.getParameter("large").toString();//大小
            String single = request.getParameter("single").toString();//单双
            String baseNum = request.getParameter("baseNum");//预测数
            BetDto betDto = new BetDto();

            betDto.setDate(date);
            betDto.setLongBets(longBets);
            betDto.setModel(model);
            betDto.setUserName(userInSession.getUserId());
            betDto.setLarge(large);
            betDto.setSingle(single);
            betDto.setBaseNum(baseNum);

            userBetService.goBetScore(lotteryRecords, betDto, list , userInSession.getId());

            jsonResult.put("status", "true");
            jsonResult.put("msg", gameService.getMessage("betSuccess", locale));

        } else {
            HightBetDto hightBetDto = new HightBetDto();
            String firstNums = request.getParameter("firstNums").toString();
            String starRank = request.getParameter("starRank").toString();
            String secondNums = request.getParameter("secondNums");
            String thirdNums = request.getParameter("thirdNums");
            String forthNums = request.getParameter("forthNums");
            String fifthNums = request.getParameter("fifthNums");

            hightBetDto.setDate(date);
            hightBetDto.setFifthNums(fifthNums);
            hightBetDto.setFirstNums(firstNums);
            hightBetDto.setForthNums(forthNums);
            hightBetDto.setLongBets(longBets);
            hightBetDto.setModel(model);
            hightBetDto.setSecondNums(secondNums);
            hightBetDto.setStarRank(starRank);
            hightBetDto.setThirdNums(thirdNums);
            hightBetDto.setUserName(userId);

            try {
                userBetService.goBetHightScore(lotteryRecords, hightBetDto, userInSession.getId());
            } catch (Exception e) {
                jsonResult.put("status", "false");
                jsonResult.put("msg", gameService.getMessage("betFail", locale));
                return jsonResult;
            }
            jsonResult.put("status", "true");
            jsonResult.put("msg", gameService.getMessage("betSuccess", locale));
        }
        //期号
        long termnumber=lotteryRecords.getRecordsId();
        if(redisService.getCache(termnumber+"")!=null){
        	score1 = new BigDecimal(redisService.getCache(termnumber+"").toString()).add(score1);
        }
        redisService.setCache(termnumber+"", score1);
        // 新用户奖励
        PublicMethod.newUserScore(Constants.LOTTERY_TYPE,lotteryUserScore, lotteryUserService, userTransactionScoreRecordsService, "1", null, termnumber, handler, logger);
        // 发送佣金
     	PublicMethod.invitationAwardScoreUser(Constants.LOTTERY_TYPE,score1, lotteryUserScore,
     			lotteryUserService, userTransactionScoreRecordsService, Constants.GAME_TYPE_DICE, null, termnumber,
     			handler, logger);
     	//	投注积分流水
     	UserTransactionScore record = new UserTransactionScore(lotteryUserScore.getId(), Constants.GAME_TYPE_LOTTERY, null,
     			termnumber, "0", "0", score1, null);
     	PublicMethod.insertTransactionScore(record, userTransactionScoreRecordsService, logger);
     	// 保留四位小数
     	String str=new DecimalFormat("0.0000").format(Double.parseDouble(score)) + "GOS";
     	// 推送投注记录
     	sendBetRecords(userId, str, date, termnumber+"");
        return jsonResult;
    }

    /**
     * 查询开奖历史记录
     */
    @GetMapping("/getLotteryHis.do")
    public Map<String, Object> getLotteryHis(HttpServletRequest request) {
        Map<String, Object> jsonResult = new HashMap<String, Object>(1);
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        String rowNum = request.getParameter("rowNum");
        jsonResult.put("hisRecords", lotteryService.getLotteryHistry(pageNum, rowNum));

        return jsonResult;
    }


    /**
     * 查询EOS用户下注记录
     */
    @GetMapping("/getUserBetHis.do")
    public Map<String, Object> getUserBetHis(HttpServletRequest request) {
        Map<String, Object> jsonResult = new HashMap<String, Object>(1);
        int pageNum = Integer
                .parseInt(StringUtils.isBlank(request.getParameter("pageNum")) ? "0" : request.getParameter("pageNum"));
        String userName = request.getParameter("userName").toString();

        jsonResult.put("userHis", userBetService.getUserBetHistry(pageNum, userName));

        return jsonResult;
    }

    /**
     * 查询积分用户下注记录
     */
    @GetMapping("/getUserBetHisScore.do")
	public Map<String, Object> getUserBetHisScore(HttpServletRequest request) {
		Locale locale = localeResolver.resolveLocale(request);
		Map<String, Object> jsonResult = new HashMap<String, Object>(1);
		// 验证登录
		LotteryUserScore userInSession = (LotteryUserScore) request.getSession()
				.getAttribute(Constants.USER_IN_SESSION);
		if (userInSession == null) {
			jsonResult.put("msg", gameService.getMessage("notLogin", locale));
			return jsonResult;
		}
		int pageNum = Integer
				.parseInt(StringUtils.isBlank(request.getParameter("pageNum")) ? "0" : request.getParameter("pageNum"));
		List<UserBetScoreRecords> list=userBetScoreRecordsService.getUserBetHistry(pageNum, userInSession.getId());
		jsonResult.put("userHis", list);
		return jsonResult;
	}

    /**
     * 发送用户下注记录
     * @param userName	用户
     * @param count		金额
     * @param date		时间
     * @param num		期号
     */
    public void sendBetRecords(String userName, String count, Date date, String num) {
        Message msg = new Message();
        JSONObject obj = new JSONObject();

        obj.put("userName", userName);
        obj.put("count", count);
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

    /**
     * 根据期号获取开奖记录
     */
    @GetMapping("/getBlockNum")
    public Map<String, Object> getBlockNum(HttpServletRequest request) {
        Map<String, Object> jsonResult = new HashMap<String, Object>(2);
        String betNum = request.getParameter("betNum");
        if (StringUtils.isBlank(betNum)) {
            return jsonResult;
        }
        LotteryRecords lotteryRecords = userBetService.selectLotteryRecordsById(Long.parseLong(betNum));
        if (null == lotteryRecords) {
            return jsonResult;
        }
        jsonResult.put("fristNum", lotteryRecords.getHashOne());
        jsonResult.put("lastkNum", lotteryRecords.getHashFive());
        jsonResult.put("betNum", ""+lotteryRecords.getLotteryFive()+lotteryRecords.getLotteryFour()
        		+lotteryRecords.getLotteryThree()+lotteryRecords.getLotteryTwo()
                +lotteryRecords.getLotteryOne());
        return jsonResult;
    }

    /**
     * 注册
     *
     * @param request
     * @return
     */
    @PostMapping("/regist")
    public Map<String, Object> regist(HttpServletRequest request) {
        Map<String, Object> jsonResult = new HashMap<String, Object>(2);
        jsonResult.put("success", Boolean.FALSE);
        String email = request.getParameter("email");
        String mobilePhone = request.getParameter("mobilePhone");
        String password = request.getParameter("password");
        String idencode = request.getParameter("idencode");
        String referrer = request.getParameter("referrer");
        if (StringUtils.isBlank(email) && StringUtils.isBlank(mobilePhone) || StringUtils.isBlank(password)) {
        	jsonResult.put("msg", "参数不正确");
            return jsonResult;
        }
        LotteryUserScore UserScore = null;
        // 查询用户信息
        if(!StringUtils.isBlank(mobilePhone)){
        	UserScore=lotteryUserService.getLotteryUserScoreTel(mobilePhone);
		}else{
			UserScore=lotteryUserService.getLotteryUserScoreEmil(email);
		}
        if(UserScore != null){
        	jsonResult.put("msg", "用户已存在");
            return jsonResult;
        }
        String code = (String) request.getSession().getAttribute(mobilePhone);
        //判断验证码
        if(!idencode.equals(code)){
        	jsonResult.put("msg", "验证码错误");
    		return jsonResult;
        }
        LotteryUserScore lotteryUserScore = new LotteryUserScore();
        lotteryUserScore.setEmail(email);
        lotteryUserScore.setMobilePhone(mobilePhone);
        lotteryUserScore.setPassword(MD5Utils.getPwd(password));
        lotteryUserScore.setCreateTime(new Date());
        lotteryUserScore.setStatus(1);
        lotteryUserScore.setReferrer(Long.parseLong(referrer));
        int n =lotteryUserService.addLotteryUserScore(lotteryUserScore);
        if(n>0){
        	jsonResult.put("success",Boolean.TRUE);
        }
        return jsonResult;
    }
    
    /**
     * 创建账号
     *
     * @param request
     * @return
     */
    @PostMapping("/createAccount.do")
    public Map<String, Object> createAccount(@RequestParam int userId) {
    	Map<String, Object> jsonResult = new HashMap<String, Object>(2);
    	LotteryUser record =new LotteryUser();
    	record.setUserId(userId);
    	LotteryUser user=lotteryUserService.getlotteryUser(record);
    	if(user==null){
    		jsonResult.put("msg", "用户不存在");
        	return jsonResult;
    	}
        try {
            Rpc rpc = new Rpc("https://api.eosbeijing.one");
            Transaction t2 = rpc.createAccount(Constants.add_caccount_prive_key,Constants.add_account,
            		user.getUserName(), user.getUserKey(), user.getUserKey(),5120l,"0.0000 EOS","0.3000 EOS", 0l);
            if(t2.getTransactionId()!=null){
            	String msg="创建成功";
            	if(!accountService.delegatebw(Constants.add_caccount_prive_key,Constants.add_account, "5.0000 EOS")){
            		msg=msg+",抵押失败";
            	}
            	jsonResult.put("msg", msg);
                //  激活用户状态
            	LotteryUser record1 =new LotteryUser();
            	record1.setUserId(userId);
            	record1.setUserStatus("1");
            	lotteryUserService.updateByPrimaryKeySelective(record1);
                return jsonResult;
            }
        }catch(Exception ex) {
            logger.error(ex.toString());
        }
        jsonResult.put("msg", "创建失败");
		return jsonResult;
    	
    }
    
}
