package com.stip.net.entity.tiger;

/**
 * 龙虎斗投注项
 */
public enum TigerOption {
	/**
	 * 龙单
	 */
	long1(1, 750),
	/**
	 * 龙双
	 */
	long2(2, 1075),

	/**
	 * 虎单
	 */
	hu1(3, 750),
	/**
	 * 虎双
	 */
	hu2(4, 1075),
	/**
	 * 龙
	 */
	long0(5, 1000),
	/**
	 * 和
	 */
	he(6, 8000),
	/**
	 * 虎
	 */
	hu0(7, 1000);

	private final int _id;
	private final int _pei;// 赔率，1000赔多少

	private TigerOption(int id, int pei) {
		_id = id;
		_pei = pei;
	}

	public final int getId() {
		return _id;
	}

	public final int getPei() {
		return _pei;
	}

	public static final TigerOption getEnumById(int id) {
		for (TigerOption to : TigerOption.values())
			if (to.getId() == id) {
				return to;
			}
		return null;
	}
}