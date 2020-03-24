package org.ril.hrss.service.language;

import org.ril.hrss.model.language.OrgLanguage;
import org.ril.hrss.repository.LanguageRepository;
import org.ril.hrss.repository.OrgLanguageRepository;
import org.ril.hrss.repository.OrgRepository;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.LanguageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Service
public class OrgLanguageService {

    @Autowired
    OrgRepository orgRepository;

    @Autowired
    OrgLanguageRepository orgLanguageRepository;

    @Autowired
    LanguageRepository languageRepository;


    public List<OrgLanguage> getOrgLanguage(Integer orgId) {
        return orgLanguageRepository.findAllByOrgId(orgId);
    }

    public OrgLanguage getLanguageDataByOrgId(Integer orgId, Integer id) {
        return orgLanguageRepository.findByOrgIdAndId(orgId, id);
    }

    public Boolean updateOrgLanguage(Integer id, Integer orgId, HttpServletRequest httpServletRequest) {
        try {
            String jsonData = httpServletRequest.getParameter(Constants.JSON_INPUT);
            OrgLanguage orgLanguage = orgLanguageRepository.findByOrgIdAndId(orgId, id);
            orgLanguageRepository.save(LanguageUtil.updateSOrgLanguge(httpServletRequest, orgLanguage, jsonData));
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean setActivation(Integer id, Integer value, Integer orgId) {
        try {
            OrgLanguage orgLanguage = orgLanguageRepository.findByOrgIdAndId(orgId, id);
            orgLanguage.setStatus(value);
            orgLanguageRepository.save(orgLanguage);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public Integer createLanguage(MultipartFile file, HttpServletRequest httpServletRequest) throws IOException {
        if (!file.isEmpty()) {
            try {
                orgLanguageRepository.save(LanguageUtil.createOrgLanguage(httpServletRequest, file));
                return 1;
            } catch (Exception ex) {
                if (ex instanceof DataIntegrityViolationException) {
                    return 2;
                }
            }
        }
        return 0;
    }
}


