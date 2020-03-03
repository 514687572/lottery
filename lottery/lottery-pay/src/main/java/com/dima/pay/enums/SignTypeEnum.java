package com.dima.pay.enums;

/**
 * @author: iron
 * @description: 签名类型
 * @date : created in  2018/3/30 11:13.
 */
public enum  SignTypeEnum {
    MD5 ("MD5","MD5"),
    SHA_256 ("SHA-256","SHA-256");
    private String code;
    private String cnName;

    public static SignTypeEnum getEnum(String code) {
        if(null == code) {
            return null;
        } else {
            SignTypeEnum[] arr$ = values();
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                SignTypeEnum c = arr$[i$];
                if(code.equals(c.code)) {
                    return c;
                }
            }
            return null;
        }
    }

    private SignTypeEnum(String code, String cnName) {
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
