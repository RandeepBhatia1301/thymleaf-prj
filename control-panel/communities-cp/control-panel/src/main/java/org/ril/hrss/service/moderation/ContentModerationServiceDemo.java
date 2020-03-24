/*
package com.ril.svc.service.moderation;

import CategoryHierarchy;
import ContentApprovalConfiguration;
import ContentApprovalConfigurationRepo;
import HierarchyLevel1Service;
import Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContentModerationService {
    @Autowired
    ContentApprovalConfigurationRepo contentApprovalConfigurationRepo;
    @Autowired
    HierarchyLevel1Service hierarchyLevel1Service;

    public List<ContentApprovalConfiguration> getModeratedData(Integer contentId, Integer orgId, HttpServletRequest httpServletRequest, Integer categoryId, Integer subOrgId) {
        List<ContentApprovalConfiguration> contentApprovalConfigurations = new ArrayList<>();

        if (categoryId != null) {
            contentApprovalConfigurations = contentApprovalConfigurationRepo.findByOrgIdAndHierarchyIdAndContentTypeIdAndSubOrgId(orgId, categoryId, contentId,subOrgId);
        }
        return contentApprovalConfigurations;
    }

    public Boolean moderateContent(HttpServletRequest httpServletRequest, Integer orgId, Integer adminId, Integer subOrgId) {
        Integer noOfLevels = Integer.valueOf(httpServletRequest.getParameter(Constants.NUMBER_OF_LEVEL));
        Integer contentTypeId = Integer.valueOf(httpServletRequest.getParameter(Constants.ID));

        if (Integer.valueOf(httpServletRequest.getParameter(Constants.ALL_COMMUNITIES)) != null && Integer.valueOf(httpServletRequest.getParameter(Constants.ALL_COMMUNITIES)) == Constants.ONE) {
                */
/*set moderation all for communities*//*

            return this.setModerationForAll(httpServletRequest, orgId, contentTypeId, noOfLevels, adminId,subOrgId);

        }
        Integer hierarchyId = Integer.valueOf(httpServletRequest.getParameter(Constants.CATEGORY_ID));
        Integer status = contentApprovalConfigurationRepo.deleteAllByOrgIdAndHierarchyIdAndContentTypeId(orgId, hierarchyId, contentTypeId);
        for (int i = Constants.ONE; i <= noOfLevels; i++) {
            ContentApprovalConfiguration contentApprovalConfiguration = new ContentApprovalConfiguration();
            contentApprovalConfiguration.setHierarchyLevelType(Constants.ONE);
            contentApprovalConfiguration.setApprovalOrder(i);
            contentApprovalConfiguration.setOrgId(orgId);
            contentApprovalConfiguration.setSubOrgId(subOrgId);
            contentApprovalConfiguration.setHierarchyId(hierarchyId);
            contentApprovalConfiguration.setContentTypeId(Integer.valueOf(httpServletRequest.getParameter(Constants.ID)));
            contentApprovalConfiguration.setApprovalRoleId(Integer.valueOf(httpServletRequest.getParameter(Constants.LEVEL_ONE_ROLE_ID)));
            contentApprovalConfiguration.setCreatedBy(adminId);
            contentApprovalConfiguration.setUpdatedBy(adminId);
            if (i == Constants.TWO) {
                contentApprovalConfiguration.setApprovalRoleId(Integer.valueOf(httpServletRequest.getParameter(Constants.LEVEL_TWO_ROLE_ID)));
            }
            contentApprovalConfiguration.setCreatedBy(Integer.valueOf(httpServletRequest.getParameter(Constants.ID)));
            contentApprovalConfigurationRepo.save(contentApprovalConfiguration);

        }

        return true;

    }

    private Boolean setModerationForAll(HttpServletRequest httpServletRequest, Integer orgId, Integer contentTypeId, Integer noOfLevels, Integer adminId, Integer subOrgId) {
        try {
            */
/*get categoryIds to be deleted*//*

            List<CategoryHierarchy> categoryHierarchies = hierarchyLevel1Service.getCategoryHierarchy(orgId);
            List<Integer> collect = categoryHierarchies.stream()
                    .map(categoryHierarchy -> categoryHierarchy.getId())
                    .collect(Collectors.toList());
            */
/*delete data for current ids*//*

            Integer deleteStatus = contentApprovalConfigurationRepo.deleteAllByOrgIdAndContentTypeIdAndHierarchyIdIn(orgId, contentTypeId, collect);
            for (Integer id : collect) {
                for (int i = Constants.ONE; i <= noOfLevels; i++) {
                    ContentApprovalConfiguration contentApprovalConfiguration = new ContentApprovalConfiguration();
                    contentApprovalConfiguration.setHierarchyLevelType(Constants.ONE);
                    contentApprovalConfiguration.setApprovalOrder(i);
                    contentApprovalConfiguration.setOrgId(orgId);
                    contentApprovalConfiguration.setSubOrgId(subOrgId);
                    contentApprovalConfiguration.setHierarchyId(id);
                    contentApprovalConfiguration.setContentTypeId(Integer.valueOf(httpServletRequest.getParameter(Constants.ID)));
                    contentApprovalConfiguration.setApprovalRoleId(Integer.valueOf(httpServletRequest.getParameter(Constants.LEVEL_ONE_ROLE_ID)));
                    contentApprovalConfiguration.setCreatedBy(adminId);
                    contentApprovalConfiguration.setUpdatedBy(adminId);
                    if (i == Constants.TWO) {
                        contentApprovalConfiguration.setApprovalRoleId(Integer.valueOf(httpServletRequest.getParameter(Constants.LEVEL_TWO_ROLE_ID)));
                    }
                    contentApprovalConfiguration.setCreatedBy(Integer.valueOf(httpServletRequest.getParameter(Constants.ID)));
                    contentApprovalConfigurationRepo.save(contentApprovalConfiguration);
                }

            }
            return true;
        } catch (Exception ex) {
            return false;
        }

    }
}
*/
