package org.ril.hrss.controller;

import org.ril.hrss.service.analytics.AnalyticsReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AnalyticsReportController {
    @Autowired
    AnalyticsReportService analyticsReportService;


    @GetMapping("/report")
    public String index(Model model, HttpServletRequest httpServletRequest) {
        return analyticsReportService.indexCommunities(httpServletRequest, model);
    }

    @PostMapping("/filter_report")
    public String filterIndex(Model model, HttpServletRequest httpServletRequest) {
        return analyticsReportService.indexCommunities(httpServletRequest, model);
    }

    @GetMapping("/report-blog")
    public String indexBlog(Model model, HttpServletRequest httpServletRequest) {
        return analyticsReportService.indexBlog(httpServletRequest, model);
    }

    @PostMapping("/report-blog-post")
    public String indexBlogPost(Model model, HttpServletRequest httpServletRequest) {
        return analyticsReportService.indexBlog(httpServletRequest, model);
    }

    @GetMapping("/report-discussion")
    public String indexDiscussion(Model model, HttpServletRequest httpServletRequest) {
        return analyticsReportService.indexDiscussion(httpServletRequest, model);
    }

    @PostMapping("/report-discussion-post")
    public String indexDiscussionPost(Model model, HttpServletRequest httpServletRequest) {
        return analyticsReportService.indexDiscussion(httpServletRequest, model);
    }


}
