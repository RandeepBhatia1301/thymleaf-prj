package org.ril.hrss.service.moderation;

import org.ril.hrss.model.category_hierarchy.CategoryHierarchy;
import org.ril.hrss.model.content.OrgContent;
import org.ril.hrss.model.content.SubOrgContent;
import org.ril.hrss.model.moderation.ContentApprovalConfiguration;
import org.ril.hrss.repository.CategoryHierarchyRepository;
import org.ril.hrss.repository.ContentApprovalConfigurationRepo;
import org.ril.hrss.repository.SubOrgContentTypeRepository;
import org.ril.hrss.service.content.SubOrgContentService;
import org.ril.hrss.utility.ApprovalUtil;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ControlPanelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ContentModerationService {
    @Autowired
    ContentApprovalConfigurationRepo contentApprovalConfigurationRepo;

    @Autowired
    CategoryHierarchyRepository categoryHierarchyRepository;

    @Autowired
    SubOrgContentTypeRepository subOrgContentTypeRepository;

    @Autowired
    SubOrgContentService subOrgContentService;

    public String index(Model model, HttpServletRequest httpServletRequest) {
        List<LinkedHashMap> hashMapList = this.getModeratedCommunity(httpServletRequest);
        List<OrgContent> subOrgContents = subOrgContentService.getSubOrgContent(ControlPanelUtil.setSubOrgId(httpServletRequest), ControlPanelUtil.setOrgId(httpServletRequest));
        return ApprovalUtil.indexModel(model, subOrgContents, hashMapList);
    }

    public String contentApprovalEdit(HttpServletRequest httpServletRequest, Model model, String categoryName, Integer contentId) {
        if (contentId == null) {
            contentId = 1;
        }
        List<CategoryHierarchy> categoryHierarchies = categoryHierarchyRepository.findBySuborgIdAndParentId(ControlPanelUtil.setSubOrgId(httpServletRequest), Constants.ZERO);
        CategoryHierarchy c = categoryHierarchies.stream()
                .filter(categoryHierarchy -> categoryName.trim().equals(categoryHierarchy.getTitle().trim())).findAny().orElse(null);

        ContentApprovalConfiguration configuration = new ContentApprovalConfiguration();
        Integer categoryId = 0;
        if (c != null) {
            categoryId = c.getId();
            configuration = contentApprovalConfigurationRepo.findBySubOrgIdAndCategoryIdAndContentTypeId(1, categoryId, contentId);
        }
        List<OrgContent> subOrgContents = subOrgContentService.getSubOrgContent(ControlPanelUtil.setSubOrgId(httpServletRequest), ControlPanelUtil.setOrgId(httpServletRequest));
        return ApprovalUtil.editContentApproval(model, categoryHierarchies, subOrgContents, configuration, categoryId, contentId);
    }

    /*old edit model*/

    /* public String contentEdit(HttpServletRequest httpServletRequest, Model model, Integer contentId, Integer categoryId, String contentTypeName) throws IOException {
         Integer approvalOrder = null;
         ContentApprovalConfiguration contentApprovalConfiguration = contentApprovalConfigurationRepo.findBySubOrgIdAndCategoryIdAndContentTypeId(ControlPanelUtil.setSubOrgId(httpServletRequest), categoryId, contentId);

         if (contentApprovalConfiguration != null) {
             approvalOrder = contentApprovalConfiguration.getApprovalOrder();
         }
         Integer tagLevel = this.getTagedLevel(contentId, ControlPanelUtil.setSubOrgId(httpServletRequest));
         List<CategoryHierarchy> categoryHierarchies = categoryHierarchyRepository.findBySuborgIdAndParentId(ControlPanelUtil.setSubOrgId(httpServletRequest), Constants.ZERO);

         return ApprovalUtil.edit(model, contentId, categoryId, approvalOrder, tagLevel, categoryHierarchies, contentApprovalConfiguration, contentTypeName);
     }
 */
   /* public String moderateContent(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        try {
            Integer contentTypeId = Integer.valueOf(httpServletRequest.getParameter(Constants.ID));
            if (Integer.valueOf(httpServletRequest.getParameter(Constants.ALL_COMMUNITIES)) != null && Integer.valueOf(httpServletRequest.getParameter(Constants.ALL_COMMUNITIES)) == Constants.ONE) {
                return this.setModerationForAll(httpServletRequest, contentTypeId, ControlPanelUtil.setAdminId(httpServletRequest), ControlPanelUtil.setSubOrgId(httpServletRequest), redirectAttributes);
            }
            Integer hierarchyId = Integer.valueOf(httpServletRequest.getParameter(Constants.CATEGORY_ID));
            ContentApprovalConfiguration contentApprovalConfiguration = contentApprovalConfigurationRepo.findBySubOrgIdAndCategoryIdAndContentTypeId(ControlPanelUtil.setSubOrgId(httpServletRequest), hierarchyId, contentTypeId);
            if (contentApprovalConfiguration != null) {
                contentApprovalConfiguration.setApprovalOrder(Integer.valueOf(httpServletRequest.getParameter(Constants.NUMBER_OF_LEVEL)));
                contentApprovalConfigurationRepo.save(contentApprovalConfiguration);
            } else {
                contentApprovalConfigurationRepo.save(ApprovalUtil.createConfiguration(httpServletRequest, ControlPanelUtil.setSubOrgId(httpServletRequest), hierarchyId, ControlPanelUtil.setAdminId(httpServletRequest)));

            }
            return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, Constants.MODERATED_SUCCESSFULLY, "redirect:/moderation");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.SOMETHING_WENT_WRONG, "redirect:/moderation");
        }

    }*/

    public String storeModeration(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        if (Integer.valueOf(httpServletRequest.getParameter(Constants.ALL_COMMUNITIES)) != null && Integer.valueOf(httpServletRequest.getParameter(Constants.ALL_COMMUNITIES)) == Constants.ONE) {
            return this.setApprovalForAll(httpServletRequest, redirectAttributes);
        }
        contentApprovalConfigurationRepo.deleteBySubOrgIdAndContentTypeIdAndCategoryId(ControlPanelUtil.setSubOrgId(httpServletRequest), Integer.valueOf(httpServletRequest.getParameter("contentId")), Integer.valueOf(httpServletRequest.getParameter("categoryId")));
        contentApprovalConfigurationRepo.save(ApprovalUtil.storeModeration(httpServletRequest));
        String referer = httpServletRequest.getHeader(Constants.REFERER);
        return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, Constants.MODERATED_SUCCESSFULLY, Constants.REDIRECT + referer);
    }

    private String setApprovalForAll(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        List<CategoryHierarchy> categoryHierarchies = categoryHierarchyRepository.findBySuborgIdAndParentId(ControlPanelUtil.setSubOrgId(httpServletRequest), 0);
        contentApprovalConfigurationRepo.deleteAllBySubOrgIdAndContentTypeIdAndCategoryIdIn(ControlPanelUtil.setSubOrgId(httpServletRequest), Integer.valueOf(httpServletRequest.getParameter("contentId")), ApprovalUtil.streamId(categoryHierarchies));
        contentApprovalConfigurationRepo.saveAll(ApprovalUtil.setApprovalForAll(ApprovalUtil.streamId(categoryHierarchies), httpServletRequest));
        String referer = httpServletRequest.getHeader(Constants.REFERER);
        return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, Constants.MODERATED_SUCCESSFULLY, Constants.REDIRECT + referer);
    }

  /*  private String setModerationForAll(HttpServletRequest httpServletRequest, Integer contentTypeId, Integer adminId, Integer subOrgId, RedirectAttributes redirectAttributes) {
        try {
            List<CategoryHierarchy> categoryHierarchies = categoryHierarchyRepository.findBySuborgIdAndParentId(subOrgId, 0);
            List<Integer> collect = categoryHierarchies.stream()
                    .map(categoryHierarchy -> categoryHierarchy.getId())
                    .collect(Collectors.toList());
            for (Integer categoryId : collect) {
                ContentApprovalConfiguration contentApprovalConfiguration = contentApprovalConfigurationRepo.findBySubOrgIdAndCategoryIdAndContentTypeId(subOrgId, categoryId, contentTypeId);
                if (contentApprovalConfiguration != null) {
                    contentApprovalConfiguration.setCategoryId(categoryId);
                    contentApprovalConfiguration.setApprovalOrder(Integer.valueOf(httpServletRequest.getParameter(Constants.NUMBER_OF_LEVEL)));
                    contentApprovalConfigurationRepo.save(contentApprovalConfiguration);

                } else {
                    ContentApprovalConfiguration contentApprovalConfiguration1 = new ContentApprovalConfiguration();
                    *//*contentApprovalConfiguration1.setCategoryLevel(Constants.ONE);*//*
                    contentApprovalConfiguration1.setApprovalOrder(Integer.valueOf(httpServletRequest.getParameter(Constants.NUMBER_OF_LEVEL)));
                    contentApprovalConfiguration1.setSubOrgId(subOrgId);
                    contentApprovalConfiguration1.setCategoryId(categoryId);
                    contentApprovalConfiguration1.setContentTypeId(Integer.valueOf(httpServletRequest.getParameter(Constants.ID)));
                    contentApprovalConfiguration1.setCreatedBy(adminId);
                    contentApprovalConfiguration1.setUpdatedBy(adminId);
                    contentApprovalConfigurationRepo.save(contentApprovalConfiguration1);
                }
            }

          *//*  Integer deleteStatus = contentApprovalConfigurationRepo.deleteAllBySubOrgIdAndContentTypeIdAndCategoryIdIn(subOrgId, contentTypeId, collect);

            for (Integer categoryId : collect) {
                contentApprovalConfigurationRepo.save(ApprovalUtil.createConfiguration(httpServletRequest, subOrgId, categoryId, adminId));
            }*//*
            return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, Constants.MODERATED_SUCCESSFULLY, "redirect:/moderation");

        } catch (Exception ex) {
            System.out.println("in catch");
            ex.printStackTrace();
            return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.SOMETHING_WENT_WRONG, "redirect:/moderation");
        }
    }*/

   /* private Integer getTagedLevel(Integer id, Integer subOrgId) throws IOException {
        Integer tagLevel = Constants.ZERO;
        SubOrgContent subOrgContent = subOrgContentTypeRepository.findBySubOrgIdAndContentTypeId(subOrgId, id);
        Map map = new HashMap<>();
        // convert JSON string to Map
        ObjectMapper mapper = new ObjectMapper();
        map = mapper.readValue(subOrgContent.getContentSetting(), new TypeReference<Map>() {
        });
        if (map != null) {
            tagLevel = (Integer) map.get(Constants.TAGGED_AT_LEVEL);
        }
        return tagLevel;
    }*/

    private List<LinkedHashMap> getModeratedCommunity(HttpServletRequest httpServletRequest) {
        List<CategoryHierarchy> categoryHierarchies = categoryHierarchyRepository.findBySuborgIdAndParentId(ControlPanelUtil.setSubOrgId(httpServletRequest), 0);
        List<SubOrgContent> subOrgContents = subOrgContentTypeRepository.findBySubOrgIdAndIsAvailableAndIsConfigurable(ControlPanelUtil.setSubOrgId(httpServletRequest), 1, 1);
        List<LinkedHashMap> hashMapList = new ArrayList<>();
        categoryHierarchies.forEach(categoryHierarchy -> {
            LinkedHashMap map = new LinkedHashMap();
            map.put("name", categoryHierarchy.getTitle());
            subOrgContents.forEach(subOrgContent -> {
                ContentApprovalConfiguration approvalConfigurations = contentApprovalConfigurationRepo.findBySubOrgIdAndCategoryIdAndContentTypeId(ControlPanelUtil.setSubOrgId(httpServletRequest), categoryHierarchy.getId(), subOrgContent.getContentTypeId());
                map.put(subOrgContent.getContentTypeId(), 0);
                if (approvalConfigurations != null && approvalConfigurations.getApprovalOrder() != 0) {
                    map.put(subOrgContent.getContentTypeId(), 1);
                }
            });
            hashMapList.add(map);
        });
        return hashMapList;
    }
}

