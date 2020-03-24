package org.ril.hrss.controller;


import org.ril.hrss.service.category_hierarchy.CategoryTypeService;
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
public class CategoryTypeController {
    @Autowired
    private CategoryTypeService categoryTypeService;

    @GetMapping("/categoryType")
    public String index(Model model, HttpServletRequest httpServletRequest) {
        return categoryTypeService.index(httpServletRequest, model);
    }

    @GetMapping("/categoryType/create")
    public String create(HttpServletRequest httpServletRequest) {
        return categoryTypeService.createModel(httpServletRequest);
    }

    @PostMapping("/categoryType/store")
    public String store(HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes) {
        return categoryTypeService.create(httpServletRequest, redirectAttributes);
    }

    @GetMapping("/categoryType/edit/{id}")
    public String edit(@PathVariable(value = "id") Integer id, Model model, HttpServletRequest httpServletRequest) {
        return categoryTypeService.editModel(httpServletRequest, model, id);
    }

    @PutMapping("/categoryType/update")
    public String update(HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes) {
        return categoryTypeService.updateCategory(httpServletRequest, redirectAttributes);

    }
}
