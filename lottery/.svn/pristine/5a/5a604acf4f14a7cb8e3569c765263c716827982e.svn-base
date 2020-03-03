package com.stip.net.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import com.lottery.net.utils.CoinUtil;
import com.lottery.net.utils.GrnerateUUID;
import com.lottery.net.utils.TimeUtils;
import com.stip.net.dao.GameDao;
import com.stip.net.entity.EosFlow;
import com.stip.net.service.GameService;

@Service
public class GameServiceImpl implements GameService {

	@Autowired
	private GameDao gameDao;
	@Autowired
	private ReloadableResourceBundleMessageSource messageSource;

	@Override
	public void insertEosFlow(EosFlow ef) {
		Date date = new Date();
		ef.setSerialNumber(TimeUtils.dateToString(date, "yyyyMMddHHmmss") + GrnerateUUID.getAtomicCounter());
		ef.setCreateTime(date);
		gameDao.insertEosFlow(ef);
	}

	@Override
	public long countMyPut(String username) {
		return gameDao.countMyPut(username);
	}

	@Override
	public String getMessage(String key, Locale locale) {
		return messageSource.getMessage(key, null, locale);
	}

	@Override
	public BigDecimal getPoolScore(String poolName) {
		String balanceStr = gameDao.getV(poolName);
		if (balanceStr != null) {
			return new BigDecimal(balanceStr);
		}
		return new BigDecimal(0);
	}

	@Override
	public BigDecimal plusPoolScore(String poolName, BigDecimal score) {
		BigDecimal oldBalance = getPoolScore(poolName);
		BigDecimal newBalance = oldBalance.add(score);
		int update = gameDao.updateVar(poolName, CoinUtil.formatMoney(newBalance));
		if (update > 0) {// 更新成功
			return newBalance;
		}
		return oldBalance;
	}

	@Override
	public BigDecimal minusPoolScore(String poolName, BigDecimal score) {
		BigDecimal oldBalance = getPoolScore(poolName);
		BigDecimal newBalance = oldBalance.subtract(score);
		int update = gameDao.updateVar(poolName, CoinUtil.formatMoney(newBalance));
		if (update > 0) {// 更新成功
			return newBalance;
		}
		return oldBalance;
	}
}