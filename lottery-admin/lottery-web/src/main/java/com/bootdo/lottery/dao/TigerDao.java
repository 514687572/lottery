package com.bootdo.lottery.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bootdo.lottery.domain.TigerConfirmDO;
import com.bootdo.lottery.domain.TigerHistoryDO;
import com.bootdo.lottery.domain.TigerPutRecordDO;
import com.bootdo.lottery.domain.TigerRoomDO;
import com.bootdo.lottery.domain.TigerUserDO;

@Mapper
public interface TigerDao {
	List<TigerConfirmDO> queryConfirm(Map<String, Object> map);

	int countConfirm(Map<String, Object> map);

	List<TigerRoomDO> queryRoom(Map<String, Object> map);

	int countRoom(Map<String, Object> map);

	List<TigerHistoryDO> queryHistory(Map<String, Object> map);

	int countHistory(Map<String, Object> map);

	List<TigerPutRecordDO> selectQueryRecord(Map<String, Object> map);

	int countRecord(Map<String, Object> map);

	List<Map<String, Object>> statsPutByDay(Map<String, Object> params);

	List<Map<String, Object>> statsPutByHour(Map<String, Object> params);

	List<Map<String, Object>> statsActiveByDay(Map<String, Object> params);

	List<Map<String, Object>> statsActiveByHour(Map<String, Object> params);

	List<Map<String, Object>> statsProfitByDay(Map<String, Object> params);

	List<Map<String, Object>> statsProfitByHour(Map<String, Object> params);

	BigDecimal getTotalPutMoney();

	List<TigerUserDO> queryUser(Map<String, Object> map);

	int countUser(Map<String, Object> map);

	Map<String, Object> calWins(@Param("userId") String userId);

	void updateUserStatus(@Param("userId") String userId);

	List<Map<String, Object>> queryChild(Map<String, Object> map);

	int countChild(Map<String, Object> map);
}