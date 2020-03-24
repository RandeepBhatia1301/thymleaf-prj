package org.ril.hrss.service.language;

import org.ril.hrss.model.language.Language;
import org.ril.hrss.model.language.OrgLanguage;
import org.ril.hrss.model.language.SubOrgLanguage;
import org.ril.hrss.repository.LanguageCustom;
import org.ril.hrss.repository.LanguageRepository;
import org.ril.hrss.repository.OrgLanguageRepository;
import org.ril.hrss.repository.SubOrgLanguageRepository;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ControlPanelUtil;
import org.ril.hrss.utility.LanguageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private OrgLanguageService orgLanguageService;

    @Autowired
    private SubOrgLanguageService subOrgLanguageService;

    @Autowired
    private LanguageCustom languageCustom;

    @Autowired
    private OrgLanguageRepository orgLanguageRepository;

    @Autowired
    private SubOrgLanguageRepository subOrgLanguageRepository;

    public String index(HttpServletRequest httpServletRequest, Model model) {
        return languageCustom.listLanguage(httpServletRequest, model);
    }

    public String createModel(Model model) {
        return LanguageUtil.createModel(model);
    }

    public String editModel(Model model, HttpServletRequest httpServletRequest, Integer id) {
        return languageCustom.findLanguage(id, httpServletRequest, model);
    }

    public String createLanguageSetParam(MultipartFile file, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) throws Exception {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        Integer status = Constants.ZERO;
        LanguageUtil.isEmptyCheck(file, status, redirectAttributes);

        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SAAS_ADMIN) {
            status = this.createLanguage(file, httpServletRequest);
        } else if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.ORG_ADMIN) {
            status = orgLanguageService.createLanguage(file, httpServletRequest);
        } else if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SUB_ORG_ADMIN) {
            status = subOrgLanguageService.createLanguage(file, httpServletRequest);
        }
        return LanguageUtil.successCreateModel(status, redirectAttributes);
    }

    private Integer createLanguage(MultipartFile file, HttpServletRequest httpServletRequest) throws Exception {
        if (!file.isEmpty()) {
            try {
                languageRepository.save(LanguageUtil.createLanguage(httpServletRequest, file));
                return 1;
            } catch (Exception ex) {
                if (ex instanceof DataIntegrityViolationException) {
                    return 2;
                }
            }
        }
        return 0;
    }

/*
    public Page<Language> getLanguageList(HttpServletRequest httpServletRequest) {
        return languageRepository.findAll(LanguageUtil.pagination(httpServletRequest));

    }

    public Language getLanguageDataById(Integer id) {
        return languageRepository.findAllById(id).get(0);
    }*/

    public String updateLanguageSetParam(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) throws Exception {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }

        Boolean status = false;

        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SAAS_ADMIN) {
            status = this.updateLanguage(httpServletRequest);

        } else if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.ORG_ADMIN) {

            Integer id = Integer.valueOf(httpServletRequest.getParameter(Constants.ID));
            status = orgLanguageService.updateOrgLanguage(id, ControlPanelUtil.setOrgId(httpServletRequest), httpServletRequest);
        } else if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SUB_ORG_ADMIN) {

            Integer id = Integer.valueOf(httpServletRequest.getParameter(Constants.ID));

            status = subOrgLanguageService.updateSubOrgLanguge(id, ControlPanelUtil.setSubOrgId(httpServletRequest), httpServletRequest);
        }
        return LanguageUtil.successUpdateModel(status, redirectAttributes);
    }

    private Boolean updateLanguage(HttpServletRequest httpServletRequest) throws Exception {

        Integer id = Integer.valueOf(httpServletRequest.getParameter(Constants.ID));
        String jsonData = httpServletRequest.getParameter(Constants.JSON_INPUT);
        Optional<Language> l = languageRepository.findById(id);
        if (l.isPresent()) {
            Language language = l.get();
            language.setContent(jsonData);
            languageRepository.save(language);
            return true;
        }
        return false;
    }

    public String setActivationParam(Integer id, HttpServletRequest httpServletRequest, Integer value, RedirectAttributes redirectAttributes) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        boolean status = false;
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SAAS_ADMIN) {
            status = this.setActivation(id, value);

        } else if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.ORG_ADMIN) {
            status = orgLanguageService.setActivation(id, value, ControlPanelUtil.setOrgId(httpServletRequest));
        } else if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SUB_ORG_ADMIN) {
            status = subOrgLanguageService.setActivation(id, value, ControlPanelUtil.setSubOrgId(httpServletRequest));
        }
        return LanguageUtil.successSetActivationModel(status, httpServletRequest, redirectAttributes);
    }


    public Boolean setActivation(Integer id, Integer value) {
        Integer count = languageRepository.setActivation(id, value);
        return LanguageUtil.setActivation(count);
    }

    public String setDefaultValue(Integer id, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SAAS_ADMIN) {
            Language language = languageRepository.findById(id).get();
            language.setContent(language.getDefaultContent());
            languageRepository.save(language);

        } else if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.ORG_ADMIN) {
            OrgLanguage language = orgLanguageRepository.findById(id).get();
            language.setContent(language.getDefaultContent());
            orgLanguageRepository.save(language);

        } else if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SUB_ORG_ADMIN) {
            SubOrgLanguage language = subOrgLanguageRepository.findById(id).get();
            language.setContent(language.getDefaultContent());
            subOrgLanguageRepository.save(language);
        }


        String referer = httpServletRequest.getHeader(Constants.REFERER);
        return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, "Data Set to default", Constants.REDIRECT + referer);
    }

}
