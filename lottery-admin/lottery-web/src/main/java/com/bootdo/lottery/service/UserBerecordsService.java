package com.bootdo.lottery.service;


import com.bootdo.lottery.domain.UserBerecordsDO;
import com.bootdo.lottery.pojo.UserBerecordsPojo;

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
public interface UserBerecordsService {

    UserBerecordsDO get(Integer betId);

    List<UserBerecordsDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(UserBerecordsDO userBerecords);

    int update(UserBerecordsDO userBerecords);

    int remove(Integer betId);

    int batchRemove(Integer[] betIds);

    /**
     * 统计用户下注
     * @param status 1按月 2最近7天 3最近24小时
     * @return
     */
    List<UserBerecordsPojo> listByStatistics(String status);

    /**
     * 统计活跃用户
     * @param status 1按月 2最近7天 3最近24小时
     * @return
     */
    List<UserBerecordsPojo> listByUserActivity(String status);

    /**
     * 利润统计
     * @param status
     * @return
     */
    List<UserBerecordsPojo> listByProfit(String status);

    /**
     * 累计交易额
     * @return
     */
    BigDecimal accumulateBalance();

    /**
     * 修改用户冻状态
     * @return
     */
    int updateUserStatus(Integer userId);

    /**
     * 用户统计 列表
     * @param map
     * @return
     */
    List<UserBerecordsDO> listForUsers(Map<String, Object> map);

    /**
     * 用户统计 总数  用于分页
     * @param map
     * @return
     */
    int countForUsers(Map<String, Object> map);

    /**
     * 下级列表
     * @param map
     * @return
     */
    List<UserBerecordsDO> getMyChilds(Map<String, Object> map);

    /**
     * 下级列表总数
     * @param map
     * @return
     */
    int getMyChildsDataCount(Map<String, Object> map);

    /**
     * 根据用户名获取用户id
     * @param userName
     * @return
     */
    Long getUserInfoByName(String userName);

}
