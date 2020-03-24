package org.ril.hrss.controller;

import org.ril.hrss.model.product_hierarchy.OrgProductHierarchy;
import org.ril.hrss.model.product_hierarchy.ProductHierarchy;
import org.ril.hrss.model.product_hierarchy.SubOrgProductHierarchy;
import org.ril.hrss.service.product_hierarchy.OrgProductHierarchyService;
import org.ril.hrss.service.product_hierarchy.ProductHierarchyService;
import org.ril.hrss.service.product_hierarchy.SubOrgProductHierarchyService;
import org.ril.hrss.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class ProductHierarchyController {
    @Autowired
    private ProductHierarchyService productHierarchyService;

    @Autowired
    private OrgProductHierarchyService orgProductHierarchyService;

    @Autowired
    private SubOrgProductHierarchyService subOrgProductHierarchyService;

    @RequestMapping("/product-hierarchy")
    public String productHierarchy(Model model, HttpServletRequest httpServletRequest, HttpSession httpSession) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        List<ProductHierarchy> productHierarchies;
        Integer orgId = (Integer) httpSession.getAttribute(Constants.ORG_ID);
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == "SAAS ADMIN") {
            productHierarchies = productHierarchyService.getProductData();
            model.addAttribute("productData", productHierarchies);
        } else if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.ORG_ADMIN) {
            List<OrgProductHierarchy> orgProductHierarchies = orgProductHierarchyService.getProductData(orgId);
            model.addAttribute("productData", orgProductHierarchies);
        } else if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SUB_ORG_ADMIN) {
            Integer subOrgId = (Integer) httpSession.getAttribute(Constants.SUB_ORG_ID);
            List<SubOrgProductHierarchy> subOrgProductHierarchies = subOrgProductHierarchyService.getProductData(subOrgId);
            model.addAttribute("productData", subOrgProductHierarchies);
        }
        return "product-hierarchy/product-hierarchy";
    }

    /*show edit hierarchy page*/
    @GetMapping("/product-hierarchy/edit/{id}")
    public String edit(@PathVariable(value = "id") Integer id, Model model, HttpServletRequest httpServletRequest, HttpSession httpSession) throws IOException {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        String productHierarchyName = null;
        String productHierarchyDesc = null;
        Integer level = null;
        ProductHierarchy productHierarchy = new ProductHierarchy();
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == "SAAS ADMIN") {
            productHierarchy = productHierarchyService.getProductHierarchyDataById(id);
            productHierarchyName = productHierarchy.getName();
            productHierarchyDesc = productHierarchy.getDescription();
            level = productHierarchy.getLevel();
        } else if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.ORG_ADMIN) {
            /*get setting for org view*/
            Integer orgId = (Integer) httpSession.getAttribute(Constants.ORG_ID);
            OrgProductHierarchy orgProductHierarchy = orgProductHierarchyService.getProductHierarchyDataById(id, orgId);
            productHierarchyName = orgProductHierarchy.getName();
            productHierarchyDesc = orgProductHierarchy.getDescription();
            level = productHierarchy.getLevel();

        } else if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SUB_ORG_ADMIN) {
             /*get setting for sub org view*/
            Integer subOrgId = (Integer) httpSession.getAttribute(Constants.SUB_ORG_ID);
            SubOrgProductHierarchy subOrgProductHierarchy = subOrgProductHierarchyService.getProductHierarchyDataById(id, subOrgId);
            productHierarchyName = subOrgProductHierarchy.getName();
            productHierarchyDesc = subOrgProductHierarchy.getDescription();
            level = subOrgProductHierarchy.getLevel();
        }
        model.addAttribute("id", id);
        model.addAttribute(Constants.NAME, productHierarchyName);
        model.addAttribute("description", productHierarchyDesc);
        model.addAttribute("level", level);
        model.addAttribute("URL", "/productHierarchy/update");
        model.addAttribute("method", "PUT");
        return "product-hierarchy/form";
    }

    /*edit product-hierarchy API*/
    @PutMapping("/productHierarchy/update")
    public String update(HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes, HttpSession httpSession) throws Exception {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }

        Boolean status = false;
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == "SAAS ADMIN") {
            status = productHierarchyService.updateProductHierarchy(httpServletRequest);

        } else if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.ORG_ADMIN) {
            /* setting updated in org-product mapping*/
            Integer orgId = (Integer) httpSession.getAttribute(Constants.ORG_ID);
            status = orgProductHierarchyService.updateProductHierarchy(orgId, httpServletRequest);

        } else if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SUB_ORG_ADMIN) {
            Integer subOrgId = (Integer) httpSession.getAttribute(Constants.SUB_ORG_ID);
            status = subOrgProductHierarchyService.updateProductHierarchy(subOrgId, httpServletRequest);
        }

        if (status) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.SUCCESS);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "Product Hierarchy updated successfully");
        } else {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "Please try again");
        }
        return "redirect:/product-hierarchy?v=467543557hr";
    }

    @GetMapping("/product/setActivation/{id}/{value}")
    public String setActivation(@PathVariable(value = "id") Integer id, @PathVariable(value = "value") Integer value, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, HttpSession httpSession) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        boolean status = false;
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == "SAAS ADMIN") {
            status = productHierarchyService.setActivation(id, httpServletRequest, value);

        } else if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.ORG_ADMIN) {

            Integer orgId = (Integer) httpSession.getAttribute(Constants.ORG_ID);

            status = orgProductHierarchyService.setActivation(id, httpServletRequest, value, orgId);

        } else if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SUB_ORG_ADMIN) {
            Integer subOrgId = (Integer) httpSession.getAttribute(Constants.SUB_ORG_ID);
            status = subOrgProductHierarchyService.setActivation(id, httpServletRequest, value, subOrgId);
        }
        if (status) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.SUCCESS);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "Status changed successfully");
        } else {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "Something went wrong, Please try again");
        }
        String referer = httpServletRequest.getHeader(Constants.REFERER);
        return "redirect:" + referer;
    }
}
