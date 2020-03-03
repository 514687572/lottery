package com.stip.net.service;

import java.math.BigDecimal;
import java.util.Locale;

import com.stip.net.entity.EosFlow;

public interface GameService {

	/**
	 * 取国际化文本
	 */
	String getMessage(String key, Locale locale);

	/**
	 * 插入EOS流水
	 */
	void insertEosFlow(EosFlow ef);

	/**
	 * 查询我的投注条数
	 */
	long countMyPut(String username);

	/**
	 * 获取积分奖池余额
	 */
	BigDecimal getPoolScore(String poolName);

	/**
	 * 增加积分奖池余额
	 */
	BigDecimal plusPoolScore(String poolName, BigDecimal score);

	/**
	 * 扣除积分奖池余额
	 */
	BigDecimal minusPoolScore(String poolName, BigDecimal score);
}