package com.jnshu.resourceservice.service;

import com.jnshu.resourceservice.entity.*;


/**
 * @program: morepineapple
 * @description: 用户管理模块-服务类
 * @author: Mr.huang
 * @create: 2018-12-17 00:07
 **/
public interface UserModuleService {

	void addUser(User user, JWT jwt);

}
