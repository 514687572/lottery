package com.stip.net.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.stip.net.entity.EosFlow;

public interface GameDao {

	void insertEosFlow(EosFlow ef);

	long countMyPut(@Param("username") String username);
	
	@Select("SELECT v FROM t_var WHERE k = #{k}")
	String getV(@Param("k") String k);
	
	@Update("UPDATE t_var SET v = #{v} WHERE k = #{k}")
	int updateVar(@Param("k") String k, @Param("v")String v);
}