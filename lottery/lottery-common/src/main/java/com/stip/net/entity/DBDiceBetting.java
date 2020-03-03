package com.stip.net.entity;

import java.util.Date;

/**
 * eos
 * @author yc
 *
 */
public class DBDiceBetting implements Comparable<DBDiceBetting>{
	private long id;
	private long termnumber; 	// 期号
	private String account; 	// 用户
	private int type; 			// 投注类型(1:大于,2:小于,3:大,4:小,5:对子)
	private long forecast;		// 预测号码
	private String prizenumber;	// 开奖号码
	private String hash;		// 开奖hash值
	private String bettingEOS; 	// 投注金额
	private int state; 			// 状态(0:未中奖，1：中奖，2：发奖未成功，3：已扣钱，请求未收到)
	private String prizeEOS; 	// 中奖金额
	private Date time; 			// 时间
	private String odds;		// 赔率
	private String transaction_id = "0";//交易hash
	private int dice_state;		//交易状态(0：交易中，1：交易成功,2：私钥登录，已扣款,3:交易已回滚)
	private int topStatus;// 0未发代币或发币失败，1已发代币，-1不发代币
	
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public int getTopStatus() {
		return topStatus;
	}
	public void setTopStatus(int topStatus) {
		this.topStatus = topStatus;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	
	public int getDice_state() {
		return dice_state;
	}
	public void setDice_state(int dice_state) {
		this.dice_state = dice_state;
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
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
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
	public String getBettingEOS() {
		return bettingEOS;
	}
	public void setBettingEOS(String bettingEOS) {
		this.bettingEOS = bettingEOS;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getPrizeEOS() {
		return prizeEOS;
	}
	public void setPrizeEOS(String prizeEOS) {
		this.prizeEOS = prizeEOS;
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
	@Override
	public int compareTo(DBDiceBetting o) {
		// TODO Auto-generated method stub
		if(Double.parseDouble(o.prizeEOS)>Double.parseDouble(getPrizeEOS())){
			return 1;
		}else if(Double.parseDouble(o.prizeEOS)<Double.parseDouble(getPrizeEOS())){
			return -1;
		}
		return 0;
	}
	@Override
	public String toString() {
		return "DBDiceBetting [id=" + id + ", termnumber=" + termnumber + ", account=" + account + ", type=" + type
				+ ", forecast=" + forecast + ", prizenumber=" + prizenumber + ", hash=" + hash + ", bettingEOS="
				+ bettingEOS + ", state=" + state + ", prizeEOS=" + prizeEOS + ", time=" + time + ", odds=" + odds
				+ ", transaction_id=" + transaction_id + ", dice_state=" + dice_state + ", topStatus=" + topStatus
				+ ", getHash()=" + getHash() + ", getTopStatus()=" + getTopStatus() + ", getTransaction_id()="
				+ getTransaction_id() + ", getDice_state()=" + getDice_state() + ", getId()=" + getId()
				+ ", getTermnumber()=" + getTermnumber() + ", getAccount()=" + getAccount() + ", getType()=" + getType()
				+ ", getForecast()=" + getForecast() + ", getPrizenumber()=" + getPrizenumber() + ", getBettingEOS()="
				+ getBettingEOS() + ", getState()=" + getState() + ", getPrizeEOS()=" + getPrizeEOS() + ", getTime()="
				+ getTime() + ", getOdds()=" + getOdds() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
}
