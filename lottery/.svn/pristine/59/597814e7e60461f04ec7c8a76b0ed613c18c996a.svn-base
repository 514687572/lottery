package com.dima.pay.enums;

/**
 * @author: iron
 * @description: 银行编码
 * @date : created in  2018/3/26 18:16.
 */
public enum BankCodeEnum {
    ICBC("ICBC", "中国工商银行"),
    ABC("ABC", "中国农业银行"),
    BOC("BOC", "中国银行"),
    CCB("CCB", "中国建设银行"),
    BCM("BCM", "交通银行"),
    TICIC("TICIC", "中信银行"),
    CEB("CEB", "中国光大银行"),
    HXB("HXB", "华夏银行"),
    CMBC("CMBC", "华夏银行"),
    CGB("CGB", "广发银行"),
    PAB("PAB", "平安银行"),
    CMB("CMB", "招商银行"),
    CIB("CIB", "兴业银行"),
    SPDB("SPDB", "浦发银行"),
    BOB("BOB", "北京银行"),
    EGB("EGB", "恒丰银行"),
    CZB("CZB", "浙商银行"),
    CBHB("CBHB", "渤海银行"),
    HSB("HSB", "徽商银行"),
    SRCB("SRCB", "上海农村商业银行"),
    BOS("BOS", "上海银行"),
    PSBC("PSBC", "中国邮政储蓄银行"),
    NBCB("NBCB", "宁波银行"),
    HZCB("HZCB", "杭州商业银行"),
    WFCCB("WFCCB", "潍坊银行"),
    BEA("BEA", "东亚银行"),
    NCB("NCB", "南洋商业银行"),
    HSBANK("HSBANK", "恒生银行"),
    SHBB("SHBB", "上海商业银行"),
    NJCB("NJCB", "南京银行"),
    QDCCB("QDCCB", "青岛银行"),
    TJCB("TJCB", "天津银行"),
    HBB("HBB", "河北银行"),
    BOCD("BOCD", "成都银行"),
    DEFAULT("DEFAULT", "默认值");
    private String code;
    private String cnName;

    public static BankCodeEnum getEnumByName(String cnName) {
        if (null == cnName) {
            return null;
        } else {
            BankCodeEnum[] arr$ = values();
            int len$ = arr$.length;

            for (int i$ = 0; i$ < len$; ++i$) {
                BankCodeEnum c = arr$[i$];
                if (cnName.equals(c.cnName)) {
                    return c;
                }
            }
            return null;
        }
    }

    public static BankCodeEnum getEnumByCode(String code) {
        if (null == code) {
            return null;
        } else {
            BankCodeEnum[] arr$ = values();
            int len$ = arr$.length;

            for (int i$ = 0; i$ < len$; ++i$) {
                BankCodeEnum c = arr$[i$];
                if (code.equals(c.code)) {
                    return c;
                }
            }
            return null;
        }
    }

    private BankCodeEnum(String code, String cnName) {
        this.code = code;
        this.cnName = cnName;
    }


    public String getCode() {
        return this.code;
    }

    public String getCnName() {
        return this.cnName;
    }

}
