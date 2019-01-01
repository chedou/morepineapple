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

    /**
     * @Description 根据角id roleId删除该角色的 角色与权限关联的信息
     * @param [roleUId]
     * @return int
     * @author Mr.HUANG
     * @date 2018/12/25
     * @throws
     */
    int deleteByRoleId(Integer roleUId);

    /**
     * @Description 根据角色的ID查出角色与权限的关联信息
     * @param [roleId]
     * @return com.jnshu.resourceservice.entity.RolePermission
     * @author Mr.HUANG
     * @date 2018/12/25
     * @throws Exception
     */
    List<RolePermission> selectByRoleId(Integer roleId);

    RolePermission insertSelectiveRolePer(Map roleAndPermission );

}