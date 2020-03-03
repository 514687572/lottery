package com.stip.net.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stip.net.dao.UserRechargeRecordsDao;
import com.stip.net.entity.UserRechargeRecords;
import com.stip.net.service.UserRechargeRecordsService;

@Service
public class UserRechargeRecordsImpl implements UserRechargeRecordsService {
	@Autowired
	private UserRechargeRecordsDao userRechargeRecordsdao;
	
	@Override
	public void addRechargeRecords(UserRechargeRecords record) {
		userRechargeRecordsdao.addRechargeRecords(record);
	}

	@Override
	public UserRechargeRecords getRechargeRecordinOrderId(BigDecimal inOrderId) {
		return userRechargeRecordsdao.getRechargeRecordinOrderId(inOrderId);
	}

	@Override
	public void updateRechargeRecordin(BigDecimal inOrderId, int status) {
		userRechargeRecordsdao.updateRechargeRecordin(inOrderId, status);
	}

}
