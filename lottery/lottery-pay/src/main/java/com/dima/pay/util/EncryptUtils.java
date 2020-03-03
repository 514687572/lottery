package com.dima.pay.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Author: iron
 * @Description: 数据安全处理方法
 * @Date : created in  2017/12/6 16:54.
 */
public class EncryptUtils {

    /**
     * 进行M5 签名  数据按照字母大小排序
     * @param sParaTemp
     * @param signKey
     * @return
     */
    public final String signMD5ForDes(Map<String, String> sParaTemp,String signKey){
        List<String> keys = new ArrayList<String>(sParaTemp.keySet());
        Collections.sort(keys);
        StringBuffer sb = new StringBuffer();
        for (String key : keys) {
            String value = sParaTemp.get(key);
            if(StringUtils.isBlank(value)){
                continue;
            }
            sb.append(key);
            sb.append('=');
            sb.append(value);
            sb.append('&');
        }

        String signString = sb.append("key=").append(signKey).toString();
        String sign = DigestUtils.md5Hex(signString);
        return sign;
    }

}
