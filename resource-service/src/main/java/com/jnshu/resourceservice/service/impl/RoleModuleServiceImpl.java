package com.jnshu.resourceservice.service.impl;

import com.jnshu.resourceservice.dao.*;
import com.jnshu.resourceservice.entity.*;
import com.jnshu.resourceservice.service.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

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

	}
}
