package com.jnshu.resourceservice.service.impl;

import com.jnshu.resourceservice.dao.*;
import com.jnshu.resourceservice.entity.*;
import com.jnshu.resourceservice.service.*;
import org.springframework.beans.factory.annotation.*;

/**
 * @program: morepineapple
 * @description: 用户管理模块-服务实现类
 * @author: Mr.huang
 * @create: 2018-12-17 00:11
 **/
public class UserModuleServiceImpl implements UserModuleService {

	@Autowired
	private UserMapper userMapper;


	@Override
	public Integer insertSelective(User user) {


		return null;
	}
}
