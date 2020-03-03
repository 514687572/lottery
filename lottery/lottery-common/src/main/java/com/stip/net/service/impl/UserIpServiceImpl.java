package com.stip.net.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stip.net.dao.UserIpDao;
import com.stip.net.entity.UserIp;
import com.stip.net.example.UserIpExample;
import com.stip.net.service.UserIpService;

/**
 * @Package: com.stip.net.service.impl
 * @Description:
 * @Author: cgnet05
 * @CreatDate: 2018/12/18
 */

@Service
public class UserIpServiceImpl implements UserIpService {
    @Autowired
    private UserIpDao userIpDao;

    @Override
    public List<UserIp> selectByExample(UserIpExample example) {
        return userIpDao.selectByExample(example);
    }

    @Override
    public int insert(UserIp userIp) {
        return userIpDao.insert(userIp);
    }

    @Override
    public int updateByPrimaryKey(UserIp userIp) {
        return userIpDao.updateByPrimaryKey(userIp);
    }
}
