package com.jnshu.resourceservice.dao;

import com.jnshu.resourceservice.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

/**
 * @description Role角色持久层
 * @author Mr.HUANG
 * @date 2018/12/13
 */
@Mapper
public interface RoleMapper {
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(Role record);

    /**
     * @Description 用户管理-插入角色附带相关的权限信息
     * @param [record]
     * @return int
     * @author Mr.HUANG
     * @date 2018/12/25
     * @throws Exception
     */
    Integer insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(Role record);

    Integer updateByPrimaryKey(Role record);

    /**
     * @Description 根据角色ID查询对应的角色信息以及权限信息
     * @param [id]
     * @return com.jnshu.resourceservice.entity.Role
     * @author Mr.HUANG
     * @date 2018/12/26
     * @throws Exception
     */
    Role selectRolePermissionById(Integer id);

    /**
     * @Description 角色管理-根据角色名查询角色信息
     * @param [RoleName]
     * @return com.jnshu.resourceservice.entity.Role
     * @author Mr.HUANG
     * @date 2018/12/25
     * @throws
     */
    Role selectByRoleName(String roleName);

    /**
     * @Description 获取角色信息列表
     * @param []
     * @return java.util.List<com.jnshu.resourceservice.entity.Role>
     * @author Mr.HUANG
     * @date 2018/12/26
     * @throws Exception
     */
    List<Role> selectAll();



}