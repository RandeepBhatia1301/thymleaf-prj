package org.ril.hrss.service.gamification;

import org.ril.hrss.model.gamification.PointCategoryMaster;
import org.ril.hrss.repository.PointCategoryMasterRepository;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ControlPanelUtil;
import org.ril.hrss.utility.PointsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class PointCategoryService {

    @Autowired
    private PointCategoryMasterRepository pointCategoryMasterRepository;

    public List<PointCategoryMaster> getCategoryByOrgId() {
        return pointCategoryMasterRepository.findAll();

    }

    public String createCategory(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        try {
            pointCategoryMasterRepository.save(PointsUtil.storeCategory(httpServletRequest));
            return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, Constants.CATEGORY_ADD_SUCCESS, "redirect:/points?v=467543557hr");

        } catch (Exception e) {
            e.printStackTrace();
            return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.SOMETHING_WENT_WRONG, "redirect:/points?v=467543557hr");
        }
    }
}
