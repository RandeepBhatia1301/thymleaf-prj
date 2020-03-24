package org.ril.hrss.model.language;

import javax.persistence.*;

@Entity
@Table(name = "organization.sub_org_language")
public class SubOrgLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sub_org_id")
    private Integer suborgId;

    @Column(name = "org_id")
    private Integer orgId;

    @Column(name = "language_id")
    private Integer language_id;

    @Column(name = "content")
    private String content;

    @Column(name = "language_name")
    private String name;

    @Column(name = "status")
    private Integer status;

    @Column(name = "is_edited")
    private Integer isEdited;

    @Column(name = "default_content")
    private String defaultContent;

    @Column(name = "lang_code")
    private String langCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSuborgId() {
        return suborgId;
    }

    public void setSuborgId(Integer suborgId) {
        this.suborgId = suborgId;
    }

    public Integer getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(Integer language_id) {
        this.language_id = language_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsEdited() {
        return isEdited;
    }

    public void setIsEdited(Integer isEdited) {
        this.isEdited = isEdited;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getDefaultContent() {
        return defaultContent;
    }

    public void setDefaultContent(String defaultContent) {
        this.defaultContent = defaultContent;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }
}
