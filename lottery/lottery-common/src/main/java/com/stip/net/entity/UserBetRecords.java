package com.stip.net.entity;

import java.io.Serializable;
import java.util.Date;

import com.stip.mybatis.generator.plugin.BaseModel;

public class UserBetRecords extends BaseModel<Integer> implements Serializable {
    private Integer betId;

    private String recordsId;

    private String transactionId;

    private Long betNum;

    private Long userId = 0L;// 0未发代币或发币失败，1已发代币，-1不发代币

    private String userName;

    private String betJson;

    private String lotteryOne;

    private String lotteryTwo;

    private String lotteryThree;

    private String lotteryFour;

    private String lotteryFive;

    private String highClass;

    private String largeNum;

    private String lotterySingle;

    private Integer noteNum;

    private Double noteMoney;

    private Double lotteryBonus;

    private String lotteryStatus;

    private Date createTime;

    private Date updateTime;

    private String remark;

    private Double money;

    private static final long serialVersionUID = 1L;

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getBetId() {
        return betId;
    }

    public void setBetId(Integer betId) {
        this.betId = betId;
    }

    public String getRecordsId() {
        return recordsId;
    }

    public void setRecordsId(String recordsId) {
        this.recordsId = recordsId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Long getBetNum() {
        return betNum;
    }

    public void setBetNum(Long betNum) {
        this.betNum = betNum;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBetJson() {
        return betJson;
    }

    public void setBetJson(String betJson) {
        this.betJson = betJson;
    }

    public String getLotteryOne() {
        return lotteryOne;
    }

    public void setLotteryOne(String lotteryOne) {
        this.lotteryOne = lotteryOne;
    }

    public String getLotteryTwo() {
        return lotteryTwo;
    }

    public void setLotteryTwo(String lotteryTwo) {
        this.lotteryTwo = lotteryTwo;
    }

    public String getLotteryThree() {
        return lotteryThree;
    }

    public void setLotteryThree(String lotteryThree) {
        this.lotteryThree = lotteryThree;
    }

    public String getLotteryFour() {
        return lotteryFour;
    }

    public void setLotteryFour(String lotteryFour) {
        this.lotteryFour = lotteryFour;
    }

    public String getLotteryFive() {
        return lotteryFive;
    }

    public void setLotteryFive(String lotteryFive) {
        this.lotteryFive = lotteryFive;
    }

    public String getHighClass() {
        return highClass;
    }

    public void setHighClass(String highClass) {
        this.highClass = highClass;
    }

    public String getLargeNum() {
        return largeNum;
    }

    public void setLargeNum(String largeNum) {
        this.largeNum = largeNum;
    }

    public String getLotterySingle() {
        return lotterySingle;
    }

    public void setLotterySingle(String lotterySingle) {
        this.lotterySingle = lotterySingle;
    }

    public Integer getNoteNum() {
        return noteNum;
    }

    public void setNoteNum(Integer noteNum) {
        this.noteNum = noteNum;
    }

    public Double getNoteMoney() {
        return noteMoney;
    }

    public void setNoteMoney(Double noteMoney) {
        this.noteMoney = noteMoney;
    }

    public Double getLotteryBonus() {
        return lotteryBonus;
    }

    public void setLotteryBonus(Double lotteryBonus) {
        this.lotteryBonus = lotteryBonus;
    }

    public String getLotteryStatus() {
        return lotteryStatus;
    }

    public void setLotteryStatus(String lotteryStatus) {
        this.lotteryStatus = lotteryStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", betId=").append(betId);
        sb.append(", recordsId=").append(recordsId);
        sb.append(", transactionId=").append(transactionId);
        sb.append(", betNum=").append(betNum);
        sb.append(", userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append(", betJson=").append(betJson);
        sb.append(", lotteryOne=").append(lotteryOne);
        sb.append(", lotteryTwo=").append(lotteryTwo);
        sb.append(", lotteryThree=").append(lotteryThree);
        sb.append(", lotteryFour=").append(lotteryFour);
        sb.append(", lotteryFive=").append(lotteryFive);
        sb.append(", highClass=").append(highClass);
        sb.append(", largeNum=").append(largeNum);
        sb.append(", lotterySingle=").append(lotterySingle);
        sb.append(", noteNum=").append(noteNum);
        sb.append(", noteMoney=").append(noteMoney);
        sb.append(", lotteryBonus=").append(lotteryBonus);
        sb.append(", lotteryStatus=").append(lotteryStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        UserBetRecords other = (UserBetRecords) that;
        return (this.getBetId() == null ? other.getBetId() == null : this.getBetId().equals(other.getBetId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBetId() == null) ? 0 : getBetId().hashCode());
        return result;
    }
}