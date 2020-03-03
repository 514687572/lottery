package com.dima.pay.enums;

/**
 * @author: iron
 * @description: 收款账户类型
 * @date : created in  2018/6/29 17:28.
 */
public enum PayeeTypeEum {
    BANK_CARD("bankCard", "银行卡"),
    ALI_APY_ACCOUNT("aliPayAccount", "支付宝账户");
    private String code;
    private String desc;

    private PayeeTypeEum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static PayeeTypeEum getEnum(String code) {
        PayeeTypeEum[] arr$ = values();
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            PayeeTypeEum c = arr$[i$];
            if(code.equals(c.code)) {
                return c;
            }
        }
        return null;
    }
}
