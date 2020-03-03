package com.stip.net.entity;

import java.util.Date;

/**
 * 积分
 * @author yc
 *
 */
public class DBDiceBettingScore {
	private long id;
	private long termnumber; 	// 期号
	private long uid; 			// 用户id
	private int type; 			// 投注类型(1:大于,2:小于,3:大,4:小,5:对子)
	private long forecast;		// 预测号码
	private String prizenumber;	// 开奖号码
	private String bettingScore; // 投注金额
	private int state; 			// 状态(0:未中奖，1：中奖，2：发奖未成功)
	private String prizeScore; 	// 中奖金额
	private Date time; 			// 时间
	private String odds;		// 赔率
	private String hash;		// 开奖hash值
	private String user;		// 用户名
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getTermnumber() {
		return termnumber;
	}
	public void setTermnumber(long termnumber) {
		this.termnumber = termnumber;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getForecast() {
		return forecast;
	}
	public void setForecast(long forecast) {
		this.forecast = forecast;
	}
	public String getPrizenumber() {
		return prizenumber;
	}
	public void setPrizenumber(String prizenumber) {
		this.prizenumber = prizenumber;
	}
	public String getBettingScore() {
		return bettingScore;
	}
	public void setBettingScore(String bettingScore) {
		this.bettingScore = bettingScore;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getPrizeScore() {
		return prizeScore;
	}
	public void setPrizeScore(String prizeScore) {
		this.prizeScore = prizeScore;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getOdds() {
		return odds;
	}
	public void setOdds(String odds) {
		this.odds = odds;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public DBDiceBettingScore() {
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public DBDiceBettingScore(long id, long termnumber, long uid, int type, long forecast, String prizenumber,
			String bettingScore, int state, String prizeScore, Date time, String odds, String hash) {
		super();
		this.id = id;
		this.termnumber = termnumber;
		this.uid = uid;
		this.type = type;
		this.forecast = forecast;
		this.prizenumber = prizenumber;
		this.bettingScore = bettingScore;
		this.state = state;
		this.prizeScore = prizeScore;
		this.time = time;
		this.odds = odds;
		this.hash = hash;
	}
	@Override
	public String toString() {
		return "DBDiceBettingScore [id=" + id + ", termnumber=" + termnumber + ", uid=" + uid + ", type=" + type
				+ ", forecast=" + forecast + ", prizenumber=" + prizenumber + ", bettingScore=" + bettingScore
				+ ", state=" + state + ", prizeScore=" + prizeScore + ", time=" + time + ", odds=" + odds + ", hash="
				+ hash + "]";
	}

}
