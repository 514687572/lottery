package com.stip.net.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stip.net.dao.LotteryRecordsDao;
import com.stip.net.dao.LotteryUserDao;
import com.stip.net.entity.DBDiceBetting;
import com.stip.net.entity.DBDiceBettingScore;
import com.stip.net.entity.LotteryRecords;
import com.stip.net.entity.LotteryUserScore;
import com.stip.net.example.LotteryRecordsExample;
import com.stip.net.service.LotteryService;

@Service
public class LotteryServiceImpl implements LotteryService{
	@Autowired
	private LotteryRecordsDao lotteryRecordsDao;
	@Autowired
	private LotteryUserDao lotteryUserdao;
	
	public void addLottery(LotteryRecords record) {
		lotteryRecordsDao.insert(record);
	}
	
	public void updateByPrimaryKey(LotteryRecords record) {
		record.setUpdateTime(new Date());
		lotteryRecordsDao.updateByPrimaryKey(record);
	}
	
	/**
	 * 查询出当前未开奖的信息
	 */
	public LotteryRecords getNotPrize() {
		LotteryRecordsExample example=new LotteryRecordsExample();
		example.createCriteria().andUpdateTimeIsNull();
		
		List<LotteryRecords> list=lotteryRecordsDao.selectByExample(example);
		
		if(list!=null&&list.size()>0) {
			return list.get(0);
		}else {
			LotteryRecords records=new LotteryRecords();
			records.setCreateTime(new Date());
			records.setRecordsId(getMaxNum());
			addLottery(records);
			
			return records;
		}
	}
	
	/**
	 * 查询出当前未开奖的信息
	 */
	public LotteryRecords getNotPrizeByTime(String order) {
		LotteryRecordsExample example=new LotteryRecordsExample();
		example.createCriteria().andUpdateTimeIsNull();
		example.setOrderByClause(" lotteryRecords.records_id "+order);
		
		List<LotteryRecords> list=lotteryRecordsDao.selectByExample(example);
		
		if(list!=null&&list.size()>0) {
			return list.get(0);
		}else {
			LotteryRecords records=new LotteryRecords();
			records.setCreateTime(new Date());
			records.setRecordsId(getMaxNum());
			addLottery(records);
			
			List<LotteryRecords> newList=lotteryRecordsDao.selectByExample(example);
			
			if(newList!=null&&newList.size()>0) {
				return newList.get(0);
			}else {
				return null;
			}
			
		}
	}
	
	/**
	 * 查询出当前未开奖的信息
	 */
	public LotteryRecords getNotPrizeByTimeForPrize(String order) {
		LotteryRecordsExample example=new LotteryRecordsExample();
		example.createCriteria().andUpdateTimeIsNull();
		example.setOrderByClause(" lotteryRecords.records_id "+order);
		
		List<LotteryRecords> list=lotteryRecordsDao.selectByExample(example);
		
		if(list!=null&&list.size()>0) {
			if(list.size()==1) {
				LotteryRecords records=new LotteryRecords();
				records.setCreateTime(new Date());
				records.setRecordsId(list.get(0).getRecordsId()+1);
				addLottery(records);
			}
			
			return list.get(0);
		}else {
			LotteryRecords records=new LotteryRecords();
			records.setCreateTime(new Date());
			records.setRecordsId(getMaxNum());
			addLottery(records);
			
			List<LotteryRecords> newList=lotteryRecordsDao.selectByExample(example);
			
			if(newList!=null&&newList.size()>0) {
				if(list.size()==1) {
					LotteryRecords newRecords=new LotteryRecords();
					newRecords.setCreateTime(new Date());
					newRecords.setRecordsId(newList.get(0).getRecordsId()+1);
					addLottery(newRecords);
				}
				
				return newList.get(0);
			}else {
				return null;
			}
			
		}
	}

	/**
	 * 根据期号查询开奖历史
	 */
	public LotteryRecords getLotteryById(long id) {
		LotteryRecordsExample example=new LotteryRecordsExample();
		example.createCriteria().andRecordsIdEqualTo(id);
		
		List<LotteryRecords> list=lotteryRecordsDao.selectByExample(example);
		
		if(list!=null&&list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}
	
	/**
	 * 查询开奖历史
	 * @param pageNum
	 * @return
	 */
	public List<LotteryRecords> getLotteryHistry(int pageNum,String rowNum) {
		LotteryRecordsExample example=new LotteryRecordsExample();
		example.createCriteria().andUpdateTimeIsNotNull();
		example.setOrderByClause(" lotteryRecords.records_id desc");
		if(null==rowNum) {
			example.setPager(pageNum, 10);
		}else {
			example.setPager(pageNum,Integer.parseInt(rowNum));
		}
		
		List<LotteryRecords> list=lotteryRecordsDao.selectByExample(example);
		
		return list;
	}
	
	public long getMaxNum() {
		LotteryRecordsExample examplet=new LotteryRecordsExample();
		examplet.setOrderByClause(" lotteryRecords.records_id desc");
		examplet.setPager(1, 1);
		
		List<LotteryRecords> listt=lotteryRecordsDao.selectByExample(examplet);
		
		if(listt!=null&&listt.size()>0) {
			return listt.get(0).getRecordsId()+1;
		}else {
			return 1;
		}
	}

	@Override
	public void addDBDiceBetting(List<DBDiceBetting> record) {
		lotteryRecordsDao.insertDiceBetting(record);
	}

	@Override
	public List<DBDiceBetting> getAccountDice(String account,int begin,int limit) {
		return lotteryRecordsDao.getAccountDice(account,begin,limit);
	}

	@Override
	public void updateDiceStateto(String account) {
		lotteryRecordsDao.updateDiceStateto(account);
	}

	@Override
	public List<DBDiceBetting> getDiceState() {
		return lotteryRecordsDao.getDiceState();
	}

	@Override
	public List<DBDiceBetting> getDiceTopState() {
		return lotteryRecordsDao.getDiceTopState();
	}

	@Override
	public void updateDiceTopState(DBDiceBetting dice) {
		lotteryRecordsDao.updateDiceTopState(dice);
	}

	@Override
	public List<DBDiceBettingScore> getAccountDiceScore(long uid, int begin, int limit) {
		//用户信息
		LotteryUserScore lotteryUserScore = lotteryUserdao.getLotteryUserScoreId(uid);
		if(lotteryUserScore==null){
			return null;
		}
		return lotteryRecordsDao.getAccountDiceScore(lotteryUserScore.getId(), begin, limit);
	}

	@Override
	public void addDBDiceBettingScore(DBDiceBettingScore record) {
		lotteryRecordsDao.insertDiceBettingScore(record);
	}

	@Override
	public List<DBDiceBetting> getLateDice() {
		return lotteryRecordsDao.getLateDice();
	}

	@Override
	public void updateDiceState(long id) {
		lotteryRecordsDao.updateDiceState(id);
	}

}
