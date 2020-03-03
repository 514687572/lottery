package com.bootdo.lottery.domain;

/**
 * 龙虎斗开奖历史记录
 */
public class TigerHistoryDO {
	private int roomId;// 房间号
	private int qid;// 期号
	private int lp;// 龙牌点数
	private int hp;// 虎牌点数
	private long openTimeSpt;// 开奖时间

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

	public int getLp() {
		return lp;
	}

	public void setLp(int lp) {
		this.lp = lp;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public long getOpenTimeSpt() {
		return openTimeSpt;
	}

	public void setOpenTimeSpt(long openTimeSpt) {
		this.openTimeSpt = openTimeSpt;
	}
}