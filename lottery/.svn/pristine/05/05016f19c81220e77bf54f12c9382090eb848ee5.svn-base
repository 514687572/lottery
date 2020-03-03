package com.stip.net.dao;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.stip.net.entity.UserRechargeRecords;

public interface UserRechargeRecordsDao {
	
	@Insert("insert into t_user_recharge_records(inOrderId,uid,eos_price,recharge_num,recharge_type,recharge_status,num,time) values(#{inOrderId},#{uid},#{eos_price},#{recharge_num},#{recharge_type},#{recharge_status},#{num},#{time})")
	public void addRechargeRecords(UserRechargeRecords record);

	@Select("select * from t_user_recharge_records where inOrderId=#{inOrderId} and recharge_status != 1")
	public UserRechargeRecords getRechargeRecordinOrderId(@Param("inOrderId") BigDecimal inOrderId);
	
	@Update("update t_user_recharge_records set recharge_status = #{status} where inOrderId= #{inOrderId}")
	public void updateRechargeRecordin(@Param("inOrderId") BigDecimal inOrderId,@Param("status") int status);
}
