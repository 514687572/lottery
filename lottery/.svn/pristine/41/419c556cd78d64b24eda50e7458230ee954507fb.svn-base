package com.stip.net.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stip.net.dao.LotteryConfirmDao;
import com.stip.net.entity.LotteryConfirm;
import com.stip.net.example.LotteryConfirmExample;
import com.stip.net.service.ConfirmService;

@Service
public class ConfirmServiceImpl implements ConfirmService{
	@Autowired
	private LotteryConfirmDao lotteryConfirmDao;
	
	public void addConfirm(LotteryConfirm record) {
		lotteryConfirmDao.insert(record);
	}
	
	public void updateByPrimaryKey(LotteryConfirm record) {
		lotteryConfirmDao.updateByPrimaryKey(record);
	}
	
	public void deleteByPrimaryKey(String confirmId) {
		lotteryConfirmDao.deleteByPrimaryKey(confirmId);
	}
	
	/**
	 * 查询所有未开奖记录
	 */
	public List<LotteryConfirm> selectByExample() {
		LotteryConfirmExample example=new LotteryConfirmExample();
		List<LotteryConfirm> list=lotteryConfirmDao.selectByExample(example);
		
		return list;
	}
	
}
