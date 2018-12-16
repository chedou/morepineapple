package com.jnshu.resourceservice.dao;

import com.jnshu.resourceservice.entity.*;
import org.apache.ibatis.annotations.*;

import java.sql.*;

/**
 * @description  用户信息持久层
 * @author Mr.HUANG
 * @date 2018/12/13
 */
@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Long id) throws SQLException;

    int insert(User record) throws SQLException;

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectUserDetailById(Integer id);

    User findByUsername(String username);

}