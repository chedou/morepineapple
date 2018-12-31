package com.jnshu.resourceservice.entity;

import com.fasterxml.jackson.databind.annotation.*;
import org.springframework.security.core.*;

/**
 * @description 权限实体类model
 * @author Mr.HUANG
 * @date 2018/12/6
 */
@JsonSerialize
public class Permission implements GrantedAuthority {

    private static final long serialVersionUID = -1428285660796168326L;

    /**
     * @Fields 权限ID
     */
    private Integer id;

    /**
     * @Fields 权限名
     */
    private String permissionName;

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
     * @Description 权限构造函数
     * @param id permissionName gmtCreate gmtUpdate createBy updateBy
     *
     */
    public Permission(Integer id, String permissionName, Long gmtCreate, Long gmtUpdate,
                      String createBy, String updateBy) {
        this.id = id;
        this.permissionName = permissionName;
        this.gmtCreate = gmtCreate;
        this.gmtUpdate = gmtUpdate;
        this.createBy = createBy;
        this.updateBy = updateBy;
    }

    public Permission() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName == null ? null : permissionName.trim();
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

    /**
     * @Description 获取权限名
     * @param null
     * @return String   权限名
     * @author Mr.HUANG
     * @date 2018/12/12
     * @throws
     */
    @Override
    public String getAuthority(){
        return permissionName;
    }

    /**
     * @Description 打印权限名
     * @param null
     * @return String   权限名
     * @author Mr.HUANG
     * @date 2018/12/12
     */
    @Override
    public String toString() {
        return permissionName;
    }

    /**
     * @Description 打印 Permission model 的整体信息
     * @param null
     * @return String Permission model 的整体信息
     * @author Mr.HUANG
     * @date 2018/12/12
     */
    public String toStringAll() {
        return "Permission{" +
                "id=" + id +
                ", permissionName='" + permissionName + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtUpdate=" + gmtUpdate +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                '}';
    }


}