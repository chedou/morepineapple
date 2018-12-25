package com.jnshu.resourceservice.service.impl;

import com.jnshu.resourceservice.dao.*;
import com.jnshu.resourceservice.entity.*;
import com.jnshu.resourceservice.exception.*;
import com.jnshu.resourceservice.service.*;
import com.sun.xml.internal.bind.v2.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

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
		// TODO:此处需要验证
		// 插入角色与权限的关联信息，此处需要验证
		if (1 != rolePermissionMapper.insertRolePermission(rolePermissionMap)){
			throw new ServiceException("角色与权限关联信息出错，请查看服务器日志");
		}
		// 事务管理逻辑，此处带优化
		// 验证数据是否插入成功
	}

	/**
	 * @param targetRole
	 * @param jwt
	 * @return void
	 * @throws
	 * @Description 角色管理-删除角色，此处将角色信息保留，角色权限信息全删除，在更改参数时，
	 * 					也是删除关联的参数，重新生成，以此保证不生成脏数据
	 * @author Mr.HUANG
	 * @date 2018/12/25
	 */
	@Override
	public void deleteRole(Integer targetRole, JWT jwt) {
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("当前用户id是：{}，传入的参数是:删除的目标ID：{}",
					jwt.getUserID(), targetRole);
		}
		// 更改角色信息状态
		Role roleDB = roleMapper.selectRolePermissionById(targetRole);
		if (1 == roleDB.getStatus()){
			roleDB.setStatus(0);
		}
		// 写入修改时间、修改人
		roleDB.setGmtUpdate(System.currentTimeMillis());
		roleDB.setUpdateBy(userMapper.selectUserDetailById(jwt.getUserID()).getName());
		if (1 != rolePermissionMapper.deleteByPrimaryKey(roleDB.getId())){
			throw new ServiceException("数据库中删除角色信息出错，请查看服务器日志");
		}
		// 删除角色与权限关联的信息
		if (null != rolePermissionMapper.selectByRoleId(roleDB.getId())){
			rolePermissionMapper.deleteByRoleId(roleDB.getId());
		}
	}

	@Override
	public void updateRole(Role targetRole, JWT jwt) {
		// 打印核心参数，角色id，操作者ID
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("当前用户id是：{}，修改目标ID是:{}",
					jwt.getUserID(), targetRole.getId());
		}
		// 判断目标数据是否为失效数据
		if (0 == roleMapper.selectByPrimaryKey(targetRole.getId()).getStatus()){
			throw new ServiceException("目标用户为失效用户");
		}
		// 写入修改时间
		targetRole.setGmtUpdate(System.currentTimeMillis());
		// 写入修改人
		targetRole.setUpdateBy(userMapper.selectUserDetailById(jwt.getUserID()).getName());

		// 对于角色的信息直接修改，对于角色与权限关联的信息，则直接删除只之后，再重新生成
		roleMapper.updateByPrimaryKeySelective(targetRole);
		// 先判断是否有需要删除的参数
		if (null !=rolePermissionMapper.selectByRoleId(targetRole.getId())){
			rolePermissionMapper.deleteByRoleId(targetRole.getId());
		}
		// 重新插入新的 角色与权限关联的信息
		List<Integer> permissionIds = new ArrayList<>();
		Map<String, Object> rolePermissionMap =new HashMap<>();
		for (int i =0; i< targetRole.getPermissionsList().size(); i++){
			permissionIds.add(targetRole.getPermissionsList().get(i).getId());
		}
		rolePermissionMap.put("roleId", targetRole.getId());
		rolePermissionMap.put("Permissions", Collections.singletonList(permissionIds));
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("当前用户id是：{}，准备更改的数据为:更改角色ID：{}，权限ID集合：{}",
					jwt.getUserID(), rolePermissionMap.get("roleId"),rolePermissionMap.get("Permissions"));
		}
		// TODO:此处需要验证
		// 插入角色与权限的关联信息
		if (1 != rolePermissionMapper.insertRolePermission(rolePermissionMap)){
			throw new ServiceException("更新角色与权限关联信息出错，请查看服务器日志");
		}

	}

}
