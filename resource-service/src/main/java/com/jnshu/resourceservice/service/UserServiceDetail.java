package com.jnshu.resourceservice.service;

import com.jnshu.resourceservice.client.*;
import com.jnshu.resourceservice.dao.*;
import com.jnshu.resourceservice.dto.*;
import com.jnshu.resourceservice.entity.*;
import com.jnshu.resourceservice.exception.*;
import com.jnshu.resourceservice.entity.User;
import com.jnshu.resourceservice.utils.password.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

/**
 * @program: purview
 * @description: 用户详情服务类
 * @author: Mr.huang
 * @create: 2018-12-06 20:12
 **/
@Service
public class UserServiceDetail implements UserDetailsService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	AuthServiceClient client;

	@Autowired
	private RedisService redisService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userMapper.findByUsername(username);
	}

	public com.jnshu.resourceservice.entity.User insertUser(String username, String  password){
		com.jnshu.resourceservice.entity.User user = new com.jnshu.resourceservice.entity.User();
		user.setName(username);
		user.setPassword(BPwdEncoderUtil.BCryptPassword(password));
		userMapper.insertSelective(user);
		com.jnshu.resourceservice.entity.User userReturn =userMapper.findByUsername(username);
		return userReturn;
	}

	public UserLoginDTO login(String username, String password){
		User user=userMapper.findByUsername(username);
		if (null == user || null  == userMapper.findByUsername(username)) {
			throw new UserLoginException("error username ");
		}
		if(!BPwdEncoderUtil.matches(password,user.getPassword())){
			throw new UserLoginException("error password");
		}
		// 获取token
		JWT jwt=client.getToken("Basic dXNlci1zZXJ2aWNlOjEyMzQ1Ng==","password",username,password);
		// 获得用户菜单
		
		if(jwt==null){
			throw new UserLoginException("error internal");
		}
		// 用户id写入jwt
		jwt.setUserID(user.getId());
		// 将用户登录信息写入redis中，accessToken作为key，时间戳为value
		redisService.set(jwt.getAccess_token(), String.valueOf(System.currentTimeMillis()));
		redisService.expire(jwt.getAccess_token(), 3*24*60*60);

		UserLoginDTO userLoginDTO=new UserLoginDTO();
		userLoginDTO.setJwt(jwt);
		userLoginDTO.setUser(user);
		return userLoginDTO;

	}

}
