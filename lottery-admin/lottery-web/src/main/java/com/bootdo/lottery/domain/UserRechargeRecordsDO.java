package com.bootdo.lottery.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 
 * 
 * @author zhangliang
 * @email 877495411@.com
 * @date 2019-01-09 15:37:15
 */
public class UserRechargeRecordsDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//订单号
	private String inOrderId;
	//用户id
	private Integer uid;
	//eos价格
	private BigDecimal eosPrice;
	//充值金额单位（分）
	private Integer rechargeNum;
	//充值类型
	private Integer rechargeType;
	//充值状态
	private Integer rechargeStatus;
	//充值eos数量
	private BigDecimal num;
	//充值时间
	private Date time;

	public String getInOrderId() {
		return inOrderId;
	}
	public void setInOrderId(String inOrderId) {
		this.inOrderId = inOrderId;
	}
	public BigDecimal getNum() {
		return num;
	}
	public void setNum(BigDecimal num) {
		this.num = num;
	}
	/**
	 * 设置：用户id
	 */
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	/**
	 * 获取：用户id
	 */
	public Integer getUid() {
		return uid;
	}
	/**
	 * 设置：eos价格
	 */
	public void setEosPrice(BigDecimal eosPrice) {
		this.eosPrice = eosPrice;
	}
	/**
	 * 获取：eos价格
	 */
	public BigDecimal getEosPrice() {
		return eosPrice;
	}
	/**
	 * 设置：充值金额单位（分）
	 */
	public void setRechargeNum(Integer rechargeNum) {
		this.rechargeNum = rechargeNum;
	}
	/**
	 * 获取：充值金额单位（分）
	 */
	public Integer getRechargeNum() {
		return rechargeNum;
	}
	/**
	 * 设置：充值类型
	 */
	public void setRechargeType(Integer rechargeType) {
		this.rechargeType = rechargeType;
	}
	/**
	 * 获取：充值类型
	 */
	public Integer getRechargeType() {
		return rechargeType;
	}
	/**
	 * 设置：充值状态
	 */
	public void setRechargeStatus(Integer rechargeStatus) {
		this.rechargeStatus = rechargeStatus;
	}
	/**
	 * 获取：充值状态
	 */
	public Integer getRechargeStatus() {
		return rechargeStatus;
	}
	/**
	 * 设置：充值时间
	 */
	public void setTime(Date time) {
		this.time = time;
	}
	/**
	 * 获取：充值时间
	 */
	public Date getTime() {
		return time;
	}
}
