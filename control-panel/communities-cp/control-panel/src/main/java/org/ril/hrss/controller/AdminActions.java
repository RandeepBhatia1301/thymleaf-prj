package org.ril.hrss.controller;

import org.ril.hrss.repository.UserLogRepository;
import org.ril.hrss.service.auth.AdminActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminActions {
    @Autowired
    AdminActionService adminActionService;

    @Autowired
    UserLogRepository userLogRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpServletRequest httpServletRequest) {
        return adminActionService.dashboard(model, httpServletRequest);
    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest httpServletRequest) {
        return adminActionService.logout(httpServletRequest);
    }

    @GetMapping("/signup")
    public ModelAndView signup() {
        return adminActionService.signup();
    }

    @GetMapping("/staticpage")
    public String staticPge() {
        return "static-page";
    }
}


