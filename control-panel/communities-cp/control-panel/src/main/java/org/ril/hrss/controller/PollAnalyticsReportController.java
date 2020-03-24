package org.ril.hrss.controller;

import org.ril.hrss.service.analytics.PollAnalyticsReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PollAnalyticsReportController {
    @Autowired
    PollAnalyticsReportService analyticsReportService;

    @GetMapping("/poll-report")
    public String index(Model model, HttpServletRequest httpServletRequest) {
        return analyticsReportService.customizedPollReports(httpServletRequest, model);
    }

    @PostMapping("/customised-poll-report")
    public String filterIndex(Model model, HttpServletRequest httpServletRequest) {
        return analyticsReportService.customizedPollReports(httpServletRequest, model);
    }

}
