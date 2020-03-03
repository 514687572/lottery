package com.bootdo.lottery.service;


import com.bootdo.lottery.domain.LotteryRecordsDO;

import java.util.List;
import java.util.Map;

/**
 * 开奖记录
 * 
 * @author zhangliang
 * @email 877495411@.com
 * @date 2018-11-16 17:02:19
 */
public interface LotteryRecordsService {
	
	LotteryRecordsDO get(Integer recordsId);
	
	List<LotteryRecordsDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(LotteryRecordsDO lotteryRecords);
	
	int update(LotteryRecordsDO lotteryRecords);
	
	int remove(Integer recordsId);
	
	int batchRemove(Integer[] recordsIds);
}
