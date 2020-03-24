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
public class GoogleAnalyticsController {
    @Autowired
    SubOrgConfigurationService subOrgConfigurationService;

    @GetMapping("/analytics")
    public String index(Model model, HttpServletRequest httpServletRequest) {
        return subOrgConfigurationService.index(model, httpServletRequest);
    }

    @PostMapping("/analytics/store")
    public String store(HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes, HttpSession httpSession) throws Exception {
        return subOrgConfigurationService.create(httpServletRequest, redirectAttributes);
    }

}
