package com.stip.net.dao;

import com.stip.mybatis.generator.plugin.GenericMapper;
import com.stip.net.entity.UserIp;
import com.stip.net.example.UserIpExample;

/**
* 可添加自定义查询语句，方便后续扩展
**/
public interface UserIpDao extends GenericMapper<UserIp, UserIpExample, Long> {
}