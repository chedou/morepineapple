package com.jnshu.resourceservice.service;

import com.jnshu.resourceservice.entity.*;

import javax.jws.soap.SOAPBinding.*;


/**
 * @program: morepineapple
 * @description: 用户管理模块-服务类
 * @author: Mr.huang
 * @create: 2018-12-17 00:07
 **/
public interface UserModuleService {

	/**
	 * @Description 用户管理-新增用户
	 * @param [user, jwt]
	 * @return java.lang.Integer
	 * @author Mr.HUANG
	 * @date 2018/12/17
	 * @throws
	 */
	void addUser(User newuUser, JWT jwt);

	void update(User targetUser, JWT jwt);

}
