package com.stip.net.dto;

import java.util.Date;

public class BetDto {
	private Date date;
	private String large;
	private String single;
	private String baseNum;
	private String starRank;
	private double longBets;
	private String userName;
	private String model;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getLarge() {
		return large;
	}
	public void setLarge(String large) {
		this.large = large;
	}
	public String getSingle() {
		return single;
	}
	public void setSingle(String single) {
		this.single = single;
	}
	public String getBaseNum() {
		return baseNum;
	}
	public void setBaseNum(String baseNum) {
		this.baseNum = baseNum;
	}
	public String getStarRank() {
		return starRank;
	}
	public void setStarRank(String starRank) {
		this.starRank = starRank;
	}
	public double getLongBets() {
		return longBets;
	}
	public void setLongBets(double longBets) {
		this.longBets = longBets;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
}
