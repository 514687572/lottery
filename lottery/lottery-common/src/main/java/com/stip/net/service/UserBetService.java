package com.stip.net.service;

import java.util.Date;
import java.util.List;

import com.stip.net.dto.BetDto;
import com.stip.net.dto.HightBetDto;
import com.stip.net.entity.LotteryRecordGroup;
import com.stip.net.entity.LotteryRecords;
import com.stip.net.entity.UserBetRecords;
import com.stip.net.entity.UserBetScoreRecords;

public interface UserBetService {
	boolean addBet(UserBetRecords record);
	
	boolean updateByPrimaryKey(UserBetRecords record);
	
	int batchInsert(List<UserBetRecords> records);
	
	List<UserBetRecords> getUserBetHistry(int pageNum,String userName);
	
	List<UserBetRecords> getUserBetHistry(int pageNum,String userName,int records,String recordsId);

	/**
	 * 大乐透，根据recordId查询投注记录
	 */
	LotteryRecordGroup getRecordsBySid(String sid);

	int goBet(LotteryRecords lotteryRecords,BetDto betDto,List<UserBetRecords> list,String sid);

	int goBetScore(LotteryRecords lotteryRecords,BetDto betDto,List<UserBetScoreRecords> list ,long userId);
	
	int goBetHight(LotteryRecords lotteryRecords,HightBetDto hightBetDto,String sid) throws Exception;

	int goBetHightScore(LotteryRecords lotteryRecords,HightBetDto hightBetDto,long userId) throws Exception;
	
	List<UserBetRecords> getBetRecordsByTime(Date datetime,LotteryRecords records);
	
	int updateByBetRecordsExample(UserBetRecords record,String transactionId);

	/**
	 * 用户下注记录数量 判断是否为首次
	 */
	int countByUser(String userName,Long betNum);
	
	List<UserBetRecords> getRecordsBytransactionId(String transactionId);

	List<UserBetRecords> getUserBetByRecordId(String recordId);
	
	int getUserUnPrizeRecords();

	/**
	 * 查询用户代币未发放列表
	 * @return
	 */
	List<UserBetRecords> getUserScripIssued();

	int updateLotteryBetScripIssued(UserBetRecords userBetRecords);

	/**
	 * 根据期号获取开奖记录
	 * @param recordsId
	 * @return
	 * @throws Exception
	 */
	LotteryRecords selectLotteryRecordsById(Long recordsId);

}
