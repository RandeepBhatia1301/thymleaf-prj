package org.ril.hrss.service.gamification;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ril.hrss.model.gamification.SubOrgwelcomeScreen;
import org.ril.hrss.model.gamification.WelcomeScreen;
import org.ril.hrss.repository.SubOrgwelcomeScreenRepository;
import org.ril.hrss.repository.WelcomeScreenRepository;
import org.ril.hrss.service.rest_api_services.StorageContainer;
import org.ril.hrss.service.rest_api_services.UploadClient;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ControlPanelUtil;
import org.ril.hrss.utility.WelcomeScreenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class WelcomeScreenService {
    @Autowired
    WelcomeScreenRepository welcomeScreenRepository;

    @Autowired
    SubOrgwelcomeScreenRepository subOrgwelcomeScreenRepository;

    @Autowired
    UploadClient uploadClient;

    @Autowired
    StorageContainer storageContainer;

    public String index(Model model, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        List<SubOrgwelcomeScreen> welcomeScreenSubOrg = new ArrayList();
        List<WelcomeScreen> welcomeScreen = new ArrayList();
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SAAS_ADMIN) {
            welcomeScreen = this.getData();
            model.addAttribute(Constants.WELCOME_SCREEN, welcomeScreen);
        }
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SUB_ORG_ADMIN) {
            welcomeScreenSubOrg = this.getSubOrgWelcomeScreen(ControlPanelUtil.setOrgId(httpServletRequest), ControlPanelUtil.setSubOrgId(httpServletRequest));
            model.addAttribute(Constants.WELCOME_SCREEN, welcomeScreenSubOrg);
        }

        return "welcomeScreen/list";
    }

    public String editModel(Integer id, Model model, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SAAS_ADMIN) {
            Map map = storageContainer.getAzureData(Constants.ZERO, Constants.ZERO);
            WelcomeScreen welcomeScreen = this.getDataById(id);

            model.addAttribute("welcomeScreenImage", map.get(Constants.DOWNLOAD_BASE_PATH) + Constants.SEPARATOR + map.get(Constants.FOLDER_NAME));

            model.addAttribute("welcomeScreen", welcomeScreen);
        }
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SUB_ORG_ADMIN) {
            Map map = storageContainer.getAzureData(Constants.ONE, ControlPanelUtil.setOrgId(httpServletRequest));
            SubOrgwelcomeScreen subOrgwelcomeScreen = this.editSubOrgWelcomeScreen(id);
            model.addAttribute("welcomeScreenImage", map.get(Constants.DOWNLOAD_BASE_PATH) + Constants.SEPARATOR + map.get(Constants.FOLDER_NAME));

            model.addAttribute("welcomeScreen", subOrgwelcomeScreen);
        }
        model.addAttribute(Constants.ID, id);

        return "welcomeScreen/edit";
    }

    public String updateSetParam(HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes, MultipartFile file) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        Boolean status = false;
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SAAS_ADMIN) {
            status = this.update(httpServletRequest, file);
        }
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SUB_ORG_ADMIN) {
            status = this.updateSubOrgWelcomeScreen(httpServletRequest, file);
        }
        return WelcomeScreenUtil.setUpdateParam(status, redirectAttributes);
    }


    public List<WelcomeScreen> getData() {
        return welcomeScreenRepository.findAll();

    }

    private WelcomeScreen getDataById(Integer id) {
        return welcomeScreenRepository.findById(id).get();
    }

    public Boolean update(HttpServletRequest httpServletRequest, MultipartFile file) {
        String filePath = null;
        if (!file.isEmpty()) {
            filePath = this.upload(file, httpServletRequest);
        }
        Integer id = Integer.valueOf(httpServletRequest.getParameter(Constants.ID));
        Optional<WelcomeScreen> welcomeScreen = welcomeScreenRepository.findById(id);
        if (welcomeScreen.isPresent()) {
            welcomeScreenRepository.save(WelcomeScreenUtil.update(welcomeScreen, httpServletRequest, filePath));
            return true;
        }
        return false;
    }

    private Boolean updateSubOrgWelcomeScreen(HttpServletRequest httpServletRequest, MultipartFile file) {
        String filePath = null;
        if (!file.isEmpty()) {
            filePath = this.upload(file, httpServletRequest);
        }
        Integer id = Integer.valueOf(httpServletRequest.getParameter(Constants.ID));
        Optional<SubOrgwelcomeScreen> subOrgwelcomeScreen = subOrgwelcomeScreenRepository.findById(id);
        if (subOrgwelcomeScreen.isPresent()) {
            subOrgwelcomeScreenRepository.save(WelcomeScreenUtil.updateSubOrgWelcomeScreen(subOrgwelcomeScreen, httpServletRequest, filePath));
            return true;
        }
        return false;

    }

    public String upload(MultipartFile file, HttpServletRequest httpServletRequest) {
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap();
        multiValueMap.add(Constants.DIR_NAME, Constants.DIR_WELCOME_SCREEN);

        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SUB_ORG_ADMIN) {
            Integer orgId = (Integer) httpServletRequest.getSession().getAttribute(Constants.ORG_ID);
            Integer subOrgId = (Integer) httpServletRequest.getSession().getAttribute(Constants.SUB_ORG_ID);
            multiValueMap.add(Constants.SUB_ORG_ID, subOrgId);
            multiValueMap.add(Constants.ORG_ID, orgId);
        }
        String relativePath = Constants.EMPTY;
        ResponseEntity<Map> response = null;
        Map map = new HashMap();
        try {
            response = uploadClient.UploadClient(multiValueMap, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response != null && response.getStatusCodeValue() == 200) {
            map = response.getBody();
            if (map != null) {
                relativePath = map.get(Constants.PATH).toString();
            }
        }

        return relativePath;
    }

    private List<SubOrgwelcomeScreen> getSubOrgWelcomeScreen(Integer orgId, Integer subOrgId) {
        return subOrgwelcomeScreenRepository.findByOrgIdAndSubOrgId(orgId, subOrgId);
    }

    private SubOrgwelcomeScreen editSubOrgWelcomeScreen(Integer id) {
        return subOrgwelcomeScreenRepository.findById(id).get();
    }


}
