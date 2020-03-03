package com.bootdo.lottery.dao;


import com.bootdo.lottery.domain.UserBerecordsDO;
import com.bootdo.lottery.pojo.UserBerecordsPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 用户下注记录
 *
 * @author zhangliang
 * @email 877495411@.com
 * @date 2018-11-16 17:02:19
 */
@Mapper
public interface UserBerecordsDao {

    UserBerecordsDO get(Integer betId);

    List<UserBerecordsDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(UserBerecordsDO userBerecords);

    int update(UserBerecordsDO userBerecords);

    int remove(Integer bet_id);

    int batchRemove(Integer[] betIds);

    List<UserBerecordsPojo> listByHourStatistics(Map<String, Object> map);

    List<UserBerecordsPojo> listByDayStatistics(Map<String, Object> map);

    List<UserBerecordsPojo> listByHourUserCount(Map<String, Object> map);

    List<UserBerecordsPojo> listByDayUserCount(Map<String, Object> map);

    List<UserBerecordsPojo> listByDayProfit(Map<String, Object> map);

    List<UserBerecordsPojo> listByHourProfit(Map<String, Object> map);

    BigDecimal accumulateBalance();

    int updateUserStatus(@Param("userId") Integer userId, @Param("userName") String userName, @Param("optionId") Long optionId);

    List<UserBerecordsDO> listForUsers(Map<String, Object> map);

    UserBerecordsDO userWin(Map<String, Object> map);

    int countForUsers(Map<String, Object> map);

    List<UserBerecordsDO> getMyChildsData(Map<String, Object> map);

    int getMyChildsDataCount(Map<String, Object> map);

    Long getUserInfoByName(String userName);

    List<String> getDayList(Map<String, Object> map);

    List<String> getHourList(Map<String, Object> map);
}
