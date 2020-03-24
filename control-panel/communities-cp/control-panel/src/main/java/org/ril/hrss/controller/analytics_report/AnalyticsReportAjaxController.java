package org.ril.hrss.controller.analytics_report;

import org.ril.hrss.model.category_hierarchy.CategoryHierarchy;
import org.ril.hrss.service.analytics.AnalyticsReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class AnalyticsReportAjaxController {
    @Autowired
    AnalyticsReportService analyticsReportService;

    @GetMapping("/filterAOI/{id}")
    public List<CategoryHierarchy> filterAOI(@PathVariable(value = "id", required = false) Integer id, HttpServletRequest httpServletRequest) {
        return analyticsReportService.getCommunityList(httpServletRequest, id);
    }

}