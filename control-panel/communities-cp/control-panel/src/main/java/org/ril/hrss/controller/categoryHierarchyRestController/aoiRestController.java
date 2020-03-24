package org.ril.hrss.controller.categoryHierarchyRestController;

import org.ril.hrss.service.category_hierarchy.HierarchyLevel2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@RestController
public class aoiRestController {
    @Autowired
    HierarchyLevel2Service hierarchyLevel2Service;

    @GetMapping("/aoi/setActivation/{id}/{value}")
    public String setActivation(@PathVariable(value = "id") Integer id, @PathVariable(value = "value") Integer value, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        return hierarchyLevel2Service.setActivation(id, httpServletRequest, value, redirectAttributes);
    }
}
