package com.dima.pay.vo;


/**
 * @author: iron
 * @description: 提现支付请求vo
 * @date : created in  2018/3/26 10:25.
 */
public class WithdrawTradeReqVo extends TradeBaseResVo{
    /**
     * 银行账户信息
     */
    private BankAccountInfo bankAccountInfo;
    /**
     * 商户异步通知地址
     */
    private String notifyUrl;
    /**
     * 商户同步跳转地址
     */
    private String returnUrl;
    /**
     * 交易金额 单位分
     */
    private long amount;
    /**
     * 货币类型 默认人民币
     */
    private String currency = "CNY";
    /**
     * 第三方账户信息
     */
    private IdentityAccountInfo identityAccountInfo;
    /**
     * 收款账户类型
     */
    private String payeeType;

    public BankAccountInfo getBankAccountInfo() {
        return bankAccountInfo;
    }

    public void setBankAccountInfo(BankAccountInfo bankAccountInfo) {
        this.bankAccountInfo = bankAccountInfo;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public IdentityAccountInfo getIdentityAccountInfo() {
        return identityAccountInfo;
    }

    public void setIdentityAccountInfo(IdentityAccountInfo identityAccountInfo) {
        this.identityAccountInfo = identityAccountInfo;
    }

    public String getPayeeType() {
        return payeeType;
    }

    public void setPayeeType(String payeeType) {
        this.payeeType = payeeType;
    }
}
