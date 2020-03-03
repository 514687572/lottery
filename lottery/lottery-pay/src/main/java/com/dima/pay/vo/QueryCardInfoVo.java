package com.dima.pay.vo;

/**
 * @author: iron
 * @description: 卡bin 查询数据类
 * @date : created in  2018/5/10 16:57.
 */
public class QueryCardInfoVo extends TradeBaseResVo {
    /**
     * 银行卡号
     */
    private String cardNo;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
