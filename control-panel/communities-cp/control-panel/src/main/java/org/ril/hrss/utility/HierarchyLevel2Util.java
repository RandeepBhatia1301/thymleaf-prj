package org.ril.hrss.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.ril.hrss.model.SubOrg;
import org.ril.hrss.model.auth.User;
import org.ril.hrss.model.category_hierarchy.CategoryHierarchy;
import org.ril.hrss.model.category_hierarchy.CategorySubscription;
import org.ril.hrss.model.category_hierarchy.CategoryType;
import org.ril.hrss.model.category_hierarchy.ImageUploadJson;
import org.ril.hrss.model.roles_and_access.CommunityRole;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.ril.hrss.utility.Constants.CATEGORY_HIERARCHY;

@Component
public class HierarchyLevel2Util {

    public static String indexModel() {
        return "community/community-list";
    }

    public static String createModel(Model model, List<SubOrg> subOrgs, List<CategoryType> categoryTypes) {
        model.addAttribute(Constants.SUB_ORGS, subOrgs);
        model.addAttribute(Constants.CATEGORY_TYPES, categoryTypes);
        return "community/add-community";
    }

    public static CategoryHierarchy create(HttpServletRequest httpServletRequest, String filePath, Integer orgId, Integer adminId, Integer isHybrid, Integer subOrgId, CategoryType categoryType) {
        CategoryHierarchy categoryHierarchy = new CategoryHierarchy();
        categoryHierarchy.setTitle(httpServletRequest.getParameter("title"));
        categoryHierarchy.setImagePath(filePath);
        categoryHierarchy.setDescription(httpServletRequest.getParameter("description"));
        categoryHierarchy.setIsPrivate(Integer.valueOf(httpServletRequest.getParameter("isPrivate")));
        categoryHierarchy.setCategoryType(categoryType);

        categoryHierarchy.setParentId(Integer.valueOf(httpServletRequest.getParameter("categoryId")));
        categoryHierarchy.setOrganizationId(orgId);
        categoryHierarchy.setSuborgId(subOrgId);
        categoryHierarchy.setStatus(1);
        categoryHierarchy.setIsHybrid(isHybrid);
        categoryHierarchy.setCreatedBy(adminId);
        return categoryHierarchy;
    }

    public static List<CategorySubscription> createCategorySubscription(List<Integer> userIds, CategoryHierarchy categoryHierarchy, Integer orgId) {
        List<CategorySubscription> categorySubscriptions = new ArrayList<>();
        for (Integer id : userIds) {
            CategorySubscription categorySubscription = new CategorySubscription();
            categorySubscription.setCategoryHierarchyId(categoryHierarchy.getId());
            categorySubscription.setUserId(Long.valueOf(id));
            categorySubscription.setOrganizationId(orgId);
            categorySubscription.setOrganizationId(orgId);
            categorySubscription.setIsAdmin(Constants.ZERO);
            categorySubscription.setStatus(Constants.ONE);
            categorySubscriptions.add(categorySubscription);
        }
        return categorySubscriptions;
    }

    public static String setFilePath(String imgUrl) throws JsonProcessingException {
        ImageUploadJson imageUploadJson = new ImageUploadJson();
        imageUploadJson.setImgUrl(imgUrl);
        return new EntityToJsonUtility().getEntityJson(imageUploadJson);
    }


    public static String editModel(Model model, Integer id, CategoryHierarchy categoryHierarchy, Map map, Set<CategoryHierarchy> categoryHierarchies, List<CommunityRole> communityRoles, List<User> users, List<User> userSme, Integer adminRoleId, Integer smeRoleId) throws JsonProcessingException {

        model.addAttribute(Constants.ID, id);
        model.addAttribute(CATEGORY_HIERARCHY, categoryHierarchy);
        model.addAttribute(Constants.CATEGORY_LIST, categoryHierarchies);
        model.addAttribute(Constants.IMAGE_UPLOAD_JSON, createImageEntity(categoryHierarchy));
        model.addAttribute(Constants.AOI_IMAGE_LOCATION, map.get(Constants.DOWNLOAD_BASE_PATH) + Constants.SEPARATOR + map.get(Constants.FOLDER_NAME));
        if (userSme.isEmpty()) {
            User user = new User();
            userSme.add(user);
        }
        if (users.isEmpty()) {
            User user = new User();
            users.add(user);
        }
        model.addAttribute("roles", communityRoles);
        model.addAttribute("users", users);
        model.addAttribute("usersSme", userSme);
        model.addAttribute("adminRoleId", adminRoleId);
        model.addAttribute("smeRoleId", smeRoleId);
        return "aoi/aoi-edit";
    }


