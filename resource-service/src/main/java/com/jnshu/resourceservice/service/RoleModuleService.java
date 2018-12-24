package com.jnshu.resourceservice.service;

import com.jnshu.resourceservice.entity.*;

/**
 * @Description 角色管理模块-服务类
 * @author Mr.HUANG
 * @date 2018/12/24
 * 
 */ 
public interface RoleModuleService {

	/**
	 * @Description 角色管理-增加角色
	 * @param [newRole, jwt]
	 * @return void
	 * @author Mr.HUANG
	 * @date 2018/12/24
	 * @throws
	 */
	void addRole(Role newRole, JWT jwt);



}
