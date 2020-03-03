package com.dima.pay.util;

import com.dima.pay.config.DimaPayConfig;
import com.dima.pay.service.impl.PayServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: iron
 * @description: 公共父类
 * @date : created in  2018/2/24 13:38.
 */
public class Base {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static PayServiceImpl payService ;
    /**
     * 交易网关地址
     */
    public final static String GATEWAY_URL = "http://47.105.89.165:8888/pgw.shtml";
    /**
	 * 交易网关地址
	 */
	public final static String GATEWAY_URL1 = "http://47.105.89.165:8888/gateway/index.json";
    /**
     * 交易返回地址
     */
//    public final static String GAME_URL = "http://3s.dkys.org:27357/lottery";
    public final static String GAME_URL = "https://myeosgame.com";
    /**
     * 基础url
     */
    public final static String BASE_URL = "http://47.105.89.165:8888/";
    /**
     * 接口版本 固定1.0
     */
    public static final String INTERFACE_VERSION = "1.0";
    /**
     * 接口数据格式化
     */
    public static final String INTERFACE_FORMAT_TYPE = "JSON";
    static {
		payService = new PayServiceImpl();
		DimaPayConfig dimaPayConfig = new DimaPayConfig();

		dimaPayConfig.setMchId("8385576");
		dimaPayConfig.setMemberNo("360800000008385576");
		dimaPayConfig.setEncryptKey("bt@EhezwjFS13%brCnn%mecx");
		dimaPayConfig.setMchKey("jx6XPSEBeW*");
		dimaPayConfig.setShaKey("eeM@nT5NX#jM6XPSEBeW*A1H");

		payService.setConfig(dimaPayConfig);
	}

    /**
     * 账户余额查询
     */
    public static final String METHOD_ACCOUNT_QUERY = "dima.account.query";
    /**
     * 提现转账申请接口
     */
    public static final String METHOD_WITHDRAW_APPLY = "dima.withdraw.tradeApply";
    /**
     * 提现单申请接口
     */
    public static final String METHOD_WITHDRAW_QUERY = "dima.withdraw.tradeQuery";
    /**
     * 根据银行卡号查询银行卡信息接口
     */
    public static final String METHOD_CARD_INFO_QUERY = "dima.cardCenter.queryBankCardInfo";
    /**
     * 实名认证接口
     */
    public static final String METHOD_AUTH_BANK_APPLY = "dima.authTrade.authBankInfo";
    /**
     * 认证结果查询
     */
    public static final String METHOD_AUTH_BANK_RESULT_QUERY = "dima.authTrade.queryAuthBankInfo";

}
