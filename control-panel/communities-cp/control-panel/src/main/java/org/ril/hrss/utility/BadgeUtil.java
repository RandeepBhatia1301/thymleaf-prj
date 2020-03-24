package org.ril.hrss.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import org.ril.hrss.model.gamification.Badge;
import org.ril.hrss.model.gamification.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class BadgeUtil {
    public static String indexModel(Page<Badge> badges, Model model, Map map) throws JsonProcessingException {
        for (Badge b : badges) {
            if (b.getIsAutomatic() == 0 && b.getLevel() == null) {
                Level level = new Level();
                String[] counter = new String[]{"no counter"};
                level.setName("no levels");
                level.setCounter(counter);
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String json = ow.writeValueAsString(level);
                b.setLevel(json);
            }
            Gson g = new Gson();
            if (b.getLevel() != null) {
                Level l = g.fromJson(b.getLevel(), Level.class);
                b.setL(l);
            }
        }
        model.addAttribute(Constants.BADGE_IMAGE_LOCATION, map.get(Constants.DOWNLOAD_BASE_PATH) + Constants.SEPARATOR + map.get(Constants.FOLDER_NAME));
        model.addAttribute(Constants.BADGES, badges);
        model.addAttribute(Constants.PAGE, badges);
        return "badge/index";
    }

    public static Pageable paginate(HttpServletRequest httpServletRequest) {
        int page = httpServletRequest.getParameterMap().containsKey(Constants.PAGE) ? Integer.valueOf(httpServletRequest.getParameter(Constants.PAGE)) : Constants.ZERO;

        return PageRequest.of(page, Constants.PAGE_SIZE_TEN);
    }

    public static String editModel(Model model, Badge badge, Map map) {
        String levelStr = badge.getLevel();
        Level l = new Gson().fromJson(levelStr, Level.class);
        model.addAttribute(Constants.BADGE, badge);
        model.addAttribute(Constants.LEVEL, l);
        model.addAttribute(Constants.BADGE_IMAGE_LOCATION, map.get(Constants.DOWNLOAD_BASE_PATH) + Constants.SEPARATOR + map.get(Constants.FOLDER_NAME));
        return "badge/edit";

    }

    public static String createModel(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        return "badge/create";
    }

    public static Level setLevel(HttpServletRequest httpServletRequest) {
        Level l = new Level();
        l.setName("level");
        l.setCounter(httpServletRequest.getParameterValues(Constants.LEVEL_VALUE));

        return l;
    }

    public static Badge setBadge(HttpServletRequest httpServletRequest, String imagePath, Badge badge) throws JsonProcessingException {
        badge.setTitle(httpServletRequest.getParameter(Constants.NAME));
        badge.setDescription(httpServletRequest.getParameter(Constants.DESCRIPTION));
        if (!imagePath.equals(Constants.EMPTY)) {
            badge.setImage(imagePath);
        }

        /*if (httpServletRequest.getParameter(Constants.LEVEL_NAME) != null && httpServletRequest.getParameter(Constants.LEVEL_VALUE) != null) {
            badge.setLevel(BadgeUtil.objectWriter(httpServletRequest));
        }*/

        /*badge.setIsAutomatic(Integer.parseInt(httpServletRequest.getParameter(Constants.IS_AUTOMATIC)));
        badge.setIsPeerToPeer(Integer.parseInt(httpServletRequest.getParameter(Constants.IS_PEERTOPEER)));
        badge.setIsAdminToPeer(Integer.parseInt(httpServletRequest.getParameter(Constants.IS_PEERTOADMIN)));*/
        badge.setLevel(BadgeUtil.objectWriter(httpServletRequest));
        badge.setPopupMsg(httpServletRequest.getParameter(Constants.POP_MESSAGE));
        badge.setDashboardBadge(httpServletRequest.getParameter(Constants.DASHBOARD_MESSAGE));
        badge.setDashboardNextLevel(httpServletRequest.getParameter(Constants.DASHBOARD_NEXT));
        badge.setDashboardOnCompletion(httpServletRequest.getParameter(Constants.DASHBOARD_COMPLETION));
        return badge;
    }


    private static String objectWriter(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(BadgeUtil.setLevel(httpServletRequest));
    }

    public static MultiValueMap upload() {
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap();
        multiValueMap.add(Constants.DIR_NAME, Constants.GAMIFICATION);
        return multiValueMap;
    }
}
