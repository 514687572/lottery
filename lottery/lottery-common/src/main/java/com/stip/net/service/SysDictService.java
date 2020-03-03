package com.stip.net.service;

import java.util.List;

import com.stip.net.entity.SysDict;

public interface SysDictService {
    /**
     * 根据类型获取字典配置
     * @param type
     * @return
     */
    List<SysDict> getSysdictByType(String type);
}