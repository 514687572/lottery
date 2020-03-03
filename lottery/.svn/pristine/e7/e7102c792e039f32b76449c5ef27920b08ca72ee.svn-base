package com.stip.net.entity;

import java.io.Serializable;

import com.stip.mybatis.generator.plugin.BaseModel;

public class LotteryConfirm extends BaseModel<String> implements Serializable {
    private String confirmId;

    private String transactionId;

    private String userName;

    private String gameType;

    private String confirmExt;

    private String remark;

    private static final long serialVersionUID = 1L;

    public String getConfirmId() {
        return confirmId;
    }

    public void setConfirmId(String confirmId) {
        this.confirmId = confirmId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getConfirmExt() {
        return confirmExt;
    }

    public void setConfirmExt(String confirmExt) {
        this.confirmExt = confirmExt;
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
        sb.append(", confirmId=").append(confirmId);
        sb.append(", transactionId=").append(transactionId);
        sb.append(", userName=").append(userName);
        sb.append(", gameType=").append(gameType);
        sb.append(", confirmExt=").append(confirmExt);
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
        LotteryConfirm other = (LotteryConfirm) that;
        return (this.getConfirmId() == null ? other.getConfirmId() == null : this.getConfirmId().equals(other.getConfirmId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getConfirmId() == null) ? 0 : getConfirmId().hashCode());
        return result;
    }
}