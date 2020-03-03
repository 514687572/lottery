package com.bootdo.lottery.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 区块链投注确认消息比客户端投注消息还快，记录的confirm
 */
public class TigerConfirmDO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String txId;// 区块链事务id
	private String userId;// 用户名
	private BigDecimal putMoney;// 投注金额
	private Long blockTime;// 区块时间

	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getPutMoney() {
		return putMoney;
	}

	public void setPutMoney(BigDecimal putMoney) {
		this.putMoney = putMoney;
	}

	public Long getBlockTime() {
		return blockTime;
	}

	public void setBlockTime(Long blockTime) {
		this.blockTime = blockTime;
	}

	@Override
	public String toString() {
		return "TigerConfirmDO [txId=" + txId + ", userId=" + userId + ", putMoney=" + putMoney + ", blockTime="
				+ blockTime + "]";
	}
}