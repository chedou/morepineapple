package com.jnshu.resourceservice.service;

import com.aliyuncs.dysmsapi.model.v20170525.*;
import com.jnshu.resourceservice.dto.*;
import com.jnshu.resourceservice.entity.*;
import com.jnshu.resourceservice.utils.pageutil.*;

/**
 * @program: morepineapple
 * @description: 用户管理模块-服务类
 * @author: Mr.huang
 * @create: 2018-12-17 00:07
 **/
public interface UserModuleService {

	/**
	 * @Description 用户管理-新增用户
	 * @param [newuUser, operatorId]
	 * @return java.lang.Integer
	 * @author Mr.HUANG
	 * @date 2018/12/17
	 * @throws
	 */
	UserModuleDTO addUser(User newUser,Long operatorId);

	/**
	 * @Description 用户管理-修改个人信息
	 * @param [targetUser, operatorId]
	 * @return UserModuleDTO
	 * @author Mr.HUANG
	 * @date 2018/12/19
	 * @throws
	 */
	User update(User targetUser, Long operatorId);

	/**
	 * @Description 用户管理-删除用户
	 * @param [targetUserId, jwt]
	 * @return void
	 * @author Mr.HUANG
	 * @date 2018/12/20
	 * @throws
	 */
	void delete(Long targetUserId, Long operatorId);

	/**
	 * @Description 用户管理-获取单个用户信息
	 * @param [targetUserId]
	 * @return com.jnshu.resourceservice.dto.UserModuleDTO
	 * @author Mr.HUANG
	 * @date 2018/12/20
	 * @throws
	 */
	UserModuleDTO select(Long targetUserId);


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
	UserModuleDTO selectUserList(PageUtil pageUtil, User user);

	/**
	 * @Description 后台管理-更换密码
	 * @param [operatorId, oldPassword, newPassword, code]
	 * @return void 
	 * @author Mr.HUANG
	 * @date 2019/1/3 
	 * @throws
	 */ 
	void updatePassword(Long operatorId, String oldPassword, String newPassword, String code );

	/**
	 * @Description 更换密码-获取短信验证码
	 * @param [photoNum, operatorId] 
	 * @return void 
	 * @author Mr.HUANG
	 * @date 2019/1/3 
	 * @throws 
	 */
	SendSmsResponse smsVerification(String photoNum, Long operatorId);




}
