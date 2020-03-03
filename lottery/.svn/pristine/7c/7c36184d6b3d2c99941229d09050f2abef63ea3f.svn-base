package com.stip.net.entity;

import com.stip.mybatis.generator.plugin.BaseModel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserTransactionScore extends BaseModel<Integer> implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;// 自增主键

	private Integer roomid;// 房间号

	private Long betNum;// 期号

	private Long userId;// 用户主键id

	private String serialNumber;// 流水号

	private String gameType;// 游戏类型

	private String paymentsType;// 收支类型：0用户给系统钱，1系统给用户钱

	private String source;// 收支来源：0投注，1投注中奖，2新用户的邀请者奖励，3新用户自己的奖励，4下级投注奖励（佣金），5投注退款，6充值,7提现

	private Long childId;// 下级主键id

	private BigDecimal money;// 积分

	private Date createTime;// 创建时间

	private Date updateTime;// 更新时间

	private String remark;// 备注

	public UserTransactionScore() {
		super();
	}

	/**
	 * @param userId
	 *            用户id
	 * @param gameType
	 *            游戏类型 1:时时彩 2:龙虎斗 3:21点
	 * @param roomId
	 *            房间号
	 * @param betNum
	 *            期号
	 * @param paymentsType
	 *            收支类型：0用户给系统钱，1系统给用户钱
	 * @param source
	 *            收支来源：0投注，1投注中奖，2新用户的邀请者奖励，3新用户自己的奖励，4下级投注奖励（佣金），5投注退款，6充值,7提现
	 * @param money
	 *            金额
	 */
	public UserTransactionScore(Long userId, String gameType, Integer roomId, Long betNum, String paymentsType,
			String source, BigDecimal money, Long childId) {
		this.userId = userId;
		this.gameType = gameType;
		this.roomid = roomId;
		this.betNum = betNum;
		this.paymentsType = paymentsType;
		this.source = source;
		this.money = money;
		this.childId = childId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoomid() {
		return roomid;
	}

	public void setRoomid(Integer roomid) {
		this.roomid = roomid;
	}

	public Long getBetNum() {
		return betNum;
	}

	public void setBetNum(Long betNum) {
		this.betNum = betNum;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public String getPaymentsType() {
		return paymentsType;
	}

	public void setPaymentsType(String paymentsType) {
		this.paymentsType = paymentsType;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Long getChildId() {
		return childId;
	}

	public void setChildId(Long childId) {
		this.childId = childId;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (getClass() != that.getClass()) {
			return false;
		}
		UserTransactionScore other = (UserTransactionScore) that;
		return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", id=").append(id);
		sb.append(", roomid=").append(roomid);
		sb.append(", betNum=").append(betNum);
		sb.append(", userId=").append(userId);
		sb.append(", serialNumber=").append(serialNumber);
		sb.append(", gameType=").append(gameType);
		sb.append(", paymentsType=").append(paymentsType);
		sb.append(", source=").append(source);
		sb.append(", childId=").append(childId);
		sb.append(", money=").append(money);
		sb.append(", createTime=").append(createTime);
		sb.append(", updateTime=").append(updateTime);
		sb.append(", remark=").append(remark);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}