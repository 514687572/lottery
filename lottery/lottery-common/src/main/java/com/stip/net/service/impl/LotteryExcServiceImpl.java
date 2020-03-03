package com.stip.net.service.impl;

import com.stip.mybatis.generator.plugin.BaseService;
import com.stip.net.entity.LotteryExc;
import com.stip.net.example.LotteryExcExample;
import com.stip.net.example.LotteryUserExample;
import com.stip.net.service.LotteryExcService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LotteryExcServiceImpl extends BaseService<LotteryExc, LotteryExcExample, Integer>
        implements LotteryExcService {

    @Override
    public void addLotteryExc(LotteryExc lotteryExc) {
        super.insertSelective(lotteryExc);
    }

    @Override
    public void updateLotteryExcByPrimaryKeySelective(LotteryExc lotteryExc) {
        super.updateByPrimaryKeySelective(lotteryExc);
    }


    @Override
    public LotteryExc getlotteryUser(LotteryExc lotteryExc) {
        return super.selectByPrimaryKey(lotteryExc.getExcId());
    }

    @Override
    public List<LotteryExc> selectLotteryExcByExample(LotteryExcExample example) {
        return super.selectByExample(example);
    }
}