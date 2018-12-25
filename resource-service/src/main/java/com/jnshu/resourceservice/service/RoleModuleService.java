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

	/**
	 * @Description 角色管理-删除角色，此处为直接删除原有数据，在更改参数时，
	 * 					也是删除关联的参数，以此保证不生成脏数据
	 * @param [targetRole, jwt]
	 * @return void
	 * @author Mr.HUANG
	 * @date 2018/12/25
	 * @throws
	 */
	void deleteRole(Integer targetRole, JWT jwt);

	void updateRole(Role targetRole, JWT jwt);

}
