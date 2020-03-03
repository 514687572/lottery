package com.bootdo.lottery.domain;

import java.io.Serializable;

/**
 * 龙虎斗房间信息
 */
public class TigerRoomDO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;// 房间号
	private int x;// 从最新区块往后数x位，为龙牌开奖区块
	private int y;// 从最新区块往后数y位，为虎牌开奖区块
	private int qid;// 期号
	private int cardNum;// 房间剩余牌数

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getQid() {
		return qid;
	}

	public void setQid(int qid) {
		this.qid = qid;
	}

	public int getCardNum() {
		return cardNum;
	}

	public void setCardNum(int cardNum) {
		this.cardNum = cardNum;
	}

	@Override
	public String toString() {
		return "TigerRoomDO [id=" + id + ", x=" + x + ", y=" + y + ", qid=" + qid + ", cardNum=" + cardNum + "]";
	}
}