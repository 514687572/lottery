package com.bootdo.lottery.service.impl;

import com.bootdo.lottery.dao.UserRechargeRecordsDao;
import com.bootdo.lottery.domain.UserRechargeRecordsDO;
import com.bootdo.lottery.service.UserRechargeRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;




@Service
public class UserRechargeRecordsServiceImpl implements UserRechargeRecordsService {
	@Autowired
	private UserRechargeRecordsDao userRechargeRecordsDao;
	
	@Override
	public UserRechargeRecordsDO get(Integer id){
		return userRechargeRecordsDao.get(id);
	}
	
	@Override
	public List<UserRechargeRecordsDO> list(Map<String, Object> map){
		return userRechargeRecordsDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return userRechargeRecordsDao.count(map);
	}
	
	@Override
	public int save(UserRechargeRecordsDO userRechargeRecords){
		return userRechargeRecordsDao.save(userRechargeRecords);
	}
	
	@Override
	public int update(UserRechargeRecordsDO userRechargeRecords){
		return userRechargeRecordsDao.update(userRechargeRecords);
	}
	
	@Override
	public int remove(Integer id){
		return userRechargeRecordsDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return userRechargeRecordsDao.batchRemove(ids);
	}
	
}
