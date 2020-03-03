package com.stip.net.entity.tiger;

import java.math.BigDecimal;

/**
 * 龙虎斗玩家投注记录
 */
public class TigerPutRecord {
	private int roomId;// 房间号
	private int qid;// 期号
	private String userId;// 玩家uid
	private String txId;// 区块链扣款的transaction_id，唯一键
	private int txStatus;// 0为已投注，1为交易确认
	private int opt;// 投注项，参考TigerOption枚举
	private BigDecimal putMoney;// 投注金额
	private long putTimeSpt;// 投注时间戳
	private BigDecimal gainMoney;// 获奖金额
	private long openTimeSpt;// 开奖时间戳
	private int status;// 0未开奖，1未中奖，2中奖未发，3发奖成功，-1发奖失败，-2区块链查不到txId禁止发奖
	private int type;// 0是scatter投注，1是私钥投注
	private int topStatus;// 0未发代币或发币失败，1已发代币，-1不发代币

	public TigerPutRecord() {
		super();
	}

	public TigerPutRecord(int roomId, int qid, String userId, String txId, int opt, BigDecimal putMoney, int type) {
		this.roomId = roomId;
		this.qid = qid;
		this.userId = userId;
		this.txId = txId;
		this.opt = opt;
		this.putMoney = putMoney;
		this.type = type;
		this.putTimeSpt = System.currentTimeMillis();
	}

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

	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
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