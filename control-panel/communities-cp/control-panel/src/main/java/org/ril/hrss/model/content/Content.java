package org.ril.hrss.model.content;

import javax.persistence.*;

@Entity
@Table(name = "content.content_type")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "default_setting")
    private String defaultSetting;

    @Column(name = "content_settings")
    private String contentSetting;

    @Column(name = "status")
    private Integer status;

    @Column(name = "is_configurable")
    private Integer isConfigurable;
/*

        @OneToMany(fetch = FetchType.EAGER,mappedBy = "content", cascade = {CascadeType.MERGE})
        private Set<OrgContent> orgContents = new HashSet<>();
*/

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

    public Integer getIsConfigurable() {
        return isConfigurable;
    }

    public void setIsConfigurable(Integer isConfigurable) {
        this.isConfigurable = isConfigurable;
    }

    public String getDefaultSetting() {
        return defaultSetting;
    }

    public void setDefaultSetting(String defaultSetting) {
        this.defaultSetting = defaultSetting;
    }
}
