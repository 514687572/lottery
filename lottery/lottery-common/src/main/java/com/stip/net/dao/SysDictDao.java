package com.stip.net.dao;

import com.stip.mybatis.generator.plugin.GenericMapper;
import com.stip.net.entity.SysDict;
import com.stip.net.example.SysDictExample;

/**
* 可添加自定义查询语句，方便后续扩展
**/
public interface SysDictDao extends GenericMapper<SysDict, SysDictExample, Long> {
}