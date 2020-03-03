package com.bootdo.lottery.service;


import com.bootdo.lottery.domain.UserIpDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhangliang
 * @email 877495411@.com
 * @date 2018-12-18 14:44:15
 */
public interface UserIpService {
	
	UserIpDO get(Long id);
	
	List<UserIpDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(UserIpDO userIp);
	
	int update(UserIpDO userIp);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	int updateIpStatus(UserIpDO userIp);
}
