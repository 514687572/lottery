package com.stip.net.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dima.pay.enums.EncryptTypeEnum;
import com.dima.pay.enums.PayeeTypeEum;
import com.dima.pay.enums.SignTypeEnum;
import com.dima.pay.util.ConvertUtils;
import com.dima.pay.vo.BankAccountInfo;
import com.dima.pay.vo.WithdrawTradeReqVo;
import com.dima.pay.enums.PayTypeEnum;
import com.dima.pay.util.Base;
import com.dima.pay.util.EncryptUtils;
import com.dima.pay.util.SignUtils;
import com.google.common.collect.Maps;
import com.lottery.net.utils.Constants;
import com.stip.net.entity.LotteryUserScore;
import com.stip.net.entity.UserRechargeRecords;
import com.stip.net.entity.UserTransactionScore;
import com.stip.net.entity.UserWithdrawApplRecords;
import com.stip.net.imessage.IMWebSocketHandler;
import com.stip.net.kafka.utils.PublicMethod;
import com.stip.net.service.LotteryUserService;
import com.stip.net.service.UserRechargeRecordsService;
import com.stip.net.service.UserTransactionScoreService;
import com.stip.net.service.UserWithdrawApplRecordsService;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DESede;
import cn.hutool.http.HttpUtil;

/**
 * 支付操作
 * 
 * @author yc
 */
