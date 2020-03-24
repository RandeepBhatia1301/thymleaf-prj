package org.ril.hrss.utility;

import org.ril.hrss.model.category_hierarchy.CategoryType;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

@Component
public class CategoryTypeUtil {
    public static String createModel(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }

        return "categoryType/add";
    }

    public static CategoryType create(HttpServletRequest httpServletRequest, Integer currentOrder) {
        CategoryType categoryType = new CategoryType();
        if (currentOrder == null) {
            currentOrder = Constants.ZERO;
        }
        categoryType.setTitle(httpServletRequest.getParameter(Constants.NAME));
        categoryType.setOrderBy(currentOrder + Constants.ONE);
        categoryType.setOrgId(ControlPanelUtil.setOrgId(httpServletRequest));
        return categoryType;
    }

    public static String editModel(Model model, Integer id, CategoryType categoryType) {
        model.addAttribute(Constants.ID, id);
        model.addAttribute(Constants.CATEGORY, categoryType);
        return "categoryType/edit";
    }

}
