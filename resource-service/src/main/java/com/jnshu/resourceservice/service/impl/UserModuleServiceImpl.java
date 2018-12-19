package com.jnshu.resourceservice.service.impl;

import com.jnshu.resourceservice.dao.*;
import com.jnshu.resourceservice.entity.*;
import com.jnshu.resourceservice.exception.*;
import com.jnshu.resourceservice.service.*;
import com.jnshu.resourceservice.utils.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import sun.security.ssl.*;


/**
 * @program: morepineapple
 * @description: 用户管理模块-服务实现类
 * @author: Mr.huang
 * @create: 2018-12-17 00:11
 **/
@Service
public class UserModuleServiceImpl implements UserModuleService {


	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired(required = false )
	private UserMapper userMapper;

	/**
	 * @Description 用户管理-新增用户
	 * @param [user, jwt]
	 * @return java.lang.Integer
	 * @author Mr.HUANG
	 * @date 2018/12/17
	 * @throws
	 */
	@Override
	public void addUser(User newUser, JWT jwt){

		// 打印核心参数，用户姓名，手机号，操作者ID
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("当前用户id是：{}，传入的参数是:{}，{}",
					jwt.getUserID(),newUser.getUsername(),newUser.getPhoneNum());
		}
		// 判断新增用户是否为null
		if (null == newUser || null != userMapper.findByUsername(newUser.getName()) ){
			throw new ServiceException("用户名已被使用，请重新输入");
		}
		// 判断操作者ID是否存在，此处可省略
		if (null == jwt.getUserID()){
			throw new ServiceException("操作用户信息错误");
		}
		// 对输入的用户进行加密
		newUser.setPassword(BPwdEncoderUtil.BCryptPassword(newUser.getPassword()));
		// 写入创建时间，默认更新时间为操作时间
		Long nowTime = System.currentTimeMillis();
		newUser.setGmtCreate(nowTime);
		newUser.setGmtUpdate(nowTime);
		// 写入操作人，默认更新人为创建人
		User userExecutor = userMapper.selectUserDetailById(jwt.getUserID());
		newUser.setCreateBy(userExecutor.getName());
		newUser.setUpdateBy(userExecutor.getName());
		// 将新增数据插入数据库
		userMapper.insertSelective(newUser);
		// 对插入数据库的数据的返回ID进行查询，判断是否成功

		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("新增用户的ID为：{}",newUser.getId());
		}

		LOGGER.info("新增用户的ID为：{}",newUser.getId());
		LOGGER.info( "新增用户是否存在" +  userMapper.selectUserDetailById(newUser.getId()));
		if (null == userMapper.selectUserDetailById(newUser.getId())){
			throw new ServiceException("数据插入失败，请查看日志");
		}
	}

	/**
	 * @Description 用户管理-修改个人信息
	 * @param [user, jwt]
	 * @return void
	 * @author Mr.HUANG
	 * @date 2018/12/19
	 * @throws
	 */
	@Override
	public void update(User targetUser, JWT jwt) {

		// 打印核心参数，用户姓名，手机号，操作者ID
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("当前用户id是：{}，修改目标ID是:{}",
					jwt.getUserID(), targetUser.getId());
		}

		// 对需要验证的信息进行验证，username 用户名 、password 密码、

	}
}
