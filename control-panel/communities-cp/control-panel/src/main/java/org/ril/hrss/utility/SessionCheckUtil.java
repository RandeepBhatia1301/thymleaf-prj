package org.ril.hrss.utility;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class SessionCheckUtil {

    public static void checkSession(HttpServletRequest httpServletRequest) {

      /*  if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }*/
    }

}
