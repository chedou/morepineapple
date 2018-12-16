package com.jnshu.resourceservice.service;

import com.jnshu.resourceservice.dao.*;
import com.jnshu.resourceservice.entity.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * @program: morepineapple
 * @description: 用户管理模块-服务类
 * @author: Mr.huang
 * @create: 2018-12-17 00:07
 **/
@Service
public interface UserModuleService {

	// private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// @Autowired
	// private UserMapper userMapper;

	Integer insertSelective(User user);

}
