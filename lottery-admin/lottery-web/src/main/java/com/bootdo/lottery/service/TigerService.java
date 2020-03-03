package com.bootdo.lottery.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.bootdo.lottery.domain.TigerConfirmDO;
import com.bootdo.lottery.domain.TigerHistoryDO;
import com.bootdo.lottery.domain.TigerPutRecordDO;
import com.bootdo.lottery.domain.TigerRoomDO;
import com.bootdo.lottery.domain.TigerUserDO;

public interface TigerService {

	List<TigerConfirmDO> queryConfirm(Map<String, Object> map);

	int countConfirm(Map<String, Object> map);

	List<TigerRoomDO> queryRoom(Map<String, Object> map);

	int countRoom(Map<String, Object> map);

	List<TigerHistoryDO> queryHistory(Map<String, Object> map);

	int countHistory(Map<String, Object> map);

	List<TigerPutRecordDO> queryRecord(Map<String, Object> map);

	int countRecord(Map<String, Object> map);

	List<Map<String, Object>> statsPut(String state);

	List<Map<String, Object>> statsActive(String state);

	List<Map<String, Object>> statsProfit(String state);

	BigDecimal getTotalPutMoney();

	List<TigerUserDO> queryUser(Map<String, Object> map);

	int countUser(Map<String, Object> map);

	void updateUserStatus(String userId);

	List<Map<String, Object>> queryChild(Map<String, Object> map);

	int countChild(Map<String, Object> map);
}