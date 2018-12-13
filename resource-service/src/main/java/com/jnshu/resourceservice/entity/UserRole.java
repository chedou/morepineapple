package com.jnshu.resourceservice.entity;

import com.fasterxml.jackson.databind.annotation.*;

import java.io.*;

/**
 * @description 用户角色表model
 * @author Mr.HUANG
 * @date 2018/12/6
 */
@JsonSerialize
public class UserRole implements Serializable {

	private static final long serialVersionUID = -1428285660796168326L;

	/**
	 * @Fields 用户角色关联表id
	 */
	private Integer id;

	/**
	 * @Fields 用户id
	 */
	private Long userId;

	/**
	 * @Fields 角色id
	 */
	private Long roleId;

	/**
	 * @Fields 创建时间
	 */
	private Long gmtCreate;

	/**
	 * @Fields 更新时间
	 */
	private Long gmtUpdate;

	/**
	 * @Fields 创建人
	 */
	private String createBy;

	/**
	 * @Fields 更新人
	 */
	private String updateBy;

	/**
	 * @Description 更具用户角色信息创建用户角色model
	 * @param id userId roleId gmtCreate gmtUpdate createBy updateBy
	 *
	 */
	public UserRole(Integer id, Long userId, Long roleId, Long gmtCreate, Long gmtUpdate,
					String createBy, String updateBy) {
		this.id = id;
		this.userId = userId;
		this.roleId = roleId;
		this.gmtCreate = gmtCreate;
		this.gmtUpdate = gmtUpdate;
		this.createBy = createBy;
		this.updateBy = updateBy;
	}

	public UserRole() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Long gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Long getGmtUpdate() {
		return gmtUpdate;
	}

	public void setGmtUpdate(Long gmtUpdate) {
		this.gmtUpdate = gmtUpdate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy == null ? null : createBy.trim();
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy == null ? null : updateBy.trim();
	}
}