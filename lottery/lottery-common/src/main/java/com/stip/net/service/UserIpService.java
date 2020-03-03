package com.stip.net.service;

import java.util.List;

import com.stip.net.entity.UserIp;
import com.stip.net.example.UserIpExample;

public interface UserIpService {

    List<UserIp> selectByExample(UserIpExample example);

    int insert(UserIp userIp);

    int updateByPrimaryKey(UserIp userIp);

}
