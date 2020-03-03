package com.dima.pay;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DESede;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dima.pay.enums.EncryptTypeEnum;
import com.dima.pay.enums.SignTypeEnum;
import com.dima.pay.util.Base;
import com.dima.pay.util.ConvertUtils;
import com.dima.pay.util.SignUtils;
import com.dima.pay.vo.QueryCardInfoVo;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: iron
 * @description: 卡bin查询信息
 * @date : created in  2018/5/10 16:53.
 */
public class CardBinQueryTest extends Base {
    /**
     * 根据银行卡号查询银行卡信息接口
     */
    @Test
    public void queryBankCardInfo() throws Exception {
        String url = BASE_URL + "gateway/index.json";
        long timestamp = System.currentTimeMillis();
        Map<String, String> param = new HashMap<String, String>(6);
        param.put("timestamp", timestamp + "");
        param.put("member_no", payService.getConfig().getMemberNo());
        param.put("nonce_str", RandomUtil.simpleUUID());
        param.put("method", METHOD_CARD_INFO_QUERY);
        param.put("version", INTERFACE_VERSION);
        param.put("format_type", INTERFACE_FORMAT_TYPE);
        QueryCardInfoVo reqVo = new QueryCardInfoVo();
        // 银行卡号
        reqVo.setCardNo("6259655533117715");

        Map<String, Object> reqMap = Maps.newHashMap();
        reqMap = BeanUtil.beanToMap(reqVo, reqMap, true, true);
        String jsonValue = JSONObject.toJSONString(reqMap);
        logger.info("biz_body=" + jsonValue);

        DESede des = SecureUtil.desede(payService.getConfig().getEncryptKey().getBytes(CharsetUtil.UTF_8));
        String desValue = des.encryptBase64(jsonValue);
        param.put("biz_body", desValue);
        param.put("encrypt_type", EncryptTypeEnum.DESEDE.getCode());
        String sign = SignUtils.sign(param, SignTypeEnum.SHA_256.getCode(), payService.getConfig().getMchKey(), CharsetUtil.UTF_8);
        logger.info("签名源:" + param + ",SHA-256=" + sign);

        // 由于BASE64 存在加号特殊字符
        param.put("biz_body", ConvertUtils.safeUrlEncoder(desValue, CharsetUtil.UTF_8));
        //签名数据
        param.put("sign_type", SignTypeEnum.SHA_256.getCode());
        param.put("sign_data", sign);
        String result = payService.post(url, param, false);
        logger.info("对方返回:" + result);
        Map resultMap = JSON.parseObject(result, Map.class);
        String signType = (String) resultMap.remove("sign_type");
        String signData = (String) resultMap.remove("sign_data");
        boolean verifyResult = SignUtils.verify(signData, resultMap, signType, payService.getConfig().getMchKey(),CharsetUtil.UTF_8);
        logger.info("对方返回 verifyResult:" + verifyResult);
    }
}
