package com.jnshu.uaaservice.dao;

import com.jnshu.uaaservice.pojo.User;
import org.apache.ibatis.annotations.*;

/**
 * @Description 用户信息持久层
 * @author Mr.HUANG
 * @date 2018/12/12
 *
 */
@Mapper
public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * @Description 根据用户ID查询用户信息
     * @param  id   用户id
     * @return  User    用户model
     * @author Mr.HUANG
     * @date 2018/12/12
     * @throws
     */
    User selectUserDetailById(Integer id);

    /**
     * @Description 根据用户姓名查询用户信息
     * @param username 用户名
     * @return user     用户model
     * @author Mr.HUANG
     * @date 2018/12/12
     * @throws
     */
    User findByUsername(String username);

}