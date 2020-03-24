package org.ril.hrss.service.gamification;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ril.hrss.model.gamification.Badge;
import org.ril.hrss.repository.BadgesRepository;
import org.ril.hrss.service.org_management.OrgService;
import org.ril.hrss.service.rest_api_services.StorageContainer;
import org.ril.hrss.service.rest_api_services.UploadClient;
import org.ril.hrss.utility.BadgeUtil;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ControlPanelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@Service
public class BadgeService {
    @Autowired
    OrgService orgService;

    @Autowired
    UploadClient uploadClient;

    @Autowired
    StorageContainer storageContainer;

    @Autowired
    BadgesRepository badgesRepository;

    public String index(HttpServletRequest httpServletRequest, Model model) throws JsonProcessingException {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        Map map = storageContainer.getAzureData(Constants.ZERO, Constants.ZERO);
        Page<Badge> badges = badgesRepository.findAll(BadgeUtil.paginate(httpServletRequest));
        return BadgeUtil.indexModel(badges, model, map);
    }

    public String createModel(HttpServletRequest httpServletRequest) {
        return BadgeUtil.createModel(httpServletRequest);
    }

    public String editModel(HttpServletRequest httpServletRequest, Integer id, Model model) {

        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        Map map = storageContainer.getAzureData(Constants.ZERO, Constants.ZERO);
        Badge badge = badgesRepository.findById(id).get();
        return BadgeUtil.editModel(model, badge, map);
    }

    public String badgeUpdate(HttpServletRequest httpServletRequest, MultipartFile file, RedirectAttributes redirectAttributes) throws JsonProcessingException {
        try {
            String filePath = Constants.EMPTY;
            if (!file.isEmpty()) {
                filePath = this.upload(file, httpServletRequest);
            }
            Integer id = Integer.valueOf(httpServletRequest.getParameter(Constants.ID));
            Optional<Badge> badge = badgesRepository.findById(id);
            if (badge.isPresent()) {
                badgesRepository.save(BadgeUtil.setBadge(httpServletRequest, filePath, badge.get()));
            }

            return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, Constants.BADGE_UPDATE_SUCCESS, "redirect:/badges?v=467Rtr57hr");
        } catch (Exception e) {
            e.printStackTrace();
            return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.SOMETHING_WENT_WRONG, "redirect:/badges?v=467Rtr57hr");
        }
    }

    public String upload(MultipartFile file, HttpServletRequest httpServletRequest) {
        String relativePath = Constants.EMPTY;
        ResponseEntity<Map> response = uploadClient.UploadClient(BadgeUtil.upload(), file);
        Map map = response.getBody();
        if (map != null) {
            relativePath = map.get(Constants.PATH).toString();
        }
        return relativePath;
    }
}
