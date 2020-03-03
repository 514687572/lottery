package com.dima.pay;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DESede;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dima.pay.enums.SignTypeEnum;
import com.dima.pay.util.Base;
import com.dima.pay.util.ConvertUtils;
import com.dima.pay.util.SignUtils;
import com.dima.pay.vo.AuthRequestVo;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: iron
 * @description: 实名认证接口
 * @date : created in  2018/3/13 17:30.
 */
public class AuthTradeTest extends Base {


    /**
     * 实名鉴权接口
     */
    @Test
    public void bankAuthTradeApply() throws Exception {
        long timestamp = System.currentTimeMillis();
        Map<String, String> param = new HashMap<String, String>(6);
        param.put("timestamp", timestamp + "");
        param.put("member_no", payService.getConfig().getMemberNo());
        param.put("nonce_str", RandomUtil.simpleUUID());
        param.put("method", METHOD_AUTH_BANK_APPLY);
        param.put("version", INTERFACE_VERSION);
        param.put("format_type", INTERFACE_FORMAT_TYPE);
        AuthRequestVo reqVo = new AuthRequestVo();
        reqVo.setOutTradeNo(System.currentTimeMillis() + "");
        reqVo.setReturnParam("回传信息");
        reqVo.setAuthType("ALL");
        reqVo.setAuthType("NAME_CARD_ID");
        reqVo.setAuthType("NAME_CARD");
        reqVo.setCardNo("6222620910002880965");
        reqVo.setCardNo("6214867801868986");
        reqVo.setCardName("刘燕卿子");
        reqVo.setIdNum("130928198403102441");
        Map<String, Object> reqMap = Maps.newHashMap();
        reqMap = BeanUtil.beanToMap(reqVo, reqMap, true, true);
        String jsonValue = JSONObject.toJSONString(reqMap);
        logger.info("biz_body=" + jsonValue);

        DESede des = SecureUtil.desede(payService.getConfig().getEncryptKey().getBytes(CharsetUtil.UTF_8));
        String desValue = des.encryptBase64(jsonValue);
        param.put("biz_body", desValue);
        param.put("encrypt_type", "3DES");

        String sign = SignUtils.sign(param, SignTypeEnum.SHA_256.getCode(), payService.getConfig().getShaKey(), CharsetUtil.UTF_8);
        logger.info("签名源:" + param + ",SHA-256=" + sign);
        //签名数据
        param.put("sign_type", SignTypeEnum.SHA_256.getCode());
        param.put("sign_data", sign);
        param.put("biz_body", ConvertUtils.safeUrlEncoder(desValue, CharsetUtil.UTF_8));
        String result = payService.post(GATEWAY_URL, param, false);
        logger.info("对方返回:" + result);
        Map resultMap = JSON.parseObject(result, Map.class);
        String signType = (String) resultMap.remove("sign_type");
        String signData = (String) resultMap.remove("sign_data");
        boolean verifyResult = SignUtils.verify(signData, resultMap, signType, payService.getConfig().getMchKey(), "UTF-8");
        logger.info("对方返回 verifyResult:" + verifyResult);
    }


    /**
     * 鉴权查询接口
     */
    @Test
    public void tradeQuery() throws Exception {
        long timestamp = System.currentTimeMillis();
        Map<String, String> param = new HashMap<String, String>(6);
        param.put("timestamp", timestamp + "");
        param.put("member_no", payService.getConfig().getMemberNo());
        param.put("nonce_str", RandomUtil.simpleUUID());
        param.put("method", METHOD_AUTH_BANK_RESULT_QUERY);
        param.put("version", INTERFACE_VERSION);
        param.put("format_type", INTERFACE_FORMAT_TYPE);
        param.put("biz_body", "{out_trade_no:\"1529905827489\",trade_no:\"2018062513502842635314002657280\",return_param:\"记得回传给我\"}");

        String sign = SignUtils.sign(param, SignTypeEnum.SHA_256.getCode(), payService.getConfig().getShaKey(), CharsetUtil.UTF_8);
        logger.info("签名源:" + param + ",SHA_256=" + sign);
        //签名数据
        param.put("sign_type", SignTypeEnum.SHA_256.getCode());
        param.put("sign_data", sign);
        String result = payService.post(GATEWAY_URL, param, false);
        logger.info("对方返回:" + result);
        Map resultMap = JSON.parseObject(result, Map.class);
        String signType = (String) resultMap.remove("sign_type");
        String signData = (String) resultMap.remove("sign_data");
        boolean verifyResult = SignUtils.verify(signData, resultMap, signType, payService.getConfig().getMchKey(),CharsetUtil.UTF_8);
        logger.info("对方返回 verifyResult:" + verifyResult);
    }

}
