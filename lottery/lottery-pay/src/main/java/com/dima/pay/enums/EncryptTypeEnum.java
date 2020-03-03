
package com.dima.pay.enums;

/**
 * @author: iron
 * @description: 加密类型类型枚举
 * @date : created in  2018/1/19 18:14.
 */
public enum EncryptTypeEnum {

    DESEDE ("3DES","3DES"),
    RSA ("RSA","RSA"),
    DES ("DES","DES");


    private String code;
    private String cnName;

    public static EncryptTypeEnum getEnum(String code) {
        if(null == code) {
            return null;
        } else {
            EncryptTypeEnum[] arr$ = values();
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                EncryptTypeEnum c = arr$[i$];
                if(code.equals(c.code)) {
                    return c;
                }
            }
            return null;
        }
    }

    private EncryptTypeEnum(String code, String cnName) {
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
