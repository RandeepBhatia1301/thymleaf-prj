package org.ril.hrss.utility;

import org.ril.hrss.model.gamification.SubOrgwelcomeScreen;
import org.ril.hrss.model.gamification.WelcomeScreen;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class WelcomeScreenUtil {
    public static String setUpdateParam(Boolean status, RedirectAttributes redirectAttributes) {
        if (status) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.SUCCESS);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, Constants.UPDATED_SUCCESSFULLY);
        } else {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
        }
        return "redirect:/welcomescreen?v=467543557hr";
    }

    public static WelcomeScreen update(Optional<WelcomeScreen> welcomeScreen, HttpServletRequest httpServletRequest, String filePath) {

        WelcomeScreen welcomeScreen1 = welcomeScreen.get();
        welcomeScreen1.setHeaderText(httpServletRequest.getParameter("header"));
        welcomeScreen1.setBodyText(httpServletRequest.getParameter("body"));
        welcomeScreen1.setFooterText(httpServletRequest.getParameter("footer"));
        if (filePath != null && filePath != Constants.EMPTY) {
            welcomeScreen1.setImage(filePath);
        }

        return welcomeScreen1;
    }

    public static SubOrgwelcomeScreen updateSubOrgWelcomeScreen(Optional<SubOrgwelcomeScreen> subOrgwelcomeScreen, HttpServletRequest httpServletRequest, String filePath) {
        SubOrgwelcomeScreen subOrgwelcomeScreen1 = subOrgwelcomeScreen.get();
        subOrgwelcomeScreen1.setHeaderText(httpServletRequest.getParameter("header"));
        subOrgwelcomeScreen1.setBodyText(httpServletRequest.getParameter("body"));
        subOrgwelcomeScreen1.setFooterText(httpServletRequest.getParameter("footer"));
        if (filePath != null && filePath != Constants.EMPTY) {
            subOrgwelcomeScreen1.setImage(filePath);
        }
        return subOrgwelcomeScreen1;
    }

}
