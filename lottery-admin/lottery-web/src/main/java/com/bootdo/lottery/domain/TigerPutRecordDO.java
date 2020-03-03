package com.bootdo.lottery.domain;

import java.math.BigDecimal;

/**
 * 龙虎斗用户投注记录
 */
public class TigerPutRecordDO {
	private int roomId;// 房间号
	private int qid;// 期号
	private String userId;// 玩家uid
	private int txStatus;// 0为已投注，1为交易确认
	private int opt;// 投注项，参考TigerOption枚举
	private BigDecimal putMoney;// 投注金额
	private long putTimeSpt;// 投注时间戳
	private BigDecimal gainMoney;// 获奖金额
	private long openTimeSpt;// 开奖时间戳
	private int status;// 0未开奖，1未中奖，2中奖未发，3发奖成功，-1发奖失败，-2区块链查不到txId禁止发奖
	private int type;// 0是scatter投注，1是私钥投注
	private int topStatus;// 0未发代币或发币失败，1已发代币

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getQid() {
		return qid;
	}

	public void setQid(int qid) {
		this.qid = qid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getTxStatus() {
		return txStatus;
	}

	public void setTxStatus(int txStatus) {
		this.txStatus = txStatus;
	}

	public int getOpt() {
		return opt;
	}

	public void setOpt(int opt) {
		this.opt = opt;
	}

	public BigDecimal getPutMoney() {
		return putMoney;
	}

	public void setPutMoney(BigDecimal putMoney) {
		this.putMoney = putMoney;
	}

	public long getPutTimeSpt() {
		return putTimeSpt;
	}

	public void setPutTimeSpt(long putTimeSpt) {
		this.putTimeSpt = putTimeSpt;
	}

	public BigDecimal getGainMoney() {
		return gainMoney;
	}

	public void setGainMoney(BigDecimal gainMoney) {
		this.gainMoney = gainMoney;
	}

	public long getOpenTimeSpt() {
		return openTimeSpt;
	}

	public void setOpenTimeSpt(long openTimeSpt) {
		this.openTimeSpt = openTimeSpt;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getTopStatus() {
		return topStatus;
	}

	public void setTopStatus(int topStatus) {
		this.topStatus = topStatus;
	}
}