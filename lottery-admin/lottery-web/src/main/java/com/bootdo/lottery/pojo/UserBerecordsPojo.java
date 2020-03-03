package com.bootdo.lottery.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 用户下注相关统计
 * @author zhangliang
 * @email 87749541@.com
 * @date 2018-11-16 17:02:19
 */
public class UserBerecordsPojo implements Serializable {
    private static final long serialVersionUID = 1L;

    //总用户
    private Integer userCount;
    //总下注金额
    private BigDecimal noteMoneyCount;
    //总奖金
    private BigDecimal lotteryBonus;
    //统计时间
    private Date tatisticDate;

    private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public BigDecimal getNoteMoneyCount() {
        return noteMoneyCount;
    }

    public void setNoteMoneyCount(BigDecimal noteMoneyCount) {
        this.noteMoneyCount = noteMoneyCount;
    }

    public BigDecimal getLotteryBonus() {
        return lotteryBonus;
    }

    public void setLotteryBonus(BigDecimal lotteryBonus) {
        this.lotteryBonus = lotteryBonus;
    }

    public Date getTatisticDate() {
        return tatisticDate;
    }

    public void setTatisticDate(Date tatisticDate) {
        this.tatisticDate = tatisticDate;
    }

    @Override
    public String toString() {
        return "UserBerecordsPojo{" +
                "userCount=" + userCount +
                ", noteMoneyCount=" + noteMoneyCount +
                ", lotteryBonus=" + lotteryBonus +
                ", tatisticDate=" + tatisticDate +
                '}';
    }
}
