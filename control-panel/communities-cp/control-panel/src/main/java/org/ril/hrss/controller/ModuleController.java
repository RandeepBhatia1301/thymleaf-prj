/*
package com.ril.svc.controller;

import Module;
import com.ril.svc.service.ModuleService;
import Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ModuleController {

    @Autowired
    ModuleService moduleService;

    @GetMapping("/modules")
    public String index(Model model, HttpServletRequest httpServletRequest) {
        return moduleService.index(httpServletRequest, model);
    }

    @GetMapping("/modules/create")
    public String create(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        return "module/create";
    }

    @PostMapping("/modules/store")
    public String store(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        Module module = new Module();
        module.setName(httpServletRequest.getParameter(Constants.NAME));
        module.setDescription(httpServletRequest.getParameter("description"));
        module.setStatus(1);
        module = moduleService.create(module);
        if (module.getId() != null) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.SUCCESS);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "Module Added successfully");
        } else {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "Something went wrong");
        }

        return "redirect:/modules";
    }

    @GetMapping("/modules/show/{id}")
    public String show(@PathVariable("id") Integer id, Model model, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        Module module = moduleService.find(id);
        model.addAttribute("module", module);
        return "module/show";
    }

    @GetMapping("modules/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        Module module = moduleService.find(id);
        model.addAttribute("module", module);
        return "module/edit";
    }

    @PutMapping("modules/update")
    public String update(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        Boolean status = false;
        Module module;
        try {
            Integer id = Integer.valueOf(httpServletRequest.getParameter("id"));
            module = moduleService.find(id);
            module.setName(httpServletRequest.getParameter(Constants.NAME));
            module.setDescription(httpServletRequest.getParameter("description"));
            moduleService.update(module);
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (status) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.SUCCESS);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "Module edited successfully");
        } else {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "Something went wrong");
        }

        String referer = httpServletRequest.getHeader(Constants.REFERER);
        return "redirect:" + referer;
    }

    @GetMapping("/module/setActivation/{id}/{value}")
    public String setActivation(@PathVariable(value = "id") Integer id, @PathVariable(value = "value") Integer value, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        Boolean status = false;
        Module module;
        try {
            module = moduleService.find(id);
            module.setStatus(value);
            moduleService.update(module);
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    @DeleteMapping("modules/{module}")
    public String delete(@PathVariable("module") Integer id) {
        return "redirect:/modules";
    }
}
*/
