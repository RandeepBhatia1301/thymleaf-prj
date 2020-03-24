package org.ril.hrss.service.gamification;

import org.ril.hrss.model.gamification.GamificationLevel;
import org.ril.hrss.repository.GamificationLevelRepository;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ControlPanelUtil;
import org.ril.hrss.utility.LevelsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Service
public class GamificationLevelService {
    @Autowired
    private GamificationLevelRepository gamificationLevelRepository;

    public String index(HttpServletRequest httpServletRequest, Model model) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        Page<GamificationLevel> gamificationLevels = gamificationLevelRepository.findAll(ControlPanelUtil.pagination(httpServletRequest));
        return LevelsUtil.indexModel(model, gamificationLevels);
    }

    public String edit(HttpServletRequest httpServletRequest, Model model, Integer id) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        GamificationLevel gamificationLevels = gamificationLevelRepository.findById(id).get();
        return LevelsUtil.editModel(model, id, gamificationLevels);
    }

    public String update(HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        try {
            GamificationLevel gamificationLevel = gamificationLevelRepository.findById(Integer.valueOf(httpServletRequest.getParameter(Constants.ID))).get();
            gamificationLevel.setPoint(Integer.valueOf(httpServletRequest.getParameter(Constants.POINTS)));
            gamificationLevelRepository.save(gamificationLevel);
            return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, Constants.LEVEL_UPDATE_SUCCESS, Constants.REDIRECT_LEVEL_INDEX);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.SOMETHING_WENT_WRONG, Constants.REDIRECT_LEVEL_INDEX);

        }
    }

    public String setActivation(Integer id, Integer value, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        GamificationLevel gamificationLevel = gamificationLevelRepository.findById(id).get();
        gamificationLevel.setIsStage(value);
        gamificationLevelRepository.save(gamificationLevel);
        return "redirect:/level?v=467543557hr";
    }

}
