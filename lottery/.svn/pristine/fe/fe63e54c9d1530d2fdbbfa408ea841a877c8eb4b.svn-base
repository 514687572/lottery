package com.dima.pay.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * @Author: iron
 * @Description: 数据签名处理方法
 * @Date : created in  2017/12/6 16:54.
 */
public class SignUtils {
    protected static final Logger logger = LoggerFactory.getLogger(SignUtils.class);

    private static final String[] hexStrings;

    static {
        hexStrings = new String[256];
        for (int i = 0; i < 256; i++) {
            StringBuilder d = new StringBuilder(2);
            char ch = Character.forDigit(((byte) i >> 4) & 0x0F, 16);
            d.append(Character.toUpperCase(ch));
            ch = Character.forDigit((byte) i & 0x0F, 16);
            d.append(Character.toUpperCase(ch));
            hexStrings[i] = d.toString();
        }
    }

    /**
     * 计算签名
     * @param map 有key和value的map，使用=和&拼接所有参数，
     *            "sign_type", "sign_data", "encrypt_type", "encrypt_data"不参加计算
     * @param algorithm 签名算法 MD5, SHA-1, SHA-256
     * @param salt 签名密钥
     * @param charset 字符串编码
     * @return 签名
     */
    public static String sign(Map map, String algorithm, String salt, String charset) throws UnsupportedEncodingException {
        String linkString = map2LinkString(map);
        String data = linkString +"&key="+ salt;
        logger.info("签名源 [ " + data+" ]");
        return digestHex(algorithm, data, charset);
    }

    public static String sign4LinkMap(Map map, String algorithm, String salt,String paddingChar, String charset,boolean isPaddEmpty) throws UnsupportedEncodingException {
        String linkString = linkMap2LinkString(map,paddingChar,isPaddEmpty);
        String data = linkString +paddingChar+"key="+ salt;
        logger.info("签名源 [ " + data+" ]");
        return digestHex(algorithm, data, charset);
    }
    /**
     * 对数据进行指定算法的数据摘要
     *
     * @param algorithm 算法名，如MD2, MD5, SHA-1, SHA-256, SHA-512
     * @param data      待计算的数据
     * @param charset   字符串的编码
     * @return 摘要结果
     */
    public static String digestHex(String algorithm, String data, String charset) throws UnsupportedEncodingException {
        byte[] digest = DigestUtils.getDigest(algorithm).digest(data.getBytes(charset));
        return hexString(digest);
    }
    /**
     * 将MAP数据用=和&拼接成String
     * @param map 数据
     * @return 字符串
     */
    public static String map2LinkString(Map map) {
        ArrayList<String> mapKeys = new ArrayList<String>(map.keySet());
        Collections.sort(mapKeys);
        StringBuilder link = new StringBuilder();
        boolean first = true;
        for_map_keys:
        for(String key: mapKeys) {
            Object value = map.get(key);
            if(null!=value && StringUtils.isEmpty(value.toString())){
                continue;
            }
            if (!first) link.append("&");
            link.append(key).append("=").append(value.toString());
            if (first) first = false;
        }
        return link.toString();
    }


    /**
     *  指定链接字符并有序拼接
     * @param linkMap
     * @param paddingChar
     * @return
     */
    public static String linkMap2LinkString(Map linkMap,String paddingChar,boolean isPaddEmpty) {
        ArrayList<String> mapKeys = new ArrayList<String>(linkMap.keySet());
        StringBuilder link = new StringBuilder();
        boolean first = true;
        for_map_keys:
        for(String key: mapKeys) {
            Object value = linkMap.get(key);
            if(null!=value && StringUtils.isEmpty(value.toString()) && !isPaddEmpty){
                continue;
            }
            if (!first) link.append(paddingChar);
            link.append(key).append("=").append(StringUtils.trimToEmpty((String) value));
            if (first) first = false;
        }
        return link.toString();
    }
    /**
     * 验证签名正确性。
     * @param sign 签名数据
     * @param map 数据
     * @param algorithm 签名算法 MD5, SHA-1, SHA-256
     * @param salt 签名密钥
     * @param charset 字符串
     * @return 验证结果
     */
    public static boolean verify(String sign, Map map, String algorithm, String salt,
                                 String charset) throws UnsupportedEncodingException {
        if (sign==null || "".equals(sign.trim()) || map.size()==0) return false;
        String newSign = sign(map, algorithm, salt, charset);
        boolean signIsValid = newSign.equalsIgnoreCase(sign);
        logger.info("返回的sign=" + sign + ",我方对源[" + map + "]校验结果是:" + newSign + ",是否一致:" + signIsValid);
        return signIsValid;
    }
    /**
     * 将字节数组转换成HEX String
     *
     * @param b
     * @return HEX String
     */
    public static String hexString(byte[] b) {
        StringBuilder d = new StringBuilder(b.length * 2);
        for (byte aB : b) {
            d.append(hexStrings[(int) aB & 0xFF]);
        }
        return d.toString();
    }
}
