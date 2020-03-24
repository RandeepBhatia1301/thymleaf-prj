package org.ril.hrss.controller;

import org.ril.hrss.model.StopWord;
import org.ril.hrss.service.sub_org_features.StopWordService;
import org.ril.hrss.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class StopWordController {
    @Autowired
    StopWordService stopWordService;

    @GetMapping(value = {"/stopWord"})
    public String index(Model model, HttpServletRequest httpServletRequest) {
        return stopWordService.index(model, httpServletRequest);
    }

    @GetMapping("/stopWord/create")
    public String create(Model model, HttpServletRequest httpServletRequest) {
        return stopWordService.createModel(model, httpServletRequest);
    }

    @PostMapping("/stopWord/store")
    public String store(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, HttpSession httpSession) {

        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        Integer orgId = (Integer) httpSession.getAttribute(Constants.ORG_ID);
        Boolean status;
        status = stopWordService.create(httpServletRequest, orgId);
        if (status) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.SUCCESS);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "Stop word added successfully");
        } else {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "Something went wrong, Please try again");
        }
        return "redirect:/stopWord?v=467543557hr";
    }

    @GetMapping("/stopWord/edit/{id}")
    public String edit(@PathVariable(value = "id") Integer id, Model model, HttpServletRequest httpServletRequest, HttpSession httpSession) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }

        Integer orgId = (Integer) httpSession.getAttribute(Constants.ORG_ID);

        StopWord stopWords = stopWordService.getById(id);


        model.addAttribute("id", id);
        model.addAttribute("stopWord", stopWords);
        return "stop-words/edit";
    }


    /*edit user by id API*/
    @PutMapping("/stopWord/update")
    public String update(HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes, HttpSession httpSession) throws Exception {

        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }

        Integer orgId = (Integer) httpSession.getAttribute(Constants.ORG_ID);
        Boolean status = stopWordService.update(httpServletRequest, orgId);

        if (status) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.SUCCESS);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "Stop word updated successfully");
        } else {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "Something went wrong");
        }
        return "redirect:/stopWord?v=467543557hr";
    }
}
