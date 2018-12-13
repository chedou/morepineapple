package com.jnshu.resourceservice.entity;

import com.fasterxml.jackson.databind.annotation.*;

import java.io.*;

/**
 * @description 角色权限关联表
 * @author Mr.HUANG
 * @date 2018/12/6
 */
@JsonSerialize
public class RolePermission implements Serializable {

    /**
     * @Fields 关联id
     */
    private Integer id;

    /**
     * @Fields 角色Id
     */
    private Integer roleId;

    /**
     * @Fields 权限Id
     */
    private Integer nodeId;

    /**
     * @Fields 信息状态
     */
    private String status;

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
     * @Description 根据角色权限信息构造角色权限model
     * @param id roleId nodeId status gmtCreate gmtUpdate createBy updateBy
     *
     */
    public RolePermission(Integer id, Integer roleId, Integer nodeId, String status,
                          Long gmtCreate, Long gmtUpdate, String createBy, String updateBy) {
        this.id = id;
        this.roleId = roleId;
        this.nodeId = nodeId;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtUpdate = gmtUpdate;
        this.createBy = createBy;
        this.updateBy = updateBy;
    }

    public RolePermission() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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

    @Override
    public String toString() {
        return "RolePermission{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", nodeId=" + nodeId +
                ", status='" + status + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtUpdate=" + gmtUpdate +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                '}';
    }
}