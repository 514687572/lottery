package com.stip.net.service;

import com.stip.net.entity.LotteryExc;
import com.stip.net.example.LotteryExcExample;
import com.stip.net.example.LotteryUserExample;

import java.util.List;

public interface LotteryExcService {

    void addLotteryExc(LotteryExc lotteryExc);

    void updateLotteryExcByPrimaryKeySelective(LotteryExc lotteryExc);

    LotteryExc getlotteryUser(LotteryExc lotteryExc);

    List<LotteryExc> selectLotteryExcByExample(LotteryExcExample example);
}