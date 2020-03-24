package org.ril.hrss.model.content;


import javax.persistence.*;


@Entity
@Table(name = "organization.sub_org_content_type")
public class SubOrgContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "content_settings")
    private String contentSetting;

    @Column(name = "status")
    private Integer status;

    @Column(name = "suborg_id")
    private Integer subOrgId;

    @Column(name = "org_id")
    private Integer orgId;

    @Column(name = "content_type_id")
    private Integer contentTypeId;

    @Column(name = "org_content_type_id")
    private Integer orgContentTypeId;

    @Column(name = "is_available")
    private Integer isAvailable;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "default_setting")
    private String defaultSetting;

    @Column(name = "is_configurable")
    private Integer isConfigurable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContentSetting() {
        return contentSetting;
    }

    public void setContentSetting(String contentSetting) {
        this.contentSetting = contentSetting;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSubOrgId() {
        return subOrgId;
    }

    public void setSubOrgId(Integer subOrgId) {
        this.subOrgId = subOrgId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getContentTypeId() {
        return contentTypeId;
    }

    public void setContentTypeId(Integer contentTypeId) {
        this.contentTypeId = contentTypeId;
    }

    public Integer getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Integer isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Integer getOrgContentTypeId() {
        return orgContentTypeId;
    }

    public void setOrgContentTypeId(Integer orgContentTypeId) {
        this.orgContentTypeId = orgContentTypeId;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getDefaultSetting() {
        return defaultSetting;
    }

    public void setDefaultSetting(String defaultSetting) {
        this.defaultSetting = defaultSetting;
    }

    public Integer getIsConfigurable() {
        return isConfigurable;
    }

    public void setIsConfigurable(Integer isConfigurable) {
        this.isConfigurable = isConfigurable;
    }
}
