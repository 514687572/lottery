package com.dima.pay;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DESede;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dima.pay.enums.*;
import com.dima.pay.util.Base;
import com.dima.pay.util.ConvertUtils;
import com.dima.pay.util.EncryptUtils;
import com.dima.pay.util.SignUtils;
import com.dima.pay.vo.BankAccountInfo;
import com.dima.pay.vo.WithdrawTradeReqVo;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.springframework.util.Assert;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: iron
 * @description: 提现接口测试DEMO
 * @date : created in  2018/3/13 17:30.
 */
public class WithdrawTradeTest extends Base {


    /**
     * 提现单查询接口
     */
    @Test
    public void tradeWithdrawApply() throws Exception {

        long timestamp = System.currentTimeMillis();
        Map<String, String> param = new HashMap<String, String>(16);
        param.put("timestamp", timestamp + "");
        param.put("member_no", payService.getConfig().getMemberNo());
        param.put("member_no", "360800093028385608");
        param.put("nonce_str", RandomUtil.simpleUUID());
        param.put("method", METHOD_WITHDRAW_APPLY);
        param.put("version", INTERFACE_VERSION);
        param.put("format_type", INTERFACE_FORMAT_TYPE);
        WithdrawTradeReqVo reqVo = new WithdrawTradeReqVo();
        //交易金额 单位:分
        reqVo.setAmount(100);
        //商户订单号
        reqVo.setOutTradeNo(System.currentTimeMillis() + "");
        reqVo.setReturnParam("回传信息");
        BankAccountInfo bankAccountInfo = new BankAccountInfo();
        bankAccountInfo.setBankAccountName("王大锤6");
        bankAccountInfo.setBankAccountNo("82241111111111111");
        bankAccountInfo.setBankCode(BankCodeEnum.BOC.getCode());
        // 银行名称
        bankAccountInfo.setBankName(BankCodeEnum.BOC.getCnName());
        // 账户类型，P 对私账户  ；C 对公账户
        bankAccountInfo.setBankAccountType("P");
        //支行号,联行号
        bankAccountInfo.setUniteBankNo("411111111");
        // 填充银行卡信息
        reqVo.setBankAccountInfo(bankAccountInfo);
        // 收款账户类型
        reqVo.setPayeeType(PayeeTypeEum.BANK_CARD.getCode());
        //通知地址
        reqVo.setNotifyUrl("http://www.baidu.com");

        Map<String,Object>  reqMap = Maps.newHashMap();
        // 转换为下滑线map
        reqMap = BeanUtil.beanToMap(reqVo, reqMap, true, true);
        String jsonValue = JSONObject.toJSONString(reqMap);
        logger.info("biz_body="+jsonValue);

        DESede des = SecureUtil.desede(payService.getConfig().getEncryptKey().getBytes(CharsetUtil.UTF_8));
        String desValue = des.encryptBase64(jsonValue);
        param.put("biz_body", desValue);
        param.put("encrypt_type", EncryptTypeEnum.DESEDE.getCode());

        String sign = SignUtils.sign(param,SignTypeEnum.SHA_256.getCode(), payService.getConfig().getShaKey(), CharsetUtil.UTF_8);
        logger.info("签名源:" + param + ",SHA_256 sign=" + sign);
        //签名数据
        param.put("sign_type", SignTypeEnum.SHA_256.getCode());
        param.put("sign_data", sign);
        param.put("biz_body", ConvertUtils.safeUrlEncoder(desValue, CharsetUtil.UTF_8));
        String result = payService.post(GATEWAY_URL, param, false);
        logger.info("对方返回:" + result);
        Map resultMap = JSON.parseObject(result, Map.class);
        String signType = (String) resultMap.remove("sign_type");
        String signData = (String) resultMap.remove("sign_data");
        boolean verifyResult = SignUtils.verify(signData, resultMap, signType, payService.getConfig().getMchKey(), CharsetUtil.UTF_8);
        logger.info("对方返回 verifyResult:" + verifyResult);
    }
    /**
     * 提现异步通知结果处理方法
     *
     * @throws Exception
     */
    @Test
    public void onNotify() throws Exception {
        String notifyResult = "beginTime=2018-02-26 11:00:03&endTime=2018-02-26 11:05:00&result=710010&orderId=6215581812003053918_1519614003920_7142&transactionId=164162018022611000339607498&timestamp=1519614300588&successMoney=13000.00&sign=5b814137a6243e1d192cf4be04006378";
        Map<String, String> result = ConvertUtils.getRequestDataMap(notifyResult);
        String remoteSign = result.remove("sign");
        Map<String, String> param = new HashMap<String, String>(6);
        param.put("result", result.get("result"));
        param.put("orderId", result.get("orderId"));
        param.put("transactionId", result.get("transactionId"));
        param.put("successMoney", result.get("successMoney"));
        param.put("timestamp", result.get("timestamp"));
        String mySign = SignUtils.sign(param, "MD5", payService.getConfig().getMchKey(), "UTF-8");
        boolean signIsValid = remoteSign.equalsIgnoreCase(mySign);
        logger.info("返回的sign=" + remoteSign + ",我方对源[" + param + "]校验结果是:" + mySign + ",是否一致:" + signIsValid);
        Assert.isTrue(signIsValid,"签名数据不一致");
    }
    /**
     * 提现单查询接口
     */
    @Test
    public void tradeQuery() throws Exception {
        long timestamp = System.currentTimeMillis();
        Map<String, String> param = new HashMap<String, String>(6);
        param.put("timestamp", timestamp + "");
        param.put("member_no", payService.getConfig().getMchId());
        param.put("nonce_str", RandomUtil.simpleUUID());
        param.put("method", METHOD_WITHDRAW_QUERY);
        param.put("version", INTERFACE_VERSION);
        param.put("format_type", INTERFACE_FORMAT_TYPE);
        // 查询的商户订单号
        param.put("biz_body", "{out_trade_no:\"1523350907689\"}");

        String sign = SignUtils.sign(param,SignTypeEnum.SHA_256.getCode(), payService.getConfig().getShaKey(), CharsetUtil.UTF_8);
        logger.info("签名源:" + param + ",md5=" + sign);
        //签名数据
        param.put("sign_type", SignTypeEnum.SHA_256.getCode());
        param.put("sign_data", sign);
        String result = payService.post(GATEWAY_URL, param, false);
        logger.info("对方返回:" + result);
        Map resultMap = JSON.parseObject(result, Map.class);
        String signType = (String) resultMap.remove("sign_type");
        String signData = (String) resultMap.remove("sign_data");
        boolean verifyResult = SignUtils.verify(signData, resultMap, signType, payService.getConfig().getShaKey(), CharsetUtil.UTF_8);
        logger.info("对方返回 verifyResult:" + verifyResult);
    }

