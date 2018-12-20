package com.jnshu.resourceservice.service;

import com.github.pagehelper.*;
import com.jnshu.resourceservice.entity.*;

/**
 * @program: morepineapple
 * @description: 用户管理模块-服务类
 * @author: Mr.huang
 * @create: 2018-12-17 00:07
 **/
public interface UserModuleService {

	/**
	 * @Description 用户管理-新增用户
	 * @param [newuUser, jwt]
	 * @return java.lang.Integer
	 * @author Mr.HUANG
	 * @date 2018/12/17
	 * @throws
	 */
	void addUser(User newuUser, JWT jwt);

	/**
	 * @Description 用户管理-修改个人信息
	 * @param [targetUser, jwt]
	 * @return void
	 * @author Mr.HUANG
	 * @date 2018/12/19
	 * @throws
	 */
	void update(User targetUser, JWT jwt);

	/**
	 * @Description 用户管理-删除用户
	 * @param [targetUserId, jwt]
	 * @return void
	 * @author Mr.HUANG
	 * @date 2018/12/20
	 * @throws
	 */
	void delete(Long targetUserId, JWT jwt);

	/**
	 * @Description 用户管理-获取单个用户信息
	 * @param [targetUserId]
	 * @return com.jnshu.resourceservice.entity.User
	 * @author Mr.HUANG
	 * @date 2018/12/20
	 * @throws
	 */
	User select(Long targetUserId);



	PageInfo<User> selectAll(Integer page, Integer size);
}
