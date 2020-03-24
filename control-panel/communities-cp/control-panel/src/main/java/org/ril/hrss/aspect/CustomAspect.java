package org.ril.hrss.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.ril.hrss.exception.UnAuthorizeException;
import org.ril.hrss.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Aspect
@Configuration
public class CustomAspect {

    @Autowired
    private HttpServletRequest request;


    @Around("execution(public org.springframework.web.servlet.ModelAndView org.ril.hrss.controller.OrgController.*(..))")
    public ModelAndView beforeClient(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpSession session = request.getSession();
        String adminType = "SAAS ADMIN";
        if (session.getAttribute(Constants.ROLE) == null) {
            return new ModelAndView("redirect:/login?v=467543557hr");
        } else if (!session.getAttribute(Constants.ROLE).equals(adminType)) {
            throw new UnAuthorizeException("Permission Denied");
        }
        return (ModelAndView) joinPoint.proceed();
    }

    @Around("execution(public org.springframework.web.servlet.ModelAndView org.ril.hrss.controller.SubOrgController.*(..))")
    public ModelAndView beforeOrg(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpSession session = request.getSession();
        String adminType = Constants.ORG_ADMIN;

        if (session.getAttribute(Constants.ROLE) == null) {
            return new ModelAndView("redirect:/login?v=467543557hr");

        } else if (!session.getAttribute(Constants.ROLE).equals(adminType)) {
            throw new UnAuthorizeException("Permission Denied");
        }
        return (ModelAndView) joinPoint.proceed();
    }

    @Around("execution(* org.ril.hrss.controller.*.*(..))")
    public String sessionCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpSession session = request.getSession();

        if (session.getAttribute(Constants.ROLE) == null) {
            return new String("redirect:/login?v=467543557hr");
        }
        return (String) joinPoint.proceed();
    }
}
