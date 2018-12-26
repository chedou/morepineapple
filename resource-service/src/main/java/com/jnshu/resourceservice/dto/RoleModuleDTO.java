package com.jnshu.resourceservice.dto;

import com.jnshu.resourceservice.entity.*;
import com.jnshu.resourceservice.utils.pageutil.*;

import java.util.*;


/**
 * @program: morepineapple
 * @description: 角色信息封装类
 * @author: Mr.huang
 * @create: 2018-12-26 09:11
 **/
public class RoleModuleDTO {

	private Role role;

	private List<Role> roleList;

	private JWT jwt;

	private PageUtil pageUtil;


	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public JWT getJwt() {
		return jwt;
	}

	public void setJwt(JWT jwt) {
		this.jwt = jwt;
	}

	public PageUtil getPageUtil() {
		return pageUtil;
	}

	public void setPageUtil(PageUtil pageUtil) {
		this.pageUtil = pageUtil;
	}

	@Override
	public String toString() {
		return "RoleModuleDTO{" +
				"role=" + role +
				", roleList=" + roleList +
				", jwt=" + jwt +
				", pageUtil=" + pageUtil +
				'}';
	}
}
