package com.bootdo.lottery.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 投注用户信息
 */
public class TigerUserDO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String userId;// 用户名
	private BigDecimal putMoney;// 投注总金额
	private BigDecimal winMoney;// 获奖总金额
	private int wins;// 总胜场
	private int fails;// 总败场
	private int status;// 0冻结1激活

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

	public BigDecimal getWinMoney() {
		return winMoney;
	}

	public void setWinMoney(BigDecimal winMoney) {
		this.winMoney = winMoney;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getFails() {
		return fails;
	}

	public void setFails(int fails) {
		this.fails = fails;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 胜率
	 */
	public String getWinPer() {
		double winPer = 100.0 * wins / (wins + fails);
		return new DecimalFormat("0.00").format(winPer) + "%";
	}

	@Override
	public String toString() {
		return "TigerUserDO [userId=" + userId + ", putMoney=" + putMoney + ", winMoney=" + winMoney + ", wins=" + wins
				+ ", fails=" + fails + ", status=" + status + "]";
	}
}