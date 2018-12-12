package com.jnshu.uaaservice.pojo;

import org.codehaus.jackson.map.annotate.*;

import java.io.*;

/**
 * @Description 模块管理model
 * @author Mr.HUANG
 * @date 2018/12/12
 *
 */
@JsonSerialize
public class ModuleTable implements Serializable {

    private static final long serialVersionUID = -1428285660796168326L;

    /**
     * @Fields 模块id
     */
    private Integer id;

    /**
     * @Fields 模块名
     */
    private String name;

    /**
     * @Fields 模块ID
     */
    private String menuld;
    
    /**
     * @Fields URL地址
     */ 
    private String url;

    /**
     * @Fields 父模块ID
     */
    private Integer parentModuleId;

    /**
     * @Fields 模块类别
     */
    private String moduleCategory;

    /**
     * @Description 注册时间 
     * @param  
     * 
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
     * @Description 根据模块表信息构造 moduleTable
     * @param  id name menuld url parentModuleId moduleCategory gmtCreate gmtUpdate createBy updateBy
     *
     */
    public ModuleTable(Integer id, String name, String menuld, String url, Integer parentModuleId,
                       String moduleCategory, Long gmtCreate, Long gmtUpdate, String createBy, String updateBy) {
        this.id = id;
        this.name = name;
        this.menuld = menuld;
        this.url = url;
        this.parentModuleId = parentModuleId;
        this.moduleCategory = moduleCategory;
        this.gmtCreate = gmtCreate;
        this.gmtUpdate = gmtUpdate;
        this.createBy = createBy;
        this.updateBy = updateBy;
    }

    public ModuleTable() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMenuld() {
        return menuld;
    }

    public void setMenuld(String menuld) {
        this.menuld = menuld == null ? null : menuld.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getParentModuleId() {
        return parentModuleId;
    }

    public void setParentModuleId(Integer parentModuleId) {
        this.parentModuleId = parentModuleId;
    }

    public String getModuleCategory() {
        return moduleCategory;
    }

    public void setModuleCategory(String moduleCategory) {
        this.moduleCategory = moduleCategory == null ? null : moduleCategory.trim();
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