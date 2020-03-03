package com.stip.net.dao;

import com.stip.mybatis.generator.plugin.GenericMapper;
import com.stip.net.entity.UserTransactionScore;
import com.stip.net.example.UserTransactionScoreExample;

 /**
 * 可添加自定义查询语句，方便后续扩展
 **/
public interface UserTransactionScoreDao extends GenericMapper<UserTransactionScore, UserTransactionScoreExample, Integer> {
}