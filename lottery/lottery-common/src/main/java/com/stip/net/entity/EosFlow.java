package com.stip.net.entity;

import java.math.BigDecimal;
import java.util.Date;

public class EosFlow {
	private Long id;// 自增主键
	private String username;// 用户名
	private String gameType;// 游戏类型
	private int io;// 0用户给系统钱，1系统给用户钱
	private Integer source;// 收支来源：0投注，1投注中奖，2新用户的邀请者奖励，3新用户自己的奖励，4下级投注奖励（佣金），5投注退款
	private Integer roomId;// 房间号
	private Long qid;// 期号
	private BigDecimal eos;// 投注EOS金额
	private String serialNumber;// 流水号
	private String childName;// 邀请的下级用户名
	private Date createTime;// 流水产生时间

	public EosFlow() {
		super();
	}

	public EosFlow(String username, String gameType, int io, Integer source, Integer roomId, Long qid, BigDecimal eos,
			String childName) {
		this.username = username;
		this.gameType = gameType;
		this.io = io;
		this.source = source;
		this.roomId = roomId;
		this.qid = qid;
		this.eos = eos;
		this.childName = childName;
		this.createTime = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public int getIo() {
		return io;
	}

	public void setIo(int io) {
		this.io = io;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Long getQid() {
		return qid;
	}

	public void setQid(Long qid) {
		this.qid = qid;
	}

	public BigDecimal getEos() {
		return eos;
	}

	public void setEos(BigDecimal eos) {
		this.eos = eos;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "EosFlow [id=" + id + ", username=" + username + ", gameType=" + gameType + ", io=" + io + ", source="
				+ source + ", roomId=" + roomId + ", qid=" + qid + ", eos=" + eos + ", serialNumber=" + serialNumber
				+ ", childName=" + childName + ", createTime=" + createTime + "]";
	}
}