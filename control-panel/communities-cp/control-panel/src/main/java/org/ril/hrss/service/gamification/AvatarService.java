package org.ril.hrss.service.gamification;

import org.ril.hrss.model.gamification.Avatar;
import org.ril.hrss.model.gamification.AvatarImageMaster;
import org.ril.hrss.model.gamification.GamificationLevel;
import org.ril.hrss.repository.AvatarImageRepository;
import org.ril.hrss.repository.AvatarRepository;
import org.ril.hrss.repository.GamificationLevelRepository;
import org.ril.hrss.service.rest_api_services.StorageContainer;
import org.ril.hrss.service.rest_api_services.UploadClient;
import org.ril.hrss.utility.AvatarUtil;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ControlPanelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AvatarService {
    @Autowired
    AvatarRepository avatarRepository;

    @Autowired
    AvatarImageRepository avatarImageRepository;

    @Autowired
    GamificationLevelRepository gamificationLevelRepository;

    @Autowired
    UploadClient uploadClient;

    @Autowired
    StorageContainer storageContainer;


    public String findAll(HttpServletRequest httpServletRequest, Model model) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        List<Avatar> avatars = avatarRepository.findAll();
        Map<Object, Integer> map = new HashMap<>();
        for (Avatar avatar : avatars) {
            map.put(avatar, avatarImageRepository.countAllByAvatarId(avatar.getId()));
        }
        return AvatarUtil.indexModel(model, map);
    }


    public String editModel(HttpServletRequest httpServletRequest, Model model, Integer genderId, Integer id) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }

        Integer genderIdChanged = AvatarUtil.setGenderId(genderId);

        Map map = storageContainer.getAzureData(Constants.ZERO, Constants.ZERO);
        List<AvatarImageMaster> avatars = this.findAllById(id, genderIdChanged);
        Avatar avatar = this.getAvatarData(id);
        return AvatarUtil.editModel(model, id, avatars, avatar, genderIdChanged, map);
    }

    public String createModel(HttpServletRequest httpServletRequest, Model model, Integer avatarId) {

        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        List<GamificationLevel> levels = this.getLevelMultiples();
        return AvatarUtil.createModel(model, avatarId, levels);
    }

    public List<AvatarImageMaster> findAllById(Integer id, Integer genderId) {
        return avatarImageRepository.findAllByAvatarIdAndGender(id, genderId);
    }

    public String create(HttpServletRequest httpServletRequest, MultipartFile file, RedirectAttributes redirectAttributes) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        String filePath = this.upload(file);
        String referer = httpServletRequest.getHeader(Constants.REFERER);
        try {
            avatarImageRepository.save(AvatarUtil.create(httpServletRequest, filePath));
            return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, Constants.AVATAR_IMAGE_ADD_SUCCESS, Constants.REDIRECT + referer);
        } catch (Exception e) {
            e.printStackTrace();
            return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.SOMETHING_WENT_WRONG, Constants.REDIRECT + referer);

        }
    }

    private List<GamificationLevel> getLevelMultiples() {
        return gamificationLevelRepository.findByIsStage(1);
        /*return gamificationLevelRepository.findLevelMultiples();*/
    }

    public String setActivation(Integer id, HttpServletRequest httpServletRequest, Integer value, RedirectAttributes redirectAttributes) {
        String referer = httpServletRequest.getHeader(Constants.REFERER);
        try {
            AvatarImageMaster avatarImageMaster = avatarImageRepository.findById(id).get();
            avatarImageRepository.save(AvatarUtil.setActivation(value, avatarImageMaster));
            return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, Constants.STATUS_UPDATE_SUCCESS, Constants.REDIRECT + referer);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.SOMETHING_WENT_WRONG, Constants.REDIRECT + referer);
        }
    }

    public String createCategory(HttpServletRequest httpServletRequest, MultipartFile file, RedirectAttributes redirectAttributes) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        String filePath = this.upload(file);
        try {
            avatarRepository.save(AvatarUtil.storeCategory(httpServletRequest, filePath));
            return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, Constants.AVATAR_CATEGORY_ADD_SUCCESS, "redirect:/avatar?v=467RGER");
        } catch (Exception e) {
            if (e instanceof DataIntegrityViolationException) {
                return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.AVATAR_CATEGORY_EXIST, "redirect:/avatar?v=467RGER");
            }
            e.printStackTrace();
            return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.SOMETHING_WENT_WRONG, "redirect:/avatar?v=467RGER");
        }
    }

    public String createCategoryModel(HttpServletRequest httpServletRequest) {
        return AvatarUtil.createCategoryModel(httpServletRequest);

    }

    public String setCategoryActivation(Integer id, HttpServletRequest httpServletRequest, Integer value, RedirectAttributes redirectAttributes) {
        String referer = httpServletRequest.getHeader(Constants.REFERER);
        try {
            Avatar avatar = avatarRepository.findById(id).get();
            avatarRepository.save(AvatarUtil.setCategoryActivation(value, avatar));
            return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, Constants.STATUS_UPDATE_SUCCESS, Constants.REDIRECT + referer);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.SOMETHING_WENT_WRONG, Constants.REDIRECT + referer);
        }
    }

    public String upload(MultipartFile file) {
        String relativePath = Constants.EMPTY;
        ResponseEntity<Map> response = uploadClient.UploadClient(AvatarUtil.upload(), file);
        Map map = response.getBody();
        if (map != null) {
            relativePath = map.get(Constants.PATH).toString();
        }
        return relativePath;
    }

    private Avatar getAvatarData(Integer id) {
        return avatarRepository.findById(id).get();
    }

}
