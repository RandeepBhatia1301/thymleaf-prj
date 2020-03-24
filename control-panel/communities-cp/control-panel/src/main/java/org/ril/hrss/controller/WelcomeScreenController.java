package org.ril.hrss.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ril.hrss.service.gamification.WelcomeScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class WelcomeScreenController {
    @Autowired
    WelcomeScreenService welcomeScreenService;

    @GetMapping("/welcomescreen")
    public String index(Model model, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        return welcomeScreenService.index(model, httpServletRequest);
    }

    @GetMapping("/welcomescreen/edit/{id}")
    public String edit(@PathVariable(value = "id") Integer id, Model model, HttpServletRequest httpServletRequest) {
        return welcomeScreenService.editModel(id, model, httpServletRequest);
    }

    @PutMapping("/welcomescreen/update")
    public String update(HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes, @RequestParam(value = "welcomeImg", required = false) MultipartFile file) {
        return welcomeScreenService.updateSetParam(httpServletRequest, model, redirectAttributes, file);
    }
}
