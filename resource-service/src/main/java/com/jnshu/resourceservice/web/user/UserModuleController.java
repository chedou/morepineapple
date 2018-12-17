package com.jnshu.resourceservice.web.user;

import com.jnshu.resourceservice.core.ret.*;
import com.jnshu.resourceservice.dao.*;
import com.jnshu.resourceservice.entity.*;
import com.jnshu.resourceservice.entity.userValidateGroup.*;
import com.jnshu.resourceservice.service.*;
import com.sun.xml.internal.bind.v2.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.*;
import javax.validation.constraints.*;
import javax.xml.crypto.*;

/**
 * @description: 用户管理模块
 * @author: Mr.huang
 * @create: 2018-12-13 21:31
 **/
@RestController
@RequestMapping("/a")
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
	@PostMapping(value = "/user", produces = "application/json;charset=UTF-8")
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('RoleManageAll') AND hasAuthority('RoleManageAdd') ")
	public RetResult<?> addUser(@RequestBody(required = true )@Validated(value = AddUserGroup.class) User user,
								JWT jwt) throws Exception{

		if (logger.isDebugEnabled()){
			logger.debug("----UserModuleController----");
			logger.debug("插入的用户名:" + user.getName());
		}

		userModuleService.addUser(user, jwt);
		return RetResponse.result(RetCode.SUCCESS_USER_ONE_ADD);

	}

	//TODO：用户管理-更改你用户



	//TODO：用户管理-删除用户


	//TODO: 用户管理-账号列表

	//TODO：用户管理-单个用户信息`



	}








