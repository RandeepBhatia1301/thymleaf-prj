package org.ril.hrss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.ril.hrss.utility.Constants.ENTRY_EXIT_PAGE;

@Controller
public class ExitController {

    @RequestMapping("/remove")
    public String remove(Model model) {
        return ENTRY_EXIT_PAGE;
    }

}
