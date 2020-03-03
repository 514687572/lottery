package com.dima.pay.enums;

/**
 * @author: iron
 * @description: 支付类型枚举类
 * @date : created in  2018/2/24 14:51.
 */
public enum  PayTypeEnum {
    WX_H5_PAY("1", "微信H5支付"),
    ALIPAY("2", "支付宝支付"),
    WX_JSI_PAY("101", "微信公众号支付"),
    QQ_PAY("3", "QQ钱包支付"),
    E_CUR_PAY("4", "网银支付"),
    JD_PAY("5", "京东支付"),
    BANK_SCAN_PAY("6", "银行卡扫码支付"),
    UNI_EXP_PAY("7", "银联快捷支付"),
    HEE_CARD_PAY("8", "骏宝卡支付");

    private String code;

    private String desc;

    private PayTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code == null ? this.name() : this.code;
    }

    public String getDesc() {
        return this.desc == null ? this.name() : this.desc;
    }

    public static PayTypeEnum getEnum(String code) {
        if (null == code) {
            return null;
        } else {
            PayTypeEnum[] arr$ = values();
            int len$ = arr$.length;

            for (int i$ = 0; i$ < len$; ++i$) {
                PayTypeEnum c = arr$[i$];
                if (code.equals(c.code)) {
                    return c;
                }
            }
            return null;
        }
    }
}
