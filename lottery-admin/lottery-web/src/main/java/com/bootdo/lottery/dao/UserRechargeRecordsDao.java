package com.bootdo.lottery.dao;


import java.util.List;
import java.util.Map;

import com.bootdo.lottery.domain.UserRechargeRecordsDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author zhangliang
 * @email 877495411@.com
 * @date 2019-01-09 15:37:15
 */
@Mapper
public interface UserRechargeRecordsDao {

	UserRechargeRecordsDO get(Integer id);
	
	List<UserRechargeRecordsDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(UserRechargeRecordsDO userRechargeRecords);
	
	int update(UserRechargeRecordsDO userRechargeRecords);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
