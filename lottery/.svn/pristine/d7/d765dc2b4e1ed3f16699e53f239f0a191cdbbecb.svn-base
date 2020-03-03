package com.dima.pay;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.dima.pay.enums.SignTypeEnum;
import com.dima.pay.util.Base;
import com.dima.pay.util.SignUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: iron
 * @description: 账户余额查询
 * @date : created in  2018/2/24 15:39.
 */
public class AccountBalanceQueryTest extends Base {
    /**
     * 余额查询接口
     */
    @Test
    public void balanceQuery() throws Exception {
        long timestamp = System.currentTimeMillis();
        Map<String, String> param = new HashMap<String, String>(6);
        param.put("timestamp", timestamp + "");
        param.put("member_no", payService.getConfig().getMemberNo());
        param.put("nonce_str", RandomUtil.simpleUUID());
        param.put("method", METHOD_ACCOUNT_QUERY);
        param.put("version", INTERFACE_VERSION);
        param.put("format_type", INTERFACE_FORMAT_TYPE);
        param.put("biz_body", "{out_trade_no:\"aaaa\"}");
        String sign = SignUtils.sign(param, SignTypeEnum.SHA_256.getCode(), payService.getConfig().getMchKey(), CharsetUtil.UTF_8);
        logger.info("签名源:" + param + ",SHA-256=" + sign);
        //签名数据
        param.put("sign_type", SignTypeEnum.SHA_256.getCode());
        param.put("sign_data", sign);
        String result = payService.post(GATEWAY_URL, param, false);
        logger.info("对方返回:" + result);
        Map resultMap = JSON.parseObject(result, Map.class);
        String signType = (String) resultMap.remove("sign_type");
        String signData = (String) resultMap.remove("sign_data");
        boolean verifyResult = SignUtils.verify(signData, resultMap, signType, payService.getConfig().getMchKey(), CharsetUtil.UTF_8);
        logger.info("对方返回 verifyResult:" + verifyResult);
        if(verifyResult){
            logger.info("账户信息 可用余额:" +  resultMap.get("available_amount")+"（单位：分）");
            logger.info("账户信息 待结算金额:" + resultMap.get("settle_amount")+"（单位：分）");
            logger.info("账户信息 冻结金额:" + resultMap.get("freeze_amount")+"（单位：分）");
            logger.info("账户信息 货币类型:" + resultMap.get("currency"));
        }
    }
}
