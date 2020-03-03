package com.bootdo.lottery.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootdo.common.constant.IConstInfo;
import com.bootdo.common.utils.DateUtil;
import com.bootdo.lottery.dao.TigerDao;
import com.bootdo.lottery.domain.TigerConfirmDO;
import com.bootdo.lottery.domain.TigerHistoryDO;
import com.bootdo.lottery.domain.TigerPutRecordDO;
import com.bootdo.lottery.domain.TigerRoomDO;
import com.bootdo.lottery.domain.TigerUserDO;
import com.bootdo.lottery.service.TigerService;

@Service
public class TigerServiceImpl implements TigerService {

	@Autowired
	private TigerDao tigerDao;

	@Override
	public List<TigerConfirmDO> queryConfirm(Map<String, Object> map) {
		return tigerDao.queryConfirm(map);
	}

	@Override
	public int countConfirm(Map<String, Object> map) {
		return tigerDao.countConfirm(map);
	}

	@Override
	public List<TigerRoomDO> queryRoom(Map<String, Object> map) {
		return tigerDao.queryRoom(map);
	}

	@Override
	public int countRoom(Map<String, Object> map) {
		return tigerDao.countRoom(map);
	}

	@Override
	public List<TigerHistoryDO> queryHistory(Map<String, Object> map) {
		return tigerDao.queryHistory(map);
	}

	@Override
	public int countHistory(Map<String, Object> map) {
		return tigerDao.countHistory(map);
	}

	@Override
	public List<TigerPutRecordDO> queryRecord(Map<String, Object> map) {
		return tigerDao.selectQueryRecord(map);
	}

	@Override
	public int countRecord(Map<String, Object> map) {
		return tigerDao.countRecord(map);
	}

	@Override
	public List<Map<String, Object>> statsPut(String state) {
		Map<String, Object> params = new HashMap<>();
		Date date = new Date();
		List<Map<String, Object>> pojos = new ArrayList<>();
		if (IConstInfo.STATISTICS_MONTH.equals(state)) {
			// 按天统计，最近一个月
			List<String> lastMonthDate = DateUtil.getLastMonthDate(date, 1);
			params.put("dateList", lastMonthDate);
			pojos = tigerDao.statsPutByDay(params);
		} else if (IConstInfo.STATISTICS_DAY.equals(state)) {
			// 按天统计，最近7天
			List<String> lastDayDate = DateUtil.getLastDayDate(date, 7);
			params.put("dateList", lastDayDate);
			pojos = tigerDao.statsPutByDay(params);
		} else {
			// 按小时统计，最近24小时
			List<String> lastHourDate = DateUtil.getLastHourDate(date, 24);
			params.put("dateList", lastHourDate);
			pojos = tigerDao.statsPutByHour(params);
		}
		return pojos;
	}

	@Override
	public List<Map<String, Object>> statsActive(String state) {
		Map<String, Object> params = new HashMap<>();
		Date date = new Date();
		List<Map<String, Object>> pojos = new ArrayList<>();
		if (IConstInfo.STATISTICS_MONTH.equals(state)) {
			// 按天统计，最近一个月
			List<String> lastMonthDate = DateUtil.getLastMonthDate(date, 1);
			params.put("dateList", lastMonthDate);
			pojos = tigerDao.statsActiveByDay(params);
		} else if (IConstInfo.STATISTICS_DAY.equals(state)) {
			// 按天统计，最近7天
			List<String> lastDayDate = DateUtil.getLastDayDate(date, 7);
			params.put("dateList", lastDayDate);
			pojos = tigerDao.statsActiveByDay(params);
		} else {
			// 按小时统计，最近24小时
			List<String> lastHourDate = DateUtil.getLastHourDate(date, 24);
			params.put("dateList", lastHourDate);
			pojos = tigerDao.statsActiveByHour(params);
		}
		return pojos;
	}

	@Override
	public List<Map<String, Object>> statsProfit(String state) {
		Map<String, Object> params = new HashMap<>();
		Date date = new Date();
		List<Map<String, Object>> pojos = new ArrayList<>();
		if (IConstInfo.STATISTICS_MONTH.equals(state)) {
			// 按天统计，最近一个月
			List<String> lastMonthDate = DateUtil.getLastMonthDate(date, 1);
			params.put("dateList", lastMonthDate);
			pojos = tigerDao.statsProfitByDay(params);
		} else if (IConstInfo.STATISTICS_DAY.equals(state)) {
			// 按天统计，最近7天
			List<String> lastDayDate = DateUtil.getLastDayDate(date, 7);
			params.put("dateList", lastDayDate);
			pojos = tigerDao.statsProfitByDay(params);
		} else {
			// 按小时统计，最近24小时
			List<String> lastHourDate = DateUtil.getLastHourDate(date, 24);
			params.put("dateList", lastHourDate);
			pojos = tigerDao.statsProfitByHour(params);
		}
		return pojos;
	}

	@Override
	public BigDecimal getTotalPutMoney() {
		return tigerDao.getTotalPutMoney();
	}

	@Override
	public List<TigerUserDO> queryUser(Map<String, Object> map) {
		List<TigerUserDO> list = tigerDao.queryUser(map);
		for (TigerUserDO obj : list) {
			String userId = obj.getUserId();
			Map<String, Object> cal = tigerDao.calWins(userId);
			obj.setPutMoney((BigDecimal) (cal.get("putMoney")));
			obj.setWinMoney((BigDecimal) (cal.get("winMoney")));
			obj.setWins(Integer.valueOf("" + cal.get("wins")));
			obj.setFails(Integer.valueOf("" + cal.get("fails")));
		}
		return list;
	}

	@Override
	public int countUser(Map<String, Object> map) {
		return tigerDao.countUser(map);
	}

	@Override
	public void updateUserStatus(String userId) {
		tigerDao.updateUserStatus(userId);
	}

	@Override
	public List<Map<String, Object>> queryChild(Map<String, Object> map) {
		return tigerDao.queryChild(map);
	}

	@Override
	public int countChild(Map<String, Object> map) {
		return tigerDao.countChild(map);
	}
}