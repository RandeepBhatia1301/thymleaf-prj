package org.ril.hrss.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ril.hrss.service.category_hierarchy.HierarchyLevel1Service;
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
import java.util.List;

@Controller
public class HierarchyLevel1Controller {
    @Autowired
    private HierarchyLevel1Service hierarchyLevel1Service;

    @GetMapping("/community")
    public String index(HttpServletRequest httpServletRequest, Model model) {
        return hierarchyLevel1Service.index(httpServletRequest, model);
    }

    @GetMapping("/hybridCommunity")
    public String indexHybrid(HttpServletRequest httpServletRequest, Model model) {
        return hierarchyLevel1Service.indexHybrid(httpServletRequest, model);
    }

    @GetMapping("community/create")
    public String create(HttpServletRequest httpServletRequest, Model model) {
        return hierarchyLevel1Service.createModel(httpServletRequest, model);
    }

    @GetMapping("community/hybrid/create")
    public String createHybrid(HttpServletRequest httpServletRequest, Model model) {
        return hierarchyLevel1Service.createHybridModel(httpServletRequest, model);
    }

    @PostMapping("/community/store")
    public String store(@RequestParam(value = "coverImg", required = false) MultipartFile coverImg, @RequestParam(value = "bannerImg", required = false) MultipartFile bannerImg, @RequestParam(value = "SvgImg", required = false) MultipartFile svgImg, HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes, @RequestParam(value = "level_value[]", required = false) List<String> adminEmails, @RequestParam(value = "level_value_SME[]", required = false) List<String> smeEmails, @RequestParam(value = "department[]", required = false) List<Integer> dept) {
        return hierarchyLevel1Service.create(httpServletRequest, adminEmails, smeEmails, coverImg, bannerImg, svgImg, redirectAttributes, dept);
    }

    @GetMapping("/community/edit/{id}")
    public String edit(@PathVariable(value = "id") Integer id, Model model, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        return hierarchyLevel1Service.editModel(httpServletRequest, model, id);
    }

    @GetMapping("/community/hybrid/edit/{id}")
    public String editHybrid(@PathVariable(value = "id") Integer id, Model model, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        return hierarchyLevel1Service.editHybridModel(httpServletRequest, model, id);
    }

    @PostMapping("/community/update")
    public String update(@RequestParam(value = "coverImg", required = false) MultipartFile coverImg, @RequestParam(value = "bannerImg", required = false) MultipartFile bannerImg, @RequestParam(value = "SvgImg", required = false) MultipartFile svgImg, HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes, @RequestParam(value = "level_value[]", required = false) List<String> adminEmails, @RequestParam(value = "level_value_SME[]", required = false) List<String> smeEmail, @RequestParam(value = "department[]", required = false) List<Integer> dept) {
        return hierarchyLevel1Service.update(httpServletRequest, coverImg, bannerImg, svgImg, adminEmails, smeEmail, redirectAttributes, dept);
    }

    @PostMapping("/community/hybrid/update")
    public String updateHybrid(@RequestParam(value = "coverImg", required = false) MultipartFile coverImg, @RequestParam(value = "bannerImg", required = false) MultipartFile bannerImg, @RequestParam(value = "SvgImg", required = false) MultipartFile svgImg, @RequestParam(value = "suborgId[]", required = false) List<Integer> suborgIds, HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes, @RequestParam(value = "level_value[]", required = false) List<String> adminEmails, @RequestParam(value = "level_value_SME[]", required = false) List<String> smeEmail) {
        return hierarchyLevel1Service.updateHybrid(httpServletRequest, coverImg, bannerImg, svgImg, suborgIds, adminEmails, smeEmail, redirectAttributes);
    }

    @GetMapping("/category/setActivation/{id}/{value}")
    public String setActivation(@PathVariable(value = "id") Integer id, @PathVariable(value = "value") Integer value, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        return hierarchyLevel1Service.setActivation(id, httpServletRequest, value, redirectAttributes);
    }

    @GetMapping("/ElasticIndex/Refresh")
    public String elasticIndexRefresh(HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes) {
        return hierarchyLevel1Service.elasticIndexRefresh(httpServletRequest, redirectAttributes);
    }

    @PostMapping("/community/hybrid/store")
    public String createHybridCommunity(@RequestParam(value = "coverImg", required = false) MultipartFile coverImg, @RequestParam(value = "bannerImg", required = false) MultipartFile bannerImg, @RequestParam(value = "SvgImg", required = false) MultipartFile svgImg, HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes, @RequestParam(value = "level_value[]", required = false) List<String> adminEmails, @RequestParam(value = "suborgId[]", required = false) List<Integer> suborgIds, @RequestParam(value = "level_value_SME[]", required = false) List<String> smeEmails) {
        return hierarchyLevel1Service.createHybridCommunity(httpServletRequest, suborgIds, redirectAttributes, adminEmails, smeEmails, coverImg, bannerImg, svgImg);
    }

    @GetMapping("/suborg/hybridCommunity")
    public String suborgHybridCommunity(HttpServletRequest httpServletRequest, Model model) {
        return hierarchyLevel1Service.indexSubOrgHybrid(httpServletRequest, model);
    }

}
