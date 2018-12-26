package com.jnshu.resourceservice.service;

import com.jnshu.resourceservice.dto.*;
import com.jnshu.resourceservice.entity.*;
import com.jnshu.resourceservice.utils.pageutil.*;

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


	/**
	 * @Description 角色管理-更新角色信息，此处，更改角色信息，删除角色与权限的关联信息，
	 * 				重新新增角色与权限的关联信息
	 * @param [targetRole, jwt]
	 * @return void
	 * @author Mr.HUANG
	 * @date 2018/12/25
	 * @throws Exception
	 */
	void updateRole(Role targetRole, JWT jwt);

	/**
	 * @Description 角色管理-获取单个角色信息
	 * @param [targetRoleIds]
	 * @return com.jnshu.resourceservice.dto.RoleModuleDTO
	 * @author Mr.HUANG
	 * @date 2018/12/26
	 * @throws Exception
	 */
	RoleModuleDTO selectRole(Integer targetRoleId);


	/**
	 * @Description 角色管理-获取角色列表
	 * @param [pageUtil]
	 * @return com.jnshu.resourceservice.dto.RoleModuleDTO
	 * @author Mr.HUANG
	 * @date 2018/12/26
	 * @throws Exception
	 */
	RoleModuleDTO selectRoleList(PageUtil pageUtil);
}
