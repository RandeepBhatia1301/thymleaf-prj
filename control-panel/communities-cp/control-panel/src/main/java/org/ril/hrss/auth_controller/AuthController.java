package org.ril.hrss.auth_controller;

import org.ril.hrss.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@ControllerAdvice
public class AuthController {
    @Autowired
    AuthService authService;

    @RequestMapping("/")
    public ModelAndView home() {
        return new ModelAndView("login");
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    private ModelAndView loginSubmit(HttpServletRequest request) throws Exception {
        return authService.setUserSessionAfterLogin(request);
    }
}


