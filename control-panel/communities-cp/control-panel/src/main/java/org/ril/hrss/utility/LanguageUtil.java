package org.ril.hrss.utility;

import org.apache.commons.io.IOUtils;
import org.ril.hrss.model.language.Language;
import org.ril.hrss.model.language.OrgLanguage;
import org.ril.hrss.model.language.SubOrgLanguage;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class LanguageUtil {

    public static String createModel(Model model) {
        model.addAttribute("languageURL", "/language/store");
        model.addAttribute("method", "/POST");
        return "language/form";
    }

    public static String isEmptyCheck(MultipartFile file, Integer status, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            status = 3;
            return LanguageUtil.successCreateModel(status, redirectAttributes);
        }
        return "file not empty";
    }

    public static Pageable pagination(HttpServletRequest httpServletRequest) {
        int page = httpServletRequest.getParameterMap().containsKey(Constants.PAGE) ? Integer.valueOf(httpServletRequest.getParameter(Constants.PAGE)) : Constants.ZERO;
        Integer perPage = Constants.TEN;
        return PageRequest.of(page, perPage);
    }

    public static OrgLanguage createOrgLanguage(HttpServletRequest httpServletRequest, MultipartFile file) throws IOException {
        OrgLanguage language = new OrgLanguage();
        language.setOrgId(ControlPanelUtil.setOrgId(httpServletRequest));
        language.setName(httpServletRequest.getParameter(Constants.NAME));
        ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());
        String fileContent = IOUtils.toString(stream, "UTF-8");
        language.setContent(fileContent);
        language.setDefaultContent(fileContent);
        language.setStatus(Constants.ONE);
        language.setLangCode(httpServletRequest.getParameter("langCode"));
        return language;
    }

    public static Language createLanguage(HttpServletRequest httpServletRequest, MultipartFile file) throws IOException {
        Language language = new Language();
        language.setName(httpServletRequest.getParameter(Constants.NAME));
        language.setFileName(file.getOriginalFilename());
        language.setLangCode(httpServletRequest.getParameter("langCode"));
        ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());
        String fileContent = IOUtils.toString(stream, "UTF-8");
        language.setContent(fileContent);
        language.setDefaultContent(fileContent);
        language.setStatus(Constants.ONE);
        return language;
    }

    public static SubOrgLanguage createSubOrgLanguage(HttpServletRequest httpServletRequest, MultipartFile file) throws IOException {
        SubOrgLanguage language = new SubOrgLanguage();
        language.setName(httpServletRequest.getParameter(Constants.NAME));
        language.setSuborgId(ControlPanelUtil.setSubOrgId(httpServletRequest));
        language.setOrgId(ControlPanelUtil.setOrgId(httpServletRequest));
        ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());
        String fileContent = IOUtils.toString(stream, "UTF-8");
        language.setContent(fileContent);
        language.setDefaultContent(fileContent);
        language.setStatus(Constants.ZERO);
        language.setLangCode(httpServletRequest.getParameter("langCode"));
        return language;
    }

    public static String successCreateModel(Integer status, RedirectAttributes redirectAttributes) {
        if (status == 1) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.SUCCESS);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "Language Added successfully");
        } else if (status == 2) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "The File is not valid");
        } else {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "File cannot be empty");
        }
        return "redirect:/language?v=54rt233";
    }

    public static String successSetActivationModel(Boolean status, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        if (status) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.SUCCESS);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, Constants.STATUS_UPDATE_SUCCESS);
        } else {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
        }
        String referer = httpServletRequest.getHeader(Constants.REFERER);
        return "redirect:" + referer;
    }


    public static String successUpdateModel(Boolean status, RedirectAttributes redirectAttributes) {
        if (status) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.SUCCESS);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "Language updated successfully");
        } else {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "Please try again");
        }
        return "redirect:/language?v=54rt233";
    }

    public static SubOrgLanguage updateSubOrgLanguge(HttpServletRequest httpServletRequest, SubOrgLanguage subOrgLanguage) {

        String jsonData = httpServletRequest.getParameter(Constants.JSON_INPUT);
        subOrgLanguage.setName(httpServletRequest.getParameter(Constants.NAME));
        subOrgLanguage.setContent(jsonData);
        subOrgLanguage.setIsEdited(Constants.ONE);
        return subOrgLanguage;
    }

    public static OrgLanguage updateSOrgLanguge(HttpServletRequest httpServletRequest, OrgLanguage orgLanguage, String jsonData) {
        orgLanguage.setName(httpServletRequest.getParameter(Constants.NAME));
        orgLanguage.setIsEdited(Constants.ONE);
        orgLanguage.setContent(jsonData);
        return orgLanguage;
    }

    public static Boolean setActivation(Integer count) {
        return count > 0;
    }

    public static List<SubOrgLanguage> setActivationSubOrg(List<SubOrgLanguage> subOrgLanguages) {
        List<SubOrgLanguage> subOrgLanguages1 = new ArrayList<>();
        for (SubOrgLanguage subOrgLanguage : subOrgLanguages) {
            subOrgLanguage.setStatus(Constants.ZERO);
            subOrgLanguages1.add(subOrgLanguage);
        }
        return subOrgLanguages1;
    }
}
