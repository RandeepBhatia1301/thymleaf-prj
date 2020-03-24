package org.ril.hrss.controller;

import org.ril.hrss.service.moderation.ContentModerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ModerationController {
    @Autowired
    ContentModerationService contentModerationService;

    @GetMapping("/moderation")
    public String index(Model model, HttpServletRequest httpServletRequest) {
        return contentModerationService.index(model, httpServletRequest);
    }

    @GetMapping(value = {"/moderation/content/{categoryName}", "/moderation/content/{contentId}/{categoryName}"})
    public String content(@PathVariable(value = "categoryName", required = false) String categoryName, HttpServletRequest httpServletRequest, Model model, @PathVariable(value = "contentId", required = false) Integer contentId) {

        return contentModerationService.contentApprovalEdit(httpServletRequest, model, categoryName, contentId);
    }

    @PostMapping("/moderation/content/store")
    public String storeContent(HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes) {
        return contentModerationService.storeModeration(httpServletRequest, redirectAttributes);
    }

}
