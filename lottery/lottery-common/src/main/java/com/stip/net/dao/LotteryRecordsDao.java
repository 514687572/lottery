package com.stip.net.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.stip.mybatis.generator.plugin.GenericMapper;
import com.stip.net.entity.DBDiceBetting;
import com.stip.net.entity.DBDiceBettingScore;
import com.stip.net.entity.LotteryRecords;
import com.stip.net.example.LotteryRecordsExample;

 /**
 * 可添加自定义查询语句，方便后续扩展
 **/
public interface LotteryRecordsDao extends GenericMapper<LotteryRecords, LotteryRecordsExample, Long> {
	//插入骰子EOS开奖信息
	int insertDiceBetting(List<DBDiceBetting> dices);

	//插入骰子积分开奖信息
	@Insert("insert into t_user_bet_dice_score (termnumber,uid,type,forecast,prizenumber,bettingScore,state,prizeScore,time,hash) values (#{termnumber},#{uid},#{type},#{forecast},#{prizenumber},#{bettingScore},#{state},#{prizeScore},#{time},#{hash})")
	int insertDiceBettingScore(DBDiceBettingScore dices);

	//查询EOS用户的参与骰子记录
	@Select("select * from t_user_bet_dice where account=#{account} and dice_state != 3 ORDER BY id DESC limit #{begin},#{limit}")
	List<DBDiceBetting> getAccountDice(@Param(value = "account") String account,@Param(value = "begin") int begin,@Param(value = "limit") int limit);

	//查询积分用户的参与骰子记录
	@Select("select * from t_user_bet_dice_score where uid=#{uid} ORDER BY id DESC limit #{begin},#{limit}")
	List<DBDiceBettingScore> getAccountDiceScore(@Param(value = "uid") long uid,@Param(value = "begin") int begin,@Param(value = "limit") int limit);
	
	//修改补发的状态
	@Update("update t_user_bet_dice set state = 1 where account = #{account}")
	void updateDiceStateto(@Param(value = "account") String account);
	
	//查询发奖失败列表
	@Select("select * from t_user_bet_dice where state = 2 and dice_state != 3")
	List<DBDiceBetting> getDiceState();


	//查询代币未发的列表
	@Select("select * from t_user_bet_dice where topStatus = 0 and dice_state != 3")
	List<DBDiceBetting> getDiceTopState();
	
	//查询未查到交易地址的列表
	@Select("select * from t_user_bet_dice where dice_state = 3")
	List<DBDiceBetting> getLateDice();
	
	//修改代币发放的状态
	@Update("update t_user_bet_dice set topStatus = #{topStatus} where id = #{id}")
	void updateDiceTopState(DBDiceBetting dice);
	
	//修改交易地址的状态
	@Update("update t_user_bet_dice set dice_state = 1 where id = #{id}")
	void updateDiceState(@Param(value = "id") long id);
	
	
}