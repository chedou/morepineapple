package com.jnshu.resourceservice.service.impl;

import com.github.pagehelper.*;
import com.jnshu.resourceservice.dao.*;
import com.jnshu.resourceservice.dto.*;
import com.jnshu.resourceservice.entity.*;
import com.jnshu.resourceservice.exception.*;
import com.jnshu.resourceservice.service.*;
import com.jnshu.resourceservice.utils.pageutil.*;
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

	@Autowired(required = false)
	private PermissionMapper permissionMapper;
	
	/**
	 * @Description 将请求参数中的权限ID给取出
	 * @param [targetRole, operatorId, permissionIds, maxId] 
	 * @return void 
	 * @author Mr.HUANG
	 * @date 2019/1/1 
	 * @throws 
	 */ 
	private void addRolePerm(Role targetRole, Long operatorId, List<Integer> permissionIds, Integer maxId) {
		Integer perID;
		for (int i = 0; i< targetRole.getPermissionsList().size(); i++){
			permissionIds.add(perID =targetRole.getPermissionsList().get(i).getId());
			LOGGER.info("当前用户ID为：{}，权限的最大ID为{}，从请求参数中获取到的权限ID为：{}",
					operatorId, maxId, perID);
			if (perID == null || perID >maxId){
				throw new ServiceException("权限ID有误，请查看服务器");
			}
		}
	}

	/**
	 * @param newRole
	 * @param operatorId
	 * @return void
	 * @throws
	 * @Description 角色管理-增加角色
	 * @author Mr.HUANG
	 * @date 2018/12/24
	 */
	@Override
	public RoleModuleDTO addRole(Role newRole, Long operatorId) {

		LOGGER.info("当前用户id是：{}，传入的参数是:{},传入的权限参数：{}", operatorId,
				newRole.getRoleName(), newRole.getPermissionsList().toString());

		// 判断新增的角色是否已被使用
		if (null != roleMapper.selectByRoleName(newRole.getRoleName())){
			throw new ServiceException("角色名已被使用，请重新输入");
		}
		// 判断新增的权限参数是否为null
		if (null == newRole.getPermissionsList()){
			throw new ServiceException("用户权限为null,请查看日志");
		}
		// 首先插入角色信息
		Long nowTime =System.currentTimeMillis();
		newRole.setGmtCreate(nowTime);
		newRole.setGmtUpdate(nowTime);

		User userExecutor = userMapper.selectUserDetailById(operatorId);
		newRole.setCreateBy(userExecutor.getName());
		newRole.setUpdateBy(userExecutor.getName());

		// 插入之前打印相应的核心参数

		LOGGER.info("当前用户id是：{}，准备插入的数据为:{}", operatorId, newRole.toString());
		// 插入数据后，新增的ID将返回到原对象中的ID，详细请看mapper文件中的SQL语句
		roleMapper.insertSelective(newRole);
		if (null == newRole.getId()){
			throw new ServiceException("单独插入角色信息出错，请查看日志");
		}
		// 获取角色的ID和权限的ID集合，并写入Map中
		List<Integer> permissionIds = new ArrayList<>();
		Map<String, Object> rolePermissionMap =new HashMap<>();

		// 对前端传回的权限ID进行转移，并进行判断
		Integer maxId =(permissionMapper.selectMaxId()).getId();
		Integer perID =null;

		addRolePerm(newRole, operatorId, permissionIds, maxId);

		rolePermissionMap.put("roleId", newRole.getId());
		rolePermissionMap.put("Permissions", permissionIds);
		rolePermissionMap.put("status",1);
		rolePermissionMap.put("gmtCreate", System.currentTimeMillis());
		rolePermissionMap.put("gmtUpdate", System.currentTimeMillis());
		rolePermissionMap.put("createBy", userExecutor.getName());
		rolePermissionMap.put("updateBy", userExecutor.getName());
		LOGGER.info("当前用户id是：{}，准备新增的数据为:新增角色ID：{}，权限ID集合：{},整体参数：{}",
					operatorId, rolePermissionMap.get("roleId"), rolePermissionMap.get("Permissions"), rolePermissionMap.toString());
		// 插入角色与权限的关联信息，此处需要验证
		if (0 == rolePermissionMapper.insertRolePermission(rolePermissionMap)){
			throw new ServiceException("角色与权限关联信息出错，请查看服务器日志");
		}
		// 事务管理逻辑，此处带优化
		// 验证数据是否插入成功
		Role returnRole =new Role();
		returnRole.setId(newRole.getId());
		RoleModuleDTO roleModuleDTO =new RoleModuleDTO();
		roleModuleDTO.setRole(returnRole);

		return roleModuleDTO;
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
	public void deleteRole(Integer targetRole, Long operatorId) {

		LOGGER.info("当前用户id是：{}，传入的参数是:删除的目标ID：{}", operatorId, targetRole);

		// 更改角色信息状态
		Role roleDB = roleMapper.selectByPrimaryKey(targetRole);
		LOGGER.info("从数据库中查询到的参数roleDB为：{}",roleDB);
		if (1 == roleDB.getStatus()){
			roleDB.setStatus(0);
		}
		// 写入修改时间、修改人
		roleDB.setGmtUpdate(System.currentTimeMillis());
		roleDB.setUpdateBy(userMapper.selectUserDetailById(operatorId).getName());
		LOGGER.info("当前用户id是：{}，传入的参数是:删除的目标ID：{}，删除之后的参数信息为：{}", operatorId, targetRole, roleDB);

		if (1 != roleMapper.updateByPrimaryKeySelective(roleDB)){
			throw new ServiceException("数据库中删除角色信息出错，请查看服务器日志");
		}
		// 删除角色与权限关联的信息
		if (null != rolePermissionMapper.selectByRoleId(roleDB.getId())){
			rolePermissionMapper.deleteByRoleId(roleDB.getId());
		}
	}

	/**
	 * @Description 角色管理-更新角色信息，此处，更改角色信息，删除角色与权限的关联信息，
	 * 				重新新增角色与权限的关联信息
	 * @param [targetRole, jwt]
	 * @return void
	 * @author Mr.HUANG
	 * @date 2018/12/25
	 * @throws Exception
	 */
	@Override
	public RoleModuleDTO updateRole(Role targetRole, Long operatorId) {
		// 打印核心参数，角色id，操作者ID
		LOGGER.debug("当前用户id是：{}，修改目标ID是:{}", operatorId, targetRole.getId());

		// 判断目标数据是否为失效数据
		Role roleBase;
		if (0 == (roleBase = roleMapper.selectByPrimaryKey(targetRole.getId())).getStatus()
				|| null == roleBase.getRoleName()){
			throw new ServiceException("目标用户为失效用户");
		}
		// 判断新增的权限参数是否为null
		if (null == targetRole.getPermissionsList()){
			throw new ServiceException("用户权限为null,请查看日志");
		}
		// 写入修改时间
		targetRole.setGmtUpdate(System.currentTimeMillis());
		// 写入修改人
		User userExecutor =userMapper.selectUserDetailById(operatorId);
		targetRole.setUpdateBy(userExecutor.getName());
		targetRole.setCreateBy(userExecutor.getCreateBy());

		// 对于角色的信息直接修改，对于角色与权限关联的信息，则直接删除只之后，再重新生成
		roleMapper.updateByPrimaryKeySelective(targetRole);
		// 先判断是否有需要删除的参数
		if (null !=rolePermissionMapper.selectByRoleId(targetRole.getId())){
			rolePermissionMapper.deleteByRoleId(targetRole.getId());
		}
		// 重新插入新的 角色与权限关联的信息
		// 存放插入目标的ID
		List<Integer> permissionIds = new ArrayList<>();
		// 存放插入的信息
		Map<String, Object> rolePermissionMap =new HashMap<>();

		// 对前端传回的权限ID进行转移，并进行判断
		Integer maxId =(permissionMapper.selectMaxId()).getId();
		Integer perID =null;
		addRolePerm(targetRole, operatorId, permissionIds, maxId);
		rolePermissionMap.put("roleId", targetRole.getId());
		rolePermissionMap.put("Permissions", permissionIds);
		rolePermissionMap.put("status",1);
		rolePermissionMap.put("gmtCreate", System.currentTimeMillis());
		rolePermissionMap.put("gmtUpdate", System.currentTimeMillis());
		rolePermissionMap.put("createBy", userExecutor.getName());
		rolePermissionMap.put("updateBy", userExecutor.getName());
		LOGGER.info("当前用户id是：{}，准备新增的数据为:新增角色ID：{}，权限ID集合：{},整体参数：{}",
				operatorId, rolePermissionMap.get("roleId"), rolePermissionMap.get("Permissions"), rolePermissionMap.toString());
		// 插入角色与权限的关联信息，此处需要验证
		if (0 == rolePermissionMapper.insertRolePermission(rolePermissionMap)){
			throw new ServiceException("角色与权限关联信息出错，请查看服务器日志");
		}
		// 事务管理逻辑，此处带优化
		// 验证数据是否插入成功
		Role returnRole =new Role();
		returnRole.setId(targetRole.getId());
		RoleModuleDTO roleModuleDTO =new RoleModuleDTO();
		roleModuleDTO.setRole(returnRole);

		return roleModuleDTO;

	}



	/**
	 * @param targetRoleId
	 * @return com.jnshu.resourceservice.dto.RoleModuleDTO
	 * @throws Exception
	 * @Description 角色管理-获取单个角色信息
	 * @author Mr.HUANG
	 * @date 2018/12/26
	 */
	@Override
	public RoleModuleDTO selectRole(Integer targetRoleId) {
		// 打印核心参数，角色id，操作者ID
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("查询目标角色id是：{}", targetRoleId);
		}
		// 判断需要查询的角色状态，status激活状态的有效值为：1
		Role returnRole = roleMapper.selectRolePermissionById(targetRoleId);
		if (null == returnRole || 0 == returnRole.getStatus()){
			LOGGER.debug("查询得到的用户的姓名：{}，目标用户的ID：{}",returnRole,returnRole.getStatus());
		}
		RoleModuleDTO roleModuleDTO = new RoleModuleDTO();
		roleModuleDTO.setRole(returnRole);
		return roleModuleDTO;
	}

	/**
	 * @param pageUtil
	 * @return com.jnshu.resourceservice.dto.RoleModuleDTO
	 * @throws Exception
	 * @Description 角色管理-获取角色列表
	 * @author Mr.HUANG
	 * @date 2018/12/26
	 */
	@Override
	public RoleModuleDTO selectRoleList(PageUtil pageUtil) {
		List<Role> roleList =roleMapper.selectAll();

		// 对页数进行判断是否超过最大页
		if (pageUtil.getPage() > roleList.size()/pageUtil.getSize() +1){
			throw new ServiceException("所查询页码已超过最大值");
		}
		// 设置物理分页插件参数
		PageHelper.startPage(pageUtil.getPage(), pageUtil.getSize());
		// 创建分页后封装类
		PageInfo<Role> pageInfo = new PageInfo<>(roleList);
		// 将返回参数写入返回封装类中
		RoleModuleDTO roleModuleDTO = new RoleModuleDTO();
		roleModuleDTO.setRoleList(pageInfo.getList());
		pageUtil.setTotal(pageInfo.getTotal());
		roleModuleDTO.setPageUtil(pageUtil);

		// 打印核心参数，用户id，操作者ID
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("返回的参数：{}",roleModuleDTO.toString() );
		}
		return roleModuleDTO;
	}





}
