package com.jnshu.resourceservice.dao;

import com.jnshu.resourceservice.entity.*;
import org.apache.ibatis.annotations.*;

/**
 * @description Role角色持久层
 * @author Mr.HUANG
 * @date 2018/12/13
 */
@Mapper
public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    /**
     * @Description 用户管理-插入角色附带相关的权限信息
     * @param [record]
     * @return int
     * @author Mr.HUANG
     * @date 2018/12/25
     * @throws Exception
     */
    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    Role selectRolePermissionById(Integer id);

    int insertRole(Role role);

    /**
     * @Description 角色管理-根据角色名查询角色信息
     * @param [RoleName]
     * @return com.jnshu.resourceservice.entity.Role
     * @author Mr.HUANG
     * @date 2018/12/25
     * @throws
     */
    Role selectByRoleName(String roleName);
}