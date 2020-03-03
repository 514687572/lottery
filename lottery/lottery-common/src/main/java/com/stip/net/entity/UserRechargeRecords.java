package com.stip.net.entity;

import java.math.BigDecimal;
import java.util.Date;

public class UserRechargeRecords {
	private String inOrderId;	//订单号
	private long uid;				//用户
	private BigDecimal eos_price;	//eos价格
	private int recharge_num;		//充值金额单位（分）
	private int recharge_type;		//充值类型（0：注册，1：充值，2：其他）
	private int recharge_status;	//充值状态（0：未成功，1：成功,2：充值成功，发积分失败）
	private BigDecimal num;			//充值数量
	private Date time;				//充值时间
	public String getInOrderId() {
		return inOrderId;
	}
	public void setInOrderId(String inOrderId) {
		this.inOrderId = inOrderId;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public BigDecimal getEos_price() {
		return eos_price;
	}
	public void setEos_price(BigDecimal eos_price) {
		this.eos_price = eos_price;
	}
	public int getRecharge_num() {
		return recharge_num;
	}
	public void setRecharge_num(int recharge_num) {
		this.recharge_num = recharge_num;
	}
	public int getRecharge_type() {
		return recharge_type;
	}
	public void setRecharge_type(int recharge_type) {
		this.recharge_type = recharge_type;
	}
	public int getRecharge_status() {
		return recharge_status;
	}
	public void setRecharge_status(int recharge_status) {
		this.recharge_status = recharge_status;
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
	
	public UserRechargeRecords(String inOrderId, long uid, BigDecimal eos_price, int recharge_num, int recharge_type,
			int recharge_status, BigDecimal num, Date time) {
		super();
		this.inOrderId = inOrderId;
		this.uid = uid;
		this.eos_price = eos_price;
		this.recharge_num = recharge_num;
		this.recharge_type = recharge_type;
		this.recharge_status = recharge_status;
		this.num = num;
		this.time = time;
	}
	public UserRechargeRecords(){}
	@Override
	public String toString() {
		return "UserRechargeRecords [inOrderId=" + inOrderId + ", uid=" + uid + ", eos_price=" + eos_price
				+ ", recharge_num=" + recharge_num + ", recharge_type=" + recharge_type + ", recharge_status="
				+ recharge_status + ", num=" + num + ", time=" + time + "]";
	}
	
}
