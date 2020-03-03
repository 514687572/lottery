package com.dima.pay;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpUtil;
import com.dima.pay.enums.PayTypeEnum;
import com.dima.pay.util.Base;
import com.dima.pay.util.ConvertUtils;
import com.dima.pay.util.EncryptUtils;
import com.dima.pay.util.SignUtils;
import org.junit.Test;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author: iron
 * @description: 支付测试类
 * @date : created in  2018/2/24 14:55.
 */
public class PayDemoTest extends Base {
    /**
     * 支付跳转接口
     * @throws Exception
     */
    @Test
    public void onPay(){

        String inOrderId = String.valueOf(System.currentTimeMillis());
        // 支付方式类型
        String payTypeId = PayTypeEnum.UNI_EXP_PAY.getCode();
        String timestamp = String.valueOf(System.currentTimeMillis());
        Map<String, String> param = new HashMap<String, String>(16);
        param.put("payFromAccount", payService.getConfig().getMchId());
        //  支付金额 精确小数点后两位
        param.put("faceMoney", "3.00");
        param.put("name", "测试商品");
        param.put("timestamp", String.valueOf(timestamp));
        param.put("inOrderId", inOrderId);
        param.put("payTypeId", payTypeId);
        // 交易跳转地址
        param.put("inJumpUrl", GATEWAY_URL);
        // 交易通知地址
        param.put("inNotifyUrl", "10.0.0.171/tiger/notify");


        EncryptUtils encryptUtils = new EncryptUtils();
        String sign = encryptUtils.signMD5ForDes(param, payService.getConfig().getMchKey());
        System.out.println("签名源:" + param + ",md5=" + sign);
        // 签名数据
        param.put("sign", sign);
        // wap true 终端是移动端， false 是PC 端
        param.put("wap", "true");
        // 对特殊中文做url编码 -签名完成后做编码
        param.put("name", HttpUtil.encode("测试商品", CharsetUtil.UTF_8));
        System.out.println("生成请求交易支付链接：");
        System.out.println(BASE_URL + "pgw.shtml?" + SignUtils.map2LinkString(param));
    }

    /**
     * 订单查询 商户订单号+商户号查询
     */
    @Test
    public void onOrderQuery() {
        String url = BASE_URL + "pgw/query.json";
        long timestamp = System.currentTimeMillis();
        String orderId = "1519089646800";
        Map<String, String> param = new HashMap<String, String>(6);
        param.put("timestamp", timestamp + "");
        param.put("orderId", orderId);
        param.put("payFromAccount", payService.getConfig().getMchId());

        EncryptUtils encryptUtils = new EncryptUtils();
        String sign = encryptUtils.signMD5ForDes(param, payService.getConfig().getMchKey());
        logger.info("签名源:" + param + ",md5=" + sign);
        //签名数据
        param.put("sign", sign);

        url += "?" + SignUtils.map2LinkString(param);
        //实际使用通过url 进行跳转
        logger.info("对方返回  url:" + url);
    }

    /**
     * 交易同步和异步通知结果处理
     * 注意：当商户收到并正确处理通知后，请输出 1 或 true 或 success（不区分大小写，不要带标 签或其他字符），通知平台已成功接收并不要再通知。
     *
     * @throws Exception
     */
    @Test
    public void onNotify() throws Exception {
        String notifyResult = "result=710010&orderId=20180240040930&payCardType=DE&sign=4cae8710ccc5b23863a2aebf41975460&beginTime=2018-02-24 16:00:35&endTime=2018-02-24 16:00:56&transactionId=161112018022416003551303305&requestMoney=800.00&successMoney=800.00&timestamp=1519459256086";
        Map<String, String> result = ConvertUtils.getRequestDataMap(notifyResult);
        String remoteSign = result.remove("sign");
        Map<String, String> param = new HashMap<String, String>(6);
        param.put("result", result.get("result"));
        param.put("orderId", result.get("orderId"));
        param.put("transactionId", result.get("transactionId"));
        param.put("requestMoney", result.get("requestMoney"));
        param.put("successMoney", result.get("successMoney"));
        param.put("timestamp", result.get("timestamp"));
        String mySign = SignUtils.sign(param, "MD5", payService.getConfig().getMchKey(), "UTF-8");
        boolean signIsValid = remoteSign.equalsIgnoreCase(mySign);
        logger.info("返回的sign=" + remoteSign + ",我方对源[" + param + "]校验结果是:" + mySign + ",是否一致:" + signIsValid);
        Assert.isTrue(signIsValid,"签名数据不一致");

    }
}
