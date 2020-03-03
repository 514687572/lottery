package com.stip.net.entity;

import com.stip.mybatis.generator.plugin.BaseModel;
import java.io.Serializable;
import java.math.BigDecimal;

public class LotteryExc extends BaseModel<Integer> implements Serializable {
    private Integer excId;

    private String userName;

    private Integer isplus;

    private BigDecimal amount;

    private String excStatus;

    private Integer excCount;

    private String gameType;

    private String remark;

    private static final long serialVersionUID = 1L;

    public LotteryExc() {
    }

    public LotteryExc(String userName, Integer isplus, BigDecimal amount, String gameType) {
        this.userName = userName;
        this.isplus = isplus;
        this.amount = amount;
        this.gameType = gameType;
    }

    public Integer getExcId() {
        return excId;
    }

    public void setExcId(Integer excId) {
        this.excId = excId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getIsplus() {
        return isplus;
    }

    public void setIsplus(Integer isplus) {
        this.isplus = isplus;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getExcStatus() {
        return excStatus;
    }

    public void setExcStatus(String excStatus) {
        this.excStatus = excStatus;
    }

    public Integer getExcCount() {
        return excCount;
    }

    public void setExcCount(Integer excCount) {
        this.excCount = excCount;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
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
        sb.append(", excId=").append(excId);
        sb.append(", userName=").append(userName);
        sb.append(", isplus=").append(isplus);
        sb.append(", amount=").append(amount);
        sb.append(", excStatus=").append(excStatus);
        sb.append(", excCount=").append(excCount);
        sb.append(", gameType=").append(gameType);
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
        LotteryExc other = (LotteryExc) that;
        return (this.getExcId() == null ? other.getExcId() == null : this.getExcId().equals(other.getExcId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getExcId() == null) ? 0 : getExcId().hashCode());
        return result;
    }
}