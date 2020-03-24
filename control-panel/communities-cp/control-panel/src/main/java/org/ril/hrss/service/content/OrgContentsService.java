package org.ril.hrss.service.content;

import org.ril.hrss.model.content.Content;
import org.ril.hrss.model.content.OrgContent;
import org.ril.hrss.repository.OrgContentTypeRepository;
import org.ril.hrss.repository.OrgRepository;
import org.ril.hrss.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class OrgContentsService {

    @Autowired
    OrgRepository orgRepository;

    @Autowired
    OrgContentTypeRepository orgContentTypeRepository;

    public OrgContent getContentById(Integer orgId, Integer Id) {

        OrgContent orgContent = orgContentTypeRepository.findByIdAndOrgId(Id, orgId);
        return orgContent;
    }

    public Boolean setActivation(Integer id, HttpServletRequest httpServletRequest, Integer value, Integer orgId) {
        try {
            OrgContent orgContent = orgContentTypeRepository.findByIdAndOrgId(id, orgId);
            if (orgContent != null) {
                orgContent.setStatus(value);
                orgContent.setContentSetting(httpServletRequest.getParameter(Constants.JSON_INPUT));
                orgContentTypeRepository.save(orgContent);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public Boolean updateContent(Integer orgId, HttpServletRequest httpServletRequest, Integer id) {
        OrgContent orgContent = orgContentTypeRepository.findByIdAndOrgId(id, orgId);
        if (orgContent != null) {
            orgContent.setName(httpServletRequest.getParameter("name"));
            orgContent.setDescription(httpServletRequest.getParameter("description"));
            orgContent.setContentSetting(httpServletRequest.getParameter(Constants.JSON_INPUT));
            orgContentTypeRepository.save(orgContent);
            return true;
        }
        return false;
    }

    public Content getSubOrgContents(Integer subOrgId) {
        Content content = new Content();
        return content;
    }

    public List<OrgContent> getOrgContents(Integer orgId) {
        return orgContentTypeRepository.findAllByOrgIdAndIsAvailableAndIsConfigurable(orgId, 1, 1);
    }
}

