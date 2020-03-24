/*
package com.ril.svc.service.moderation;

import User;
import CategoryHierarchy;
import OrgContent;
import HierarchyModeration;
import ModerationRepository;
import CategorySubscriptionService;
import HierarchyLevel1Service;
import HierarchyLevel2Service;
import SubOrgContentService;
import Constants;
import ControlPanelUtil;
import com.ril.svc.utility.ModerationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModerationService {
    @Autowired
    ModerationRepository moderationRepository;
    @Autowired
    HierarchyLevel1Service hierarchyLevel1Service;
    @Autowired
    HierarchyLevel2Service hierarchyLevel2Service;
    @Autowired
    SubOrgContentService subOrgContentService;
    @Autowired
    CategorySubscriptionService categorySubscriptionService;

*/
/*
    public String index(Model model, HttpServletRequest httpServletRequest, HttpSession httpSession) {
        List<OrgContent> subOrgContents = subOrgContentService.getSubOrgContent(ControlPanelUtil.setSubOrgId(httpServletRequest), ControlPanelUtil.setOrgId(httpServletRequest));
        return ModerationUtil.indexModel(model, subOrgContents);
    }*//*



  */
/*  public String createModel(Model model, HttpServletRequest httpServletRequest, HttpSession httpSession, Integer id) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }

        Integer selectedCategory = id;
        Integer joinCommunity = null;
        Integer moderatedBy = null;

        HierarchyModeration hierarchyModeration = new HierarchyModeration();

        Integer subOrgId = (Integer) httpSession.getAttribute(Constants.SUB_ORG_ID);
        if (id != null) {
            hierarchyModeration = this.getCategoryData(id);
            if (hierarchyModeration != null) {
                selectedCategory = hierarchyModeration.getCatergoryHierarchyId();
                joinCommunity = hierarchyModeration.getCanJoinCategory();
                moderatedBy = hierarchyModeration.getModeratedBy();
            }
        }
        *//*
*/
/*List<CategoryHierarchy> categoryHierarchies = hierarchyLevel1Service.getClosedCategoryHierarchy(orgId);*//*
*/
/*

        List<CategoryHierarchy> categoryHierarchies = hierarchyLevel1Service.getClosedCategoryHierarchySubOrg(subOrgId);

        *//*
*/
/*List<ModeratorRole> moderatorRoles = moderatorRoleService.getRolesByorgId(orgId);*//*
*/
/*
        *//*
*/
/*moderation by user*//*
*/
/*
        List<User> adminUser = categorySubscriptionService.getUserIds();
        *//*
*/
/*moderation by user*//*
*/
/*

        ModerationUtil.createModel(model, categoryHierarchies, hierarchyModeration, selectedCategory, joinCommunity, moderatedBy, adminUser);
        return "moderation/community";
    }
*//*


  */
/*  public Boolean create(HttpServletRequest httpServletRequest, Integer orgId, Integer subOrgId) {
        try {
            Integer allCommunities = Integer.valueOf(httpServletRequest.getParameter(Constants.ALL_COMMUNITIES));
            if (allCommunities == Constants.ONE) {
                *//*
*/
/*set moderation all for communities*//*
*/
/*
                return this.setModerationForAll(httpServletRequest, orgId, subOrgId);
            }
            Integer categoryId = Integer.valueOf(httpServletRequest.getParameter(Constants.CATEGORY_NAME));
            HierarchyModeration hierarchyModeration = moderationRepository.findByCatergoryHierarchyId(categoryId);
            if (hierarchyModeration == null) {
                HierarchyModeration hierarchyModeration1 = new HierarchyModeration();
                hierarchyModeration1.setOrgId(orgId);
                hierarchyModeration1.setSuborgId(subOrgId);
                hierarchyModeration1.setCatergoryHierarchyId(Integer.valueOf(httpServletRequest.getParameter(Constants.CATEGORY_NAME)));
                hierarchyModeration1.setCanJoinCategory(Integer.valueOf(httpServletRequest.getParameter(Constants.JOINCOMM)));
                hierarchyModeration1.setModeratedBy(Integer.valueOf(httpServletRequest.getParameter(Constants.ROLE_ID)));
                moderationRepository.save(hierarchyModeration1);
                return true;
            }
            hierarchyModeration.setCatergoryHierarchyId(Integer.valueOf(httpServletRequest.getParameter(Constants.CATEGORY_NAME)));
            hierarchyModeration.setCanJoinCategory(Integer.valueOf(httpServletRequest.getParameter(Constants.JOINCOMM)));
            hierarchyModeration.setModeratedBy(Integer.valueOf(httpServletRequest.getParameter(Constants.ROLE_ID)));
            moderationRepository.save(hierarchyModeration);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }*//*


    */
/*public HierarchyModeration getCategoryData(Integer id) {
        HierarchyModeration hierarchyModeration = moderationRepository.findByCatergoryHierarchyId(id);
        return hierarchyModeration;
    }*//*


   */
/* private boolean setModerationForAll(HttpServletRequest httpServletRequest, Integer orgId, Integer subOrgId) {
        try {
            List<CategoryHierarchy> categoryHierarchies;
            *//*
*/
/*get categoryIds to be deleted*//*
*/
/*
            if (Integer.valueOf(httpServletRequest.getParameter(Constants.IS_AOI)) == Constants.ZERO) {
                *//*
*/
/*for community for sub org admin*//*
*/
/*
                categoryHierarchies = hierarchyLevel1Service.getSubOrgCategoryHierarchy(subOrgId);
            } else {
                   *//*
*/
/*for AOI for sub org admin*//*
*/
/*
                categoryHierarchies = hierarchyLevel2Service.getSubOrgCategoryHierarchyLevel2(subOrgId);
            }
            List<Integer> collect = categoryHierarchies.stream()
                    .map(categoryHierarchy -> categoryHierarchy.getId())
                    .collect(Collectors.toList());
            *//*
*/
/*delete data for current ids*//*
*/
/*
            Integer deleteStatus = moderationRepository.deleteAllByOrgIdAndCatergoryHierarchyIdIn(orgId, collect);

            for (Integer id : collect) {
                HierarchyModeration hierarchyModeration1 = new HierarchyModeration();
                hierarchyModeration1.setOrgId(orgId);
                hierarchyModeration1.setSuborgId(subOrgId);
                hierarchyModeration1.setCatergoryHierarchyId(id);
                hierarchyModeration1.setCanJoinCategory(Integer.valueOf(httpServletRequest.getParameter(Constants.JOINCOMM)));
                hierarchyModeration1.setModeratedBy(Integer.valueOf(httpServletRequest.getParameter(Constants.ROLE_ID)));
                moderationRepository.save(hierarchyModeration1);*//*
*/
/*save new data for all*//*
*/
/*

            }
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
*//*

}
*/
