package com.jnshu.resourceservice.service.impl;

import com.jnshu.resourceservice.dao.*;
import com.jnshu.resourceservice.entity.*;
import com.jnshu.resourceservice.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class ModuleTableServiceImpl implements ModuleTableService {
    @Autowired
    private ModuleTableMapper moduleTableMapper;

    // TODO: 2018/11/8 获取模块管理列表
    @Override
    public List<ModuleTable> qureyModuleList() {
        return moduleTableMapper.qureyModuleList();
    }

    // TODO: 2018/11/8 新增模块信息
    @Override
    public int insertModuleInfo(String name, String menuld, String url, Integer parentModuleId, String moduleCategory, String createBy, Long gmtCreate) {
        Integer i = moduleTableMapper.insertModuleInfo(name, menuld, url, parentModuleId, moduleCategory, createBy, gmtCreate);
        return i;
    }

    // TODO: 2018/11/8 更新模块信息
    @Override
    public int updateModuleById(Integer id, String name, String menuld, String url, Integer parentModuleId, String moduleCategory, String updateBy, Long gmtUpdate) {
        Integer i = moduleTableMapper.updateModuleById(id, name, menuld, url, parentModuleId, moduleCategory, updateBy, gmtUpdate);
        return i;
    }

    // TODO: 2018/11/8 删除模块信息
    @Override
    public int deleteModuleById(Integer id) {
        Integer i=moduleTableMapper.deleteModuleById(id);
        return i;
    }

    // TODO: 2018/11/8 查询单个模块信息
    @Override
    public List<ModuleTable> qureyModuleInfoById(Integer id) {
        List list=moduleTableMapper.qureyModuleInfoById(id);
        return list;
    }
}
