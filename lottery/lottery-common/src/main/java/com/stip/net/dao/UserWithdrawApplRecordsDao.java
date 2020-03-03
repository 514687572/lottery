package com.stip.net.dao;

import com.stip.mybatis.generator.plugin.GenericMapper;
import com.stip.net.entity.UserWithdrawApplRecords;
import com.stip.net.example.UserWithdrawApplRecordsExample;

 /**
 * 可添加自定义查询语句，方便后续扩展
 **/
public interface UserWithdrawApplRecordsDao extends GenericMapper<UserWithdrawApplRecords, UserWithdrawApplRecordsExample, String> {
}