package com.jnshu.uaaservice.pojo;

import org.codehaus.jackson.map.annotate.*;

import java.io.*;
import java.util.*;

/**
 * @Description 角色model
 * @author Mr.HUANG
 * @date 2018/12/12
 *
 */
@JsonSerialize
public class Role implements Serializable {

    private static final long serialVersionUID = -1428285660796168326L;

    /**
     * @Fields 角色id
     */
    private Integer id;

    /**
     * @Fields 角色名
     */
    private String roleName;

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
     * @Fields 角色状态
     */
    private Integer status;

    /**
     * 角色所对用的权限信息集合
     */
    private List<Permission> permissionsList;

    /**
     * @Description 根据角色信息构造角色model
     * @param id roleName gmtCreate gmtUpdate createBy updateBy status
     *
     */
    public Role(Integer id, String roleName, Long gmtCreate, Long gmtUpdate, String createBy,
                String updateBy, Integer status) {
        this.id = id;
        this.roleName = roleName;
        this.gmtCreate = gmtCreate;
        this.gmtUpdate = gmtUpdate;
        this.createBy = createBy;
        this.updateBy = updateBy;
        this.status = status;
    }

    public Role() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
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

    public List<Permission> getPermissionsList() {
        return permissionsList;
    }

    public void setPermissionsList(List<Permission> permissionsList) {
        this.permissionsList = permissionsList;
    }

    @Override
    public String
       toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtUpdate=" + gmtUpdate +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", status=" + status +
                ", permissionsList=" + permissionsList +
                '}';
    }
}