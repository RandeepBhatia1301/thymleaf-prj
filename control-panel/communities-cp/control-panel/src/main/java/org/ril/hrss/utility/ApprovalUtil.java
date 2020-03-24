package org.ril.hrss.utility;

import org.ril.hrss.model.category_hierarchy.CategoryHierarchy;
import org.ril.hrss.model.content.OrgContent;
import org.ril.hrss.model.moderation.ContentApprovalConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApprovalUtil {
    public static ContentApprovalConfiguration createConfiguration(HttpServletRequest httpServletRequest, Integer subOrgId, Integer hierarchyId, Integer adminId) {
        ContentApprovalConfiguration contentApprovalConfiguration1 = new ContentApprovalConfiguration();
        /*contentApprovalConfiguration1.setCategoryLevel(Constants.ONE);*/
        contentApprovalConfiguration1.setApprovalOrder(Integer.valueOf(httpServletRequest.getParameter(Constants.NUMBER_OF_LEVEL)));
        contentApprovalConfiguration1.setSubOrgId(subOrgId);
        contentApprovalConfiguration1.setCategoryId(hierarchyId);
        contentApprovalConfiguration1.setContentTypeId(Integer.valueOf(httpServletRequest.getParameter(Constants.ID)));
        contentApprovalConfiguration1.setCreatedBy(adminId);
        contentApprovalConfiguration1.setUpdatedBy(adminId);
        return contentApprovalConfiguration1;
    }

    public static String edit(Model model, Integer contentId, Integer categoryId, Integer approvalOrder, Integer tagLevel, List<CategoryHierarchy> categoryHierarchies, ContentApprovalConfiguration contentApprovalConfiguration, String contentTypeName) {
        model.addAttribute(Constants.ID, contentId);
        model.addAttribute(Constants.CATEGORY_LIST, categoryHierarchies);
        model.addAttribute(Constants.CONTENT_APPROVAL, contentApprovalConfiguration);
        model.addAttribute(Constants.SELECTED_CATEGORY, categoryId);
        model.addAttribute(Constants.MODERATED_BY_LEVEL, approvalOrder);
        model.addAttribute(Constants.TAG_LEVEL, tagLevel);
        model.addAttribute("contentTypeName", contentTypeName);
        return "moderation/content";
    }

    public static String editContentApproval(Model model, List<CategoryHierarchy> categoryHierarchies, List<OrgContent> subOrgContents, ContentApprovalConfiguration configuration, Integer categoryId, Integer contentTypeId) {
        model.addAttribute(Constants.CATEGORY_LIST, categoryHierarchies);
        model.addAttribute("contentList", subOrgContents);
        model.addAttribute("configuration", configuration);
        model.addAttribute("selectedCategory", categoryId);
        model.addAttribute("selectedContent", contentTypeId);
        return "moderation/content";
    }

    /*  public static String indexModel(Model model, List<OrgContent> subOrgContents, List<ContentApprovalConfiguration> contentApprovalConfigurations) {
          model.addAttribute(Constants.CONTENT_LIST, subOrgContents);
          model.addAttribute("approvalConfiguration", contentApprovalConfigurations);
          return "moderation/moderation-list-demo";

      } */

    public static String indexModel(Model model, List<OrgContent> subOrgContents, List<LinkedHashMap> hashMapList) {
        model.addAttribute(Constants.CONTENT_LIST, subOrgContents);
        model.addAttribute("approvalConfiguration", hashMapList);
        return "moderation/moderation-list";

    }

    public static ContentApprovalConfiguration storeModeration(HttpServletRequest httpServletRequest) {

        ContentApprovalConfiguration contentApprovalConfiguration = new ContentApprovalConfiguration();
        contentApprovalConfiguration.setSubOrgId(ControlPanelUtil.setSubOrgId(httpServletRequest));
        contentApprovalConfiguration.setCategoryId(Integer.valueOf(httpServletRequest.getParameter("categoryId")));
        contentApprovalConfiguration.setContentTypeId(Integer.valueOf(httpServletRequest.getParameter("contentId")));
        contentApprovalConfiguration.setApprovalOrder(Integer.valueOf(httpServletRequest.getParameter("numberOfLevel")));
        if (Integer.valueOf(httpServletRequest.getParameter("numberOfLevel")) != 0) {
            contentApprovalConfiguration.setOrder1(Integer.valueOf(httpServletRequest.getParameter("order1")));
        }
        if (Integer.valueOf(httpServletRequest.getParameter("numberOfLevel")) == 2) {
            contentApprovalConfiguration.setOrder2(Integer.valueOf(httpServletRequest.getParameter("order2")));
        }
        contentApprovalConfiguration.setCreatedBy(ControlPanelUtil.setAdminId(httpServletRequest));
        return contentApprovalConfiguration;
    }

    public static List<ContentApprovalConfiguration> setApprovalForAll(List<Integer> collect, HttpServletRequest httpServletRequest) {
        List<ContentApprovalConfiguration> list = new ArrayList<>();
        collect.forEach(integer -> {
            ContentApprovalConfiguration c = new ContentApprovalConfiguration();
            c.setSubOrgId(ControlPanelUtil.setSubOrgId(httpServletRequest));
            c.setCategoryId(integer);
            c.setContentTypeId(Integer.valueOf(httpServletRequest.getParameter("contentId")));
            c.setApprovalOrder(Integer.valueOf(httpServletRequest.getParameter("numberOfLevel")));
            if (Integer.valueOf(httpServletRequest.getParameter("numberOfLevel")) != 0) {
                c.setOrder1(Integer.valueOf(httpServletRequest.getParameter("order1")));
            }
            if (Integer.valueOf(httpServletRequest.getParameter("numberOfLevel")) == 2) {
                c.setOrder2(Integer.valueOf(httpServletRequest.getParameter("order2")));
            }
            c.setCreatedBy(ControlPanelUtil.setAdminId(httpServletRequest));
            list.add(c);
        });
        return list;
    }

    public static List<Integer> streamId(List<CategoryHierarchy> categoryHierarchies) {
        return categoryHierarchies.stream()
                .map(categoryHierarchy -> categoryHierarchy.getId())
                .collect(Collectors.toList());
    }

}
