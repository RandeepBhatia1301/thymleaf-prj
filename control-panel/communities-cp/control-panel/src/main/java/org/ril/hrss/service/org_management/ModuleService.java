/*
package com.ril.svc.service;

import Module;
import ModuleRepository;
import Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

@Service
public class ModuleService extends ControlPanelService<Module> {
    @Autowired
    ModuleRepository moduleRepository;

    public String index(HttpServletRequest httpServletRequest, Model model) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        model.addAttribute("modules", moduleRepository.findAll());
        return "module/index";
    }
}
*/
