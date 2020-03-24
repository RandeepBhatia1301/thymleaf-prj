package org.ril.hrss.service.rest_api_services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ril.hrss.model.OrgConfiguration;
import org.ril.hrss.model.StorageConfiguration;
import org.ril.hrss.repository.OrgConfigurationRepository;
import org.ril.hrss.repository.StorageConfigurationRepository;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.OrgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class StorageConfigService {
    @Autowired
    StorageConfigurationRepository storageConfigurationRepository;
    @Autowired
    OrgConfigurationRepository orgConfigurationRepository;

    public Map getStorageConfig(Integer is_org_level, Integer orgId) {
        Map config = new HashMap();
        if (is_org_level.equals(Constants.ZERO)) {
            String settingName = Constants.STORAGE_CONFIG;
            StorageConfiguration storageConfiguration = storageConfigurationRepository.findBySettingName(settingName);
            return JsonParserFactory.getJsonParser().parseMap(storageConfiguration.getSettingValue());

        } else if (is_org_level.equals(Constants.ONE)) {
            String settingName = Constants.STORAGE_CONFIG;
            OrgConfiguration orgConfiguration = orgConfigurationRepository.findByOrganizationIdAndSettingName(orgId, settingName);
            if (orgConfiguration != null) {
                return JsonParserFactory.getJsonParser().parseMap(orgConfiguration.getSettingValue());
            }
        }
        return config;
    }

    public Boolean update(HttpServletRequest httpServletRequest, Integer orgId) throws JsonProcessingException {
        try {
            OrgConfiguration orgConfiguration = orgConfigurationRepository.findByOrganizationIdAndSettingName(orgId, Constants.STORAGE_CONFIG);
            orgConfiguration.setSettingValue(OrgUtil.createStorageConfig(httpServletRequest));
            orgConfigurationRepository.save(orgConfiguration);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
