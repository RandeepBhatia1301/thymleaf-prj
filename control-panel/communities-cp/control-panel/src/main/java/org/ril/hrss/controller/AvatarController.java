package org.ril.hrss.controller;

import org.ril.hrss.service.gamification.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AvatarController {
    @Autowired
    AvatarService avatarService;


    @GetMapping("/avatar")
    public String index(Model model, HttpServletRequest httpServletRequest) {
        return avatarService.findAll(httpServletRequest, model);
    }

    @GetMapping(value = {"/avatar/edit/{id}", "/avatar/edit/{id}/{genderId}"})
    public String edit(@PathVariable(value = "id") Integer id, @PathVariable(value = "genderId", required = false) Integer genderId, Model model, HttpServletRequest httpServletRequest) {
        return avatarService.editModel(httpServletRequest, model, genderId, id);
    }

    @GetMapping("/avatar/create/{avatarId}")
    public String create(HttpServletRequest httpServletRequest, @PathVariable(value = "avatarId") Integer avatarId, Model model) {
        return avatarService.createModel(httpServletRequest, model, avatarId);
    }

    @PostMapping("/avatar/store")
    public String store(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, @RequestParam("avatarImg") MultipartFile file) {

        return avatarService.create(httpServletRequest, file, redirectAttributes);
    }

    @GetMapping("/avatar/setActivation/{id}/{value}")
    public String setActivation(@PathVariable(value = "id") Integer id, @PathVariable(value = "value") Integer value, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        return avatarService.setActivation(id, httpServletRequest, value, redirectAttributes);
    }

    @GetMapping("/avatar/category/create")
    public String createCategory(HttpServletRequest httpServletRequest) {
        return avatarService.createCategoryModel(httpServletRequest);
    }

    @PostMapping("/avatar/category/store")
    public String storeCategory(HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes, @RequestParam("avatarCategoryImage") MultipartFile file) {
        return avatarService.createCategory(httpServletRequest, file, redirectAttributes);
    }

    @GetMapping("/avatar/category/setActivation/{id}/{value}")
    public String setCategoryActivation(@PathVariable(value = "id") Integer id, @PathVariable(value = "value") Integer value, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {

        return avatarService.setCategoryActivation(id, httpServletRequest, value, redirectAttributes);
    }
}
