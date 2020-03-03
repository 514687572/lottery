package com.bootdo.lottery.dao;


import java.util.List;
import java.util.Map;

import com.bootdo.lottery.domain.UserIpDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author zhangliang
 * @email 877495411@.com
 * @date 2018-12-18 14:44:15
 */
@Mapper
public interface UserIpDao {

	UserIpDO get(Long id);
	
	List<UserIpDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(UserIpDO userIp);
	
	int update(UserIpDO userIp);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	int updateIpStatus(UserIpDO userIp);
}
