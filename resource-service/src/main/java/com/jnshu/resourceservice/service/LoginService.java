package com.jnshu.resourceservice.service;

import com.jnshu.resourceservice.dto.*;

/**
 * @program: morepineapple
 * @description: 用户登录登出模块
 * @author: Mr.huang
 * @create: 2019-01-14 21:33
 **/
public interface LoginService {

	/**
	 * @Description 用户登录接口
	 * @param [username, password]
	 * @return com.jnshu.resourceservice.dto.UserLoginDTO
	 * @author Mr.HUANG
	 * @date 2019/1/14
	 * @throws
	 */
	UserLoginDTO login(String username, String password);

	/**
	 * @Description 用户登出
	 * @param [authorization]
	 * @return void
	 * @author Mr.HUANG
	 * @date 2019/1/14
	 * @throws
	 */
	void out(String authorization);



}
