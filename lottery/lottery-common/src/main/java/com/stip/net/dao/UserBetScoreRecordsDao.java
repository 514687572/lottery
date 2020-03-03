package com.stip.net.dao;

import com.stip.mybatis.generator.plugin.GenericMapper;
import com.stip.net.entity.UserBetScoreRecords;
import com.stip.net.example.UserBetScoreRecordsExample;

 /**
 * 可添加自定义查询语句，方便后续扩展
 **/
public interface UserBetScoreRecordsDao extends GenericMapper<UserBetScoreRecords, UserBetScoreRecordsExample, Integer> {
}