package org.ril.hrss.service.gamification;

import org.ril.hrss.model.gamification.PointCategoryMaster;
import org.ril.hrss.model.gamification.PointsMaster;
import org.ril.hrss.repository.PointCategoryMasterRepository;
import org.ril.hrss.repository.PointsMasterRepository;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ControlPanelUtil;
import org.ril.hrss.utility.PointsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class PointsService {
    @Autowired
    private PointsMasterRepository pointsMasterRepository;

    @Autowired
    private PointCategoryService pointCategoryService;
    @Autowired
    private PointCategoryMasterRepository pointCategoryMasterRepository;


    public String index(HttpServletRequest httpServletRequest, Model model) {
        Page<PointsMaster> pointsMasters = pointsMasterRepository.findAll(PointsUtil.paginate(httpServletRequest));

        pointsMasters.forEach(pointsMaster -> {
            Optional<PointCategoryMaster> pointCategoryMaster = pointCategoryMasterRepository.findById(pointsMaster.getCategoryId());
            if (pointCategoryMaster.isPresent()) {
                pointsMaster.setCategoryName(pointCategoryMaster.get().getCategoryName());
            }
        });
        return PointsUtil.indexModel(pointsMasters, model);
    }

    public String createModel(HttpServletRequest httpServletRequest, Model model) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        List<PointCategoryMaster> pointCategoryMasters = pointCategoryService.getCategoryByOrgId();
        return PointsUtil.createModel(model, pointCategoryMasters);
    }

    public String editModel(HttpServletRequest httpServletRequest, Model model, Integer id) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        PointsMaster pointsMaster = this.getActivityById(id);
        List<PointCategoryMaster> pointCategoryMasters = pointCategoryService.getCategoryByOrgId();
        return PointsUtil.editModel(model, id, pointsMaster, pointCategoryMasters);
    }

    public String createCategoryModel(HttpServletRequest httpServletRequest) {
        return PointsUtil.createCategoryModel(httpServletRequest);
    }

    public String create(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        try {
            pointsMasterRepository.save(PointsUtil.create(httpServletRequest));
            return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, Constants.ACTIVITY_ADD_SUCCESS, "redirect:/points?v=467543557hr");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.SOMETHING_WENT_WRONG, "redirect:/points?v=467543557hr");
        }

    }

    public PointsMaster getActivityById(Integer id) {
        Optional<PointsMaster> pointsMasters = pointsMasterRepository.findById(id);
        PointsMaster pointsMaster = new PointsMaster();
        if (pointsMasters.isPresent()) {
            pointsMaster = pointsMasters.get();
        }
        return pointsMaster;

    }

    public String updateActivity(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        String referer = httpServletRequest.getHeader(Constants.REFERER);

        try {
            Integer activityId = Integer.valueOf(httpServletRequest.getParameter(Constants.ID));
            Optional<PointsMaster> pointsMasters = pointsMasterRepository.findById(activityId);
            pointsMasterRepository.save(PointsUtil.update(httpServletRequest, pointsMasters));
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.SUCCESS);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, Constants.ACTIVITY_UPDATE_SUCCESS);
            return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, Constants.ACTIVITY_UPDATE_SUCCESS, Constants.REDIRECT + referer);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.SOMETHING_WENT_WRONG, Constants.REDIRECT + referer);
        }
    }

    public String updatePointsBaseMultiplier(HttpServletRequest httpServletRequest) {
        try {
            List<PointsMaster> pointsMasters = pointsMasterRepository.findAll();
            pointsMasterRepository.saveAll(PointsUtil.updatePointsBaseMultiplier(httpServletRequest, pointsMasters));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "redirect:/points?v=467543557hr";

    }
}
