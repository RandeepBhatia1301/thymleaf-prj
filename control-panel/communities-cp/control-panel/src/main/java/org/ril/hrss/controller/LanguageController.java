package org.ril.hrss.controller;

import org.ril.hrss.service.language.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @RequestMapping("/language")
    public String language(HttpServletRequest httpServletRequest, Model model) {
        return languageService.index(httpServletRequest, model);
    }

    @GetMapping("/language/create")
    public String create(Model model) {
        return languageService.createModel(model);
    }

    @PostMapping("/language/store")
    public String store(@RequestParam("uploadFile") MultipartFile file, HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes) throws Exception {
        return languageService.createLanguageSetParam(file, httpServletRequest, redirectAttributes);
    }

    @GetMapping("/language/edit/{id}")
    public String edit(@PathVariable(value = "id") Integer id, Model model, HttpServletRequest httpServletRequest) throws IOException {
        return languageService.editModel(model, httpServletRequest, id);
    }

    @PutMapping("/language/update")
    public String update(HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes) throws Exception {
        return languageService.updateLanguageSetParam(httpServletRequest, redirectAttributes);
    }

    @GetMapping("/language/setActivation/{id}/{value}")
    public String setActivation(@PathVariable(value = "id") Integer id, @PathVariable(value = "value") Integer value, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        return languageService.setActivationParam(id, httpServletRequest, value, redirectAttributes);
    }

    @RequestMapping(path = "/default/{id}", method = RequestMethod.GET)
    public String defaultValue(@PathVariable(value = "id") Integer id, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        return languageService.setDefaultValue(id, httpServletRequest, redirectAttributes);
    }
}
