package com.bootdo.lottery.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author zhangliang
 * @email 877495411@.com
 * @date 2018-12-18 14:44:15
 */
public class UserIpDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//用户列表
	private String userName;
	//登陆的真实IP地址
	private String loginIp;
	//ip状态 1正常 0禁止
	private String ipStatus;
	//操作人
	private String optUser;
	//操作人ID
	private Long optUserId;
	//
	private Date createTime;
	//
	private Date updateTime;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：用户列表
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取：用户列表
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置：登陆的真实IP地址
	 */
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	/**
	 * 获取：登陆的真实IP地址
	 */
	public String getLoginIp() {
		return loginIp;
	}
	/**
	 * 设置：ip状态 1正常 0禁止
	 */
	public void setIpStatus(String ipStatus) {
		this.ipStatus = ipStatus;
	}
	/**
	 * 获取：ip状态 1正常 0禁止
	 */
	public String getIpStatus() {
		return ipStatus;
	}
	/**
	 * 设置：操作人
	 */
	public void setOptUser(String optUser) {
		this.optUser = optUser;
	}
	/**
	 * 获取：操作人
	 */
	public String getOptUser() {
		return optUser;
	}
	/**
	 * 设置：操作人ID
	 */
	public void setOptUserId(Long optUserId) {
		this.optUserId = optUserId;
	}
	/**
	 * 获取：操作人ID
	 */
	public Long getOptUserId() {
		return optUserId;
	}
	/**
	 * 设置：
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
}
