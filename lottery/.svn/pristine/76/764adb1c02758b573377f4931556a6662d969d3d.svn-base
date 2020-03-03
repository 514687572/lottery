package com.stip.net.service;

import java.util.List;

import com.stip.net.entity.DBDiceBetting;
import com.stip.net.entity.DBDiceBettingScore;
import com.stip.net.entity.LotteryRecords;

public interface LotteryService {
    void addLottery(LotteryRecords record);

    LotteryRecords getNotPrize();

    void updateByPrimaryKey(LotteryRecords record);

    List<LotteryRecords> getLotteryHistry(int pageNum, String rowNum);

    void addDBDiceBetting(List<DBDiceBetting> record);

    void addDBDiceBettingScore(DBDiceBettingScore record);

    List<DBDiceBetting> getAccountDice(String account,int begin,int limit);
    
    List<DBDiceBettingScore> getAccountDiceScore(long uid,int begin,int limit);
    
    LotteryRecords getLotteryById(long id);
    
    /**
     * 修改骰子补发状态
     * @param account
     */
    void updateDiceStateto(String account);
    
    /**
     * 查询骰子发奖失败的列表
     */
    List<DBDiceBetting> getDiceState();
    
    LotteryRecords getNotPrizeByTime(String order);
    
    LotteryRecords getNotPrizeByTimeForPrize(String order);

    /**
     * 查询骰子未发代币的列表
     */
    List<DBDiceBetting> getDiceTopState();
    
    /**
     * 查询骰子未查到交易地址的列表
     */
    List<DBDiceBetting> getLateDice();

    /**
     * 修改代币发放状态
     * @param dice
     */
    void updateDiceTopState(DBDiceBetting dice);
    
    /**
     * 修改交易状态
     * @param dice
     */
    void updateDiceState(long id);
    
    
    

}
