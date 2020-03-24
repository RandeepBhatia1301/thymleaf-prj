package org.ril.hrss.model;

import javax.persistence.Embeddable;

@Embeddable
public class AzureStorage {

    private String activeStorage;

    private String uploadFolderName;

    public String getActiveStorage() {
        return activeStorage;
    }

    public void setActiveStorage(String activeStorage) {
        this.activeStorage = activeStorage;
    }

    public String getUploadFolderName() {
        return uploadFolderName;
    }

    public void setUploadFolderName(String uploadFolderName) {
        this.uploadFolderName = uploadFolderName;
    }
}
