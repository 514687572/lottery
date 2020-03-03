package com.stip.net.service;

import java.util.Date;
import java.util.List;

import com.stip.mybatis.generator.plugin.IService;
import com.stip.net.entity.LotteryRecords;
import com.stip.net.entity.UserBetScoreRecords;
import com.stip.net.example.UserBetScoreRecordsExample;

 /**
 * Extensible custom interface
 **/
public interface UserBetScoreRecordsService extends IService<UserBetScoreRecords, UserBetScoreRecordsExample, Integer> {
	
	List<UserBetScoreRecords> getUserBetHistry(int pageNum,long uid);
	
	List<UserBetScoreRecords> getBetRecordsByTime(Date datetime,LotteryRecords records);
}