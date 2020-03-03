package com.bootdo.lottery.service.impl;

import com.bootdo.common.constant.IConstInfo;
import com.bootdo.common.utils.DateUtil;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.lottery.dao.UserBerecordsDao;
import com.bootdo.lottery.domain.UserBerecordsDO;
import com.bootdo.lottery.pojo.UserBerecordsPojo;
import com.bootdo.lottery.service.UserBerecordsService;
import com.bootdo.system.domain.UserDO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


@Service
public class UserBerecordsServiceImpl implements UserBerecordsService {
    @Autowired
    private UserBerecordsDao userBerecordsDao;

    @Override
    public UserBerecordsDO get(Integer betId) {
        return userBerecordsDao.get(betId);
    }

    @Override
    public List<UserBerecordsDO> list(Map<String, Object> map) {
        return userBerecordsDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return userBerecordsDao.count(map);
    }

    @Override
    public int save(UserBerecordsDO userBerecords) {
        return userBerecordsDao.save(userBerecords);
    }

    @Override
    public int update(UserBerecordsDO userBerecords) {
        return userBerecordsDao.update(userBerecords);
    }

    @Override
    public int remove(Integer betId) {
        return userBerecordsDao.remove(betId);
    }

    @Override
    public int batchRemove(Integer[] betIds) {
        return userBerecordsDao.batchRemove(betIds);
    }

    /**
     * 按月 日 时 统计用户下注记录
     *
     * @param status 常量
     * @return
     */
    @Override
    public List<UserBerecordsPojo> listByStatistics(String status) {

        Map<String, Object> params = new HashMap<>();
        Date date = new Date();
        List<UserBerecordsPojo> pojos = new ArrayList<>();
        if (IConstInfo.STATISTICS_MONTH.equals(status)) {
            //按月统计
            List<String> lastMonthDate = DateUtil.getLastMonthDate(date, 1);
            params.put("dataList", lastMonthDate);
            pojos = userBerecordsDao.listByDayStatistics(params);
        } else if (IConstInfo.STATISTICS_DAY.equals(status)) {
            //按日统计  7天
            List<String> lastDayDate = DateUtil.getLastDayDate(date, 7);
            params.put("dataList", lastDayDate);
            pojos = userBerecordsDao.listByDayStatistics(params);
        } else {
            //按小时统计  24小时
            List<String> lastHourDate = DateUtil.getLastHourDate(date, 24);
            params.put("dataList", lastHourDate);
            pojos = userBerecordsDao.listByHourStatistics(params);
        }
        return pojos;
    }

    /**
     * 按月 日 时 统计用户活跃量
     *
     * @param status 常量
     * @return
     */
    @Override
    public List<UserBerecordsPojo> listByUserActivity(String status) {

        Map<String, Object> params = new HashMap<>();
        Date date = new Date();
        List<UserBerecordsPojo> pojos = new ArrayList<>();
        if (IConstInfo.STATISTICS_MONTH.equals(status)) {
            //按月统计
            List<String> lastMonthDate = DateUtil.getLastMonthDate(date, 1);
            params.put("dataList", lastMonthDate);
            pojos = userBerecordsDao.listByDayUserCount(params);
        } else if (IConstInfo.STATISTICS_DAY.equals(status)) {
            //按日统计  7天
            List<String> lastDayDate = DateUtil.getLastDayDate(date, 7);
            params.put("dataList", lastDayDate);
            pojos = userBerecordsDao.listByDayUserCount(params);
        } else {
            //按小时统计  24小时
            List<String> lastHourDate = DateUtil.getLastHourDate(date, 24);
            params.put("dataList", lastHourDate);
            pojos = userBerecordsDao.listByHourUserCount(params);
        }
        return pojos;
    }

    /**
     * 按月 日 时 统计用户活跃量
     *
     * @param status 常量
     * @return
     */
    @Override
    public List<UserBerecordsPojo> listByProfit(String status) {

        Map<String, Object> params = new HashMap<>();
        Date date = new Date();
        List<UserBerecordsPojo> pojos = new ArrayList<>();
        if (IConstInfo.STATISTICS_MONTH.equals(status)) {
            //按月统计
            List<String> lastMonthDate = DateUtil.getLastMonthDate(date, 1);
            params.put("frist", lastMonthDate.get(0));
            params.put("last", lastMonthDate.get(lastMonthDate.size()-1));
//            params.put("list" ,userBerecordsDao.getDayList(params));
            params.put("list" ,lastMonthDate);
            pojos = userBerecordsDao.listByDayProfit(params);
        } else if (IConstInfo.STATISTICS_DAY.equals(status)) {
            //按日统计  7天
            List<String> lastDayDate = DateUtil.getLastDayDate(date, 7);
            params.put("frist", lastDayDate.get(lastDayDate.size()-1));
            params.put("last", lastDayDate.get(0));
            params.put("list" ,lastDayDate);
            pojos = userBerecordsDao.listByDayProfit(params);
        } else {
            //按小时统计  24小时
            List<String> lastHourDate = DateUtil.getLastHourDate(date, 24);
            params.put("frist", lastHourDate.get(lastHourDate.size()-1));
            params.put("last", lastHourDate.get(0));
            params.put("list" ,lastHourDate);
            pojos = userBerecordsDao.listByHourProfit(params);
        }
        return pojos;
    }

    @Override
    public BigDecimal accumulateBalance() {
        return userBerecordsDao.accumulateBalance();
    }

    /**
     * 修改用户冻结状态
     *
     * @return
     */
    @Override
    public int updateUserStatus(Integer userId) {
        UserDO user = ShiroUtils.getUser();
        return userBerecordsDao.updateUserStatus(userId, user.getUsername(), user.getUserId());
    }

    /**
     * 用户统计 列表
     *
     * @param map
     * @return
     */
    @Override
    public List<UserBerecordsDO> listForUsers(Map<String, Object> map) {
        List<UserBerecordsDO> userBerecordsDOS = userBerecordsDao.listForUsers(map);
        if (CollectionUtils.isNotEmpty(userBerecordsDOS)) {
            // 计算胜率
            for (int i = 0; i < userBerecordsDOS.size(); i++) {
                Map<String, Object> obj = new HashMap<>(1);
                obj.put("userName", userBerecordsDOS.get(i).getUserName());
                UserBerecordsDO userBerecordsDO = userBerecordsDao.userWin(obj);
                if (userBerecordsDO != null) {
                    userBerecordsDOS.get(i).setNoteMoney(userBerecordsDO.getNoteMoney());
                    userBerecordsDOS.get(i).setLotteryBonus(userBerecordsDO.getLotteryBonus());
                    userBerecordsDOS.get(i).setWinning(userBerecordsDO.getWinning());
                }

            }
        }

        return userBerecordsDOS;
    }

    /**
     * 用户统计 总数 用于分页
     *
     * @param map
     * @return
     */
    @Override
    public int countForUsers(Map<String, Object> map) {
        return userBerecordsDao.countForUsers(map);
    }

    @Override
    public List<UserBerecordsDO> getMyChilds(Map<String, Object> map) {
        List<UserBerecordsDO> results = userBerecordsDao.getMyChildsData(map);
        return results;
    }

    /**
     * 用户统计 总数 用于分页
     *
     * @param map
     * @return
     */
    @Override
    public int getMyChildsDataCount(Map<String, Object> map) {
        return userBerecordsDao.getMyChildsDataCount(map);
    }

    @Override
    public Long getUserInfoByName(String userName) {
        return userBerecordsDao.getUserInfoByName(userName);
    }
}
