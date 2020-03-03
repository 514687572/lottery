package com.stip.net.entity.tiger;

/**
 * 龙虎斗投注项
 */
public enum TigerTimeState {
	/**
	 * 禁止游戏
	 */
	forbid(0),

	/**
	 * 投注时间段
	 */
	put(1),

	/**
	 * 开奖时间段（此时间段必须足够长，即不允许投注，等待区块链那边确认投注交易成功）
	 */
	open(2),

	/**
	 * 牌发完了，洗牌阶段（此阶段独立于小局之外）
	 */
	wash(3);

	private final int _id;

	private TigerTimeState(int id) {
		_id = id;
	}

	public final int getId() {
		return _id;
	}

	public static final TigerTimeState getEnumById(int id) {
		for (TigerTimeState to : TigerTimeState.values())
			if (to.getId() == id) {
				return to;
			}
		return null;
	}
}