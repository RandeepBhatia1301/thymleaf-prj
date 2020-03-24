package org.ril.hrss.utility;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ControlPanelUtil {
    public static Pageable pagination(HttpServletRequest httpServletRequest) {
        int page = httpServletRequest.getParameterMap().containsKey(Constants.PAGE) ? Integer.valueOf(httpServletRequest.getParameter(Constants.PAGE)) : Constants.ZERO;
        Integer perPage = Constants.PAGE_SIZE_TWENTY;
        return PageRequest.of(page, perPage);
    }

    public static String redirect(RedirectAttributes redirectAttributes, String status, String message, String pageName) {
        redirectAttributes.addFlashAttribute(Constants.STATUS, status);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, message);
        return pageName;
    }

    public static String dateFormat(Date date) {
        Format formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
        return formatter.format(date);
    }

    public static Integer setOrgId(HttpServletRequest httpServletRequest) {
        return (Integer) httpServletRequest.getSession().getAttribute(Constants.ORG_ID);
    }

    public static Integer setAdminId(HttpServletRequest httpServletRequest) {
        return (Integer) httpServletRequest.getSession().getAttribute(Constants.ADMIN_ID);
    }

    public static Integer setSubOrgId(HttpServletRequest httpServletRequest) {
        return (Integer) httpServletRequest.getSession().getAttribute(Constants.SUB_ORG_ID);
    }

}
