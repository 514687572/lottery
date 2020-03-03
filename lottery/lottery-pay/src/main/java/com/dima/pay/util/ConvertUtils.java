package com.dima.pay.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: iron
 * @description: 转换工具处理类
 * @date : created in  2018/2/24 16:10.
 */
public class ConvertUtils {
    protected static final Logger logger = LoggerFactory.getLogger(ConvertUtils.class);

    public static Map<String, String> getRequestDataMap(String requestString) {
        HashMap dataMap = new HashMap();
        if(StringUtils.isBlank(requestString)) {
            logger.warn("尝试处理的字符串为空");
            return dataMap;
        } else {
            String[] data = null;
            if(requestString.indexOf("?") > 0) {
                data = requestString.split("\\?");
            } else {
                data = new String[]{requestString};
            }

            String[] tempData = data[0].split("&");
            if(tempData.length < 0) {
                logger.warn("尝试处理的字符串无法用&分割:" + data[0]);
                return dataMap;
            } else {
                String[] var4 = tempData;
                int var5 = tempData.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    String d = var4[var6];
                    logger.debug("XXXXXXX 处理请求字符串:" + d);
                    String[] d2 = d.split("=");
                    if(d2.length == 2) {
                        logger.debug("放入参数:" + d2[0] + "=>" + d2[1]);
                        dataMap.put(d2[0], d2[1]);
                    }
                }

                return dataMap;
            }
        }
    }

    /**
     * 指定字符解码
     *
     * @param s
     * @param es
     * @return
     */
    public static String safeUrlDecoder(String s, String es) {
        if (StringUtils.isNotEmpty(s)) {
            try {
                return URLDecoder.decode(s, es);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 指定字符编码
     *
     * @param s
     * @param es
     * @return
     */
    public static String safeUrlEncoder(String s, String es) {
        if (StringUtils.isNotEmpty(s)) {
            try {
                return URLEncoder.encode(s, es);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
