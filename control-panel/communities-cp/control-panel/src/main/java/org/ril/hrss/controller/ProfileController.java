package org.ril.hrss.controller;

import org.ril.hrss.data_security.EncryptDecrypt;
import org.ril.hrss.model.auth.AdminUser;
import org.ril.hrss.service.auth.ProfileService;
import org.ril.hrss.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ProfileController {
    @Autowired
    ProfileService profileService;

    @GetMapping("/profile")
    public String index(HttpServletRequest httpServletRequest, HttpSession httpSession, Model model) throws Exception {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        AdminUser adminUser = new AdminUser();
        adminUser.setFirstName((String) httpSession.getAttribute("firstname"));
        adminUser.setLastName((String) httpSession.getAttribute("lastname"));
        adminUser.setEmail((String) httpSession.getAttribute("email"));
        String password = EncryptDecrypt.decrypt((String) httpSession.getAttribute("password"));
        model.addAttribute("adminUser", adminUser);
        model.addAttribute("password", password);
        return "profile/profile";
    }

    @PostMapping("/profile/store")
    public String store(HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes, HttpSession httpSession) throws Exception {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        Boolean status = profileService.edit(httpServletRequest);
        if (status) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.SUCCESS);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "Profile updated successfully");
        } else {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "Something went wrong, Please try again");
        }
        return "redirect:/profile?v=467543557hr";
    }

}
