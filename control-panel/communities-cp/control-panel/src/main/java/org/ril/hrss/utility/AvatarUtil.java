package org.ril.hrss.utility;

import org.ril.hrss.model.gamification.Avatar;
import org.ril.hrss.model.gamification.AvatarImageMaster;
import org.ril.hrss.model.gamification.GamificationLevel;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Component
public class AvatarUtil {
    public static String indexModel(Model model, Map map) {
        model.addAttribute(Constants.AVATAR_MAP, map);
        return "avatar/list";
    }

    public static String editModel(Model model, Integer id, List<AvatarImageMaster> avatars, Avatar avatar, Integer genderIdChnged, Map map) {
        model.addAttribute(Constants.AVATAR, avatars);
        model.addAttribute(Constants.AVATAR_CATEGORY_NAME, avatar.getName());
        model.addAttribute(Constants.AVATAR_ID, id);
        model.addAttribute(Constants.GENDER_ID, genderIdChnged);
        model.addAttribute(Constants.AVATAR_IMAGE_LOCATION, map.get(Constants.DOWNLOAD_BASE_PATH) + Constants.SEPARATOR + map.get(Constants.FOLDER_NAME));
        return "avatar/edit";

    }

    public static String createModel(Model model, Integer avatarId, List<GamificationLevel> levels) {
        model.addAttribute(Constants.AVATAR_ID, avatarId);
        model.addAttribute(Constants.LEVELS, levels);
        return "avatar/add";
    }

    public static AvatarImageMaster create(HttpServletRequest httpServletRequest, String filePath) {
        AvatarImageMaster avatarImageMaster = new AvatarImageMaster();
        avatarImageMaster.setAvatarId(Integer.valueOf(httpServletRequest.getParameter(Constants.AVATAR_ID)));
        avatarImageMaster.setGender(Integer.valueOf(httpServletRequest.getParameter(Constants.GENDER)));
        avatarImageMaster.setImage(filePath);
        avatarImageMaster.setStatus(Constants.STATUS_ONE);
        avatarImageMaster.setUnlockAt(Integer.valueOf(httpServletRequest.getParameter(Constants.STAGE)));
        return avatarImageMaster;
    }

    public static Integer setGenderId(Integer genderId) {
        if (genderId == null) {
            genderId = Constants.ONE;
        }
        return genderId;
    }

    public static Avatar setCategoryActivation(Integer value, Avatar avatar) {
        avatar.setStatus(value);
        return avatar;
    }

    public static AvatarImageMaster setActivation(Integer value, AvatarImageMaster avatarImageMaster) {
        avatarImageMaster.setStatus(value);
        return avatarImageMaster;
    }

    public static String createCategoryModel(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        return "avatar/category/add";
    }

    public static Avatar storeCategory(HttpServletRequest httpServletRequest, String filePath) {
        Avatar avatar = new Avatar();
        avatar.setName(httpServletRequest.getParameter(Constants.CATEGORY));
        avatar.setStatus(Constants.ONE);
        avatar.setImage(filePath);
        avatar.setDescription(httpServletRequest.getParameter(Constants.DESCRIPTION));
        return avatar;
    }

    public static MultiValueMap upload() {
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap();
        multiValueMap.add(Constants.DIR_NAME, Constants.GAMIFICATION);
        return multiValueMap;
    }
}
