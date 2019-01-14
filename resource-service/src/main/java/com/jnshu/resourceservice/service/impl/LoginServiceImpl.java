package com.jnshu.resourceservice.service.impl;

import com.jnshu.resourceservice.client.*;
import com.jnshu.resourceservice.dao.*;
import com.jnshu.resourceservice.dto.*;
import com.jnshu.resourceservice.entity.*;
import com.jnshu.resourceservice.exception.*;
import com.jnshu.resourceservice.entity.User;
import com.jnshu.resourceservice.service.*;
import com.jnshu.resourceservice.utils.password.*;
import com.jnshu.resourceservice.utils.redis.*;
import org.slf4j.*;
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
public class LoginServiceImpl implements UserDetailsService ,LoginService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

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

	/**
	 * @Description 用户登录接口
	 * @param [username, password]
	 * @return com.jnshu.resourceservice.dto.UserLoginDTO
	 * @author Mr.HUANG
	 * @date 2019/1/14
	 * @throws
	 */
	@Override
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
		if (redisService.set(jwt.getAccess_token(), String.valueOf(System.currentTimeMillis()))){
			redisService.expire(jwt.getAccess_token(), 3*24*60*60);
			LOGGER.info("用户权限存入redis中，access_Token的值为：{}", jwt.getAccess_token());
		}

		UserLoginDTO userLoginDTO=new UserLoginDTO();
		userLoginDTO.setJwt(jwt);
		userLoginDTO.setUser(user);

		LOGGER.info("用户登录成功，登陆ID为：{}", user.getId());
		return userLoginDTO;

	}


	/**
	 * @param authorization
	 * @return void
	 * @throws
	 * @Description 用户登出
	 * @author Mr.HUANG
	 * @date 2019/1/14
	 */
	@Override
	public void out(String authorization) {
		LOGGER.info("传输进入的参数 authorization 为：", authorization);
		// 删除redis中存储用户登录的信息
		String accessToken = authorization.split(" ")[1];
		if (null == accessToken){
			LOGGER.info("问题点accessToken :{}", accessToken);
			throw new ServiceException("accessToken is null");
		}
		if (1 != redisService.del(accessToken)){
			throw new ServiceException("delete accessToken is wrong");
		}

	}


}
