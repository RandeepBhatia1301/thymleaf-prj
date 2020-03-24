package org.ril.hrss.controller;

import org.ril.hrss.model.tag.Tag;
import org.ril.hrss.service.sub_org_features.TagsService;
import org.ril.hrss.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TagsController {
    @Autowired
    private TagsService tagsService;

    @GetMapping("/tags")
    public String index(HttpServletRequest httpServletRequest, HttpSession httpSession, Model model) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        Integer orgId = (Integer) httpSession.getAttribute(Constants.ORG_ID);
        Page<Tag> page = tagsService.getTags(orgId, 50, httpServletRequest);
        Map<String, Long> map = new HashMap<>();
        for (Tag tag : page) {
            map.put(tag.getTitle(), tagsService.getTagFrequency(tag.getId()));
        }
        model.addAttribute("tagList", map);
        model.addAttribute(Constants.PAGE, page);

        return "tag/index";
    }
}
