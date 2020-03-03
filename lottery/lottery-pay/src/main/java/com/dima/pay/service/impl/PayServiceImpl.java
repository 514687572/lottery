package com.dima.pay.service.impl;

import cn.hutool.http.HttpUtil;
import com.dima.pay.service.PayServiceAbstract;
import com.dima.pay.vo.PayApiData;
import com.sun.corba.se.impl.ior.OldJIDLObjectKeyTemplate;
import org.apache.commons.lang.StringUtils;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.RedirectException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.net.ssl.SSLContext;

/**
 * @author: iron
 * @description: 支付处理类
 * @date : created in  2018/2/24 10:09.
 */
public class PayServiceImpl extends PayServiceAbstract {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected static ThreadLocal<PayApiData> wxApiData = new ThreadLocal<>();

    /**
     * 发送post 请求
     *
     * @param url        请求地址
     * @param paramMap 请求信息
     * @param useKey     是否使用证书
     * @return
     * @throws Exception
     */
    @Override
    public String post(String url, Map paramMap, boolean useKey) throws Exception {
        String responseString = HttpUtil.post(url,paramMap,this.getConfig().getHttpTimeout());
        this.logger.info("\n【请求地址】：{}\n【响应数据】：{}", url, responseString);
        return responseString;
    }



    /**
     * 发送Get请求
     *
     * @param url
     * @return
     */
    @Override
    public String sendGetData(String url) {
        String responseString = HttpUtil.get(url, this.getConfig().getHttpTimeout());
        this.logger.info("\n【请求地址】：{}\n【响应数据】：{}", url, responseString);
        return responseString;
    }
}
