package org.ril.hrss.service.content;

import org.ril.hrss.model.content.OrgContent;
import org.ril.hrss.model.content.SubOrgContent;
import org.ril.hrss.repository.OrgContentTypeRepository;
import org.ril.hrss.repository.SubOrgContentTypeRepository;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ContentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class SubOrgContentService {

    @Autowired
    SubOrgContentTypeRepository subOrgContentTypeRepository;

    @Autowired
    OrgContentsService orgContentsService;

    @Autowired
    OrgContentTypeRepository orgContentTypeRepository;

    public SubOrgContent getContentById(Integer id, Integer subOrgId) {
        SubOrgContent subOrgContent = subOrgContentTypeRepository.findByIdAndSubOrgId(id, subOrgId);
        subOrgContent.getOrgContentTypeId();
        OrgContent orgContent1 = null;
        Optional<OrgContent> orgContent = orgContentTypeRepository.findById(subOrgContent.getOrgContentTypeId());
        if (orgContent.isPresent()) {
            orgContent1 = orgContent.get();
        }

        return subOrgContentTypeRepository.findByIdAndSubOrgId(id, subOrgId);
    }

    public List<OrgContent> getSubOrgContent(Integer subOrgId, Integer orgId) {
        List<OrgContent> orgContents = orgContentsService.getOrgContents(orgId);
        //  List<SubOrgContent> subOrgContents = subOrgContentTypeRepository.findBySubOrgIdAndIsAvailable(subOrgId, Constants.ONE);
        List<SubOrgContent> subOrgContents = subOrgContentTypeRepository.findBySubOrgIdAndIsAvailableAndIsConfigurable(subOrgId, Constants.ONE, Constants.ONE);
        return ContentUtil.getSubOrgContent(subOrgContents, orgContents);
    }

    public Boolean updateContent(Integer subOrgId, HttpServletRequest httpServletRequest, Integer id) {
        try {
            SubOrgContent subOrgContent = subOrgContentTypeRepository.findByIdAndSubOrgId(id, subOrgId);
            subOrgContent.setContentSetting(httpServletRequest.getParameter(Constants.JSON_INPUT));
            subOrgContentTypeRepository.save(subOrgContent);

            setDisplayOrder(id, subOrgId, httpServletRequest);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Boolean setActivation(Integer id, HttpServletRequest httpServletRequest, Integer value, Integer subOrgId) {
        try {
            SubOrgContent subOrgContent = subOrgContentTypeRepository.findByIdAndSubOrgId(id, subOrgId);
            subOrgContent.setStatus(value);
            subOrgContentTypeRepository.save(subOrgContent);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void setDisplayOrder(Integer id, Integer subOrgId, HttpServletRequest httpServletRequest) {

        SubOrgContent subOrgContent = subOrgContentTypeRepository.findByIdAndSubOrgId(id, subOrgId);

        Integer currentDisplayOrder = subOrgContent.getDisplayOrder();

        Integer orderUI = Integer.valueOf(httpServletRequest.getParameter("displayOrder"));

        SubOrgContent subOrgContent1 = subOrgContentTypeRepository.findByDisplayOrderAndSubOrgId(orderUI, subOrgId);
        subOrgContent1.setDisplayOrder(currentDisplayOrder);
        subOrgContentTypeRepository.save(subOrgContent1);

        subOrgContent.setDisplayOrder(orderUI);
        subOrgContentTypeRepository.save(subOrgContent);

    }
}



