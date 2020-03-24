package org.ril.hrss.service.rest_api_services;

import org.ril.hrss.utility.StorageContainerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StorageContainer {
    @Autowired
    private StorageConfigService storageConfigService;

    public Map getAzureData(Integer is_org_level, Integer orgId) {
        Map map = storageConfigService.getStorageConfig(is_org_level, orgId);
        return StorageContainerUtil.storeSuccess(map);
    }
}
