package com.stip.net.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.net.utils.TimeUtils;
import com.stip.mybatis.generator.plugin.BaseService;
import com.stip.net.dao.UserBetScoreRecordsDao;
import com.stip.net.entity.LotteryRecords;
import com.stip.net.entity.UserBetScoreRecords;
import com.stip.net.example.UserBetScoreRecordsExample;
import com.stip.net.example.UserBetScoreRecordsExample.Criteria;
import com.stip.net.service.UserBetScoreRecordsService;

@Service
public class UserBetScoreRecordsServiceImpl extends BaseService<UserBetScoreRecords, UserBetScoreRecordsExample, Integer> implements UserBetScoreRecordsService {
	@Autowired
    public UserBetScoreRecordsDao userBetScoreRecords;
	
	@Override
	public List<UserBetScoreRecords> getUserBetHistry(int pageNum, long uid) {
		UserBetScoreRecordsExample example = new UserBetScoreRecordsExample();
        example.createCriteria().andUserIdEqualTo(uid).andLotteryStatusEqualTo("1").andSuccessOrTotal();
        example.setOrderByClause(" UserBetScoreRecords.update_time desc,UserBetScoreRecords.bet_id desc");
        example.setPager(pageNum, 10);

        List<UserBetScoreRecords> list = userBetScoreRecords.selectByExample(example);
		return list;
	}

	@Override
	public List<UserBetScoreRecords> getBetRecordsByTime(Date datetime, LotteryRecords records) {
		UserBetScoreRecordsExample example = new UserBetScoreRecordsExample();
        Criteria criteria = example.createCriteria();
        criteria.andCreateTimeLessThanOrEqualTo(datetime);
        criteria.andCreateTimeGreaterThanOrEqualTo(TimeUtils.setDateMinute(datetime, -1));
        criteria.andLotteryStatusEqualTo("1");
        criteria.andLotteryBonusIsNull();
        criteria.andBetJsonIsNull();
        criteria.andBetNumEqualTo(Long.parseLong(records.getRecordsId().toString()));
        criteria.getPrize(records.getLargeNum(), records.getLotterySingle(), records.getLotteryOne(), records.getLotteryTwo(), records.getLotteryThree(), records.getLotteryFour(), records.getLotteryFive());

        example.setOrderByClause(" UserBetScoreRecords.user_id desc");

        List<UserBetScoreRecords> list = userBetScoreRecords.selectByExample(example);
		
		return list;
	}
}