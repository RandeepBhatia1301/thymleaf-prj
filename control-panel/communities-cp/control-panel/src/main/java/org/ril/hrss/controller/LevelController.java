package org.ril.hrss.controller;

import org.ril.hrss.service.gamification.GamificationLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LevelController {
    @Autowired
    private GamificationLevelService gamificationLevelService;

    @GetMapping("/level")
    public String index(Model model, HttpServletRequest httpServletRequest) {
        return gamificationLevelService.index(httpServletRequest, model);
    }

    @GetMapping("/level/edit/{id}")
    public String edit(@PathVariable(value = "id") Integer id, Model model, HttpServletRequest httpServletRequest) {
        return gamificationLevelService.edit(httpServletRequest, model, id);
    }

    @PutMapping("/level/update")
    public String update(HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes) throws Exception {
        return gamificationLevelService.update(httpServletRequest, model, redirectAttributes);
    }

    @GetMapping("/level/setActivation/{id}/{value}")
    public String setActivation(@PathVariable(value = "id") Integer id, @PathVariable(value = "value") Integer value, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) throws Exception {
        return gamificationLevelService.setActivation(id, value, httpServletRequest, redirectAttributes);
    }
}
