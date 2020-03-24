package org.ril.hrss.controller.ExitRestController;

import org.ril.hrss.model.auth.User;
import org.ril.hrss.service.org_management.UserService;
import org.ril.hrss.service.sub_org_features.ExitCriteriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class ExitCriteriaRestController {

    @Autowired
    private ExitCriteriaService exitCriteriaService;

    @Autowired
    private UserService userService;

    @PostMapping("exit-user")
    public String exitUser(@RequestParam("userId") @NotNull Long userId, HttpServletRequest httpServletRequest) {
        return exitCriteriaService.exitUser(userId, httpServletRequest);
    }

    @GetMapping("/email/{email}")
    public List<User> getByEmail(@PathVariable String email, HttpServletRequest httpServletRequest) {
        return userService.getUserByEmail(email, httpServletRequest);
    }
}
