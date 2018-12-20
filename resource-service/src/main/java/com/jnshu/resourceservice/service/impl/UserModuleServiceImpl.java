package com.jnshu.resourceservice.service.impl;

import com.jnshu.resourceservice.dao.*;
import com.jnshu.resourceservice.entity.*;
import com.jnshu.resourceservice.exception.*;
import com.jnshu.resourceservice.service.*;
import com.jnshu.resourceservice.utils.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;



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
		// 判断新增用户名是否已使用
		if (null == newUser || null != userMapper.findByUsername(newUser.getName()) ){
			throw new ServiceException("用户名已被使用，请重新输入");
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
		// 检验插入是否成功
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

		// 打印核心参数，用户id，操作者ID
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("当前用户id是：{}，修改目标ID是:{}",
					jwt.getUserID(), targetUser.getId());
		}

		// 判断目标数据是否为失效数据
		if (0 == userMapper.selectUserDetailById(targetUser.getId()).getStatus()){
			throw new ServiceException("目标用户为失效用户");
		}
		// 若对密码进行修改，则先对其加密
		if (null != targetUser.getPassword()){
			targetUser.setPassword(BPwdEncoderUtil.BCryptPassword(targetUser.getPassword()));
		}
		// 写入修改时间
		targetUser.setGmtUpdate(System.currentTimeMillis());
		// 写入修改人
		targetUser.setUpdateBy(userMapper.selectUserDetailById(jwt.getUserID()).getName());
		// 对数据库信息进行修改,并对返回值做判断，于此来确认数据是否更改成功
		if (1 != userMapper.updateByPrimaryKeySelective(targetUser)){
			throw new ServiceException("数据更改失败，请查看服务器日志");
		}

	}

	/**
	 * @Description 用户管理-删除用户
	 * @param [targetUserId, jwt]
	 * @return void
	 * @author Mr.HUANG
	 * @date 2018/12/20
	 * @throws
	 */
	@Override
	public void delete(Long targetUserId, JWT jwt) {
		// 打印核心参数，用户id，操作者ID
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("当前用户id是：{}，删除目标ID是:{}",
					jwt.getUserID(), targetUserId);
		}

		// 此处的删除，只是将数据的状态 status 更改为 0
		User returnUser = userMapper.selectUserDetailById(targetUserId);
		if (1 == returnUser.getStatus()){
			returnUser.setStatus(0);
		}
		// 写入修改时间
		returnUser.setGmtUpdate(System.currentTimeMillis());
		// 写入修改人
		returnUser.setUpdateBy(userMapper.selectUserDetailById(jwt.getUserID()).getName());

		if (1 != userMapper.updateByPrimaryKeySelective(returnUser)){
			throw new ServiceException("数据删除失败，请查看服务器日志");
		}


	}

	/**
	 * @param targetUserId
	 * @return com.jnshu.resourceservice.entity.User
	 * @throws
	 * @Description 用户管理-获取单个用户信息
	 * @author Mr.HUANG
	 * @date 2018/12/20
	 */
	@Override
	public User select(Long targetUserId) {

		// 打印核心参数，用户id，操作者ID
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("查询目标用户id是：{}", targetUserId);
		}

		// 判断需要查询的用户状态是否有效，status 有效值为:1
		// if (0 == )





		return null;
	}
}
