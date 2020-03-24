package org.ril.hrss.controller;

import org.ril.hrss.data_security.EncryptDecrypt;
import org.ril.hrss.model.Org;
import org.ril.hrss.model.SubOrg;
import org.ril.hrss.model.auth.AdminUser;
import org.ril.hrss.service.org_management.OrgService;
import org.ril.hrss.service.org_management.UserService;
import org.ril.hrss.service.sub_org_management.SubOrgService;
import org.ril.hrss.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UsersController {

    @Autowired
    UserService userService;

    @Autowired
    OrgService orgService;

    @Autowired
    SubOrgService subOrgService;

    @GetMapping("/users")
    public String index(@RequestParam(value = "searchQuery", required = false) String query, Model model, HttpServletRequest httpServletRequest, HttpSession httpSession) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        if (query != null) {
            Page<AdminUser> adminUsers = userService.getAdminUsers(query, httpServletRequest, httpSession);
            model.addAttribute("users", adminUsers);
            model.addAttribute(Constants.PAGE, adminUsers);
            model.addAttribute("query", query);
            return "user/users-list";
        }

        Page<AdminUser> users = userService.getAdminUsers(httpServletRequest);
        model.addAttribute("users", users);
        model.addAttribute(Constants.PAGE, users);
        model.addAttribute("query", query);
        return "user/users-list";
    }

    /*show user creation page*/
    @GetMapping("/user/create")
    public String create(HttpServletRequest httpServletRequest, Model model) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        List<Org> orgList = orgService.getOrgList();
        List<SubOrg> subOrgList = subOrgService.getSubOrgList(httpServletRequest);
        model.addAttribute("orgList", orgList);
        model.addAttribute("subOrgList", subOrgList);
        return "user/create";
    }

    /*creating a new user API*/
    @PostMapping("/user/store")
    public String store(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        boolean status = userService.createUser(httpServletRequest);
        if (status) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.SUCCESS);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "User Added successfully");
        } else {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "User already exist");
        }
        return "redirect:/users?v=467543557hr";
    }

    /*call the edit user page @Param id*/
    @GetMapping("/user/edit/{id}")
    public String edit(@PathVariable(value = "id") Integer id, Model model, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        String decryptedPassword = null;
        AdminUser userData = userService.getUserdatabyId(id);
        try {
            decryptedPassword = EncryptDecrypt.decrypt(userData.getPassword());
        } catch (Exception ex) {
            decryptedPassword = userData.getPassword();
        }
        model.addAttribute("id", id);
        model.addAttribute("userData", userData);
        List<Org> orgList = orgService.getOrgList();
        List<SubOrg> subOrgList = subOrgService.getSubOrgList(httpServletRequest);
        model.addAttribute("orgList", orgList);
        model.addAttribute("subOrgList", subOrgList);
        model.addAttribute(Constants.ORG_ID, userData.getOrg().getId());
        model.addAttribute(Constants.SUB_ORG_ID, userData.getSubOrgId());
        model.addAttribute("decryptedPassword", decryptedPassword);
        return "user/edit";
    }

    /*edit user by id API*/
    @PutMapping("/user/update")
    public String update(HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        Boolean status = userService.updateUser(httpServletRequest);
        if (status) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.SUCCESS);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "User updated successfully");
        } else {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "Email Already exist");
        }
        String referer = httpServletRequest.getHeader(Constants.REFERER);
        return "redirect:" + referer;
    }

    /*show user details in disabled form*/
    @GetMapping("/user/show/{id}")
    public String show(@PathVariable(value = "id") Integer id, Model model, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        AdminUser userData = userService.getUserdatabyId(id);
        model.addAttribute("id", id);
        model.addAttribute("userData", userData);
        List<Org> orgList = orgService.getOrgList();
        List<SubOrg> subOrgList = subOrgService.getSubOrgList(httpServletRequest);
        model.addAttribute("orgList", orgList);
        model.addAttribute("subOrgList", subOrgList);
        model.addAttribute(Constants.ORG_ID, userData.getOrg().getId());
        model.addAttribute(Constants.SUB_ORG_ID, userData.getSubOrgId());
        return "user/show";
    }

    /* @GetMapping("/user/delete/{id}")
     public String delete(@PathVariable(value = "id") Integer id, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
         if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
             return "redirect:/login";
         }
         Boolean status = userService.delete(id);

         if (status) {
             redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.SUCCESS);
             redirectAttributes.addFlashAttribute(Constants.MESSAGE, "User deleted successfully");
         } else {
             redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
             redirectAttributes.addFlashAttribute(Constants.MESSAGE, "Something went wrong, Please try again");
         }
         String referer = httpServletRequest.getHeader(Constants.REFERER);
         return "redirect:" + referer;
     }
 */
    @GetMapping("/user/setActivation/{id}/{value}")
    public String setActivation(@PathVariable(value = "id") Integer id, @PathVariable(value = "value") Integer value, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        Boolean status = userService.setActivation(id, httpServletRequest, value);
        if (status) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.SUCCESS);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "Status changed successfully");
        } else {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "Something went wrong, Please try again");
        }
        String referer = httpServletRequest.getHeader(Constants.REFERER);
        return "redirect:" + referer;
    }
}
