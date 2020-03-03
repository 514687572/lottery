package com.stip.net.entity;

import java.math.BigDecimal;
import java.util.Date;

public class LotteryUserScore {
	private long id;
	private String userName; // 用户名
	private String mobilePhone; // 电话号码
	private String email; // 邮箱
	private String password; // 密码
	private int status; // 状态（0：冻结，1：激活）
	private BigDecimal score; // 积分
	private long referrer; // 邀请人id
	private Date createTime; // 创建时间
	private Date updateTime; // 修改时间

	public LotteryUserScore() {
		super();
	}

	public LotteryUserScore(long id, String userName, String mobilePhone, String email, String password, int status,
			BigDecimal score, long referrer, Date createTime, Date updateTime) {
		this.id = id;
		this.userName = userName;
		this.mobilePhone = mobilePhone;
		this.email = email;
		this.password = password;
		this.status = status;
		this.score = score;
		this.referrer = referrer;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public long getReferrer() {
		return referrer;
	}

	public void setReferrer(long referrer) {
		this.referrer = referrer;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
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

	/**
	 * 获取用户标识（邮箱或者电话）
	 */
	public String getUserId() {
		return mobilePhone == null ? email : mobilePhone;
	}

	@Override
	public String toString() {
		return "LotteryUserScore [id=" + id + ", userName=" + userName + ", mobilePhone=" + mobilePhone + ", email="
				+ email + ", password=" + password + ", status=" + status + ", score=" + score + ", referrer="
				+ referrer + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
}