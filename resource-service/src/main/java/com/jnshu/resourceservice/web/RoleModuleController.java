package com.jnshu.resourceservice.web;

import com.jnshu.resourceservice.core.ret.*;
import com.jnshu.resourceservice.entity.*;
import com.jnshu.resourceservice.entity.group.*;
import com.jnshu.resourceservice.service.*;
import com.jnshu.resourceservice.utils.authorization.*;
import com.jnshu.resourceservice.utils.pageutil.*;
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
@RequestMapping()
@Api(tags = {"角色管理模块"}, description = "测试角色管理模块")
public class RoleModuleController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RoleModuleService roleModuleService;


	//TODO:角色管理-查询角色列表
	/**
	 * @Description 角色管理-获取角色列表，分页默返回参数单页8组数据
	 * @param [pageUtil]
	 * @return com.jnshu.resourceservice.core.ret.RetResult<?>
	 * @author Mr.HUANG
	 * @date 2018/12/26
	 * @throws Exception
	 */
	@ApiOperation(value = "selectRoleList",  notes = "查询角色列表")
	@PostMapping(value = "/role/list", produces = "application/json;charset=UTF-8")
	@PreAuthorize("hasAuthority('RoleManageAll')")
	public RetResult<?> selectRoleList(@Validated ({PageUtilGroup.class})PageUtil pageUtil
			) throws Exception {

		logger.info("----UserModuleController----selectRoleList-----");
		logger.info("分页参数为:{}", pageUtil.toString());
		return RetResponse.result(RetCode.SUCCESS_ROLE_LIST_GET)
				.setData(roleModuleService.selectRoleList(pageUtil));
	}



	//TODO:角色管理-新增角色
	/**
	 * @Description 角色管理-增加角色
	 * @param [role]
	 * @return com.jnshu.resourceservice.core.ret.RetResult<?>
	 * @author Mr.HUANG
	 * @date 2018/12/24
	 * @throws
	 */
	@ApiOperation(value = "addRole",  notes = "新增角色")
	@PostMapping(value = "/role", produces = "application/json;charset=UTF-8")
	@PreAuthorize("hasAuthority('RoleManageAll') AND hasAuthority('RoleManageAdd') ")
	public RetResult<?> addRole(@Validated({addAndUpdateRoleGroup.class})@RequestBody Role role)throws Exception{
		logger.info("----RoleModuleController----addRole------");
		logger.info("插入的角色名:{}", role.getRoleName());

		Long operatorId =Long.parseLong(AuthorizationUtils.getUserId());
		return RetResponse.result(RetCode.SUCCESS_ROLE_ONE_ADD)
				.setData(roleModuleService.addRole(role, operatorId));
	}



	//TODO:角色管理-更改角色信息
	/**
	 * @Description 角色管理-更改角色信息
	 * @param [role, jwt]
	 * @return com.jnshu.resourceservice.core.ret.RetResult<?>
	 * @author Mr.HUANG
	 * @date 2018/12/25
	 * @throws Exception
	 */
	@ApiOperation(value = "updateRole",  notes = "更改角色")
	@PutMapping(value = "/role", produces = "application/json;charset=UTF-8")
	@PreAuthorize("hasAuthority('RoleManageAll') AND hasAuthority('RoleManageUpdate') ")
	public RetResult<?> updateRole(@Validated({addAndUpdateRoleGroup.class})@RequestBody Role role)throws Exception{

		logger.info("----RoleModuleController----updateRole-----");
		logger.info("需要更改的角色ID:{}", role.getId());
		Long operatorId =Long.parseLong(AuthorizationUtils.getUserId());
		return RetResponse.result(RetCode.SUCCESS_USER_ONE_UPDATE)
						.setData(roleModuleService.updateRole(role, operatorId));

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
	@ApiOperation(value = "deleteRole",  notes = "更改角色")
	@DeleteMapping(value = "/role/{targetRoleId}", produces = "application/json;charset=UTF-8")
	@PreAuthorize("hasAuthority('RoleManageAll') AND hasAuthority('RoleManageDelete') ")
	public RetResult<?> deleteRole(@PathVariable Integer id)throws Exception{

		logger.info("----RoleModuleController----deleteRole-----");
		logger.info("需要更改的角色ID:{}", id);

		Long operatorId =Long.parseLong(AuthorizationUtils.getUserId());
		roleModuleService.deleteRole(id, operatorId);
		return RetResponse.result(RetCode.SUCCESS_ROLE_ONE_DELETE);

	}


	//TODO:橘色管理-查询单个角色
	/**
	 * @Description 角色管理-新增单个角色信息
	 * @param [targetRoleId]
	 * @return com.jnshu.resourceservice.core.ret.RetResult<?>
	 * @author Mr.HUANG
	 * @date 2018/12/25
	 * @throws Exception
	 */
	@ApiOperation(value = "selectRole",  notes = "查询角色")
	@GetMapping(value = "/role/{id}", produces = "application/json;charset=UTF-8")
	@PreAuthorize("hasAuthority('RoleManageAll')")
	public RetResult<?> selectRole(@PathVariable Integer id) throws Exception{

		logger.debug("----RoleModuleController----selectRole-----");
		logger.debug("需要查询的用户ID:{}", id);


		return RetResponse.result(RetCode.SUCCESS_ROLE_ONE_GET).
				setData(roleModuleService.selectRole(id));
	}

}
