package org.ril.hrss.service.sub_org_management;

import org.ril.hrss.model.StorageConfiguration;
import org.ril.hrss.model.SubOrgConfiguration;
import org.ril.hrss.repository.StorageConfigurationRepository;
import org.ril.hrss.repository.SubOrgConfigurationRepository;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ControlPanelUtil;
import org.ril.hrss.utility.GACodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Service
public class SubOrgConfigurationService {
    @Autowired
    SubOrgConfigurationRepository subOrgConfigurationRepository;

    @Autowired
    StorageConfigurationRepository storageConfigurationRepository;

    public String index(Model model, HttpServletRequest httpServletRequest) {
        return GACodeUtil.index(model, subOrgConfigurationRepository.findByOrganizationIdAndSubOrgIdAndSettingName(ControlPanelUtil.setOrgId(httpServletRequest), ControlPanelUtil.setSubOrgId(httpServletRequest), Constants.G_ANALYTICS_SETTING_NAME));
    }

    public String create(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        try {
            SubOrgConfiguration subOrgConfiguration1 = subOrgConfigurationRepository.findByOrganizationIdAndSubOrgIdAndSettingName(ControlPanelUtil.setOrgId(httpServletRequest), ControlPanelUtil.setSubOrgId(httpServletRequest), Constants.G_ANALYTICS_SETTING_NAME);
            if (subOrgConfiguration1 == null) {
                subOrgConfigurationRepository.save(GACodeUtil.create(httpServletRequest, GACodeUtil.createJson(httpServletRequest)));
                ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, Constants.ANALYTICS_SUCCESS, "redirect:/analytics?v=467543557hr");
            } else {
                subOrgConfiguration1.setSettingValue(GACodeUtil.createJson(httpServletRequest));
                subOrgConfiguration1.setCreatedBy(ControlPanelUtil.setAdminId(httpServletRequest));
                subOrgConfigurationRepository.save(subOrgConfiguration1);
                ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, "Analytics code updated successfully", "redirect:/analytics?v=467543557hr");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "redirect:/analytics?v=467543557hr";
    }

    public String gamificationSettingIndex(Model model, HttpServletRequest httpServletRequest) {

        StorageConfiguration storageConfiguration = storageConfigurationRepository.findBySettingName("GAMIFICATION");
        model.addAttribute("gamification", storageConfiguration);

        return "GamificationLevel/gamificationSetting";
    }

    public String gamificationSettingCreate(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        StorageConfiguration storageConfiguration = storageConfigurationRepository.findBySettingName("GAMIFICATION");
        storageConfiguration.setSettingValue(httpServletRequest.getParameter("jsonInput"));
        storageConfigurationRepository.save(storageConfiguration);

        String referer = httpServletRequest.getHeader(Constants.REFERER);
        return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, "Settings updated successfully", Constants.REDIRECT + referer);
    }
}
