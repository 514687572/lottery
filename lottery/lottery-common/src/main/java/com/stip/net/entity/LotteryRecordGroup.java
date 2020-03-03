package com.stip.net.entity;

import java.util.List;

/**
 * 封装大乐透一次的投注记录
 */
public class LotteryRecordGroup {
	private UserBetRecords main;// 汇总投注记录
	private List<UserBetRecords> list;// 拆分投注记录

	public LotteryRecordGroup() {
		super();
	}

	public LotteryRecordGroup(UserBetRecords main, List<UserBetRecords> list) {
		this.main = main;
		this.list = list;
	}

	public UserBetRecords getMain() {
		return main;
	}

	public void setMain(UserBetRecords main) {
		this.main = main;
	}

	public List<UserBetRecords> getList() {
		return list;
	}

	public void setList(List<UserBetRecords> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "LotteryRecordGroup [main=" + main + ", list=" + list + "]";
	}
}