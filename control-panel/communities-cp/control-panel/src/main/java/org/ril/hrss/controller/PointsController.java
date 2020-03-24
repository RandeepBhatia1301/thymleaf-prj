package org.ril.hrss.controller;

import org.ril.hrss.service.gamification.PointCategoryService;
import org.ril.hrss.service.gamification.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PointsController {
    @Autowired
    private PointsService pointsService;

    @Autowired
    private PointCategoryService pointCategoryService;


    @GetMapping(value = {"/points"})
    public String index(Model model, HttpServletRequest httpServletRequest) {
        return pointsService.index(httpServletRequest, model);
    }

    @GetMapping("/points/create")
    public String create(Model model, HttpServletRequest httpServletRequest) {
        return pointsService.createModel(httpServletRequest, model);
    }

    @PostMapping("/point/store")
    public String store(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        return pointsService.create(httpServletRequest, redirectAttributes);
    }

    @GetMapping("/activity/edit/{id}")
    public String edit(@PathVariable(value = "id") Integer id, Model model, HttpServletRequest httpServletRequest) {
        return pointsService.editModel(httpServletRequest, model, id);
    }

    @PutMapping("/activity/update")
    public String update(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        return pointsService.updateActivity(httpServletRequest, redirectAttributes);
    }

    @GetMapping("/category/create")
    public String createCategory(HttpServletRequest httpServletRequest) {
        return pointsService.createCategoryModel(httpServletRequest);
    }

    @PostMapping("/category/store")
    public String storeCategory(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        return pointCategoryService.createCategory(httpServletRequest, redirectAttributes);
    }

    @PostMapping("/update/multiplier")
    public String changeMultiplier(HttpServletRequest httpServletRequest) {
        return pointsService.updatePointsBaseMultiplier(httpServletRequest);
    }
}



