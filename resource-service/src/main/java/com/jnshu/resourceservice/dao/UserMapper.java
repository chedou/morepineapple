package com.jnshu.resourceservice.dao;

import com.github.pagehelper.*;
import com.jnshu.resourceservice.entity.*;
import org.apache.ibatis.annotations.*;

import java.sql.*;
import java.util.*;

/**
 * @description  用户信息持久层
 * @author Mr.HUANG
 * @date 2018/12/13
 */
@Mapper
public interface UserMapper {
    /**
     * @Description 根据用户ID删除对应用户的用户信息
     * @param [id]
     * @return int 
     * @author Mr.HUANG
     * @date 2018/12/27 
     * @throws 
     */ 
    int deleteByPrimaryKey(Long id) throws SQLException;

    int insert(User record) throws SQLException;

    /**
     * @Description 插入用户数据
     * @param [record]
     * @return int 成功操作的数量
     * @author Mr.HUANG
     * @date 2018/12/17
     * @throws
     */
    int insertSelective(User record);
    
    User selectByPrimaryKey(Long id);

    /**
     * @Description 根据用户信息动态更新数据库中的用户信息
     * @param [record]
     * @return int 
     * @author Mr.HUANG
     * @date 2018/12/27 
     * @throws 
     */ 
    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * @Description 根据用户ID查询用户信息、角色信息以及权限信息
     * @param [id] 
     * @return com.jnshu.resourceservice.entity.User 
     * @author Mr.HUANG
     * @date 2018/12/27 
     * @throws 
     */ 
    User selectUserDetailById(Long id);

    /**
     * @Description 根据用户名查询用户信息、角色信息以及权限信息
     * @param [username]
     * @return com.jnshu.resourceservice.entity.User 
     * @author Mr.HUANG
     * @date 2018/12/27 
     * @throws 
     */ 
    User findByUsername(String username);

    /**
     * @Description 用户管理-获取用户列表、根据条件（用户名、角色名）模糊查询
     * @param [user, role] 
     * @return java.util.List<com.jnshu.resourceservice.entity.User>
     * @author Mr.HUANG
     * @date 2018/12/27 
     * @throws 
     */ 
    List<User> selectUserList(@Param("name") String user, @Param("roleName") String role);

}