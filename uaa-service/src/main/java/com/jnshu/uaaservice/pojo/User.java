package com.jnshu.uaaservice.pojo;

import org.codehaus.jackson.map.annotate.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;

import java.io.*;
import java.util.*;

/**
 * @Description 用户model
 * @author Mr.HUANG
 * @date 2018/12/12
 *
 */
@JsonSerialize
public class User implements UserDetails, Serializable {

	private static final long serialVersionUID = -1428285660796168326L;

	/**
	 * @Fields 用户id
	 */
	private Long id;

	/**
	 * @Fields 用户名
	 */
	private String name;

	/**
	 * @Fields 用户密码
	 */
	private String password;

	/**
	 * @Fields 盐
	 */
	private String salt;

	/**
	 * @Fields 手机号码
	 */
	private String phoneNum;

	/**
	 * @Fields 头像
	 */
	private String headImage;

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
	 * @Fields 用户有效状态
	 */
	private Integer status;

	/**
	 * 用户所对应的角色信息集合（用户与角色对应关系为一对多）
	 */
	private List<Role> roleList;

	/**
	 * @Fields 用户所拥有的权限信息
	 */
	private List<Permission> authorities;

	/**
	 * @Description 根据用户信息构造用户model 
	 * @param id name password salt phoneNum headImage gmtCreate gmtUpdate createBy updateBy status 
	 *  
	 */ 
	public User(Long id, String name, String password, String salt, String phoneNum, String headImage,
				Long gmtCreate, Long gmtUpdate, String createBy, String updateBy, Integer status
	) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.salt = salt;
		this.phoneNum = phoneNum;
		this.headImage = headImage;
		this.gmtCreate = gmtCreate;
		this.gmtUpdate = gmtUpdate;
		this.createBy = createBy;
		this.updateBy = updateBy;
		this.status = status;
	}


	public User() {
		super();
	}

	/**
	 * @Description 获取用户所拥有的全部权限
	 * @param
	 * @return 返回权限信息的集合（其中需要继承 GrantedAuthority ）
	 * @author Mr.HUANG
	 * @date 2018/12/12
	 * @throws
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		List<Permission> permissionListTem = new ArrayList<>();
		for (Role aRoleList : roleList) {
			permissionListTem.addAll(aRoleList.getPermissionsList());
			authorities = permissionListTem;
		}
		return authorities;
	}

	/**
	 * @Description 获取用户密码
	 * @param
	 * @return
	 * @author Mr.HUANG
	 * @date 2018/12/12
	 * @throws
	 */
	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	/**
	 * @Description 获取用户名
	 * @param
	 * @return 重写返回用户名，更改为 用户ID+用户名
	 * @author Mr.HUANG
	 * @date 2018/12/12
	 * @throws
	 */
	@Override
	public String getUsername() {
		return id + "," + name;
	}

	/**
	 * @Description 判断权限数量是否为空
	 * @param
	 * @return
	 * @author Mr.HUANG
	 * @date 2018/12/12
	 * @throws
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * @Description 判断用户是否被锁定 （多线程）
	 * @param
	 * @return
	 * @author Mr.HUANG
	 * @date 2018/12/12
	 * @throws
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * @Description 判断凭证是否过期
	 * @param
	 * @return
	 * @author Mr.HUANG
	 * @date 2018/12/12
	 * @throws
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * @Description 是否开启
	 * @param
	 * @return
	 * @author Mr.HUANG
	 * @date 2018/12/12
	 * @throws
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt == null ? null : salt.trim();
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum == null ? null : phoneNum.trim();
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage == null ? null : headImage.trim();
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", password='" + password + '\'' +
				", salt='" + salt + '\'' +
				", phoneNum='" + phoneNum + '\'' +
				", headImage='" + headImage + '\'' +
				", gmtCreate=" + gmtCreate +
				", gmtUpdate=" + gmtUpdate +
				", createBy='" + createBy + '\'' +
				", updateBy='" + updateBy + '\'' +
				", status=" + status +
				", roleList=" + roleList +
				", authorities=" + authorities +
				'}';
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public void setAuthorities(List<Permission> authorities) {
		this.authorities = authorities;
	}

}