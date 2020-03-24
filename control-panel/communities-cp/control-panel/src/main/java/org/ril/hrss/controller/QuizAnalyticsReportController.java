package org.ril.hrss.controller;

import org.ril.hrss.service.analytics.QuizAnalyticsReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class QuizAnalyticsReportController {
    @Autowired
    QuizAnalyticsReportService analyticsReportService;

    @GetMapping("/quiz-report")
    public String index(Model model, HttpServletRequest httpServletRequest) {
        return analyticsReportService.customizedQuizReports(httpServletRequest, model);
    }

    @PostMapping("/customised-quiz-report")
    public String filterIndex(Model model, HttpServletRequest httpServletRequest) {
        return analyticsReportService.customizedQuizReports(httpServletRequest, model);
    }

}
