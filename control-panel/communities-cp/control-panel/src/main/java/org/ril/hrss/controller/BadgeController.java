package org.ril.hrss.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ril.hrss.service.gamification.BadgeService;
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
public class BadgeController {

    @Autowired
    BadgeService badgeService;


    @GetMapping("/badges")
    public String index(Model model, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        return badgeService.index(httpServletRequest, model);
    }

    @GetMapping("/badges/create")
    public String create(HttpServletRequest httpServletRequest) {
        return badgeService.createModel(httpServletRequest);
    }

    @GetMapping("/badges/{badge}/edit")
    public String edit(@PathVariable("badge") Integer id, Model model, HttpServletRequest httpServletRequest) {
        return badgeService.editModel(httpServletRequest, id, model);
    }

    @PutMapping("/badges/update")
    public String update(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, @RequestParam("badgeImg") MultipartFile file) throws JsonProcessingException {
        return badgeService.badgeUpdate(httpServletRequest, file, redirectAttributes);
    }

}
