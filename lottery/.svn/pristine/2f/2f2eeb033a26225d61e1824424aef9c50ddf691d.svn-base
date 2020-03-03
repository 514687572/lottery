package com.lottery.net.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CoinUtil {

	private CoinUtil() {
		super();
	}

	/**
	 * 从区块消息的余额中分离出数字部分，不带单位
	 */
	public static String getFromQuantity(String quantity) {
		String balance = quantity.split(" ")[0];
		return balance;
	}

	/**
	 * 格式化转账金额（转账要求金额必须带4位小数）
	 */
	public static String formatMoney(BigDecimal money) {
		BigDecimal cutMoney = money.setScale(4, BigDecimal.ROUND_HALF_DOWN);
		return new DecimalFormat("0.0000").format(cutMoney.doubleValue());
	}

	/**
	 * 投注EOS返TOP币的比例
	 * 
	 * @param top
	 *            已返的TOP币总量
	 * @return 目前应该返TOP币的比例
	 */
	public static String getTopPer(BigDecimal top) {
		if (top == null) {// 异常情况
			return null;
		}
		if (top.compareTo(new BigDecimal(450000000)) < 0) {
			return "100";
		} else if (top.compareTo(new BigDecimal(900000000)) < 0) {
			return "50";
		} else if (top.compareTo(new BigDecimal(1350000000)) < 0) {
			return "25";
		} else if (top.compareTo(new BigDecimal(1800000000)) < 0) {
			return "12.5";
		} else if (top.compareTo(new BigDecimal(2250000000L)) < 0) {
			return "6.25";
		} else if (top.compareTo(new BigDecimal(2700000000L)) < 0) {
			return "3.13";
		} else {
			return "1.56";
		}
	}
}