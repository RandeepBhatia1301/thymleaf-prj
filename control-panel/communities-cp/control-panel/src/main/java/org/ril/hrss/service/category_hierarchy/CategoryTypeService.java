package org.ril.hrss.service.category_hierarchy;

import org.ril.hrss.model.category_hierarchy.CategoryType;
import org.ril.hrss.repository.CategoryTypeRepository;
import org.ril.hrss.utility.CategoryTypeUtil;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ControlPanelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class CategoryTypeService {
    @Autowired
    CategoryTypeRepository categoryTypeRepository;


    public String index(HttpServletRequest httpServletRequest, Model model) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        List<CategoryType> categoryTypes = this.getCategoryByOrgId(ControlPanelUtil.setOrgId(httpServletRequest));
        model.addAttribute(Constants.CATEGORY_LIST, categoryTypes);
        return "categoryType/category-list";
    }

    public String editModel(HttpServletRequest httpServletRequest, Model model, Integer id) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        CategoryType categoryType = this.getCategoryData(id, ControlPanelUtil.setOrgId(httpServletRequest));
        return CategoryTypeUtil.editModel(model, id, categoryType);
    }

    public String createModel(HttpServletRequest httpServletRequest) {
        return CategoryTypeUtil.createModel(httpServletRequest);
    }

    public List<CategoryType> getCategoryByOrgId(Integer id) {
        return categoryTypeRepository.findByOrgId(id);
    }


    public String create(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        try {
            Integer currentOrder = categoryTypeRepository.getCurrentOrder(ControlPanelUtil.setOrgId(httpServletRequest));
            categoryTypeRepository.save(CategoryTypeUtil.create(httpServletRequest, currentOrder));
            return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, Constants.CATEGORY_ADD_SUCCESS, "redirect:/categoryType?v=467543557hr");
        } catch (Exception e) {
            e.printStackTrace();
            return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.SOMETHING_WENT_WRONG, "redirect:/categoryType?v=467543557hr");
        }
    }

    public CategoryType getCategoryData(Integer id, Integer orgId) {
        return categoryTypeRepository.findByIdAndOrgId(id, orgId);
    }

    public String updateCategory(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        try {
            Integer id = Integer.valueOf(httpServletRequest.getParameter(Constants.ID));
            CategoryType categoryType = categoryTypeRepository.findByIdAndOrgId(id, ControlPanelUtil.setOrgId(httpServletRequest));
            categoryType.setTitle(httpServletRequest.getParameter("name"));
            categoryTypeRepository.save(categoryType);
            return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, Constants.CATEGORY_EDIT_SUCCESS, "redirect:/categoryType?v=467543557hr");
        } catch (Exception e) {
            e.printStackTrace();
            return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.SOMETHING_WENT_WRONG, "redirect:/categoryType?v=467543557hr");
        }

    }
}
