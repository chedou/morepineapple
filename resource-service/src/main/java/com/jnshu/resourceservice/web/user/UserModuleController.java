package com.jnshu.resourceservice.web.user;

import com.jnshu.resourceservice.core.ret.*;
import com.jnshu.resourceservice.entity.*;
import org.slf4j.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.*;

/**
 * @description: 用户管理模块
 * @author: Mr.huang
 * @create: 2018-12-13 21:31
 **/
@RestController
@RequestMapping("/a")
public class UserModuleController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	//TODO：用户管理-新增用户
	/**
	 * @Description
	 * @param [user]
	 * @return com.jnshu.resourceservice.core.ret.RetResult<?>
	 * @author Mr.HUANG
	 * @date 2018/12/13
	 * @throws
	 */
	@PostMapping(value = "/user", produces = "application/json;charset=UTF-8")
	public RetResult<?> addUser(@RequestBody(required = true )  @NotNull  User user) throws Exception{

		if (null == user ){
			// throw new
			System.out.println();
			}
		RetResult<Object> result = new RetResult<>();
		return result;
		}

	}

	//TODO：用户管理-更改你用户



	//TODO：用户管理-删除用户


	//TODO: 用户管理-账号列表

	//TODO：用户管理-单个用户信息






