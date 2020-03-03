package com.stip.net.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.net.utils.Constants;
import com.stip.net.dao.LotteryUserDao;
import com.stip.net.dao.UserBetRecordsDao;
import com.stip.net.entity.LotteryUser;
import com.stip.net.entity.LotteryUserScore;
import com.stip.net.entity.UserBetRecords;
import com.stip.net.example.LotteryUserExample;
import com.stip.net.main.MainData;
import com.stip.net.service.GameService;
import com.stip.net.service.LotteryUserService;

@Service
public class LotteryUserServiceImpl implements LotteryUserService {
    @Autowired
    private LotteryUserDao lotteryUserDao;
    @Autowired
    private UserBetRecordsDao userBetRecordsDao;
    @Autowired
    private GameService gameService;

    @Override
    public void addLottery(LotteryUser user) {
        lotteryUserDao.insert(user);
    }

    @Override
    public void updateByPrimaryKeySelective(LotteryUser record) {
        lotteryUserDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public LotteryUser getlotteryUser(LotteryUser record) {
        LotteryUserExample example = new LotteryUserExample();
        LotteryUserExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(record.getUserName())) {
            criteria.andUserNameEqualTo(record.getUserName());
        }
        if (StringUtils.isNotBlank(record.getUserCode())) {
            criteria.andUserCodeEqualTo(record.getUserCode());
        }
        List<LotteryUser> list = lotteryUserDao.selectByExample(example);

        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据用户名获取邀请人
     *
     * @param lotteryUser
     * @return
     */
    @Override
    public LotteryUser getLotteryByInviter(LotteryUser lotteryUser) {
        lotteryUser = this.getlotteryUser(lotteryUser);

        if (lotteryUser == null || lotteryUser.getReferrer() == null || "0".equals(lotteryUser.getReferrer())) {
            return null;
        }

        LotteryUser inviter = lotteryUserDao.selectByPrimaryKey(lotteryUser.getReferrer());
        if (null == inviter || StringUtils.isBlank(inviter.getUserName())) {
            return null;
        }

        return inviter;
    }

    /**
     * @param userName 当前用户
     * @param userCode 推荐人的邀请码
     */
    @Override
    public LotteryUser updateRelatioin(String userName, String userCode) {
        // 当前用户信息
        LotteryUser lotteryUser = new LotteryUser();
        lotteryUser.setUserName(userName);
        lotteryUser = this.getlotteryUser(lotteryUser);

        if (lotteryUser == null || lotteryUser.getReferrer() != null) {
            return null;
        }

        // 邀请人信息
        LotteryUser invitationUser = new LotteryUser();
        invitationUser.setUserCode(userCode);
        invitationUser = this.getlotteryUser(invitationUser);
        if (invitationUser != null) {
            // 修改当前用户的邀请人
            LotteryUser newUser = new LotteryUser();
            newUser.setUserId(lotteryUser.getUserId());
            newUser.setReferrer(invitationUser.getUserId());
            this.updateByPrimaryKeySelective(newUser);
        }

        return invitationUser;
    }

    @Override
    public Map<String, Object> getMyChilds(String userName, int pageNum,int records) {
        LotteryUserExample example = new LotteryUserExample();
        example.createCriteria().andUserNameEqualTo(userName);
        List<LotteryUser> list = lotteryUserDao.selectByExample(example);

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        records = records == 0 ? 10 : records;
        Map<String, Object> map = new HashMap<>(4);
        map.put("userId", list.get(0).getUserId());
        map.put("fromRowNum", (pageNum == 0 ? 0 : pageNum - 1) * records);
        map.put("toRowNum", records);
        List<UserBetRecords> results = userBetRecordsDao.getMyChildsData(map);

        long count = userBetRecordsDao.getMyChildCount(map);

        map.clear();
        map.put("total", count);
        map.put("childs", results);
        map.put("totalPage", (count / records) + (count % records > 0 ? 1 : 0));
        map.put("records", records);
        return map;
    }

	@Override
	public Map<String, Object> getMyChild2(long id, int currentPage, int pageSize) {
		// 封装查询参数
		Map<String, Object> qo = new HashMap<>(4);
        qo.put("id", id);
        qo.put("start", (currentPage - 1) * pageSize);
        qo.put("pageSize", pageSize);
        // 查询
        long totalCount = userBetRecordsDao.getMyChildCount2(qo);
        List<Map<String, Object>> listData = userBetRecordsDao.getMyChildsData2(qo);
        // 封装分页结果对象
        Map<String, Object> pageResult = new HashMap<>(4);
        pageResult.put("totalCount", totalCount);
        pageResult.put("data", listData);
        pageResult.put("totalPage", (totalCount - 1) / pageSize + 1);
        return pageResult;
	}

    /**
     * 验证用户冻结状态
     *
     * @param userName
     * @return
     */
    @Override
    public boolean getUserStatus(String userName) {
        LotteryUserExample example = new LotteryUserExample();
        example.createCriteria().andUserNameEqualTo(userName);
        List<LotteryUser> list = lotteryUserDao.selectByExample(example);

        if (CollectionUtils.isEmpty(list)) {
            return false;
        }

        if (StringUtils.isNotBlank(list.get(0).getUserStatus()) && "1".equals(list.get(0).getUserStatus())) {
            return true;
        }
        return false;
    }

	@Override
	public int addLotteryUserScore(LotteryUserScore lotteryUserScore) {
		return lotteryUserDao.addLotteryUserScore(lotteryUserScore);
	}

	@Override
	public LotteryUserScore getLotteryUserScoreName(String userName) {
		return lotteryUserDao.getLotteryUserScoreName(userName);
	}

	@Override
	public LotteryUserScore getLotteryUserScoreTel(String tel) {
		return lotteryUserDao.getLotteryUserScoreTel(tel);
	}

	@Override
	public boolean plusScoreFromDice(long id, BigDecimal score) {
		int a = lotteryUserDao.updateLotteryUserScoreTeljia(id, score);
		if (a <= 0) {
			return false;
		}
		// 从骰子积分奖池扣钱
		BigDecimal poolBalance = gameService.minusPoolScore(Constants.VK_DICE_POOL_SCORE, score);
		MainData.scorePoolBalance.put(Constants.DICE_TYPE, poolBalance);
		return true;
	}

	@Override
	public boolean minusScoreToDice(long id, BigDecimal score) {
		int a = lotteryUserDao.updateLotteryUserScoreTeljian(id, score);
		if (a <= 0) {
			return false;
		}
		// 给骰子积分奖池加钱
		BigDecimal poolBalance = gameService.plusPoolScore(Constants.VK_DICE_POOL_SCORE, score);
		MainData.scorePoolBalance.put(Constants.DICE_TYPE, poolBalance);
		return true;
	}

	@Override
	public boolean plusScoreFromTiger(long id, BigDecimal score) {
		int a = lotteryUserDao.updateLotteryUserScoreTeljia(id, score);
		if (a <= 0) {
			return false;
		}
		// 从龙虎斗积分奖池扣钱
		BigDecimal poolBalance = gameService.minusPoolScore(Constants.VK_TIGER_POOL_SCORE, score);
		MainData.scorePoolBalance.put(Constants.TIGER_TYPE, poolBalance);
		return true;
	}
	
	@Override
	public boolean minusScoreToTiger(long id, BigDecimal score) {
		int a = lotteryUserDao.updateLotteryUserScoreTeljian(id, score);
		if (a <= 0) {
			return false;
		}
		// 给龙虎斗积分奖池加钱
		BigDecimal poolBalance = gameService.plusPoolScore(Constants.VK_TIGER_POOL_SCORE, score);
		MainData.scorePoolBalance.put(Constants.TIGER_TYPE, poolBalance);
		return true;
	}
	
	@Override
	public boolean plusScoreFromLottery(long id, BigDecimal score) {
		int a = lotteryUserDao.updateLotteryUserScoreTeljia(id, score);
		if (a <= 0) {
			return false;
		}
		// 从大乐透积分奖池扣钱
		BigDecimal poolBalance = gameService.minusPoolScore(Constants.VK_LOTTERY_POOL_SCORE, score);
		MainData.scorePoolBalance.put(Constants.LOTTERY_TYPE, poolBalance);
		return true;
	}
	
	@Override
	public boolean minusScoreToLottery(long id, BigDecimal score) {
		int a = lotteryUserDao.updateLotteryUserScoreTeljian(id, score);
		if (a <= 0) {
			return false;
		}
		// 给大乐透积分奖池加钱
		BigDecimal poolBalance = gameService.plusPoolScore(Constants.VK_LOTTERY_POOL_SCORE, score);
		MainData.scorePoolBalance.put(Constants.LOTTERY_TYPE, poolBalance);
		return true;
	}

	@Override
	public LotteryUserScore getLotteryUserScoreEmil(String emil) {
		return lotteryUserDao.getLotteryUserScoreEmil(emil);
	}

	@Override
	public LotteryUserScore getLotteryUserScoreId(long id) {
		LotteryUserScore user=lotteryUserDao.getLotteryUserScoreId(id);
		return user;
	}

	@Override
	public LotteryUserScore getScoreInviterById(long id) {
		return lotteryUserDao.getScoreInviterById(id);
	}

	@Override
	public BigDecimal getScoreById(long id) {
		LotteryUserScore user = getLotteryUserScoreId(id);
		if (user != null) {
			return user.getScore();
		}
		return new BigDecimal(0);
	}

	@Override
	public boolean getUserScoreStatus(String data) {
		LotteryUserScore lotteryUserScore = null;//用户信息
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		//判断是否是纯数字
		if(pattern.matcher(data).matches()){
			lotteryUserScore=lotteryUserDao.getLotteryUserScoreTel(data);
		}else{
			lotteryUserScore=lotteryUserDao.getLotteryUserScoreEmil(data);
		}
		if(lotteryUserScore !=null && lotteryUserScore.getStatus()==1){
			return true;
		}
		return false;
	}
}