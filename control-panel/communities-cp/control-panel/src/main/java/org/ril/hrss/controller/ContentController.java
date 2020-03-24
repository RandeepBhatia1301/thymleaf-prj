package org.ril.hrss.controller;

import org.ril.hrss.service.content.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ContentController {

    @Autowired
    ContentService contentService;

    @GetMapping("/content")
    public String content(Model model, HttpServletRequest httpServletRequest, HttpSession httpSession) {
        return contentService.index(model, httpServletRequest, httpSession);
    }

    @GetMapping("/content/create")
    public String create(HttpServletRequest httpServletRequest) {
        return contentService.createModel(httpServletRequest);
    }

    @PostMapping("/content/store")
    public String store(HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes) {
        return contentService.createContent(httpServletRequest, redirectAttributes);
    }

    @GetMapping("/content/edit/{id}")
    public String edit(@PathVariable(value = "id") Integer Id, Model model, HttpServletRequest httpServletRequest) {
        return contentService.editModel(httpServletRequest, model, Id);
    }

    @PutMapping("/content/update")
    public String update(HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes) {
        return contentService.update(httpServletRequest, redirectAttributes);
    }

    @GetMapping("/content/setActivation/{id}/{value}")
    public String setActivation(@PathVariable(value = "id") Integer id, @PathVariable(value = "value") Integer value, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        return contentService.activation(id, httpServletRequest, value, redirectAttributes);
    }

    @RequestMapping(path = "/default/content/{id}", method = RequestMethod.GET)
    public String defaultValue(@PathVariable(value = "id") Integer id, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        return contentService.setDefaultValue(id, httpServletRequest, redirectAttributes);
    }
}
