package com.lottery.net.utils;

import java.util.ArrayList;
import java.util.List;

import com.stip.net.entity.DBDiceBetting;
import com.stip.net.entity.DBDiceBettingScore;

public class DBCache {
	private static DBCache _instance;
	
	private List<DBDiceBetting> diceBettings=new ArrayList<>();	//EOS用户投注列表
	private List<DBDiceBettingScore> diceBettingScores=new ArrayList<>();	//积分用户投注列表
	

	public static final DBCache getInstance() {
		if (_instance == null)
			_instance = new DBCache();
		return _instance;
	}

	public List<DBDiceBetting> getDiceBettings() {
		return diceBettings;
	}
	
	public List<DBDiceBettingScore> getDiceBettingScores() {
		return diceBettingScores;
	}
	/**
	 * 根据期号查询eos投注列表
	 * @param termnumber
	 * @return
	 */
	public List<DBDiceBetting> getDiceBettingsTerm(long termnumber){
		List<DBDiceBetting> bettings=new ArrayList<>();
		for(DBDiceBetting db:diceBettings){
			if(db.getTermnumber()==termnumber){
				bettings.add(db);
			}
		}
		return bettings;
	}
	
	/**
	 * 根据期号查询积分投注列表
	 * @param termnumber
	 * @return
	 */
	public List<DBDiceBettingScore> getDiceBettingsTermScore(long termnumber){
		List<DBDiceBettingScore> bettings=new ArrayList<>();
		for(DBDiceBettingScore db:diceBettingScores){
			if(db.getTermnumber()==termnumber){
				bettings.add(db);
			}
		}
		return bettings;
	}
	
	
	/**
	 * 查询骰子eos投注成功和开奖列表
	 * @return
	 */
	public List<DBDiceBetting> getDiceAwardPrizes(){
		List<DBDiceBetting> bettings=new ArrayList<>();
		for(DBDiceBetting db:diceBettings){
			if(db.getDice_state()==1 && db.getPrizenumber()!=null){
				bettings.add(db);
			}
		}
		return bettings;
	}
	
	/**测试
	 * 查询骰子投注成功和开奖列表
	 * @return
	 */
	public List<DBDiceBetting> getDiceAwardPrizes1(){
		List<DBDiceBetting> bettings=new ArrayList<>();
		for(DBDiceBetting db:diceBettings){
			if(db.getPrizenumber()!=null){
				bettings.add(db);
			}
		}
		return bettings;
	}
	
	/**
	 * 根据用户查询eos投注信息
	 * @param termnumber
	 * @return
	 */
	public DBDiceBetting getDiceBettingAccount(String account){
		for(DBDiceBetting db:diceBettings){
			if(db.getAccount().equals(account)){
				return db;
			}
		}
		return null;
	}

	/**
	 * 根据用户id查询积分投注信息
	 * @param termnumber
	 * @return
	 */
	public DBDiceBettingScore getDiceBettingAccountScoreId(long uid){
		for(DBDiceBettingScore db:diceBettingScores){
			if(db.getUid()==uid){
				return db;
			}
		}
		return null;
	}
	
	/**
	 * 根据交易地址查询eos投注信息
	 * @param termnumber
	 * @return
	 */
	public DBDiceBetting getDiceBettingTransaction_id(String transaction_id){
		for(DBDiceBetting db:diceBettings){
			if(db!=null && db.getTransaction_id().equals(transaction_id)){
				return db;
			}
		}
		return null;
	}
	
}
