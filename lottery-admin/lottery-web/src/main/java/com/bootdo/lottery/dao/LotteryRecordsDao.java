package com.bootdo.lottery.dao;


import com.bootdo.lottery.domain.LotteryRecordsDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 开奖记录
 * @author zhangliang
 * @email 877495411@.com
 * @date 2018-11-16 17:02:19
 */
@Mapper
public interface LotteryRecordsDao {

	LotteryRecordsDO get(Integer recordsId);
	
	List<LotteryRecordsDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(LotteryRecordsDO lotteryRecords);
	
	int update(LotteryRecordsDO lotteryRecords);
	
	int remove(Integer records_id);
	
	int batchRemove(Integer[] recordsIds);
}
