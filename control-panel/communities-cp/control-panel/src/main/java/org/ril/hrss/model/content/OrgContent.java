package org.ril.hrss.model.content;


import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "organization.org_content_type")
public class OrgContent implements Serializable {
    @Transient
    @Embedded
    ContentSettingJson contentSettingJson;

/*
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn
    private Org org;*/

    /* @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
     @JoinColumn(name = "content_type_id")
     private Content content;*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "content_setting")
    private String contentSetting;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private Integer status;
    @Column(name = "org_id")
    private Integer orgId;
    @Column(name = "content_type_id")
    private Integer contentTypeId;
    @Column(name = "is_available")
    private Integer isAvailable;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public ContentSettingJson getContentSettingJson() {
        return contentSettingJson;
    }

    public void setContentSettingJson(ContentSettingJson contentSettingJson) {
        this.contentSettingJson = contentSettingJson;
    }

    public Integer getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Integer isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getContentSetting() {
        return contentSetting;
    }

    public void setContentSetting(String contentSetting) {
        this.contentSetting = contentSetting;
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

    @Override
    public String toString() {
        return "OrgContent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contentSetting='" + contentSetting + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", orgId=" + orgId +
                ", contentTypeId=" + contentTypeId +
                ", isAvailable=" + isAvailable +
                ", contentSettingJson=" + contentSettingJson +
                ", defaultSetting='" + defaultSetting + '\'' +
                ", isConfigurable=" + isConfigurable +
                '}';
    }
}
