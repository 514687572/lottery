package com.stip.net.dao;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.stip.mybatis.generator.plugin.GenericMapper;
import com.stip.net.entity.LotteryUser;
import com.stip.net.entity.LotteryUserScore;
import com.stip.net.example.LotteryUserExample;

/**
* 可添加自定义查询语句，方便后续扩展
**/
public interface LotteryUserDao extends GenericMapper<LotteryUser, LotteryUserExample, Integer> {
	/**
     * 添加积分用户
     * @param lotteryUserScore
     */
	@Insert("insert into t_lottery_score_user (userName,mobilePhone,email,password,status,score,createTime,updateTime,referrer) values (#{userName},#{mobilePhone},#{email},#{password},#{status},#{score},#{createTime},#{updateTime},#{referrer})")
    int addLotteryUserScore(LotteryUserScore lotteryUserScore);
    
    /**
     * 根据用户名查询积分用户
     * @param userName
     * @return
     */
	@Select("select * from t_lottery_score_user where userName = #{userName}")
    LotteryUserScore getLotteryUserScoreName(@Param("userName") String userName);

    /**
     * 根据电话号码查询积分用户
     * @param userName
     * @return
     */
	@Select("select * from t_lottery_score_user where mobilePhone = #{tel}")
    LotteryUserScore getLotteryUserScoreTel(@Param("tel") String tel);

	/**
	 * 根据邮箱查询积分用户
	 * @param userName
	 * @return
	 */
	@Select("select * from t_lottery_score_user where email = #{emil}")
	LotteryUserScore getLotteryUserScoreEmil(@Param("emil") String emil);

	/**
	 * 根据id查询积分用户
	 */
	@Select("select * from t_lottery_score_user where id = #{id}")
	LotteryUserScore getLotteryUserScoreId(@Param("id") long id);

	/**
	 * 根据id查询积分用户的邀请者
	 */
	LotteryUserScore getScoreInviterById(@Param("id") long id);

	/**
	 * 添加积分
	 */
	@Update("update t_lottery_score_user set score = score + #{score} where id = #{id}")
	int updateLotteryUserScoreTeljia(@Param("id") long id ,@Param("score") BigDecimal score);

	/**
	 * 减积分
	 */
	@Update("update t_lottery_score_user set score = score - #{score} where id = #{id}")
	int updateLotteryUserScoreTeljian(@Param("id") long id , @Param("score") BigDecimal score);
}