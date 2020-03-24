package org.ril.hrss.controller;

import org.ril.hrss.service.rest_api_services.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ImageStorageController {
    @Autowired
    ImageStorageService imageStorageService;

    @GetMapping("/image-storage")
    public String index(Model model, HttpServletRequest httpServletRequest) {
        return imageStorageService.index(model, httpServletRequest);
    }

    @PostMapping("/image-storage/store")
    public String store(HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes) throws Exception {
        return imageStorageService.store(httpServletRequest, model, redirectAttributes);
    }
}
