package org.ril.hrss.controller;

import org.ril.hrss.service.analytics.AnalyticsEventReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AnalyticsEventReportController {
    @Autowired
    AnalyticsEventReportService analyticsEventReportService;


    @GetMapping("/event-report")
    public String index(Model model, HttpServletRequest httpServletRequest) {
        return analyticsEventReportService.index(httpServletRequest, model);
    }

    @PostMapping("/event_filter_report")
    public String filterIndex(Model model, HttpServletRequest httpServletRequest) {
        return analyticsEventReportService.index(httpServletRequest, model);
    }
}
