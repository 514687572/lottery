package com.stip.net.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.stip.mybatis.generator.plugin.GenericMapper;
import com.stip.net.entity.UserBetRecords;
import com.stip.net.example.UserBetRecordsExample;

public interface UserBetRecordsDao extends GenericMapper<UserBetRecords, UserBetRecordsExample, Integer> {

	/**
	 * 获取未发代币的投注记录
	 */
	List<UserBetRecords> getRecordsToSendTop();

	/**
	 * 获取下级人数（EOS）
	 */
	long getMyChildCount(Map<String, Object> map);

	/**
	 * 获取下级的用户名、投注额、中奖额和佣金（EOS）
	 */
	List<UserBetRecords> getMyChildsData(Map<String, Object> map);

	/**
	 * 获取下级人数（积分）
	 */
	long getMyChildCount2(Map<String, Object> map);

	/**
	 * 获取下级的用户名、投注额、中奖额和佣金（积分）
	 */
	List<Map<String, Object>> getMyChildsData2(Map<String, Object> map);

	/**
	 * 大乐透，根据recordId查询投注记录
	 */
	List<UserBetRecords> getRecordsBySid(@Param("sid") String sid);
}