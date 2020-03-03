package com.dima.pay.vo;


/**
 * @author: iron
 * @description: 鉴权请求处理类
 * @date : created in  2018/6/19 13:04.
 */
public class AuthRequestVo extends TradeBaseReqVo{
    /**
     * 鉴权类型
     */
    private String authType;
    /**
     * 卡号
     */
    private String cardNo;
    /**
     * 用户名称
     */
    private String cardName;
    /**
     * 手机号
     */
    private String mobileNo;
    /**
     * 证件号
     */
    private String idNum;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }
}
