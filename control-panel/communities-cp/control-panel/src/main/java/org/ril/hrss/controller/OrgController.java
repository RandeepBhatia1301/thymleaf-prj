package org.ril.hrss.controller;

import org.ril.hrss.repository.OrgContentTypeRepository;
import org.ril.hrss.service.org_management.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class OrgController {

    @Autowired
    OrgService orgService;

    @Autowired
    OrgContentTypeRepository orgContentTypeRepository;

    @GetMapping(value = {"/org"})
    public String index(@RequestParam(value = "searchQuery", required = false) String query, Model model, HttpServletRequest httpServletRequest) {
        return orgService.index(httpServletRequest, model, query);
    }

    @GetMapping("/org/create")
    public String create(Model model, HttpServletRequest httpServletRequest) {
        return orgService.createModel(httpServletRequest, model);
    }

    @PostMapping("/org/store")
    public String store(@RequestParam("content[]") List<Integer> contentsIds, HttpServletRequest httpServletRequest, @RequestParam("orgImg") MultipartFile file, RedirectAttributes redirectAttributes) {
        return orgService.createOrgHandling(httpServletRequest, contentsIds, file, redirectAttributes);
    }

    @GetMapping("/org/show/{orgId}")
    public String show(@PathVariable Integer orgId, Model model, HttpServletRequest httpServletRequest) {
        return orgService.show(orgId, httpServletRequest, model);
    }

    @GetMapping("/org/edit/{orgId}")
    public String edit(@PathVariable Integer orgId, Model model, HttpServletRequest httpServletRequest) {
        return orgService.edit(orgId, httpServletRequest, model);
    }

    @PutMapping("/org/update")
    public String update(@RequestParam("content[]") List<Integer> contentsIds, HttpServletRequest httpServletRequest, @RequestParam(value = "orgImg", required = false) MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {
        return orgService.updateOrgHandling(httpServletRequest, contentsIds, file, redirectAttributes);
    }

    @GetMapping("/org/setActivation/{id}/{value}")
    public String setActivation(@PathVariable(value = "id") Integer id, @PathVariable(value = "value") Integer value, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) throws Exception {
        return orgService.setActivation(id, value, httpServletRequest, redirectAttributes);
    }
}


