package org.ril.hrss.controller;

import org.ril.hrss.service.roles_matrix.RolesMatrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RolesMatrixController {
    @Autowired
    RolesMatrixService rolesMatrixService;

    @GetMapping("/roles")
    public String index(Model model, HttpServletRequest httpServletRequest) {
        return rolesMatrixService.rolesList(httpServletRequest, model);
    }


    @GetMapping("/role/view-permission/{id}")
    public String viewPermission(@PathVariable(value = "id") Integer Id, Model model, HttpServletRequest httpServletRequest) {
        return rolesMatrixService.viewPermissionByRoleId(Id, httpServletRequest, model);
    }

    @GetMapping("/help/{id}")
    public String help(@PathVariable(value = "id") Integer Id, Model model, HttpServletRequest httpServletRequest) {
        model.addAttribute("scrollId", Id);
        return "static-page";
    }


}
