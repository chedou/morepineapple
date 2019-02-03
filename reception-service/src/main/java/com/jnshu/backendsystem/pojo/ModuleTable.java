package com.jnshu.backendsystem.pojo;

public class ModuleTable {
    private Integer id;

    private String name;

    private String menuld;

    private String url;

    private Integer parentModuleId;

    private String moduleCategory;

    private Long gmtCreate;

    private Long gmtUpdate;

    private String createBy;

    private String updateBy;

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

    @Override
    public String toString() {
        return "ModuleTable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", menuld='" + menuld + '\'' +
                ", url='" + url + '\'' +
                ", parentModuleId=" + parentModuleId +
                ", moduleCategory='" + moduleCategory + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtUpdate=" + gmtUpdate +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                '}';
    }
}