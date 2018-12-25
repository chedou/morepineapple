package com.jnshu.resourceservice.dao;

import com.jnshu.resourceservice.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

/**
 * @description 角色权限关联持久层
 * @author Mr.HUANG
 * @date 2018/12/13
 */
@Mapper
public interface RolePermissionMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    RolePermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RolePermission record);

    int updateByPrimaryKey(RolePermission record);

    /**
     * @Description 将 角色ID与权限ID组 写入角色权限信息关联表
     * @param [roleAndPermission]
     * @return int 执行的操作数据
     * @author Mr.HUANG
     * @date 2018/12/25
     * @throws
     */
    int insertRolePermission(Map roleAndPermission);

}