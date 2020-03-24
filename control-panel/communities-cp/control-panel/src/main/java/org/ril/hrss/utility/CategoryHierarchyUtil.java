package org.ril.hrss.utility;

import org.ril.hrss.model.auth.User;
import org.ril.hrss.model.category_hierarchy.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static org.ril.hrss.utility.Constants.ZERO;
import static org.ril.hrss.utility.ControlPanelUtil.dateFormat;

@Component
public class CategoryHierarchyUtil {


    public static CategoryHierarchy getCategoryHierarchyById(Optional<CategoryHierarchy> categoryHierarchyOptional) {

        CategoryHierarchy categoryHierarchy1 = new CategoryHierarchy();
        if (categoryHierarchyOptional.isPresent()) {
            categoryHierarchy1 = categoryHierarchyOptional.get();
        }
        return categoryHierarchy1;
    }

    public static HashMap setElasticMap(CategoryHierarchy categoryHierarchy, CategoryType categoryType, String contentId, HttpServletRequest httpServletRequest) {

        HashMap<String, Object> hashMap = new LinkedHashMap<>();
        hashMap.put(Constants.CATEGORY_DESCRIPTION, categoryHierarchy.getDescription());
        hashMap.put(Constants.CATEGORY_NAME, categoryHierarchy.getTitle());
        hashMap.put(Constants.CATEGORY_PARENT_ID, categoryHierarchy.getParentId());
        hashMap.put(Constants.CATEGORY_TYPE, categoryType.getTitle());
        hashMap.put(Constants.CONTENT_ID, categoryHierarchy.getId());
        hashMap.put(Constants.CONTENT_MEDIA_TYPE, Constants.IMAGE);
        hashMap.put(Constants.CONTENT_MEDIA_URL, categoryHierarchy.getImagePath());
        hashMap.put(Constants.CONTENT_PUBLISH_DATE, dateFormat(categoryHierarchy.getCreatedAt()));
        hashMap.put(Constants.CONTENT_TITLE_SUGGEST, categoryHierarchy.getTitle());
        hashMap.put(Constants.CONTENT_TYPE_ID, contentId);
        hashMap.put(Constants.ORG_ID, ControlPanelUtil.setOrgId(httpServletRequest));
        if (ControlPanelUtil.setSubOrgId(httpServletRequest) != null) {
            hashMap.put(Constants.SUB_ORG_ID, ControlPanelUtil.setSubOrgId(httpServletRequest));
        }
        return hashMap;
    }

    /*public static String setAzurePrefix(CategoryHierarchy categoryHierarchy, Map mapAzure) {
        String imageStr = categoryHierarchy.getImagePath();
        ImageUploadJson imageUploadJson = new Gson().fromJson(imageStr, ImageUploadJson.class);
        String jsonFromMap = null;

        Map map = new HashMap();
        map.put("imgUrl", mapAzure.get(Constants.DOWNLOAD_BASE_PATH) + Constants.SEPARATOR + mapAzure.get(Constants.FOLDER_NAME) + imageUploadJson.getImgUrl());
        map.put("bannerImgUrl", mapAzure.get(Constants.DOWNLOAD_BASE_PATH) + Constants.SEPARATOR + mapAzure.get(Constants.FOLDER_NAME) + imageUploadJson.getBannerImgUrl());
        map.put("svgImgUrl", mapAzure.get(Constants.DOWNLOAD_BASE_PATH) + Constants.SEPARATOR + mapAzure.get(Constants.FOLDER_NAME) + imageUploadJson.getSvgImgUrl());

        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonFromMap = mapper.writeValueAsString(map);
            categoryHierarchy.setImagePath(jsonFromMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonFromMap;
    }*/

    public static User setUser(HttpServletRequest httpServletRequest, String s) {
        User user = new User();
        user.setName("test community admin");
        user.setUserExternalId(s);
        user.setEmail("admin@test.com");
        user.setFirstName("test");
        user.setLastName("test");
        user.setPassword("admin@123");
        user.setOrganizationId(ControlPanelUtil.setOrgId(httpServletRequest));
        if (ControlPanelUtil.setSubOrgId(httpServletRequest) != null) {
            user.setSubOrgId(ControlPanelUtil.setSubOrgId(httpServletRequest));
        }
        user.setStatus(Constants.ONE);

        return user;
    }

