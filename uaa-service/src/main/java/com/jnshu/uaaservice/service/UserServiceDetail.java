package com.jnshu.uaaservice.service;

import com.jnshu.uaaservice.dao.*;
import com.jnshu.uaaservice.pojo.User;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

/**
 * @Description 用户详细信息服务
 * @author Mr.HUANG
 * @date 2018/12/12
 *
 */
@Service
public class UserServiceDetail implements UserDetailsService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserMapper userMapper;

	/**
	 * @Description 获取用户信息(根据用户姓名)
	 * @param username		用户名
	 * @return  User		用户model
	 * @author Mr.HUANG
	 * @date 2018/12/12
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User returnUser =userMapper.findByUsername(username);
		System.out.println("uaa-server-封装的用户信息为：" + returnUser);
		logger.info("----------------当前时间:{}----------------------", System.currentTimeMillis());
		System.out.println(returnUser.getUsername());
		return returnUser;
	}
}
