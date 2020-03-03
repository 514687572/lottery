package com.stip.net.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.lottery.net.utils.Constants;
import com.lottery.net.utils.MD5Utils;
import com.lottery.net.utils.SendCode;
import com.stip.net.entity.LotteryUser;
import com.stip.net.entity.LotteryUserScore;
import com.stip.net.main.MainData;
import com.stip.net.service.AccountService;
import com.stip.net.service.GameService;
import com.stip.net.service.LotteryUserService;
import com.stip.net.service.RedisService;
import com.stip.net.service.UserService;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import io.eblock.eos4j.Rpc;
import io.eblock.eos4j.api.vo.account.Account;
import io.eblock.eos4j.api.vo.transaction.Transaction;
/**
 * 账户相关操作
 * @author cja
 *
 */
@Scope("request")
@RequestMapping("/account")
@RestController
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, Exception.class })
public class AccountController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	public AccountService accountService;
	@Autowired
	public UserService userService;
	@Autowired
	private LotteryUserService lotteryUserService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private ReloadableResourceBundleMessageSource messageSource;
	@Autowired
	private SessionLocaleResolver localeResolver;
	@Autowired
	private GameService gameService;
	public String getConfig(String key, Locale locale) {
		return messageSource.getMessage(key, null, locale);
	}

	/**
	 *  查询用户信息
	 * @param request
	 * @return
	 * @throws InterruptedException
	 */
	@GetMapping("/getAccount.do")
	public Map<String, Object> getAccount(HttpServletRequest request) throws InterruptedException {
		Map<String,Object> jsonResult=new HashMap<String, Object>(1);
		String account=request.getParameter("account");
		
		Account acc=accountService.getAccount(account);
		if (acc != null) {
			String topBalance = accountService.getTopBalance(account);
			jsonResult.put("balance",acc.getCoreLiquidBalance());
			jsonResult.put("cpu",acc.getCpuLimit());
			jsonResult.put("top", topBalance);
			jsonResult.put("net", acc.getNetLimit());
			jsonResult.put("ramQuota", acc.getRamQuota());
			jsonResult.put("ramUsage", acc.getRamUsage());
		}
		return jsonResult;
	}
	
	/**
	 * 查询系统用户信息
	 * @param request
	 * @return
	 * @throws InterruptedException
	 */
	@GetMapping("/getSysInfo.do")
	public Map<String, Object> getSysInfo(HttpServletRequest request) throws InterruptedException {  
		Map<String,Object> jsonResult=new HashMap<String, Object>(1);
		
		Account acc=accountService.getAccount(Constants.account);
		if (acc != null) {
			String topBalance = accountService.getTopBalance(Constants.account);
			jsonResult.put("balance",acc.getCoreLiquidBalance());
			jsonResult.put("cpu",acc.getCpuLimit());
			jsonResult.put("top", topBalance);
		}
		return jsonResult;
	}
	
	/**
	 * 收集用户信息
	 * @param request
	 * @return
	 * @throws InterruptedException
	 */
	@PostMapping("/addUserInfo.do")
	public void addUserInfo(HttpServletRequest request) throws InterruptedException {
		String publicKey= request.getParameter("account[publicKey]");
		String name=request.getParameter("account[accounts][0][name]");
		String userCode=request.getParameter("userCode");

		boolean b = userService.addUserInfo(name,publicKey);

		// 新用户 绑定关系
		if(b){
			lotteryUserService.updateRelatioin(name,userCode);
		}
	}
	
    /**
     * 用户私钥登录
     *
     * @param request
     * @return
     * @throws InterruptedException
     */
    @PostMapping("/loginWidthPriKey.do")
    public Map<String, Object> loginWidthPriKey(HttpServletRequest request) throws Exception {
    	String privateKey = request.getParameter("private_key");
    	String userCode=request.getParameter("userCode");
    	
    	Map<String, Object> result=accountService.getUserNameByPk(privateKey);
    	
    	if("1".equals(result.get("status").toString())) {
    		String userName=result.get("userName").toString();
    		String pubKey=result.get("pubKey").toString();
    		redisService.setCache(userName, privateKey);

    		boolean b = userService.addUserInfo(userName,pubKey);
    		
    		// 新用户 绑定关系
    		if(b){
    			lotteryUserService.updateRelatioin(userName,userCode);
    		}
    	}
    	
    	return result;
    }
    
	/**
	 *  抵押CPU
	 * @param request
	 * @return
	 * @throws InterruptedException
	 */
	@PostMapping("/delegatebw.do")
	public Map<String, Object> delegatebw(HttpServletRequest request) throws InterruptedException {
		Map<String,Object> jsonResult=new HashMap<String, Object>(1);
		String account=request.getParameter("account");
		String quantity=request.getParameter("quantity");
		
		String pk=redisService.getCache(account).toString();
		
		if(StringUtils.isNotBlank(pk)&&pk.length()>20) {
			boolean acc=accountService.delegatebw(pk, account, quantity);
			if (acc) {
				jsonResult.put("status","success");
				jsonResult.put("message","抵押成功");
			}else {
				jsonResult.put("status","fail");
				jsonResult.put("message","抵押失败");
			}
		}else {
			jsonResult.put("status","fail");
			jsonResult.put("message","抵押失败");
		}
		return jsonResult;
	}

	/**
	 *  发送验证码
	 */
	@GetMapping("/sendCode.do")
	public Map<String, Object> sendCode(HttpServletRequest request) throws Exception {
		String tel = request.getParameter("tel");
		String code = (int) (Math.random() * 9000 + 1000) + "";
		Map<String,Object> jsonResult = new HashMap<String, Object>(3);
		if (tel.length() != 11) {
			jsonResult.put("code", 0);
			return jsonResult;
		}
		try {
			String postData = "sname=" + Constants.SEND_CODE_NAME + "&spwd=" + Constants.SEND_CODE_PWD + "&scorpid="
					+ Constants.SEND_CODE_SCORPID + "&sprdid=" + Constants.SEND_CODE_SPRDID + "&sdst=" + tel + "&smsg="
					+ java.net.URLEncoder.encode("您的验证码是:" + code + "请在3分钟内输入【EOS Casino】", "utf-8");
			String ret = SendCode.SMS(postData, Constants.SEND_CODE_URL);
			String state = "<State>";
			String sr = ret.substring(ret.indexOf(state) + state.length(), ret.indexOf("</State>"));
			if (sr.equals("0")) {
				request.getSession().setAttribute(tel, code);
				jsonResult.put("code", 1);
			} else {
				jsonResult.put("code", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonResult;
	}
	
	/**
	 * 积分用户登出
	 */
	@GetMapping("/scoreUserLoginOut.do")
	public Map<String, Object> scoreUserLoginOut(HttpServletRequest request) {
		Map<String,Object> response = new HashMap<String, Object>(4);
		Locale locale = localeResolver.resolveLocale(request);
		// 验证登录
		LotteryUserScore userInSession = (LotteryUserScore) request.getSession()
				.getAttribute(Constants.USER_IN_SESSION);
		if (userInSession == null) {
			response.put("msg", gameService.getMessage("notLogin", locale));
			return response;
		}
		request.getSession().removeAttribute(Constants.USER_IN_SESSION);
		response.put("msg", "退出成功");
		return response;
	}
	
	/**
	 * 积分用户登录
	 */
	@PostMapping("/scoreUserLogin.do")
	public Map<String,Object> scoreUserLogin(HttpServletRequest request){
		Map<String,Object> jsonResult = new HashMap<String, Object>(4);
		jsonResult.put("success", Boolean.FALSE);
		String data=request.getParameter("data");
		String password=request.getParameter("password");
		LotteryUserScore lotteryUserScore = null;//用户信息
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		//判断是否是纯数字
		if(pattern.matcher(data).matches()){
			lotteryUserScore=lotteryUserService.getLotteryUserScoreTel(data);
		}else{
			lotteryUserScore=lotteryUserService.getLotteryUserScoreEmil(data);
		}
		if(lotteryUserScore==null){//未查到用户
			jsonResult.put("msg", "未找到用户");
			return jsonResult;
		}
		if(!lotteryUserScore.getPassword().equals(MD5Utils.getPwd(password))){//密码错误
			jsonResult.put("msg", "密码错误");
			return jsonResult;
		}
		jsonResult.put("score", lotteryUserScore.getScore());
		jsonResult.put("status", lotteryUserScore.getStatus());
		jsonResult.put("data", data);
		jsonResult.put("success", Boolean.TRUE);
		request.getSession().setAttribute(Constants.USER_IN_SESSION, lotteryUserScore);
		return jsonResult;
	}
	
	/**
	 * 查询用户信息
	 * @param request
	 * @return
	 */
	@PostMapping("/getScoreUser")
	public Map<String, Object> getScoreUser(HttpServletRequest request) {
		Locale locale = localeResolver.resolveLocale(request);
		Map<String, Object> response = new HashMap<String, Object>(4);
		// 验证登录
		LotteryUserScore userInSession = (LotteryUserScore) request.getSession()
				.getAttribute(Constants.USER_IN_SESSION);
		if (userInSession == null) {
			response.put("msg", gameService.getMessage("notLogin", locale));
			return response;
		}
		LotteryUserScore lotteryUserScore = lotteryUserService.getLotteryUserScoreId(userInSession.getId());// 用户信息
		if (lotteryUserScore == null) {
			response.put("msg", this.getConfig("errorUserInfo", locale));
			return response;
		}
		response.put("account", lotteryUserScore);
		return response;
	}
	
	/**
	 * 获取积分奖金池
	 * @param gameType
	 * @return
	 */
	@GetMapping("/getscorePoolBalance")
	public BigDecimal getscorePoolBalance(@RequestParam String gameType) {
		BigDecimal balance = MainData.scorePoolBalance.get(gameType);
		return balance;
	}
	
    /**
     * 注册
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
        // 用户唯一标识，邮箱或手机号
        if (StringUtils.isBlank(email) && StringUtils.isBlank(mobilePhone) || StringUtils.isBlank(password)) {
        	jsonResult.put("msg", "参数不正确");
            return jsonResult;
        }
        LotteryUserScore UserScore = null;
        String data=null;
        // 查询用户信息
		if (!StringUtils.isBlank(mobilePhone)) {
			UserScore = lotteryUserService.getLotteryUserScoreTel(mobilePhone);
			data=mobilePhone;
		} else {
			UserScore = lotteryUserService.getLotteryUserScoreEmil(email);
			data=email;
		}
        if(UserScore != null){
        	jsonResult.put("msg", "用户已存在");
            return jsonResult;
        }
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute(data);
        // 判断验证码
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
        lotteryUserScore.setScore(new BigDecimal(0));
        long ref = 0;
        if (!StringUtils.isBlank(referrer)) {
        	ref = Long.parseLong(Base64Decoder.decodeStr(referrer));
        }
        lotteryUserScore.setReferrer(ref);
        int n =lotteryUserService.addLotteryUserScore(lotteryUserScore);
		if (n > 0) {
			jsonResult.put("success", Boolean.TRUE);
			session.setAttribute(Constants.USER_IN_SESSION, lotteryUserScore);
		}
        return jsonResult;
    }
   
    /**
     * 创建账号
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
    
    /**
     * 验证登录
     */
    @GetMapping("/isLogin")
	public boolean isLogin(HttpServletRequest request) {
		// 验证登录
		LotteryUserScore userInSession = (LotteryUserScore) request.getSession().getAttribute(Constants.USER_IN_SESSION);
		if (userInSession == null) {
			return false;
		}
		return true;
	}
    
    /**
     * 获取邀请链接
     * @param request
     * @return
     */
    @GetMapping("/getInvitation")
    public Map<String,String> getInvitation(HttpServletRequest request){
    	Map<String,String> response=new HashMap<String,String>(1);
    	Locale locale = localeResolver.resolveLocale(request);
    	// 验证登录
    	LotteryUserScore userInSession = (LotteryUserScore) request.getSession().getAttribute(Constants.USER_IN_SESSION);
    	if(userInSession==null){
    		response.put("msg", gameService.getMessage("notLogin", locale));
			return response;
    	}
    	String code=Base64Encoder.encode(""+userInSession.getId());
    	response.put("msg","Https://myeosgame.com/userCode="+code );
    	return response;
    }
    
}