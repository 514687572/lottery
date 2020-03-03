package com.stip.net.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stip.net.dao.SysDictDao;
import com.stip.net.entity.SysDict;
import com.stip.net.example.SysDictExample;
import com.stip.net.service.SysDictService;

/**
 * 数据字典
 *
 * @author cja
 */
@Service
public class SysDictServiceImpl implements SysDictService {
    @Autowired
    private SysDictDao sysDictDao;

    /**
     * 根据类型获取字典配置
     *
     * @param type
     * @return
     */
    @Override
    public List<SysDict> getSysdictByType(String type) {
        SysDictExample example = new SysDictExample();
        example.createCriteria().andTypeEqualTo(type);
        List<SysDict> list = sysDictDao.selectByExample(example);
        return list;
    }
}