    public static CategorySubscription setCatrgorySubscription(User user, Integer categoryHierarchyId, Integer parentId, HttpServletRequest httpServletRequest) {
        CategorySubscription categorySubscription1 = new CategorySubscription();
        categorySubscription1.setCategoryHierarchyId(categoryHierarchyId);
        categorySubscription1.setUserId(user.getId());
        categorySubscription1.setOrganizationId(user.getOrganizationId());
        categorySubscription1.setStatus(Constants.ONE);
        categorySubscription1.setIsActive(Constants.ONE);
        if (user.getSubOrgId() != null) {
            categorySubscription1.setSubOrgId(user.getSubOrgId());
        }
        categorySubscription1.setParentId(parentId);
        categorySubscription1.setIsAdmin(Constants.ONE);
        categorySubscription1.setAdminRoleId(Integer.valueOf(httpServletRequest.getParameter(Constants.ROLE_ID)));
        return categorySubscription1;
    }

    public static CategorySubscription setSmeCatrgorySubscription(User user, Integer categoryHierarchyId, Integer parentId, HttpServletRequest httpServletRequest) {
        CategorySubscription categorySubscription1 = new CategorySubscription();
        categorySubscription1.setCategoryHierarchyId(categoryHierarchyId);
        categorySubscription1.setUserId(user.getId());
        categorySubscription1.setOrganizationId(user.getOrganizationId());
        categorySubscription1.setStatus(Constants.ONE);
        categorySubscription1.setIsActive(Constants.ONE);

        if (user.getSubOrgId() != null) {
            categorySubscription1.setSubOrgId(user.getSubOrgId());
        }
        categorySubscription1.setParentId(parentId);
        categorySubscription1.setIsSME(Constants.ONE);
        categorySubscription1.setSmeRoleId(Integer.valueOf(httpServletRequest.getParameter(Constants.ROLE_ID_SME)));
        return categorySubscription1;
    }

    public static List<CategorySubscription> subscribeUserToCommunity(List<CategorySubscription> subscriptions) {
        List<CategorySubscription> list = new ArrayList<>();
        subscriptions.forEach(categorySubscription -> {
            categorySubscription.setIsAdmin(ZERO);
            list.add(categorySubscription);

        });
        return list;
    }

    public static List<CategorySubscription> subscribeSmeUserToCommunity(List<CategorySubscription> subscriptions) {
        List<CategorySubscription> list = new ArrayList<>();
        subscriptions.forEach(categorySubscription -> {
            categorySubscription.setIsSME(Constants.ZERO);
            list.add(categorySubscription);

        });
        return list;
    }

    public static List<Long> streamId(List<CategorySubscription> categorySubscriptions) {
        return categorySubscriptions.stream().map(CategorySubscription::getUserId).collect(Collectors.toList());
    }

    public static List<CategoryHierarchyDepartment> departmentMapping(CategoryHierarchy categoryHierarchy, List<Integer> dept) {
        List<CategoryHierarchyDepartment> list = new ArrayList<>();
        dept.forEach(integer -> {
            CategoryHierarchyDepartment department = new CategoryHierarchyDepartment();
            department.setCategoryHierarchyId(categoryHierarchy.getId());
            department.setDepartmentId(integer);
            list.add(department);
        });
        return list;
    }

    public static List<SubOrgCategoryHierarchy> mapSubOrgToCommunity(List<Integer> subOrgIds, HttpServletRequest httpServletRequest, Integer id) {
        List<SubOrgCategoryHierarchy> list = new ArrayList<>();
        subOrgIds.forEach(integer -> {
            SubOrgCategoryHierarchy subOrgCategoryHierarchy = new SubOrgCategoryHierarchy();
            subOrgCategoryHierarchy.setOrgId(ControlPanelUtil.setOrgId(httpServletRequest));
            subOrgCategoryHierarchy.setSubOrgId(integer);
            subOrgCategoryHierarchy.setCategoryId(id);
            list.add(subOrgCategoryHierarchy);

        });
        return list;
    }

}
