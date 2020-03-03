package com.dima.pay.service;

import com.dima.pay.config.DimaPayConfig;

import java.util.Map;

/**
 * @author: iron
 * @description: 支付service
 * @date : created in  2018/2/24 10:07.
 */
public abstract class PayServiceAbstract {
    protected DimaPayConfig config;

    public DimaPayConfig getConfig() {
        return this.config;
    }

    public void setConfig(DimaPayConfig config) {
        this.config = config;
    }
    /**
     * 发送post请求
     *
     * @param url        请求地址
     * @param paramMap 请求信息
     * @param useKey     是否使用证书
     * @return 返回请求结果字符串
     */
    public abstract String post(String url, Map<String, Object> paramMap, boolean useKey) throws Exception;

    /**
     * 发送get 请求数据
     * @param url 请求地址
     * @return 返回请求结果字符串
     * @throws Exception
     */
    public abstract String sendGetData(String url)throws Exception;
}
