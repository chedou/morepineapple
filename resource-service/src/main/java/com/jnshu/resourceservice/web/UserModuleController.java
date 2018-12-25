package com.jnshu.resourceservice.web;

import com.jnshu.resourceservice.core.ret.*;
import com.jnshu.resourceservice.entity.*;
import com.jnshu.resourceservice.entity.group.*;
import com.jnshu.resourceservice.service.*;
import com.jnshu.resourceservice.utils.pageutil.*;
import io.swagger.annotations.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 用户管理模块
 * @author: Mr.huang
 * @create: 2018-12-13 21:31
 **/

@RestController
@RequestMapping("/a")
@Api(tags = {"用户管理模块"}, description = "测试用户管理模块")
public class UserModuleController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserModuleService userModuleService;

	// TODO：用户管理-新增用户
	/**
	 * @Description
	 * @param [user jwt ]
	 * @return com.jnshu.resourceservice.core.ret.RetResult<?>
	 * @author Mr.HUANG
	 * @date 2018/12/13
	 * @throws
	 */
	@ApiOperation(value = "addUser",  notes = "新增用户")
	@PostMapping(value = "/user", produces = "application/json;charset=UTF-8")
	@PreAuthorize("hasAuthority('RoleManageAll') AND hasAuthority('RoleManageAdd') ")
	public RetResult<?> addUser(@Validated({AddUserGroup.class}) User user,
								@Validated({JWTOperatingGroup.class})JWT jwt) throws Exception{

		if (logger.isDebugEnabled()){
			logger.debug("----UserModuleController----addUser------");
			logger.debug("插入的用户名:{}", user.getName());
		}

		userModuleService.addUser(user, jwt);
		return RetResponse.result(RetCode.SUCCESS_USER_ONE_ADD);

	}

	//TODO：用户管理-更改用户
	/**
	 * @Description 用户管理-修改个人信息
	 * @param [user, jwt] 
	 * @return com.jnshu.resourceservice.core.ret.RetResult<?> 
	 * @author Mr.HUANG
	 * @date 2018/12/19 
	 * @throws Exception
	 */ 
	@ApiOperation(value = "updateUser",  notes = "更改用户")
	@PutMapping(value = "/user", produces = "application/json;charset=UTF-8")
	@PreAuthorize("hasAuthority('RoleManageAll') AND hasAuthority('RoleManageUpdate') ")
	public RetResult<?> updateUser(@Validated({UpdateUserGroup.class, AddUserGroup.class}) User user,
								   @Validated({JWTOperatingGroup.class})JWT jwt)throws Exception{

		if (logger.isDebugEnabled()){
			logger.debug("----UserModuleController----updateUser-----");
			logger.debug("需要修改的用户ID:{}", user.getName());
		}
		userModuleService.update(user, jwt);
		return RetResponse.result(RetCode.SUCCESS_USER_ONE_UPDATE);
	}

	//TODO：用户管理-删除用户
	/**
	 * @Description 用户管理-删除用户
	 * @param [targetUserId, jwt]
	 * @return com.jnshu.resourceservice.core.ret.RetResult<?>
	 * @author Mr.HUANG
	 * @date 2018/12/20
	 * @throws
	 */
	@ApiOperation(value = "deleteUser",  notes = "更改用户")
	@DeleteMapping(value = "/user/{targetUserId}", produces = "application/json;charset=UTF-8")
	@PreAuthorize("hasAuthority('RoleManageAll') AND hasAuthority('RoleManageDelete') ")
	public RetResult<?> deleteUser(@PathVariable Long targetUserId,
								   @Validated({JWTOperatingGroup.class}) JWT jwt) throws Exception{

		if (logger.isDebugEnabled()){
			logger.debug("----UserModuleController----deleteUser-----");
			logger.debug("需要删除的用户ID:{}", targetUserId);
		}
		userModuleService.delete(targetUserId, jwt);
		return RetResponse.result(RetCode.SUCCESS_USER_ONE_DELETE);
	}


	//TODO：用户管理-单个用户信息

	/**
	 * @Description 用户管理-获取单个用户信息
	 * @param [targetUserID]
	 * @return com.jnshu.resourceservice.core.ret.RetResult<?>
	 * @author Mr.HUANG
	 * @date 2018/12/20
	 * @throws
	 */
	@ApiOperation(value = "selectUser",  notes = "查询用户")
	@PostMapping(value = "/user/{targetUserId}", produces = "application/json;charset=UTF-8")
	@PreAuthorize("hasAuthority('RoleManageAll')")
	public RetResult<?> selectUser(@PathVariable Long targetUserId) throws Exception{

		if (logger.isDebugEnabled()){
			logger.debug("----UserModuleController----deleteUser-----");
			logger.debug("需要查询的用户ID:{}", targetUserId);
		}

		return RetResponse.result(RetCode.SUCCESS_USER_LIST_GET)
				.setData(userModuleService.select(targetUserId));
	}


	//TODO: 用户管理-账号列表
	/**
	 * @Description 用户管理-获取用户列表
	 * @param [pageUtil]
	 * @return com.jnshu.resourceservice.core.ret.RetResult<?>
	 * @author Mr.HUANG
	 * @date 2018/12/21
	 * @throws Exception
	 */
	@ApiOperation(value = "seleceUserList",  notes = "查询用户")
	@PostMapping(value = "/user/list", produces = "application/json;charset=UTF-8")
	@PreAuthorize("hasAuthority('RoleManageAll')")
	public RetResult<?> seleceUserList(@Validated({PageUtilGroup.class}) PageUtil pageUtil)throws Exception{

		if (logger.isDebugEnabled()){
			logger.debug("----UserModuleController----seleceUserList-----");
			logger.debug("分页参数为:{}", pageUtil.toString());
		}
		return RetResponse.result(RetCode.SUCCESS_USER_LIST_GET)
				.setData(userModuleService.selectAll(pageUtil));
	}

}








