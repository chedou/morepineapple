package com.jnshu.resourceservice.dto;

import com.jnshu.resourceservice.entity.*;


/**
 * @program: purview
 * @description: 用户登录返回封装
 * @author: Mr.huang
 * @create: 2018-12-13 20:30
 **/
public class UserLoginDTO {

	/**
	 * 封装的JWT
	 */
	private JWT jwt;

	/**
	 * 用户详细信息
 	 */
	private User user;


	public JWT getJwt() {
		return jwt;
	}

	public void setJwt(JWT jwt) {
		this.jwt = jwt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
