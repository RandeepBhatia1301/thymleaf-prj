package org.ril.hrss.model;

import javax.persistence.*;

@Entity
@Table(name = "organization.saas_configuration")
public class StorageConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "setting_value", columnDefinition = "JSON")
    private String settingValue;

    @Column(name = "setting_name")
    private String settingName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue;
    }

    public String getSettingName() {
        return settingName;
    }

    public void setSettingName(String settingName) {
        this.settingName = settingName;
    }
}


