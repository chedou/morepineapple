package com.jnshu.resourceservice.web;

import com.jnshu.resourceservice.core.HandlerMethodArgumentResolver.*;
import com.jnshu.resourceservice.core.ret.*;
import com.jnshu.resourceservice.dto.*;
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

	@Autowired
	UserServiceDetail userServiceDetail;

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
	@PreAuthorize("hasAuthority('UserManageAll') AND hasAuthority('UserManageAdd') ")
	public RetResult<?> addUser(@Validated({AddUserGroup.class}) User user) throws Exception{

		logger.info("----UserModuleController----addUser------");
		logger.info("插入的用户名:{}", user.getName());

		Long operatorId =Long.parseLong(AuthorizationUtils.getUserId());

		UserModuleDTO newUser = userModuleService.addUser(user, operatorId);
		System.out.println(newUser.toString());
		return RetResponse.result(RetCode.SUCCESS_USER_ONE_ADD).setData(newUser);

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
	@PreAuthorize("hasAuthority('UserManageAll') AND hasAuthority('UserManageUpdate') ")
	public RetResult<?> updateUser(@Validated({UpdateUserGroup.class, AddUserGroup.class})User user)throws Exception{

		logger.info("----UserModuleController----updateUser-----");
		logger.info("需要修改的用户ID:{}", user.getId());
		System.out.println("--------------------");
		System.out.println(user);

		Long operatorId =Long.parseLong(AuthorizationUtils.getUserId());
		userModuleService.update(user, operatorId);
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
	@ApiOperation(value = "deleteUser",  notes = "删除用户")
	@DeleteMapping(value = "/user/{targetUserId}", produces = "application/json;charset=UTF-8")
	@PreAuthorize("hasAuthority('UserManageAll') AND hasAuthority('UserManageDelete') ")
	public RetResult<?> deleteUser(@PathVariable Long targetUserId) throws Exception{

		if (logger.isDebugEnabled()){
			logger.info("----UserModuleController----deleteUser-----");
			logger.info("需要删除的用户ID:{}", targetUserId) ;
		}
		Long operatorId =Long.parseLong(AuthorizationUtils.getUserId());
		userModuleService.delete(targetUserId, operatorId);
		return RetResponse.result(RetCode.SUCCESS_USER_ONE_DELETE);
	}


	//TODO：用户管理-单个用户信息

	/**
	 * @Description 用户管理-获取单个用户信息及根据条件搜索单个用户
	 * @param [targetUserID]
	 * @return com.jnshu.resourceservice.core.ret.RetResult<?>
	 * @author Mr.HUANG
	 * @date 2018/12/20
	 * @throws
	 */
	@ApiOperation(value = "selectUser",  notes = "查询用户")
	@GetMapping(value = "/user/{targetUserId}", produces = "application/json;charset=UTF-8")
	@PreAuthorize("hasAuthority('UserManageAll')")
	public RetResult<?> selectUser(@PathVariable Long targetUserId) throws Exception{

		logger.info("----UserModuleController----deleteUser-----");
		logger.info("需要查询的用户ID:{}", targetUserId);

		return RetResponse.result(RetCode.SUCCESS_USER_LIST_GET)
				.setData(userModuleService.select(targetUserId));
	}


	//TODO: 用户管理-账号列表
	/**
	 * @Description 用户管理-获取用户列表、根据条件查询对应的用户信息（查询条件主要有：角色名字、用户名）
	 * @param [pageUtil， user]
	 *        pageUtil 	分页参数
	 *        user		用户详细参数
	 * @return com.jnshu.resourceservice.core.ret.RetResult<?>
	 * @author Mr.HUANG
	 * @date 2018/12/21
	 * @throws Exception
	 */
	@ApiOperation(value = "selectUserList",  notes = "查询用户列表")
	@PostMapping(value = "/user/list", produces = "application/json;charset=UTF-8")
	@PreAuthorize("hasAuthority('UserManageAll')")
	public RetResult<?> selectUserList(@MultiRequestBody @Validated({PageUtilGroup.class}) PageUtil pageUtil,
									   @MultiRequestBody User user)throws Exception{


		logger.info("----UserModuleController----selectUserList-----");
		logger.info("分页参数为:{}", pageUtil.toString());

		System.out.println(user);
		System.out.println(pageUtil);
		return RetResponse.result(RetCode.SUCCESS_USER_LIST_GET)
				.setData(userModuleService.selectUserList(pageUtil, user));
	}

	/**
	 * @Description 后台管理-密码修改
	 * @param [oldPassword, newPassword, code]
	 * @return com.jnshu.resourceservice.core.ret.RetResult<?> 
	 * @author Mr.HUANG
	 * @date 2019/1/3 
	 * @throws Exception
	 */ 
	@ApiOperation(value = "UpdatePSW",  notes = "更改密码")
	@PostMapping(value = "/user/password", produces =  "application/json;charset=UTF-8")
	@PreAuthorize("hasAuthority('UserManageAll')")
	public RetResult<?> updatePassword(String oldPassword, String newPassword, String code)
			throws Exception{
		Long operatorId =Long.parseLong(AuthorizationUtils.getUserId());
		logger.info("----UserModuleController----UpdatePSW-----");
		logger.info("操作者为:{}",operatorId );
		userModuleService.updatePassword(operatorId, oldPassword, newPassword, code);
		return RetResponse.result(RetCode.SUCCESS_USER_PASSWORD_UPDATE);
	}

	/**
	 * @Description 后台管理-密码修改-获取短信验证码
	 * @param [photoNum] 
	 * @return com.jnshu.resourceservice.core.ret.RetResult<?> 
	 * @author Mr.HUANG
	 * @date 2019/1/3 
	 * @throws Exception
	 */ 
	@ApiOperation(value = "smsVerification",  notes = "获取短信验证码")
	@PostMapping(value = "/verification", produces =  "application/json;charset=UTF-8")
	@PreAuthorize("hasAuthority('UserManageAll')")
	public RetResult<?> smsVerification(String photoNum)throws Exception {
		Long operatorId =Long.parseLong(AuthorizationUtils.getUserId());
		logger.info("----UserModuleController----smsVerification-----");
		logger.info("操作者为:{}，手机号码为：{}",operatorId, photoNum );
		return RetResponse.result(RetCode.SUCCESS_VERIFICATION_GET)
				.setData(userModuleService.smsVerification(photoNum, operatorId));
	}

	/**
	 * @Description
	 * @param [username, password]
	 * @return com.jnshu.resourceservice.dto.UserLoginDTO
	 * @author Mr.HUANG
	 * @date 2019/1/10
	 * @throws
	 */
	@PostMapping(value = "/login",produces =  "application/json;charset=UTF-8")
	public UserLoginDTO login(@RequestParam("username") String username ,
							  @RequestParam("password") String password){
		//参数判断，省略
		System.out.println("username：" + username);
		System.out.println("password：" + password);
		return userServiceDetail.login(username,password);
	}

	@PostMapping(value = "/out", produces =  "application/json;charset=UTF-8")
	public UserLoginDTO out(@RequestParam("username") String username ,
							  @RequestParam("password") String password){

		return userServiceDetail.login(username,password);
	}

}