    private static ImageUploadJson createImageEntity(CategoryHierarchy categoryHierarchy) throws JsonProcessingException {
        String imageStr = categoryHierarchy.getImagePath();
        return new Gson().fromJson(imageStr, ImageUploadJson.class);
    }

    public static String storeSuccessModel(Integer status, RedirectAttributes redirectAttributes) {

        if (status == 1) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.SUCCESS);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, Constants.AOI_SUCCESS);
        } else if (status == 2) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
        } else if (status == 3) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "The email used is not a part of the system. Please try again using a different email");
        }
        return "redirect:/aoi?v=#$67";
    }

    public static String updateSuccessModel(Integer status, RedirectAttributes redirectAttributes) {

        if (status == 1) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.SUCCESS);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, Constants.AOI_UPDATE_SUCCESS);
        } else if (status == 2) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
        } else if (status == 3) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "The email used is not a part of the system. Please try again using a different email");
        }
        return "redirect:/aoi?v=#$67";
    }

    public static CategoryHierarchy update(CategoryHierarchy categoryHierarchy, HttpServletRequest httpServletRequest, String filePath, CategoryType categoryType) {
        categoryHierarchy.setTitle(httpServletRequest.getParameter("title"));
        categoryHierarchy.setDescription(httpServletRequest.getParameter("description"));
        categoryHierarchy.setIsPrivate(Integer.valueOf(httpServletRequest.getParameter("isPrivate")));
        categoryHierarchy.setParentId(Integer.valueOf(httpServletRequest.getParameter("categoryId")));
        categoryHierarchy.setOrganizationId(ControlPanelUtil.setOrgId(httpServletRequest));
        categoryHierarchy.setSuborgId(ControlPanelUtil.setSubOrgId(httpServletRequest));
        categoryHierarchy.setStatus(1);
        if (categoryType != null) {
            categoryHierarchy.setCategoryType(categoryType);
        }
        categoryHierarchy.setCreatedBy(ControlPanelUtil.setAdminId(httpServletRequest));
        if (filePath != null) {
            categoryHierarchy.setImagePath(filePath);
        }
        return categoryHierarchy;
    }

    public static MultiValueMap upload(Integer orgId, Integer subOrgId) {
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap();
        multiValueMap.add(Constants.ORG_ID, orgId);
        if (subOrgId != null) {
            multiValueMap.add(Constants.SUB_ORG_ID, subOrgId);
        }
        multiValueMap.add(Constants.DIR_NAME, Constants.DIR_COMMUNITY);
        return multiValueMap;
    }

    public static Pageable pagination(HttpServletRequest httpServletRequest) {
        int page = httpServletRequest.getParameterMap().containsKey(Constants.PAGE) ? Integer.valueOf(httpServletRequest.getParameter(Constants.PAGE)) : 0;
        Integer perPage = 50;
        return PageRequest.of(page, perPage, Sort.Direction.DESC, "createdAt");
    }

    public static List<String> setPermissionValue() {
        List<String> permissionValue = new ArrayList<>();
        permissionValue.add("Approval to subscribe to an area of interest");
        permissionValue.add("apprvoe_reject_invite_request_polls");
        permissionValue.add("apprvoe_reject_invite_request_discussion");
        permissionValue.add("apprvoe_reject_blog");
        permissionValue.add("apprvoe_reject_invite_request_event");
        permissionValue.add("apprvoe_reject_micro_blog");
        permissionValue.add("approve_quiz");

        return permissionValue;

    }

}
