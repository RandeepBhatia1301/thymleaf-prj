package org.ril.hrss.service.language;

import org.ril.hrss.model.language.SubOrgLanguage;
import org.ril.hrss.repository.SubOrgLanguageRepository;
import org.ril.hrss.utility.LanguageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Service
public class SubOrgLanguageService {
    @Autowired
    SubOrgLanguageRepository subOrgLanguageRepository;

    public SubOrgLanguage getLanguageDataBySubOrgId(Integer subOrgId, Integer id) {
        return subOrgLanguageRepository.findBySuborgIdAndId(subOrgId, id);
    }

    public List<SubOrgLanguage> getSubOrgLanguage(Integer subOrgId) {
        return subOrgLanguageRepository.findBySuborgId(subOrgId);
    }

    public Boolean updateSubOrgLanguge(Integer id, Integer subOrgId, HttpServletRequest httpServletRequest) {
        try {
            SubOrgLanguage subOrgLanguage = subOrgLanguageRepository.findBySuborgIdAndId(subOrgId, id);
            subOrgLanguageRepository.save(LanguageUtil.updateSubOrgLanguge(httpServletRequest, subOrgLanguage));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setActivation(Integer id, Integer value, Integer subOrgId) {
        try {

          /*  List<SubOrgLanguage> subOrgLanguages = subOrgLanguageRepository.findBySuborgId(subOrgId);
            subOrgLanguageRepository.saveAll(LanguageUtil.setActivationSubOrg(subOrgLanguages));*/

            /*now activate the selected sub org language*/

            SubOrgLanguage subOrgLanguage = subOrgLanguageRepository.findBySuborgIdAndId(subOrgId, id);
            subOrgLanguage.setStatus(value);
            subOrgLanguageRepository.save(subOrgLanguage);
           /* if (subOrgLanguageRepository.countBySuborgIdAndStatus(subOrgId, Constants.ONE) == 0) {
                SubOrgLanguage subOrgLanguage1 = subOrgLanguageRepository.findBySuborgIdAndLangCode(subOrgId, "en");
                subOrgLanguage1.setStatus(1);
                subOrgLanguageRepository.save(subOrgLanguage1);
            }*/
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Integer createLanguage(MultipartFile file, HttpServletRequest httpServletRequest) throws IOException {
        if (!file.isEmpty()) {
            try {
                subOrgLanguageRepository.save(LanguageUtil.createSubOrgLanguage(httpServletRequest, file));
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

