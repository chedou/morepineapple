package com.jnshu.resourceservice.service.impl;

import com.github.pagehelper.*;
import com.jnshu.resourceservice.dao.*;
import com.jnshu.resourceservice.dto.*;
import com.jnshu.resourceservice.entity.*;
import com.jnshu.resourceservice.exception.*;
import com.jnshu.resourceservice.service.*;
import com.jnshu.resourceservice.utils.pageutil.*;
import com.jnshu.resourceservice.utils.password.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;


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
	public UserModuleDTO addUser(User newUser,Long operatorId){

		// 打印核心参数，用户姓名，手机号，操作者ID
		LOGGER.info("当前用户id是：{}，传入的参数是:{}，{}",
					operatorId,newUser.getUsername(),newUser.getPhoneNum());

		// 判断新增用户名是否已使用
		if (null != userMapper.findByUsername(newUser.getName())){
			throw new ServiceException("用户名已被使用，请重新输入");
		}
		// 对输入的用户进行加密
		newUser.setPassword(BPwdEncoderUtil.BCryptPassword(newUser.getPassword()));
		// 写入创建时间，默认更新时间为操作时间
		Long nowTime = System.currentTimeMillis();
		newUser.setGmtCreate(nowTime);
		newUser.setGmtUpdate(nowTime);
		// 写入操作人，默认更新人为创建人
		User userExecutor = userMapper.selectUserDetailById(operatorId);
		if (null == userExecutor){
			throw new ServiceException("操作用户ID有误，请查看服务器日志");
		}
		newUser.setCreateBy(userExecutor.getName());
		newUser.setUpdateBy(userExecutor.getName());
		// 写入用户有效状态,有效为：1，失效为：0
		newUser.setStatus(1);
		// 将新增数据插入数据库
		userMapper.insertSelective(newUser);
		// 对插入数据库的数据的返回ID进行查询，判断是否成功
		LOGGER.info("新增用户的ID为：{}",newUser.getId());
		LOGGER.info( "新增用户是否存在" +  userMapper.selectUserDetailById(newUser.getId()));
		// 检验插入是否成功
		if (null == userMapper.selectUserDetailById(newUser.getId())){
			throw new ServiceException("数据插入失败，请查看日志");
		}
		// 将新增的用户id返回
		User returnUser = new User();
		returnUser.setId(newUser.getId());
		UserModuleDTO userModuleDTO =new UserModuleDTO();
		userModuleDTO.setUser(returnUser);
		return userModuleDTO;
	}

	/**
	 * @Description 用户管理-修改个人信息
	 * @param [user, operatorId]
	 *               user		 更改的目标用户
	 *               operatorId	 操作者ID
	 * @return void
	 * @author Mr.HUANG
	 * @date 2018/12/19
	 * @throws
	 */
	@Override
	public User update(User targetUser, Long operatorId) {

		Long targetId =targetUser.getId();
		// 打印核心参数，用户id，操作者ID
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("当前用户id是：{}，修改目标ID是:{}",
					operatorId, targetUser.getId());
		}

		// 判断目标数据是否为失效数据
		if (null == targetUser.getId() || 0 == userMapper.selectUserDetailById(targetUser.getId()).getStatus()){
			throw new ServiceException("目标用户不存在或为失效用户");
		}
		// 若对密码进行修改，则先对其加密
		if (null != targetUser.getPassword()){
			targetUser.setPassword(BPwdEncoderUtil.BCryptPassword(targetUser.getPassword()));
		}
		// 写入修改时间
		targetUser.setGmtUpdate(System.currentTimeMillis());
		// 写入修改人
		targetUser.setUpdateBy(userMapper.selectUserDetailById(operatorId).getName());
		// 对数据库信息进行修改,并对返回值做判断，于此来确认数据是否更改成功
		// 对更改参数之后返回的ID与前端传入的ID进行比较，看是否有出错
		if (1 != userMapper.updateByPrimaryKeySelective(targetUser) & !targetId.equals(targetUser.getId())){
			LOGGER.info("前端传入的ID为：{},参数修改之后返回的参数为：{}", targetId, targetUser.getId());
			throw new ServiceException("数据更改失败，请查看服务器日志");
		}

		User returnUser =new User();
		returnUser.setId(targetUser.getId());
		return returnUser;
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
	public void delete(Long targetUserId, Long operatorId) {
		// 打印核心参数，用户id，操作者ID

		LOGGER.info("------------------------------------------------------");
		LOGGER.info("当前用户id是：{}，删除目标ID是:{}", operatorId, targetUserId);

		// 此处的删除，只是将数据的状态 status 更改为 0
		User returnUser = userMapper.selectUserDetailById(targetUserId);
		if (1 == returnUser.getStatus()){
			returnUser.setStatus(0);
		}
		// 写入修改时间
		returnUser.setGmtUpdate(System.currentTimeMillis());
		// 写入修改人
		if (null == userMapper.selectUserDetailById(operatorId)){
			throw new ServiceException("操作用户ID有误，请查看服务器日志");
		}
		returnUser.setUpdateBy(userMapper.selectUserDetailById(operatorId).getName());
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
	public UserModuleDTO select(Long targetUserId) {

		// 打印核心参数，用户id，操作者ID
		LOGGER.info("------------------------------------------------------");
		LOGGER.info("查询目标用户id是：{}", targetUserId);


		// 判断需要查询的用户状态是否有效，status 有效值为:1
		User returnUser = userMapper.selectUserDetailById(targetUserId);
		if (null == returnUser || 0 == returnUser.getStatus()){
			LOGGER.debug("查询得到的用户的姓名：{}，目标用户的ID：{}",returnUser,returnUser.getStatus());
			throw new ServiceException("用户数据失效，请查看服务器日志!");
		}
		UserModuleDTO userModuleDTO =new UserModuleDTO();
		userModuleDTO.setUser(returnUser);
		return userModuleDTO;
	}

	/**
	 * @Description 用户管理-获取用户列表-用户模糊查询相关的参数
	 * 				（查询的参数有：用户名：name,角色名：roleName）
	 * @param [pageUtil， user]
	 *        pageUtil 	分页参数
	 *        user		用户详细参数
	 * @return com.jnshu.resourceservice.dto.UserModuleDTO
	 * @author Mr.HUANG
	 * @date 2018/12/21
	 * @throws
	 */
	@Override
	public UserModuleDTO selectUserList(PageUtil pageUtil, User user ) {

		LOGGER.info("------------------------------------------------------");
		LOGGER.info("分页参数为：{}，如有用到模糊查询则其参数为：{}，{}",
					pageUtil.toString(),user.getName(),user.getRoleList().toString());

		// 对模糊查询的条件进行判断，如有问题，抛出异常
		// （根据需求，模糊查询中 用户名：username 和角色名:roleName 都只能有一个值）
		if (user.getRoleList().size() > 1){
			throw new ServiceException("根据用户参数获取用户详细信息参数异常," +
					"异常参数为：user.getRoleList().size()" + user.getRoleList().size());
		}
		// 将角色信息从请求参数中取出
		String targetRoleName = null ;
		if (null != user.getRoleList() & 1 == user.getRoleList().size()){
			Role targetRole =user.getRoleList().get(0);
			targetRoleName = targetRole.getRoleName();
		}
		System.out.println("-----------------------");
		System.out.println(user.toString());
		System.out.println(user.getName());
		System.out.println(targetRoleName);
		String name =user.getName();
		String roleName =targetRoleName;
		// 根据请求参数发起查询
		List<User> userList = userMapper.selectUserList(name, roleName);


		// 对页数进行判断是否超过最大页
		if ( null == pageUtil.getSize() || 0 == pageUtil.getSize()){
			// 如果被重写页长，这里更改为默认值：10
			pageUtil.setSize(10);
		}else if (pageUtil.getPage() > userList.size()/pageUtil.getSize() +1){
			throw new ServiceException("所查询页码已超过最大值");
		}
		// 设置物理分页插件参数
		PageHelper.startPage(pageUtil.getPage(), pageUtil.getSize());
		// 创建分页后封装类
		PageInfo<User> pageInfo = new PageInfo<>(userList);
		// 将返回参数写入返回封装类中
		UserModuleDTO userModuleDTO = new UserModuleDTO();
		userModuleDTO.setUserList(pageInfo.getList());
		pageUtil.setTotal(pageInfo.getTotal());
		userModuleDTO.setPageUtil(pageUtil);

		// 打印核心参数，用户id，操作者ID
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("返回的参数：{}",userModuleDTO.toString() );
		}
		return userModuleDTO;
	}

	@Override
	public void updatePassword(Long operatorId, String oldPassword, String newPassword) {

		LOGGER.info("------------------------------------------------------");
		LOGGER.info("操作者ID为：{}", operatorId );
		User userDB =userMapper.selectUserDetailById(operatorId);
		if (null == userDB){
			throw new ServiceException("用户参数有问题，请查看服务器日志");
		}
		LOGGER.info("操作者ID：{}，更改密码之前的参数为：{}", operatorId, userDB.hashCode());
		if (!BPwdEncoderUtil.matches(oldPassword, userDB.getPassword())){
			throw new ServiceException("error old password");
		}
		// 对密码进行更新
		userDB.setPassword(BPwdEncoderUtil.BCryptPassword(newPassword));
		LOGGER.info("操作者ID：{}，更换密码之前的参数为：{}", operatorId, userDB.hashCode());
		if (1 !=userMapper.updateByPrimaryKeySelective(userDB)){
			throw new ServiceException("密码更新失败，请查看服务器日志");
		}

	}


}
