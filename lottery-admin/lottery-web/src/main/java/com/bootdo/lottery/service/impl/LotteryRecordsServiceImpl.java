package com.bootdo.lottery.service.impl;

import com.bootdo.lottery.dao.LotteryRecordsDao;
import com.bootdo.lottery.domain.LotteryRecordsDO;
import com.bootdo.lottery.service.LotteryRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class LotteryRecordsServiceImpl implements LotteryRecordsService {
	@Autowired
	private LotteryRecordsDao lotteryRecordsDao;
	
	@Override
	public LotteryRecordsDO get(Integer recordsId){
		return lotteryRecordsDao.get(recordsId);
	}
	
	@Override
	public List<LotteryRecordsDO> list(Map<String, Object> map){
		return lotteryRecordsDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return lotteryRecordsDao.count(map);
	}
	
	@Override
	public int save(LotteryRecordsDO lotteryRecords){
		return lotteryRecordsDao.save(lotteryRecords);
	}
	
	@Override
	public int update(LotteryRecordsDO lotteryRecords){
		return lotteryRecordsDao.update(lotteryRecords);
	}
	
	@Override
	public int remove(Integer recordsId){
		return lotteryRecordsDao.remove(recordsId);
	}
	
	@Override
	public int batchRemove(Integer[] recordsIds){
		return lotteryRecordsDao.batchRemove(recordsIds);
	}
	
}
