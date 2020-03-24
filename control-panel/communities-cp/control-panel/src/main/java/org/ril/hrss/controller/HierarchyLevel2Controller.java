package org.ril.hrss.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ril.hrss.service.category_hierarchy.HierarchyLevel2Service;
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
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HierarchyLevel2Controller {
    @Autowired
    HierarchyLevel2Service hierarchyLevel2Service;

    @GetMapping("/aoi")
    public String index(Model model, HttpServletRequest httpServletRequest, HttpSession httpSession) {
        return hierarchyLevel2Service.index(model, httpServletRequest, httpSession);
    }

    @GetMapping(value = {"/aoi/create", "/aoi/create/{tagName}"})
    public String create(@PathVariable(value = "tagName", required = false) String tagName, HttpServletRequest httpServletRequest, Model model) {
        return hierarchyLevel2Service.createModel(model, httpServletRequest, tagName);
    }

    @PostMapping("/aoi/store")
    public String store(@RequestParam("coverImg") MultipartFile files[], HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes, @RequestParam(value = "level_value[]", required = false) List<String> adminEmails, @RequestParam(value = "level_value_SME[]", required = false) List<String> smeEmails) throws Exception {
        return hierarchyLevel2Service.createParam(httpServletRequest, redirectAttributes, files, adminEmails, smeEmails);
    }

    @GetMapping("/aoi/edit/{id}")
    public String edit(@PathVariable(value = "id") Integer id, Model model, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        return hierarchyLevel2Service.editModel(model, httpServletRequest, id);
    }

    @PostMapping("/aoi/update")
    public String update(@RequestParam(value = "coverImg", required = false) MultipartFile coverImg, HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes, @RequestParam(value = "level_value[]", required = false) List<String> adminEmails, @RequestParam(value = "level_value_SME[]", required = false) List<String> smeEmails) throws Exception {
        return hierarchyLevel2Service.updateParam(httpServletRequest, redirectAttributes, coverImg, adminEmails, smeEmails);
    }

    @GetMapping("/ElasticIndex/Refresh/AOI")
    public String elasticIndexRefresh(HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes) {
        return hierarchyLevel2Service.elasticIndexRefresh(httpServletRequest, redirectAttributes);
    }

    @GetMapping("/hierarchy/setActivation/{id}/{value}")
    public String setActivation(@PathVariable(value = "id") Integer id, @PathVariable(value = "value") Integer value, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        return hierarchyLevel2Service.setActivation(id, httpServletRequest, value, redirectAttributes);
    }
}
