package com.stip.net.main;

/**
 * 与客户端交互的错误消息号
 */
public class Notice {

	public static final int USER_NOT_FOUND = 1;// 未找到用户
	public static final int PUT_MONEY_ERROR = 2;// 输入金额有误
	public static final int ROOM_NOT_FOUND = 3;// 房间不存在
	public static final int NOT_PUT_TIME = 4;// 非投注时间
	public static final int BALANCE_NOT_ENOUGH = 5;// 用户余额不足
	public static final int PUT_ERROR = 6;// 投注失败
	public static final int Award_prizes_ERROR = 7;// 发奖失败
	public static final int Deduct_money_ERROR = 8;// 投注扣钱消息回滚
	public static final int PUT_MONEY_WILL_RETURN = 9;// 非投注时间，稍后返还投注金额

}