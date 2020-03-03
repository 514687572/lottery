package com.stip.net.entity;

import com.stip.mybatis.generator.plugin.BaseModel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserWithdrawApplRecords extends BaseModel<String> implements Serializable {
    private String inorderid;

    private Long uid;

    private BigDecimal eosPrice;

    private Integer rechargeNum;

    private Integer rechargeType;

    private Integer rechargeStatus;

    private BigDecimal num;

    private Date time;

    private Date updateTime;

    private String remark;

    private static final long serialVersionUID = 1L;

    public String getInorderid() {
        return inorderid;
    }

    public void setInorderid(String inorderid) {
        this.inorderid = inorderid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public BigDecimal getEosPrice() {
        return eosPrice;
    }

    public void setEosPrice(BigDecimal eosPrice) {
        this.eosPrice = eosPrice;
    }

    public Integer getRechargeNum() {
        return rechargeNum;
    }

    public void setRechargeNum(Integer rechargeNum) {
        this.rechargeNum = rechargeNum;
    }

    public Integer getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(Integer rechargeType) {
        this.rechargeType = rechargeType;
    }

    public Integer getRechargeStatus() {
        return rechargeStatus;
    }

    public void setRechargeStatus(Integer rechargeStatus) {
        this.rechargeStatus = rechargeStatus;
    }

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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
        sb.append(", inorderid=").append(inorderid);
        sb.append(", uid=").append(uid);
        sb.append(", eosPrice=").append(eosPrice);
        sb.append(", rechargeNum=").append(rechargeNum);
        sb.append(", rechargeType=").append(rechargeType);
        sb.append(", rechargeStatus=").append(rechargeStatus);
        sb.append(", num=").append(num);
        sb.append(", time=").append(time);
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
        UserWithdrawApplRecords other = (UserWithdrawApplRecords) that;
        return (this.getInorderid() == null ? other.getInorderid() == null : this.getInorderid().equals(other.getInorderid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getInorderid() == null) ? 0 : getInorderid().hashCode());
        return result;
    }
}