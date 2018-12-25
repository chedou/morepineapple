package com.jnshu.resourceservice.service.impl;

import com.jnshu.resourceservice.dao.*;
import com.jnshu.resourceservice.entity.*;
import com.jnshu.resourceservice.exception.*;
import com.jnshu.resourceservice.service.*;
import io.swagger.models.auth.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.sql.*;
import java.util.*;

/**
 * @program: morepineapple
 * @description: 角色管理管理模块-服务实现类
 * @author: Mr.huang
 * @create: 2018-12-24 15:27
 **/
@Service
public class RoleModuleServiceImpl implements RoleModuleService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired(required = false)
	private RoleMapper roleMapper;

	@Autowired(required = false)
	private UserMapper userMapper;

	@Autowired(required = false)
	private RolePermissionMapper rolePermissionMapper;

	/**
	 * @param newRole
	 * @param jwt
	 * @return void
	 * @throws
	 * @Description 角色管理-增加角色
	 * @author Mr.HUANG
	 * @date 2018/12/24
	 */
	@Override
	public void addRole(Role newRole, JWT jwt) {
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("当前用户id是：{}，传入的参数是:{}",
					jwt.getUserID(), newRole.getRoleName());
		}
		// 判断新增的角色是否已被使用
		if (null == roleMapper.selectByRoleName(newRole.getRoleName())){
			throw new ServiceException("角色名已被使用，请重新输入");
		}
		// 首先插入角色信息
		Long nowTime =System.currentTimeMillis();
		newRole.setGmtCreate(nowTime);
		newRole.setGmtUpdate(nowTime);

		User userExecutor = userMapper.selectUserDetailById(jwt.getUserID());
		newRole.setCreateBy(userExecutor.getName());
		newRole.setUpdateBy(userExecutor.getName());

		// 插入之前打印相应的核心参数
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("当前用户id是：{}，准备插入的数据为:{}",
					jwt.getUserID(), newRole.toString());
		}
		// 插入数据后，新增的ID将返回到原对象中的ID，详细请看mapper文件中的SQL语句
		roleMapper.insertSelective(newRole);
		if (null == newRole.getId()){
			throw new ServiceException("单独插入角色信息出错，请查看日志");
		}
		// 获取角色的ID和权限的ID集合，并写入Map中
		List<Integer> permissionIds = new ArrayList<>();
		Map<String, Object> rolePermissionMap =new HashMap<>();
		for (int i =0; i< newRole.getPermissionsList().size(); i++){
			permissionIds.add(newRole.getPermissionsList().get(i).getId());
		}
		rolePermissionMap.put("roleId", newRole.getId());
		rolePermissionMap.put("Permissions", Collections.singletonList(permissionIds));
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("当前用户id是：{}，准备新增的数据为:新增角色ID：{}，权限ID集合：{}",
					jwt.getUserID(), rolePermissionMap.get("roleId"),rolePermissionMap.get("Permissions"));
		}
		// 插入角色与权限的关联信息
		if (1 != rolePermissionMapper.insertRolePermission(rolePermissionMap)){
			throw new ServiceException("角色与权限关联信息出错，请查看服务器日志");
		}
		// 事务管理逻辑，此处带优化
		// 验证数据是否插入成功
	}

}
