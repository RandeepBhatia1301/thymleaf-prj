package org.ril.hrss.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ril.hrss.model.SubOrgConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class GACodeUtil {
    public static String index(Model model, SubOrgConfiguration subOrgConfiguration) {
        if (subOrgConfiguration != null) {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> map = null;
            try {
                map = mapper.readValue(subOrgConfiguration.getSettingValue(), Map.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            model.addAttribute("settingValue", map.get("code"));
        }
        return "analytics/add";
    }

    public static String createJson(HttpServletRequest httpServletRequest) {
        String settingValue = "";
        Map map = new HashMap();
        map.put(Constants.CODE, httpServletRequest.getParameter(Constants.SETTING_VALUE));
        ObjectMapper mapper = new ObjectMapper();
        try {
            settingValue = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return settingValue;
    }

    public static SubOrgConfiguration create(HttpServletRequest httpServletRequest, String settingValue) {
        SubOrgConfiguration subOrgConfiguration = new SubOrgConfiguration();
        subOrgConfiguration.setOrganizationId(ControlPanelUtil.setOrgId(httpServletRequest));
        subOrgConfiguration.setSubOrgId(ControlPanelUtil.setSubOrgId(httpServletRequest));
        subOrgConfiguration.setSettingName(Constants.G_ANALYTICS_SETTING_NAME);
        subOrgConfiguration.setIsEnable(Constants.ONE);
        if (settingValue != "") {
            subOrgConfiguration.setSettingValue(settingValue);
        }
        subOrgConfiguration.setCreatedBy(ControlPanelUtil.setAdminId(httpServletRequest));
        return subOrgConfiguration;
    }


}
