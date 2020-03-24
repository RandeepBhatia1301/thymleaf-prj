package org.ril.hrss.utility;

import org.ril.hrss.model.gamification.GamificationLevel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class LevelsUtil {
    public static String indexModel(Model model, Page<GamificationLevel> gamificationLevels) {
        model.addAttribute(Constants.GAMIFICATION_LEVELS, gamificationLevels);
        model.addAttribute(Constants.PAGE, gamificationLevels);
        return "GamificationLevel/list";

    }

    public static String editModel(Model model, Integer id, GamificationLevel gamificationLevel) {
        model.addAttribute(Constants.ID, id);
        model.addAttribute(Constants.LEVEL_DATA, gamificationLevel);
        return "GamificationLevel/edit";

    }
}
