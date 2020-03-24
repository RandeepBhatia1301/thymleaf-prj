package org.ril.hrss.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.ril.hrss.model.Department;
import org.ril.hrss.model.Org;
import org.ril.hrss.model.SubOrg;
import org.ril.hrss.model.auth.User;
import org.ril.hrss.model.category_hierarchy.*;
import org.ril.hrss.model.roles_and_access.CommunityRole;
import org.springframework.data.domain.Page;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class HierarchyLevel1Util {

    public static String indexModel(Model model, Page<CategoryHierarchy> categoryHierarchies, List<CategoryHierarchy> categoryHierarchiesHybrid) {
        model.addAttribute(Constants.CATEGORY_LIST, categoryHierarchies);
        model.addAttribute(Constants.CAN_CREATE_COMMUNITY, Constants.ONE);
        if (categoryHierarchies.getTotalPages()>0){
            model.addAttribute(Constants.PAGE, categoryHierarchies);
        }
        model.addAttribute("hybridCategory", categoryHierarchiesHybrid);
        return "community/community-list";
    }

    public static String indexSubOrgHybridModel(Model model, List<CategoryHierarchy> categoryHierarchiesHybrid) {
        model.addAttribute(Constants.CATEGORY_LIST, categoryHierarchiesHybrid);
        model.addAttribute(Constants.CAN_CREATE_COMMUNITY, Constants.ZERO);
        /*model.addAttribute(Constants.PAGE, categoryHierarchiesHybrid);*/
        return "community/community-list-hybrid-subOrg";
    }

    public static String indexHybridModel(Model model, Integer canCreateCommunity, Page<CategoryHierarchy> categoryHierarchies) {
        model.addAttribute(Constants.CATEGORY_LIST, categoryHierarchies);
        model.addAttribute(Constants.CAN_CREATE_COMMUNITY, canCreateCommunity);
        if (categoryHierarchies.getTotalPages()>0){
            model.addAttribute(Constants.PAGE, categoryHierarchies);
        }

        return "community/community-hybrid-list";
    }

    public static String createModel(Model model, List<SubOrg> subOrgs, List<CategoryType> categoryTypes, List<CommunityRole> roles, List<Department> departments) {
        model.addAttribute(Constants.SUB_ORGS, subOrgs);
        model.addAttribute(Constants.CATEGORY_TYPES, categoryTypes);
        model.addAttribute("roles", roles);
        model.addAttribute("department", departments);
        return "community/add-community";
    }

    public static String createHybridModel(Model model, List<SubOrg> subOrgs, List<CategoryType> categoryTypes, List<CommunityRole> roles) {
        model.addAttribute(Constants.SUB_ORGS, subOrgs);
        model.addAttribute(Constants.CATEGORY_TYPES, categoryTypes);
        model.addAttribute("roles", roles);
        return "community/add-hybrid-community";
    }

    public static CategoryHierarchy create(HttpServletRequest httpServletRequest, String filePath, Integer orgId, Integer adminId, Integer isHybrid, Integer subOrgId, CategoryType categoryType) {
        CategoryHierarchy categoryHierarchy = new CategoryHierarchy();

        categoryHierarchy.setTitle(httpServletRequest.getParameter(Constants.TITLE));
        if (filePath != null) {
            categoryHierarchy.setImagePath(filePath);
        }
        categoryHierarchy.setDescription(httpServletRequest.getParameter(Constants.DESCRIPTION));
        categoryHierarchy.setCategoryType(categoryType);
        categoryHierarchy.setIsPrivate(Integer.valueOf(httpServletRequest.getParameter(Constants.IS_PRIVATE)));
        categoryHierarchy.setParentId(Constants.ZERO);
        categoryHierarchy.setOrganizationId(orgId);
        categoryHierarchy.setStatus(Constants.ONE);
        categoryHierarchy.setIsHybrid(isHybrid);
        categoryHierarchy.setCreatedBy(adminId);
        categoryHierarchy.setSuborgId(subOrgId);
        return categoryHierarchy;
    }

    public static CategoryHierarchy createHybrid(HttpServletRequest httpServletRequest, String filePath, Integer orgId, Integer adminId, Integer isHybrid, Integer subOrgId, CategoryType categoryType) {
        CategoryHierarchy categoryHierarchy = new CategoryHierarchy();

        categoryHierarchy.setTitle(httpServletRequest.getParameter(Constants.TITLE));
        if (filePath != null) {
            categoryHierarchy.setImagePath(filePath);
        }
        categoryHierarchy.setDescription(httpServletRequest.getParameter(Constants.DESCRIPTION));
        categoryHierarchy.setCategoryType(categoryType);
        categoryHierarchy.setIsPrivate(Integer.valueOf(httpServletRequest.getParameter(Constants.IS_PRIVATE)));
        categoryHierarchy.setParentId(Constants.ZERO);
        categoryHierarchy.setOrganizationId(orgId);
        categoryHierarchy.setStatus(Constants.ONE);
        categoryHierarchy.setIsHybrid(isHybrid);
        categoryHierarchy.setCreatedBy(adminId);
        categoryHierarchy.setSuborgId(subOrgId);
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

    public static String setFilePath(String imgUrl, String bannerImgUrl, String svgImgUrl, HttpServletRequest httpServletRequest) throws JsonProcessingException {

        if (imgUrl == null) {
            imgUrl = httpServletRequest.getParameter(Constants.COVER);
        }
        if (bannerImgUrl == null) {
            bannerImgUrl = httpServletRequest.getParameter(Constants.BANNER);
        }
        if (svgImgUrl == null) {
            svgImgUrl = httpServletRequest.getParameter(Constants.SVG);
        }

        ImageUploadJson imageUploadJson = new ImageUploadJson();
        imageUploadJson.setImgUrl(imgUrl);
        imageUploadJson.setBannerImgUrl(bannerImgUrl);
        imageUploadJson.setSvgImgUrl(svgImgUrl);
        return new EntityToJsonUtility().getEntityJson(imageUploadJson);
    }


    public static String editModel(Model model, Integer id, List<SubOrg> subOrgs, List<CategoryType> categoryTypes, CategoryHierarchy categoryHierarchy, Map map, List<User> users, List<CommunityRole> roles, List<User> userSme, List<Department> departments, List<CategoryHierarchyDepartment> list, Integer adminRoleId, Integer smeRoleId) throws JsonProcessingException {
        List<Integer> ids = list.stream().map(CategoryHierarchyDepartment::getDepartmentId).collect(Collectors.toList());
        model.addAttribute(Constants.ID, id);
        model.addAttribute(Constants.SUB_ORGS, subOrgs);
        model.addAttribute(Constants.CATEGORY_TYPES, categoryTypes);
        model.addAttribute(Constants.CATEGORY_HIERARCHY, categoryHierarchy);
        model.addAttribute(Constants.IMAGE_UPLOAD_JSON, HierarchyLevel1Util.createImageEntity(categoryHierarchy));
        model.addAttribute(Constants.COMMUNITY_IMAGE_LOCATION, map.get(Constants.DOWNLOAD_BASE_PATH) + Constants.SEPARATOR + map.get(Constants.FOLDER_NAME));
        model.addAttribute("users", users);
        if (userSme.isEmpty()) {
            User user = new User();
            userSme.add(user);
        }
        if (users.isEmpty()) {
            User user = new User();
            users.add(user);
        }
        model.addAttribute("usersSme", userSme);
        model.addAttribute("roles", roles);
        model.addAttribute("department", departments);
        model.addAttribute("selectedDept", ids);
        model.addAttribute("adminRoleId", adminRoleId);
        model.addAttribute("smeRoleId", smeRoleId);
        return "community/edit-community";
    }

    public static String editHybridModel(Model model, Integer id, List<SubOrg> subOrgs, List<CategoryType> categoryTypes, CategoryHierarchy categoryHierarchy, Map map, List<User> users, List<CommunityRole> roles, List<User> userSme, List<SubOrg> list, Integer adminRoleId, Integer smeRoleId) throws JsonProcessingException {
        model.addAttribute(Constants.ID, id);
        model.addAttribute(Constants.SUB_ORGS, subOrgs);
        model.addAttribute(Constants.CATEGORY_TYPES, categoryTypes);
        model.addAttribute(Constants.CATEGORY_HIERARCHY, categoryHierarchy);
        model.addAttribute(Constants.IMAGE_UPLOAD_JSON, HierarchyLevel1Util.createImageEntity(categoryHierarchy));
        model.addAttribute(Constants.COMMUNITY_IMAGE_LOCATION, map.get(Constants.DOWNLOAD_BASE_PATH) + Constants.SEPARATOR + map.get(Constants.FOLDER_NAME));
        if (userSme.isEmpty()) {
            User user = new User();
            userSme.add(user);
        }
        if (users.isEmpty()) {
            User user = new User();
            users.add(user);
        }
        model.addAttribute("users", users);
        model.addAttribute("usersSme", userSme);
        model.addAttribute("roles", roles);
        model.addAttribute("subOrgList", list);
        model.addAttribute("adminRoleId", adminRoleId);
        model.addAttribute("smeRoleId", smeRoleId);
        return "community/edit-hybrid-community";
    }

    private static ImageUploadJson createImageEntity(CategoryHierarchy categoryHierarchy) throws JsonProcessingException {
        String imageStr = categoryHierarchy.getImagePath();
        return new Gson().fromJson(imageStr, ImageUploadJson.class);
    }

    public static String storeSuccessModel(Integer status, RedirectAttributes redirectAttributes) {

        if (status == Constants.TWO) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.SUCCESS);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, Constants.COMMUNITY_SUCCESS);
        } else if (status == Constants.ONE) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, Constants.COMMUNITY_EXHAUST_LIMIT);
        } else {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
        }
        return "redirect:/community?v=%$feer34ed";
    }

    public static String storeHybridSuccessModel(Integer status, RedirectAttributes redirectAttributes) {

        if (status == Constants.TWO) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.SUCCESS);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, Constants.COMMUNITY_SUCCESS);
        } else if (status == Constants.ONE) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, Constants.COMMUNITY_EXHAUST_LIMIT);
        } else {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
        }
        return "redirect:/hybridCommunity?v=#$^t567rtrt";
    }

    public static String updateSuccessModel(Boolean status, RedirectAttributes redirectAttributes) {
        if (status) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.SUCCESS);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, Constants.COMMUNITY_UPDATE_SUCCESS);
        } else {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
        }
        return "redirect:/community?v=%$fe345yu";

    }

    public static String updateHybridSuccessModel(Boolean status, RedirectAttributes redirectAttributes) {
        if (status) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.SUCCESS);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, Constants.COMMUNITY_UPDATE_SUCCESS);
        } else {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
        }
        return "redirect:/hybridCommunity?v=#$^t567#$";

    }

    public static CategoryHierarchy update(CategoryHierarchy categoryHierarchy, HttpServletRequest httpServletRequest, String filePath, CategoryType categoryType) {

        categoryHierarchy.setTitle(httpServletRequest.getParameter(Constants.TITLE));

        categoryHierarchy.setDescription(httpServletRequest.getParameter(Constants.DESCRIPTION));
        categoryHierarchy.setCategoryType(categoryType);
        categoryHierarchy.setIsPrivate(Integer.valueOf(httpServletRequest.getParameter(Constants.IS_PRIVATE)));

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

    public static Boolean checkCommunityCount(Optional<Org> org, Integer currentCount) {
        Integer limitCount = Constants.ZERO;
        if (org.isPresent()) {
            Org org1 = org.get();
            limitCount = org1.getCommunity_limit();
        }
        if (currentCount < limitCount) {
            return true;
        }
        return false;
    }

    public static Integer checkCreateCommunityPermission(Optional<Org> org) {
        Integer canCreateCommunity = Constants.ZERO;
        if (org.isPresent()) {
            Org org1 = org.get();
            canCreateCommunity = org1.getCan_create_coummunity();
        }

        return canCreateCommunity;
    }

    public static Model createIndexModel(Page<CategoryHierarchy> categoryHierarchies, Model model) {

        model.addAttribute(Constants.CATEGORY_LIST, categoryHierarchies);
        model.addAttribute(Constants.PAGE, categoryHierarchies);
        return model;
    }

    public static Pageable paginate(HttpServletRequest httpServletRequest) {
        int page = httpServletRequest.getParameterMap().containsKey(Constants.PAGE) ? Integer.valueOf(httpServletRequest.getParameter(Constants.PAGE)) : Constants.ZERO;
        Integer perPage = Constants.PAGE_SIZE_TEN;
        return PageRequest.of(page, perPage, Sort.Direction.DESC, "createdAt");
    }

}
