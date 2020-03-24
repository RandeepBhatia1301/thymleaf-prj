package org.ril.hrss.utility;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StorageContainerUtil {
    public static Map storeSuccess(Map map) {
        Map azure = new HashMap();
        if (map.containsKey(Constants.AZURE)) {
            azure = (Map) map.get(Constants.AZURE);
        }
        Map storageContainer = new HashMap();
        if (map != null) {
            storageContainer.put(Constants.FOLDER_NAME, map.get(Constants.UPLOAD_FOLDER_NAME));
            storageContainer.put(Constants.DOWNLOAD_BASE_PATH, azure.get(Constants.DOWNLOAD_BASE_PATH));
            storageContainer.put(Constants.ACCOUNT_NAME, azure.get(Constants.ACCOUNT_NAME));
            storageContainer.put(Constants.ACTIVE_STORAGE, map.get(Constants.ACTIVE_STORAGE));
            storageContainer.put(Constants.ACCOUNT_KEY, azure.get(Constants.ACCOUNT_KEY));
        }
        return storageContainer;

    }


}
