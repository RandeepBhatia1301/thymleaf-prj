package org.ril.hrss.utility;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Component
public class AdminActionUtil {
    public static String dashboard(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        return "dashboard";
    }

    public static ModelAndView logout(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().invalidate();

        return new ModelAndView("login");

    }

    public static ModelAndView signup() {
        return new ModelAndView("signup");

    }
}
