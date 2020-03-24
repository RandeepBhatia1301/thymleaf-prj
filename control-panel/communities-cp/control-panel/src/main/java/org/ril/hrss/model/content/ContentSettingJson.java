package org.ril.hrss.model.content;

import javax.persistence.Embeddable;

@Embeddable
public class ContentSettingJson {

    private String contentName;

    private String contentDescription;

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getContentDescription() {
        return contentDescription;
    }

    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
    }
}
