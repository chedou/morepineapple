package com.jnshu.resourceservice.web;

import com.jnshu.resourceservice.core.ret.*;
import com.jnshu.resourceservice.entity.*;
import com.jnshu.resourceservice.entity.group.*;
import com.jnshu.resourceservice.service.*;
import io.swagger.annotations.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

/**
 * @program: morepineapple
 * @description: 角色管理模块
 * @author: Mr.huang
 * @create: 2018-12-22 20:57
 **/
@RestController
@RequestMapping("/a")
@Api(tags = {"角色管理模块"}, description = "测试角色管理模块")
public class RoleModuleController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RoleModuleService roleModuleService;

	//TODO:橘色管理-新增角色
	/**
	 * @Description 角色管理-增加角色
	 * @param [role, jwt]
	 * @return com.jnshu.resourceservice.core.ret.RetResult<?>
	 * @author Mr.HUANG
	 * @date 2018/12/24
	 * @throws
	 */
	@ApiOperation(value = "addRole",  notes = "新增角色")
	@PostMapping(value = "/role", produces = "application/json;charset=UTF-8")
	@PreAuthorize("hasAuthority('RoleManageAll') AND hasAuthority('RoleManageAdd') ")
	public RetResult<?> addRole(@Validated({addAndUpdateRoleGroup.class})Role role,
								@Validated({JWTOperatingGroup.class})JWT jwt)throws Exception{
		if (logger.isDebugEnabled()){
			logger.debug("----RoleModuleController----addRole------");
			logger.debug("插入的角色名:{}", role.getRoleName());
		}
		roleModuleService.addRole(role, jwt);
		return RetResponse.result(RetCode.SUCCESS_ROLE_ONE_ADD);
	}



	//TODO:橘色管理-更改角色信息
	/**
	 * @Description 角色管理-更改角色信息
	 * @param [role, jwt]
	 * @return com.jnshu.resourceservice.core.ret.RetResult<?>
	 * @author Mr.HUANG
	 * @date 2018/12/25
	 * @throws Exception
	 */
	@ApiOperation(value = "updateRole",  notes = "更改角色")
	@DeleteMapping(value = "/role/{targetRoleId}", produces = "application/json;charset=UTF-8")
	@PreAuthorize("hasAuthority('RoleManageAll') AND hasAuthority('RoleManageDelete') ")
	public RetResult<?> updateRole(@Validated({addAndUpdateRoleGroup.class})Role role,
								   @Validated({JWTOperatingGroup.class})JWT jwt)throws Exception{

		roleModuleService.updateRole(role, jwt);
		return RetResponse.result(RetCode.SUCCESS_USER_ONE_UPDATE);

	}



	//TODO:角色管理-删除角色
	/**
	 * @Description 角色管理-删除角色
	 * @param [targetRoleId, jwt]
	 * @return com.jnshu.resourceservice.core.ret.RetResult<?>
	 * @author Mr.HUANG
	 * @date 2018/12/25
	 * @throws Exception
	 */
	public RetResult<?> deleteRole(@PathVariable Integer targetRoleId,
								   @Validated({JWTOperatingGroup.class})JWT jwt)throws Exception{

		if (logger.isDebugEnabled()){
			logger.debug("----RoleModuleController----deleteRole-----");
			logger.debug("需要删除的角色ID:{}", targetRoleId);
		}
		roleModuleService.deleteRole(targetRoleId, jwt);
		return RetResponse.result(RetCode.SUCCESS_ROLE_ONE_DELETE);

	}



	//TODO:橘色管理-查询单个角色





	//TODO:橘色管理-查询角色列表



}