    /**
     * 查询历史全部交易数据
     * @throws Exception
     */
    @Test
    public void onQueryList() throws Exception {
        String url = BASE_URL + "withdraw/list.json";
        long timestamp = System.currentTimeMillis();
        Map<String, String> param = new HashMap<String, String>(6);
        param.put("uuid", payService.getConfig().getMchId());
        param.put("beginTime", DateUtil.format(DateUtil.yesterday(), DatePattern.NORM_DATETIME_PATTERN));
        param.put("endTime", "2018-03-30 00:00:00");
        param.put("timestamp", timestamp/1000+"");

        EncryptUtils encryptUtils = new EncryptUtils();
        String sign = encryptUtils.signMD5ForDes(param, payService.getConfig().getMchKey());
        //签名数据
        param.put("sign", sign);
        //交易状态 不参与签名 可以不填写
//        param.put("status", "710010");

        param.put("beginTime", URLEncoder.encode(param.get("beginTime")));
        param.put("endTime",URLEncoder.encode(param.get("endTime")));
        url += "?" + SignUtils.map2LinkString(param);
        logger.info("url:" + url);
        //将文件下载后保存在E盘，返回结果为下载文件大小
        long size = HttpUtil.downloadFile(url, FileUtil.file("E:/"));
        logger.info("对方返回:" + size);
    }

}