@Scope("request")
@RequestMapping("/pay")
@RestController
public class PayController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserRechargeRecordsService userRechargeRecordsService;
	@Autowired
	private LotteryUserService lotteryUserService;
	@Autowired
	private IMWebSocketHandler handler;
	@Autowired
	private UserTransactionScoreService userTransactionScoreRecordsService;
	@Autowired
	private UserWithdrawApplRecordsService userWithdrawApplRecordsService;

	/**
	 * * 跳转支付链接
	 * 
	 * @param faceMoney
	 *            交易金额（保留两位小数）
	 * @param name
	 *            签名
	 * @param isPC
	 *            0：移动端，1：PC端
	 * @param type
	 *            支付类型（0：注册，1：充值，2：其他）
	 * @param data
	 *            用户
	 * @param orderId
	 *            订单号
	 * @return
	 */
	@PostMapping("/getPayLink")
	public Map<String, Object> getPayLink(@RequestParam String faceMoney, @RequestParam String type,
			@RequestParam String isPC, @RequestParam String data, @RequestParam String name, @RequestParam String gameType,
			HttpServletRequest request) {
		Map<String, Object> response = new HashMap<String, Object>(1);
		// 支付方式类型
		String payTypeId = PayTypeEnum.UNI_EXP_PAY.getCode();
		Map<String, String> param = new HashMap<String, String>(16);
		param.put("payFromAccount", Base.payService.getConfig().getMchId());
		// 支付金额 精确小数点后两位
		param.put("faceMoney", faceMoney);
		param.put("name", name);
		long timestamp = System.currentTimeMillis();
		param.put("timestamp", timestamp + "");
		param.put("inOrderId", timestamp + "");
		param.put("payTypeId", payTypeId);
		// 交易跳转地址
		param.put("inJumpUrl", Base.GAME_URL+"/"+gameType);
		System.out.println("交易跳转地址:"+Base.GAME_URL+"/"+gameType);
		// 交易通知地址
		param.put("inNotifyUrl", Base.GAME_URL+"/pay/tradeNotice");
		EncryptUtils encryptUtils = new EncryptUtils();
		String sign = encryptUtils.signMD5ForDes(param, Base.payService.getConfig().getMchKey());
		System.out.println("签名源:" + param + ",md5=" + sign);
		// 签名数据
		param.put("sign", sign);
		// wap true 终端是移动端， false 是PC 端
		if (isPC.equals("1")) {
			param.put("wap", "false");
		}
		param.put("wap", "true");
		// 对特殊中文做url编码 -签名完成后做编码
		param.put("name", HttpUtil.encode(name, CharsetUtil.UTF_8));
		LotteryUserScore lotteryUserScore = null;// 用户信息
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		// 判断是否是纯数字
		if (pattern.matcher(data).matches()) {
			lotteryUserScore = lotteryUserService.getLotteryUserScoreTel(data);
		} else {
			lotteryUserScore = lotteryUserService.getLotteryUserScoreEmil(data);
		}
		if (lotteryUserScore == null) {
			response.put("msg", "未找到用户");
			return response;
		}
		UserRechargeRecords record = new UserRechargeRecords();
		if (type.equals("0")) {// 注册
			record.setRecharge_type(0);
		} else {// 充值
			record.setRecharge_type(1);
		}
		record.setInOrderId(timestamp+"");
		record.setNum(new BigDecimal(faceMoney));
		record.setTime(new Date(timestamp));
		record.setUid(lotteryUserScore.getId());
		BigDecimal recharge_num = new BigDecimal(faceMoney).multiply(new BigDecimal(100));
		record.setRecharge_num(recharge_num.intValue());
		userRechargeRecordsService.addRechargeRecords(record);
		System.out.println("生成请求交易支付链接：");
		System.out.println(Base.BASE_URL + "pgw.shtml?" + SignUtils.map2LinkString(param));
		response.put("url", Base.BASE_URL + "pgw.shtml?" + SignUtils.map2LinkString(param));
		return response;
	}

	/**	
	 * 接收交易信息
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@PostMapping("/tradeNotice")
	public String tradeNotice(HttpServletRequest request) throws UnsupportedEncodingException {
		System.out.println("已收到交易通知");
		String orderId = request.getParameter("orderId");// 订单号
		String results = request.getParameter("result");// 支付状态
		UserRechargeRecords record = userRechargeRecordsService.getRechargeRecordinOrderId(new BigDecimal(orderId));
		if (record != null && results.equals("710010")) {
			LotteryUserScore lotteryUserScore = lotteryUserService.getLotteryUserScoreId(record.getUid());
			try {
				userRechargeRecordsService.updateRechargeRecordin(new BigDecimal(orderId), 1);
				record.setRecharge_status(1);
				lotteryUserService.plusScoreFromDice(record.getUid(), record.getNum());
			} catch (Exception e) {
				userRechargeRecordsService.updateRechargeRecordin(new BigDecimal(orderId), 2);
				logger.error("给用户发积分失败，订单号：{}", orderId);
			}
			// 插入积分流水
			UserTransactionScore records = new UserTransactionScore(lotteryUserScore.getId(), Constants.GAME_TYPE_DICE,
					null, null, "1", "6", record.getNum(), null);
			PublicMethod.insertTransactionScore(records, userTransactionScoreRecordsService, logger);
			// 响应用户充值信息
			PublicMethod.sendScoreUserPay(lotteryUserScore.getId(), lotteryUserService, handler);
			return "success";
		}
		return "";
	}
	
	
	/**
	 * 提现操作
	 * @param score			积分(最多两位小数)
	 * @param name			姓名
	 * @param cardNumber	卡号
	 * @param bankCode		银行编码
	 * @param bankName		银行名称
	 * @throws Exception
	 */
	@PostMapping("/tradeWithdrawApply")
	public Map<String,Object> tradeWithdrawApply(@RequestParam String score,@RequestParam String name,@RequestParam String cardNumber,
 @RequestParam String bankCode, @RequestParam String bankName,
			HttpServletRequest request) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", Boolean.FALSE);
		// 验证登录
		LotteryUserScore userInSession = (LotteryUserScore) request.getSession()
				.getAttribute(Constants.USER_IN_SESSION);
		if (userInSession == null) {
			response.put("msg", "用户未登陆");
			return response;
		}
		// 用户信息
		LotteryUserScore lotteryUserScore = lotteryUserService.getLotteryUserScoreId(userInSession.getId());
		if (lotteryUserScore == null) {
			response.put("msg", "用户不存在");
			return response;
		}
		if(lotteryUserScore.getScore().compareTo(new BigDecimal(score)) < 0){
			response.put("msg", "余额不足");
			return response;
		}
		long timestamp = System.currentTimeMillis();
		BigDecimal num = new BigDecimal(score).multiply(new BigDecimal(100));
		// 插入提现信息
		UserWithdrawApplRecords withdrawAppl=new UserWithdrawApplRecords();
		withdrawAppl.setInorderid(timestamp+"");
		withdrawAppl.setNum(new BigDecimal(score));
		withdrawAppl.setRechargeNum(num.intValue());
		withdrawAppl.setRechargeStatus(0);
		withdrawAppl.setRechargeType(1);
		withdrawAppl.setUid(lotteryUserScore.getId());
		withdrawAppl.setTime(new Date(timestamp));
		userWithdrawApplRecordsService.insert(withdrawAppl);
		
		Map<String, String> param = new HashMap<String, String>(16);
		param.put("timestamp", timestamp + "");
		param.put("member_no", Base.payService.getConfig().getMemberNo());
		param.put("nonce_str", RandomUtil.simpleUUID());
		param.put("method", Base.METHOD_WITHDRAW_APPLY);
		param.put("version", Base.INTERFACE_VERSION);
		param.put("format_type", Base.INTERFACE_FORMAT_TYPE);
		WithdrawTradeReqVo reqVo = new WithdrawTradeReqVo();
		Long.parseLong(score);
		// 交易金额 单位:分
		reqVo.setAmount(num.longValue());
		// 商户订单号
		reqVo.setOutTradeNo(System.currentTimeMillis() + "");
		reqVo.setReturnParam("回传信息");
		BankAccountInfo bankAccountInfo = new BankAccountInfo();
		bankAccountInfo.setBankAccountName(name);
		bankAccountInfo.setBankAccountNo(cardNumber);
		bankAccountInfo.setBankCode(bankCode);
		// 银行名称
		bankAccountInfo.setBankName(bankName);
		// 账户类型，P 对私账户 ；C 对公账户
		bankAccountInfo.setBankAccountType("P");
		// 填充银行卡信息
		reqVo.setBankAccountInfo(bankAccountInfo);
		// 收款账户类型
		reqVo.setPayeeType(PayeeTypeEum.BANK_CARD.getCode());
		// 通知地址
		reqVo.setNotifyUrl(Base.GAME_URL + "/pay/onNotify");
		Map<String, Object> reqMap = Maps.newHashMap();
		// 转换为下滑线map
		reqMap = BeanUtil.beanToMap(reqVo, reqMap, true, true);
		String jsonValue = JSONObject.toJSONString(reqMap);
		DESede des = SecureUtil.desede(Base.payService.getConfig().getEncryptKey().getBytes(CharsetUtil.UTF_8));
		String desValue = des.encryptBase64(jsonValue);
		param.put("biz_body", desValue);
		param.put("encrypt_type", EncryptTypeEnum.DESEDE.getCode());

		String sign = SignUtils.sign(param, SignTypeEnum.SHA_256.getCode(), Base.payService.getConfig().getShaKey(),
				CharsetUtil.UTF_8);
		System.out.println("签名源:" + param + ",SHA_256 sign=" + sign);
		// 签名数据
		param.put("sign_type", SignTypeEnum.SHA_256.getCode());
		param.put("sign_data", sign);
		param.put("biz_body", ConvertUtils.safeUrlEncoder(desValue, CharsetUtil.UTF_8));
		String result = Base.payService.post(Base.GATEWAY_URL1, param, false);
		System.out.println("对方返回:" + result);
		Map<?, ?> resultMap = JSON.parseObject(result, Map.class);
		String str = "BUID,WPAR,UNKNOWN,FINI";
		if (str.contains((CharSequence) resultMap.get("trade_status"))) {
			// 扣除积分
			lotteryUserService.minusScoreToDice(lotteryUserScore.getId(), new BigDecimal(score));
			// 修改提现状态
			UserWithdrawApplRecords record = new UserWithdrawApplRecords();
			record.setInorderid(timestamp + "");
			record.setRechargeStatus(1);

			userWithdrawApplRecordsService.updateByPrimaryKeySelective(record);
			// 插入积分流水
			UserTransactionScore records = new UserTransactionScore(lotteryUserScore.getId(), null, null, null, "1",
					"7", new BigDecimal(score), null);
			PublicMethod.insertTransactionScore(records, userTransactionScoreRecordsService, logger);
			response.put("msg", "提现处理中。。");
			response.put("success", Boolean.TRUE);
		}else{
			response.put("msg", "提现失败");
		}
		return response;
	}
    /**
     * 提现异步通知结果处理方法
     *
     * @throws Exception
     */
	@PostMapping("/onNotify")
	public String onNotify(HttpServletRequest request) throws Exception {
		System.out.println("已收到提现通知");
		String orderId = request.getParameter("timestamp");// 订单号
		String results = request.getParameter("result");// 支付状态
		UserWithdrawApplRecords withdrawAppl = userWithdrawApplRecordsService.selectByPrimaryKey(orderId);
		if (withdrawAppl != null && results.equals("710010")) {// 成功到账
			//修改提现状态
			UserWithdrawApplRecords record = new UserWithdrawApplRecords();
			record.setInorderid(orderId);
			record.setRechargeStatus(2);
			userWithdrawApplRecordsService.updateByPrimaryKeySelective(record);
			return "success";
		}
		return "";
	}
}