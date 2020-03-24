package org.ril.hrss.controller;

import org.ril.hrss.service.sub_org_management.SubOrgConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class GamificationSettingController {
    @Autowired
    SubOrgConfigurationService subOrgConfigurationService;

    @GetMapping("/gamification_setting")
    public String index(Model model, HttpServletRequest httpServletRequest) {
        return subOrgConfigurationService.gamificationSettingIndex(model, httpServletRequest);
    }

    @PostMapping("/gamification_setting/store")
    public String store(HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes, HttpSession httpSession){
        return subOrgConfigurationService.gamificationSettingCreate(httpServletRequest, redirectAttributes);
    }

}
