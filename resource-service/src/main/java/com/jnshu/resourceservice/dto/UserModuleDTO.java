package com.jnshu.resourceservice.dto;

import com.jnshu.resourceservice.entity.*;
import com.jnshu.resourceservice.utils.pageutil.*;

import java.util.*;

/**
 * @program: morepineapple
 * @description: 用户管理返回封装
 * @author: Mr.huang
 * @create: 2018-12-21 09:46
 **/
public class UserModuleDTO {

	private User user;

	private List<User> userList;

	private JWT jwt;

	private PageUtil pageUtil;

	public PageUtil getPageUtil() {
		return pageUtil;
	}

	public void setPageUtil(PageUtil pageUtil) {
		this.pageUtil = pageUtil;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public JWT getJwt() {
		return jwt;
	}

	public void setJwt(JWT jwt) {
		this.jwt = jwt;
	}


	@Override
	public String toString() {
		return "UserModuleDTO{" +
				"user=" + user +
				", userList=" + userList +
				", jwt=" + jwt +
				", pageUtil=" + pageUtil +
				'}';
	}
}
