package com.stip.net.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stip.net.dao.LotteryUserDao;
import com.stip.net.entity.LotteryUser;
import com.stip.net.example.LotteryUserExample;
import com.stip.net.example.LotteryUserExample.Criteria;
import com.stip.net.service.UserService;
/**
 * 用户信息相关
 * @author cja
 *
 */
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private LotteryUserDao lotteryUserDao;

	public boolean addUserInfo(String userName,String pubKey) {
		boolean isUser=selectUserByName(userName);
		
		if(isUser) {
    		LotteryUser user=new LotteryUser();
    		user.setUserName(userName);
    		user.setUserKey(pubKey);
    		user.setCreateTime(new Date());
    		user.setUserStatus("1");
    		user.setUserCode(userName);
    		
			lotteryUserDao.insert(user);
			
			return true;
		}
		
		return false;
	}
	
	public boolean selectUserByName(String userName) {
		LotteryUserExample example=new LotteryUserExample();
		Criteria criteria=example.createCriteria();
		criteria.andUserNameEqualTo(userName);
		
		List<LotteryUser> list=lotteryUserDao.selectByExample(example);
		
		if(list!=null&&list.size()>0) {
			return false;
		}else {
			return true;
		}
	}
	
	public LotteryUser getUserByName(String userName) {
		LotteryUserExample example=new LotteryUserExample();
		Criteria criteria=example.createCriteria();
		criteria.andUserNameEqualTo(userName);
		
		List<LotteryUser> list=lotteryUserDao.selectByExample(example);
		
		if(list!=null&&list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}
	
	public LotteryUser selectUserByNameAndKey(String userName,String pubKey) {
		LotteryUserExample example=new LotteryUserExample();
		Criteria criteria=example.createCriteria();
		criteria.andUserNameEqualTo(userName).andUserKeyEqualTo(pubKey);
		
		List<LotteryUser> list=lotteryUserDao.selectByExample(example);
		
		if(list!=null&&list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}
}
