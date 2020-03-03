package com.stip.net.service;

import java.math.BigDecimal;
import java.util.Map;

import com.stip.net.entity.LotteryUser;
import com.stip.net.entity.LotteryUserScore;

public interface LotteryUserService {
    void addLottery(LotteryUser user);

    void updateByPrimaryKeySelective(LotteryUser record);

    LotteryUser getlotteryUser(LotteryUser record);

    /**
     * 根据用户名获取邀请人
     */
    LotteryUser getLotteryByInviter(LotteryUser lotteryUser);


    /**
     * 用户下注接受邀请 绑定关系
     * @param userName 当前用户
     * @param userCode 邀请人推荐码
     */
    LotteryUser updateRelatioin(String userName,String userCode);

    /**
     * 我的下级列表（EOS）
     */
    Map<String, Object> getMyChilds(String userName, int pageNum,int records);

    /**
     * 我的下级列表（积分用户）
     */
    Map<String, Object> getMyChild2(long id, int currentPage, int pageSize);

    /**
     * 检查eos用户冻结状态
     * @param userName
     * @return
     */
    boolean getUserStatus(String userName);

    /**
     * 检查积分用户冻结状态
     * @param data
     * @return
     */
    boolean getUserScoreStatus(String data);
    
    /**
     * 添加积分用户
     * @param lotteryUserScore
     */
    int addLotteryUserScore(LotteryUserScore lotteryUserScore);
    
    /**
     * 根据用户名查询积分用户
     * @param userName
     * @return
     */
    LotteryUserScore getLotteryUserScoreName(String userName);

    /**
     * 根据id查询积分用户
     */
    LotteryUserScore getLotteryUserScoreId(long id);

    /**
     * 根据id查询积分用户邀请者
     */
    LotteryUserScore getScoreInviterById(long id);

    /**
     * 根据id查询用户积分
     */
    BigDecimal getScoreById(long id);

    /**
     * 根据电话号码查询积分用户
     * @param userName
     * @return
     */
    LotteryUserScore getLotteryUserScoreTel(String tel);
    /**
     * 根据邮箱查询积分用户
     * @param userName
     * @return
     */
    LotteryUserScore getLotteryUserScoreEmil(String emil);

	/**
	 * 从骰子积分奖池给用户加钱
	 */
	boolean plusScoreFromDice(long id, BigDecimal score);

	/**
	 * 用户扣钱到骰子积分奖池
	 */
	boolean minusScoreToDice(long id, BigDecimal score);

	/**
	 * 从龙虎斗积分奖池给用户加钱
	 */
	boolean plusScoreFromTiger(long id, BigDecimal score);

	/**
	 * 用户扣钱到龙虎斗积分奖池
	 */
	boolean minusScoreToTiger(long id, BigDecimal score);

	/**
	 * 从大乐透积分奖池给用户加钱
	 */
	boolean plusScoreFromLottery(long id, BigDecimal score);

	/**
	 * 用户扣钱到大乐透积分奖池
	 */
	boolean minusScoreToLottery(long id, BigDecimal score);
}