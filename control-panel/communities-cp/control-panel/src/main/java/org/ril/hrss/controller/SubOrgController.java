package org.ril.hrss.controller;

import org.ril.hrss.service.org_management.CountryService;
import org.ril.hrss.service.org_management.OrgService;
import org.ril.hrss.service.rest_api_services.UploadClient;
import org.ril.hrss.service.sub_org_management.SubOrgService;
import org.ril.hrss.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
public class SubOrgController {

    @Autowired
    SubOrgService subOrgService;

    @Autowired
    OrgService orgService;

    @Autowired
    CountryService countryService;

    @Autowired
    UploadClient uploadClient;

    @GetMapping("/sub-org")
    public String index(@RequestParam(value = "searchQuery", required = false) String query, Model model, HttpServletRequest request) {
        return subOrgService.index(request, model, query);
    }

    /*show creation page*/
    @GetMapping("/subOrg/create")
    public String create(Model model, HttpServletRequest httpServletRequest) {
        return subOrgService.createModel(httpServletRequest, model);
    }

    /*add sub org API*/
    @PostMapping("/subOrg/store")
    public String store(@RequestParam Map<String, String> reqParam, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, @RequestParam("subOrgImg") MultipartFile file) throws Exception {

        return subOrgService.createSubOrgHandling(reqParam, file, httpServletRequest, redirectAttributes);
    }

    @GetMapping("/subOrg/edit/{subOrgId}")
    public String edit(@PathVariable Integer subOrgId, Model model, HttpServletRequest httpServletRequest) {
        return subOrgService.editModel(httpServletRequest, model, subOrgId);
    }

    @PutMapping("/subOrg/update")
    public String update(@RequestParam Map<String, String> reqParam, HttpServletRequest httpServletRequest, @RequestParam("subOrgImg") MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {

        return subOrgService.updateOrgHandling(reqParam, file, httpServletRequest, redirectAttributes);
    }

    @GetMapping("/subOrg/show/{subOrgId}")
    public String show(@PathVariable Integer subOrgId, Model model, HttpServletRequest httpServletRequest) {
        return subOrgService.showModel(httpServletRequest, model, subOrgId);

    }

    @GetMapping("/subOrg/setActivation/{id}/{value}")
    public String setActivation(@PathVariable(value = "id") Integer id, @PathVariable(value = "value") Integer value, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }

        Boolean status = subOrgService.setActivation(id, httpServletRequest, value);

        if (status) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.SUCCESS);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, Constants.STATUS_UPDATE_SUCCESS);
        } else {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
        }
        String referer = httpServletRequest.getHeader(Constants.REFERER);
        return "redirect:" + referer;
    }
}
