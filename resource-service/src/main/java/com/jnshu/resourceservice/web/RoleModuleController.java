package com.jnshu.resourceservice.web;

import com.jnshu.resourceservice.core.ret.*;
import com.jnshu.resourceservice.service.*;
import io.swagger.annotations.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
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

	/**
	 * @Description 角色管理-增加角色
	 * @param []
	 * @return com.jnshu.resourceservice.core.ret.RetResult<?>
	 * @author Mr.HUANG
	 * @date 2018/12/24
	 * @throws
	 */
	public RetResult<?> addRole(){


		return RetResponse.result(RetCode.SUCCESS_USER_ONE_ADD);

	}

	//TODO:橘色管理-新增角色

	//TODO:橘色管理-更改角色信息

	//TODO:橘色管理-删除角色

	//TODO:橘色管理-查询单个角色

	//TODO:橘色管理-查询角色列表



}
