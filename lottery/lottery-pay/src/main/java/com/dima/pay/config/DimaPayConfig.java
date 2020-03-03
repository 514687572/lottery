package com.dima.pay.config;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * @author: iron
 * @description: dima支付配置.
 * @date : created in  2018/1/16 13:24.
 */
public class DimaPayConfig {

  /**
   * http请求连接超时时间
   */
  private int httpConnectionTimeout = 5000;

  /**
   * http请求数据读取等待时间
   */
  private int httpTimeout = 10000;
  /**
   * 商户号
   */
  private String mchId;
  /**
   * 会员id
   */
  private String memberNo;
  /**
   * 商户密钥-支付签名密钥
   */
  private String mchKey;
  /**
   * SHA-256 签名密钥
   */
  private String shaKey;
  private String signType;
  private String encryptKey;
  private SSLContext sslContext;
  private String keyPath;
  private String httpProxyHost;
  private Integer httpProxyPort;
  private String httpProxyUsername;
  private String httpProxyPassword;

  public String getKeyPath() {
    return keyPath;
  }

  /**
   * 设置证书
   *
   * @param keyPath apiclient_cert.p12的文件的绝对路径
   */
  public void setKeyPath(String keyPath) {
    this.keyPath = keyPath;
  }

  /**
   * 商户号
   */
  public String getMchId() {
    return this.mchId;
  }

  public void setMchId(String mchId) {
    this.mchId = mchId;
  }

  /**
   * 商户密钥
   */
  public String getMchKey() {
    return this.mchKey;
  }

  public void setMchKey(String mchKey) {
    this.mchKey = mchKey;
  }





  /**
   * 签名方式
   * 有两种SHA256 和MD5
   * @see
   */
  public String getSignType() {
    return this.signType;
  }

  public void setSignType(String signType) {
    this.signType = signType;
  }

  public SSLContext getSslContext() {
    return this.sslContext;
  }

  public void setSslContext(SSLContext sslContext) {
    this.sslContext = sslContext;
  }


  /**
   * http请求连接超时时间
   */
  public int getHttpConnectionTimeout() {
    return this.httpConnectionTimeout;
  }

  public void setHttpConnectionTimeout(int httpConnectionTimeout) {
    this.httpConnectionTimeout = httpConnectionTimeout;
  }

  /**
   * http请求数据读取等待时间
   */
  public int getHttpTimeout() {
    return this.httpTimeout;
  }

  public void setHttpTimeout(int httpTimeout) {
    this.httpTimeout = httpTimeout;
  }

  public String getHttpProxyHost() {
    return httpProxyHost;
  }

  public void setHttpProxyHost(String httpProxyHost) {
    this.httpProxyHost = httpProxyHost;
  }

  public Integer getHttpProxyPort() {
    return httpProxyPort;
  }

  public void setHttpProxyPort(Integer httpProxyPort) {
    this.httpProxyPort = httpProxyPort;
  }

  public String getHttpProxyUsername() {
    return httpProxyUsername;
  }

  public void setHttpProxyUsername(String httpProxyUsername) {
    this.httpProxyUsername = httpProxyUsername;
  }

  public String getHttpProxyPassword() {
    return httpProxyPassword;
  }

  public void setHttpProxyPassword(String httpProxyPassword) {
    this.httpProxyPassword = httpProxyPassword;
  }

  public SSLContext initSSLContext() throws Exception {
    if (StringUtils.isBlank(this.getMchId())) {
      throw new RuntimeException("EIS0000002,请确保商户号mchId已设置");
    }

    if (StringUtils.isBlank(this.getKeyPath())) {
      throw new RuntimeException("EIS0000002,请确保证书文件地址keyPath已配置");
    }

    InputStream inputStream;
    final String prefix = "classpath:";
    String fileHasProblemMsg = "证书文件【" + this.getKeyPath() + "】有问题，请核实！";
    String fileNotFoundMsg = "证书文件【" + this.getKeyPath() + "】不存在，请核实！";
    if (this.getKeyPath().startsWith(prefix)) {
      String path = StringUtils.removeStart(this.getKeyPath(), prefix);
      if (!path.startsWith("/")) {
        path = "/" + path;
      }
      inputStream = DimaPayConfig.class.getResourceAsStream(path);
      if (inputStream == null) {
        throw new RuntimeException("EIS0000002,"+fileNotFoundMsg);
      }
    } else {
      try {
        File file = new File(this.getKeyPath());
        if (!file.exists()) {
          throw new RuntimeException("EIS0000002,"+fileNotFoundMsg);
        }

        inputStream = new FileInputStream(file);
      } catch (IOException e) {
        throw new RuntimeException(fileHasProblemMsg+ e.getMessage());
      }
    }

    try {
      KeyStore keystore = KeyStore.getInstance("PKCS12");
      char[] partnerId2charArray = this.getMchId().toCharArray();
      keystore.load(inputStream, partnerId2charArray);
      this.sslContext = SSLContexts.custom().loadKeyMaterial(keystore, partnerId2charArray).build();
      return this.sslContext;
    } catch (Exception e) {
      throw new RuntimeException(fileHasProblemMsg+ e.getMessage());
    } finally {
      IOUtils.closeQuietly(inputStream);
    }
  }


  public void setEncryptKey(String encryptKey) {
    this.encryptKey = encryptKey;
  }

  public String getEncryptKey() {
    return encryptKey;
  }

  public String getMemberNo() {
    return memberNo;
  }

  public void setMemberNo(String memberNo) {
    this.memberNo = memberNo;
  }

  public String getShaKey() {
    return shaKey;
  }

  public void setShaKey(String shaKey) {
    this.shaKey = shaKey;
  }
}
