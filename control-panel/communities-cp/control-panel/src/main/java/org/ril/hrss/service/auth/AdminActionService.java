package org.ril.hrss.service.auth;

import org.ril.hrss.utility.AdminActionUtil;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Service
public class AdminActionService {
    public String dashboard(Model model, HttpServletRequest httpServletRequest) {

        return AdminActionUtil.dashboard(httpServletRequest);
    }

    public ModelAndView logout(HttpServletRequest httpServletRequest) {
        return AdminActionUtil.logout(httpServletRequest);
    }

    public ModelAndView signup() {
        return AdminActionUtil.signup();
    }
}
