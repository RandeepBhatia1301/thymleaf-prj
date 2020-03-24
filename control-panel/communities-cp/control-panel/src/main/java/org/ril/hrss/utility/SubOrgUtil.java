package org.ril.hrss.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ril.hrss.data_security.EncryptDecrypt;
import org.ril.hrss.model.*;
import org.ril.hrss.model.auth.AdminUser;
import org.ril.hrss.model.auth.AdminUserRole;
import org.ril.hrss.model.auth.RoleMaster;
import org.ril.hrss.model.auth.User;
import org.ril.hrss.model.content.OrgContent;
import org.ril.hrss.model.content.SubOrgContent;
import org.ril.hrss.model.gamification.SubOrgwelcomeScreen;
import org.ril.hrss.model.gamification.WelcomeScreen;
import org.ril.hrss.model.language.OrgLanguage;
import org.ril.hrss.model.language.SubOrgLanguage;
import org.ril.hrss.model.product_hierarchy.OrgProductHierarchy;
import org.ril.hrss.model.product_hierarchy.SubOrgProductHierarchy;
import org.ril.hrss.model.roles_and_access.CommunityRole;
import org.ril.hrss.model.roles_and_access.CommunityRolePermission;
import org.ril.hrss.model.roles_and_access.CommunityUserRole;
import org.ril.hrss.model.roles_and_access.RolePermissionMaster;
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
import java.util.*;

@Component
public class SubOrgUtil {
    public static SubOrg create(Map<String, String> reqParam, Integer orgId, Integer adminId) {
        SubOrg subOrg = new SubOrg();

        subOrg.setName(reqParam.get(Constants.NAME));
        subOrg.setAbbrevation(reqParam.get(Constants.ABBREVIATION));
        subOrg.setAddressLine1(reqParam.get(Constants.ADDRESS_LINE_1));
        subOrg.setAddressLine2(reqParam.get(Constants.ADDRESS_LINE_2));
        subOrg.setCity(reqParam.get(Constants.CITY));
        subOrg.setState(reqParam.get(Constants.STATE));
        subOrg.setZipCode(reqParam.get(Constants.ZIP));
        subOrg.setCountry(reqParam.get(Constants.COUNTRY));
        subOrg.setPhoneNumber(reqParam.get(Constants.PHONE_NUMBER));
        subOrg.setOrgId(orgId);
        subOrg.setCreatedBy(adminId);
        subOrg.setSsoKey(reqParam.get(Constants.SSO__KEY));
        subOrg.setSsoType(reqParam.get(Constants.SSO__TYPE));
        subOrg.setSsoUrl(reqParam.get(Constants.SSO__URL));
        subOrg.setAppName(reqParam.get(Constants.APP_NAME));
        subOrg.setAppUrl(reqParam.get(Constants.APP_URL));
        subOrg.setApiUrl(reqParam.get(Constants.API_URL));
        subOrg.setStatus(Constants.ONE);
      /*  if (reqParam.containsKey(Constants.GA_CODE)) {
            subOrg.setGaCode(reqParam.get(Constants.GA_CODE));
        }*/
        return subOrg;

    }

    public static AdminUser createAdminUser(Map<String, String> reqParam, SubOrg subOrg, Org org) throws Exception {

        AdminUser adminUser = new AdminUser();
        adminUser.setFirstName(reqParam.get(Constants.FIRST__NAME));
        adminUser.setLastName(reqParam.get(Constants.LAST__NAME));
        adminUser.setEmail(reqParam.get(Constants.EMAIL));
        adminUser.setPassword(reqParam.get(Constants.PASSWORD));
        adminUser.setSubOrgId(subOrg.getId());
        adminUser.setStatus(Constants.ONE);
        adminUser.setAdminType(Constants.TWO);
        adminUser.setOrg(org);
        adminUser.setStatus(Constants.ONE);
        return adminUser;
    }

    public static AdminUserRole createAdminUserRole(AdminUser adminUser) throws Exception {

        AdminUserRole adminUserRole = new AdminUserRole();
        adminUserRole.setAdminUserId(adminUser.getId());
        adminUserRole.setRoleId(Constants.SUB_ORG_ADMIN_ROLE_ID);
        return adminUserRole;
    }

    public static Boolean checkSubOrgCount(Optional<Org> org, Integer currentCount) {

        Integer limitCount = Constants.ZERO;
        if (org.isPresent()) {
            Org org1 = org.get();
            limitCount = org1.getSub_organization_limit();
        }
        if (currentCount < limitCount) {
            return true;
        }
        return false;
    }


    public static List<SubOrgProductHierarchy> createSubOrgProductHierarchy(List<OrgProductHierarchy> orgProductHierarchies, SubOrg subOrg, Integer orgId) {

        List<SubOrgProductHierarchy> subOrgProductHierarchies = new ArrayList<>();
        for (OrgProductHierarchy orgProductHierarchy : orgProductHierarchies) {
            SubOrgProductHierarchy subOrgProductHierarchy = new SubOrgProductHierarchy();
            subOrgProductHierarchy.setOrgId(orgId);
            subOrgProductHierarchy.setSubOrgId(subOrg.getId());
            subOrgProductHierarchy.setProductId(orgProductHierarchy.getProductId());
            subOrgProductHierarchy.setName(orgProductHierarchy.getName());
            subOrgProductHierarchy.setDescription(orgProductHierarchy.getDescription());
            subOrgProductHierarchy.setStatus(Constants.ONE);
            subOrgProductHierarchy.setLevel(orgProductHierarchy.getLevel());
            subOrgProductHierarchies.add(subOrgProductHierarchy);

        }
        return subOrgProductHierarchies;
    }

    public static List<SubOrgLanguage> createSubOrgLanguage(List<OrgLanguage> orgLanguages, SubOrg subOrg, Integer orgId) throws Exception {
        List<SubOrgLanguage> subOrgLanguages = new ArrayList<>();

        for (OrgLanguage orgLanguage : orgLanguages) {
            SubOrgLanguage subOrgLanguage = new SubOrgLanguage();
            subOrgLanguage.setSuborgId(subOrg.getId());
            subOrgLanguage.setLanguage_id(orgLanguage.getLanguageId());
            subOrgLanguage.setStatus(orgLanguage.getStatus());
            subOrgLanguage.setContent(orgLanguage.getContent());
            subOrgLanguage.setName(orgLanguage.getName());
            subOrgLanguage.setOrgId(orgId);
            subOrgLanguage.setLangCode(orgLanguage.getLangCode());
            subOrgLanguages.add(subOrgLanguage);
        }
        return subOrgLanguages;
    }

    public static List<SubOrgContent> createSubOrgContent(List<OrgContent> contentsAvailable, SubOrg subOrg, Integer orgId) throws Exception {
        List<SubOrgContent> subOrgContents = new ArrayList<>();

        Integer i = Constants.ONE;
        for (OrgContent orgContent : contentsAvailable) {
            SubOrgContent subOrgContent = new SubOrgContent();
            subOrgContent.setOrgId(orgId);
            subOrgContent.setContentTypeId(orgContent.getContentTypeId());
            subOrgContent.setContentSetting(orgContent.getContentSetting());
            subOrgContent.setDefaultSetting(orgContent.getContentSetting());
            subOrgContent.setStatus(orgContent.getStatus());
            subOrgContent.setSubOrgId(subOrg.getId());
            //subOrgContent.setId(orgContent.getId());
            subOrgContent.setIsAvailable(orgContent.getIsAvailable());
            subOrgContent.setOrgContentTypeId(orgContent.getId());
            subOrgContent.setIsConfigurable(orgContent.getIsConfigurable());

            subOrgContent.setDisplayOrder(i);
            i++;

            subOrgContents.add(subOrgContent);

        }
        return subOrgContents;
    }

    public static List<OrgContent> updateOrgContentSetExisting(List<OrgContent> contentsExisting) throws
            Exception {
        List<OrgContent> orgContents = new ArrayList<>();

        for (OrgContent orgContent : contentsExisting) {
            orgContent.setIsAvailable(Constants.ZERO);
            orgContents.add(orgContent);
        }
        return orgContents;
    }

    public static List<OrgContent> updateOrgContentSetSelected(List<OrgContent> contentsAvailable) throws
            Exception {
        List<OrgContent> orgContents = new ArrayList<>();

        for (OrgContent orgContent : contentsAvailable) {
            orgContent.setIsAvailable(Constants.ONE);
            orgContents.add(orgContent);
        }
        return orgContents;
    }

    public static List<SubOrgContent> updateSubOrgContentSetExisting(List<SubOrgContent> subOrgContents) throws
            Exception {
        List<SubOrgContent> subOrgContents1 = new ArrayList<>();
        for (SubOrgContent subOrgContent : subOrgContents) {
            subOrgContent.setIsAvailable(Constants.ZERO);
            subOrgContents1.add(subOrgContent);
        }
        return subOrgContents1;
    }

    public static List<SubOrgContent> updateSubOrgContentSetSelected(List<SubOrgContent> subOrgContentList) {
        List<SubOrgContent> subOrgContents1 = new ArrayList<>();
        for (SubOrgContent subOrgContent : subOrgContentList) {
            subOrgContent.setIsAvailable(Constants.ONE);
            subOrgContents1.add(subOrgContent);
        }
        return subOrgContents1;
    }

    public static SubOrg setImagePath(SubOrg subOrg, String filePath) {

        subOrg.setLogoImage(filePath);
        return subOrg;
    }


    public static MultiValueMap uploadCreate(Integer orgId, Integer subOrgId) {

        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap();
        multiValueMap.add(Constants.ORG_ID, orgId);
        multiValueMap.add(Constants.SUB_ORG_ID, subOrgId);
        return multiValueMap;
    }

    public static String createStorageConfig(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        Map map = new LinkedHashMap();
        Map azure = new LinkedHashMap();
        Map sftp = new LinkedHashMap();

        azure.put(Constants.ACCOUNT_NAME, httpServletRequest.getParameter(Constants.ACCOUNT_NAME));
        azure.put(Constants.ACCOUNT_KEY, httpServletRequest.getParameter(Constants.ACCOUNT_KEY));

        azure.put(Constants.ENDPOINT_SUFFIX, Constants.CORE_WINDOWS_NET);
        azure.put(Constants.DEFAULT_ENDPOINTS_PROTOCOL, Constants.HTTPS);
        azure.put(Constants.DOWNLOAD_BASE_PATH, httpServletRequest.getParameter(Constants.DOWNLOAD_BASE_PATH));

        map.put(Constants.ACTIVE_STORAGE, httpServletRequest.getParameter(Constants.ACTIVE_STORAGE));
        map.put(Constants.UPLOAD_FOLDER_NAME, httpServletRequest.getParameter(Constants.FOLDER_NAME));
        map.put(Constants.AZURE, azure);
        map.put(Constants.SFTP, sftp);

        return new ObjectMapper().writeValueAsString(map);

    }

    public static List<Map> roleConfiguration(RoleMaster roleMaster, CommunityRole communityRole, Map map, List<Map> list) throws JsonProcessingException {
        /*create a map with currentmaster id and community role id*/
        map.put(Constants.MASTER_ROLE_ID, roleMaster.getId());
        map.put(Constants.COMMUNITY_ROLE_ID, communityRole.getId());

        list.add(map);
        return list;
    }

    public static List<CommunityRolePermission> createRolePermission(List<RolePermissionMaster> rolePermissionMasters, Integer orgId, Map map) {
        List<CommunityRolePermission> communityRolePermissions = new ArrayList<>();
        for (RolePermissionMaster rolePermissionMaster : rolePermissionMasters) {
            CommunityRolePermission communityRolePermission = new CommunityRolePermission();
            communityRolePermission.setCommunityRoleId((Integer) map.get(Constants.COMMUNITY_ROLE_ID));
            communityRolePermission.setOrgId(orgId);
            /*communityRolePermission.setSubOrgId(subOrgId);*/
            communityRolePermission.setPermissionId(rolePermissionMaster.getPermissionId());

            communityRolePermissions.add(communityRolePermission);
        }
        return communityRolePermissions;
    }


    public static User createSubOrgUser(Integer count, AdminUser adminUser) {
        User user = new User();
        if (count <= Constants.ZERO) {

            user.setName(adminUser.getFirstName() + " " + adminUser.getLastName());
            user.setEmail(adminUser.getEmail());
            user.setFirstName(adminUser.getFirstName());
            user.setLastName(adminUser.getLastName());
            user.setPassword(adminUser.getPassword());
            user.setOrganizationId(adminUser.getOrg().getId());
            user.setSubOrgId(adminUser.getSubOrgId());
            user.setStatus(Constants.ONE);
        }
        return user;
    }

    public static CommunityUserRole createSubOrgUserRole(CommunityRole communityRole, User user) {
        CommunityUserRole communityUserRole = new CommunityUserRole();
        communityUserRole.setCommunityRoleId(communityRole.getId());
        communityUserRole.setUserId(user.getId());
        return communityUserRole;
    }

    public static List<SubOrgwelcomeScreen> createWelcomeScreenData(Integer orgId, Integer subOrgId, List<WelcomeScreen> welcomeScreens) {
        List<SubOrgwelcomeScreen> welcomeScreens1 = new ArrayList<>();
        for (WelcomeScreen welcomeScreen : welcomeScreens) {
            SubOrgwelcomeScreen subOrgwelcomeScreen = new SubOrgwelcomeScreen();
            subOrgwelcomeScreen.setCard(welcomeScreen.getCard());
            subOrgwelcomeScreen.setHeaderText(welcomeScreen.getHeaderText());
            subOrgwelcomeScreen.setFooterText(welcomeScreen.getFooterText());
            subOrgwelcomeScreen.setBodyText(welcomeScreen.getBodyText());
            subOrgwelcomeScreen.setOrgId(orgId);
            subOrgwelcomeScreen.setSubOrgId(subOrgId);
            subOrgwelcomeScreen.setImage(welcomeScreen.getImage());
            welcomeScreens1.add(subOrgwelcomeScreen);
        }
        return welcomeScreens1;
    }

    public static Pageable paginationSubOrgSearchBox(HttpServletRequest httpServletRequest) {
        Integer page = httpServletRequest.getParameter(Constants.PAGE) != null ? Integer.valueOf(httpServletRequest.getParameter(Constants.PAGE)) : Constants.ZERO;
        Integer limit = Constants.PAGE_SIZE_FIFTY;
        return PageRequest.of(page, limit);
    }

    public static Pageable paginationSubOrgListing(HttpServletRequest httpServletRequest) {
        Integer page = httpServletRequest.getParameter(Constants.PAGE) != null ? Integer.valueOf(httpServletRequest.getParameter(Constants.PAGE)) : Constants.ZERO;
        Integer limit = Constants.TEN;
        return PageRequest.of(page, limit, Sort.Direction.DESC, Constants.CREATED_AT);
    }

    public static Page<SubOrg> listSanitization(Page<SubOrg> subOrgs) {
        for (SubOrg subOrg : subOrgs) {
            List<AdminUser> au = subOrg.getAdminUser();
            Iterator<AdminUser> adminUserItr = au.iterator();
            if (adminUserItr.hasNext()) {
                AdminUser au2 = au.get(Constants.ZERO);
                subOrg.setRegisteredAdminName(au2.getFirstName() + " " + au2.getLastName());
                subOrg.setRegisteredAdminEmail(au2.getEmail());
            }
        }
        return subOrgs;
    }


    public static SubOrg update(Map<String, String> reqParam, Integer orgId, Integer adminId, SubOrg subOrg, String filePath) {
        subOrg.setName(reqParam.get(Constants.NAME));
        subOrg.setAbbrevation(reqParam.get(Constants.ABBREVIATION));
        subOrg.setAddressLine1(reqParam.get(Constants.ADDRESS_LINE_1));
        subOrg.setAddressLine2(reqParam.get(Constants.ADDRESS_LINE_2));
        subOrg.setCity(reqParam.get(Constants.CITY));
        subOrg.setState(reqParam.get(Constants.STATE));
        subOrg.setZipCode(reqParam.get(Constants.ZIP));
        subOrg.setCountry(reqParam.get(Constants.COUNTRY));
        subOrg.setPhoneNumber(reqParam.get(Constants.PHONE_NUMBER));
        /*subOrg.setDisjoinDefaultCommunities(Integer.parseInt(reqParam.get("is_default_communities")));*/
        subOrg.setOrgId(orgId);
        subOrg.setCreatedBy(adminId);
        subOrg.setSsoKey(reqParam.get(Constants.SSO__KEY));
        subOrg.setSsoType(reqParam.get(Constants.SSO__TYPE));
        subOrg.setSsoUrl(reqParam.get(Constants.SSO__URL));
        if (filePath != null) {
            subOrg.setLogoImage(filePath);
        }
        subOrg.setAppName(reqParam.get(Constants.APP_NAME));
        subOrg.setAppUrl(reqParam.get(Constants.APP_URL));
        subOrg.setApiUrl(reqParam.get(Constants.API_URL));
        subOrg.setStatus(Constants.ONE);
       /* if (reqParam.containsKey(Constants.GA_CODE)) {
            subOrg.setGaCode(reqParam.get(Constants.GA_CODE));
        }*/
        return subOrg;
    }

    public static AdminUser updateSubOrgAdminUser(SubOrg subOrg, Map<String, String> reqParam) throws Exception {

        List<AdminUser> adminUsers = subOrg.getAdminUser();
        AdminUser adminUser = adminUsers.get(Constants.ZERO);
        adminUser.setFirstName(reqParam.get(Constants.FIRST__NAME));
        adminUser.setLastName(reqParam.get(Constants.LAST__NAME));
        adminUser.setEmail(reqParam.get(Constants.EMAIL));
        adminUser.setPassword(reqParam.get(Constants.PASSWORD));
        adminUser.setSubOrgId(subOrg.getId());
        adminUser.setStatus(Constants.ONE);
        return adminUser;
    }

    public static Integer setSubOrgId(HttpServletRequest httpServletRequest) {
        return Integer.parseInt(httpServletRequest.getParameter(Constants.SUB_ORG_ID));
    }

    public static List<SubOrg> deActivateSubOrgs(List<SubOrg> subOrgs, Integer value) throws Exception {
        List<SubOrg> subOrgs1 = new ArrayList<>();
        for (SubOrg subOrg : subOrgs) {
            subOrg.setStatus(value);
            subOrgs1.add(subOrg);
        }
        return subOrgs1;
    }

    public static String deactivationCount(Integer count, Integer setActivationAdmins, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        String referer = httpServletRequest.getHeader(Constants.REFERER);
        if (count > Constants.ZERO && setActivationAdmins > Constants.ZERO) {
            return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, Constants.STATUS_UPDATE_SUCCESS, Constants.REDIRECT + referer);
        }
        return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, Constants.SOMETHING_WENT_WRONG, Constants.REDIRECT + referer);
    }

    public static Boolean checkNameCount(Integer nameCount) {

        if (nameCount > Constants.ZERO) {
            return false;
        }
        return true;
    }

    public static String subOrgListingIndex(Page<SubOrg> subOrgs, Model model, String query, Integer canCreateSubOrg) {
        model.addAttribute(Constants.SUB_ORG_LIST, subOrgs);
        model.addAttribute(Constants.PAGE, subOrgs);
        model.addAttribute(Constants.QUERY, query);
        model.addAttribute("canCreateSubOrg", canCreateSubOrg);
        return "suborg/sub-org-list";
    }

    public static String createModel(List<Country> countries, Model model, Org org) {
        model.addAttribute(Constants.COUNTRIES, countries);
        model.addAttribute(Constants.ORG, org);
        return "suborg/create";
    }

    public static List<OrgContent> addAvailableContents(List<OrgContent> contents) {
        List<OrgContent> availableContents = new ArrayList<OrgContent>();
        for (OrgContent orgContent : contents) {
            if (orgContent.getIsAvailable().equals(Constants.ONE)) {
                availableContents.add(orgContent);
            }
        }
        return availableContents;
    }

    public static String showModel(SubOrg subOrg, Model model, AdminUser admin, List<Country> countries, Integer subOrgId, Map map) {

        model.addAttribute(Constants.SUB_ORG, subOrg);
        model.addAttribute(Constants.ADMIN, admin);
        model.addAttribute(Constants.COUNTRIES, countries);
        model.addAttribute(Constants.SUB_ORG_ID, subOrgId);
        model.addAttribute(Constants.SUB_ORG_IMAGE_LOCATION, map.get(Constants.DOWNLOAD_BASE_PATH) + Constants.SEPARATOR + map.get(Constants.FOLDER_NAME));
        model.addAttribute(Constants.DECRYPTED_PASSWORD, SubOrgUtil.setDecryptedPassword(admin));
        return "suborg/show";
    }

    public static void setAzureMap(Model model, Map map) {
        model.addAttribute(Constants.ORG_IMAGE_LOCATION, Constants.EMPTY);
        if (!map.isEmpty() || map != null) {
            model.addAttribute(Constants.ORG_IMAGE_LOCATION, map.get(Constants.DOWNLOAD_BASE_PATH) + Constants.SEPARATOR + map.get(Constants.FOLDER_NAME));
        }
    }

    public static String editModel(SubOrg subOrg, Model model, AdminUser admin, List<Country> countries, Integer subOrgId, Map map, Org org) {

        model.addAttribute(Constants.SUB_ORG, subOrg);
        model.addAttribute(Constants.ADMIN, admin);
        model.addAttribute(Constants.COUNTRIES, countries);
        model.addAttribute(Constants.SUB_ORG_ID, subOrgId);
        model.addAttribute(Constants.SUB_ORG_IMAGE_LOCATION, map.get(Constants.DOWNLOAD_BASE_PATH) + Constants.SEPARATOR + map.get(Constants.FOLDER_NAME));
        model.addAttribute(Constants.DECRYPTED_PASSWORD, SubOrgUtil.setDecryptedPassword(admin));
        model.addAttribute(Constants.ORG, org);

        return "suborg/edit";
    }

    public static String setDecryptedPassword(AdminUser admin) {
        String decryptedPassword = null;
        try {
            decryptedPassword = EncryptDecrypt.decrypt(admin.getPassword());
        } catch (Exception ex) {
            decryptedPassword = admin.getPassword();
        }
        return decryptedPassword;
    }

    public static List<AdminUser> setActivationAdmins(List<AdminUser> adminUsers, Integer value) {
        List<AdminUser> adminUsers1 = new ArrayList<>();
        for (AdminUser adminUser : adminUsers) {
            adminUser.setStatus(value);
            adminUsers1.add(adminUser);

        }

        return adminUsers1;
    }

    public static SubOrgConfiguration floatReportContentData(SubOrg subOrg) {
        SubOrgConfiguration subOrgConfiguration = new SubOrgConfiguration();
        subOrgConfiguration.setOrganizationId(subOrg.getOrgId());
        subOrgConfiguration.setIsEnable(Constants.ONE);
        subOrgConfiguration.setSettingName(Constants.REPORTED_MESSAGE_KEY);
        subOrgConfiguration.setSettingValue(Constants.REPORTED_MESSAGE_VALUE);
        subOrgConfiguration.setSubOrgId(subOrg.getId());
        return subOrgConfiguration;
    }
    public static SubOrgConfiguration floatTrendingData(SubOrg subOrg , StorageConfiguration storageConfiguration) {
        SubOrgConfiguration subOrgConfiguration = new SubOrgConfiguration();
        subOrgConfiguration.setSubOrgId(subOrg.getId());
        subOrgConfiguration.setOrganizationId(subOrg.getOrgId());
        subOrgConfiguration.setSettingName(storageConfiguration.getSettingName());
        subOrgConfiguration.setSettingValue(storageConfiguration.getSettingValue());
        subOrgConfiguration.setIsEnable(1);
        return subOrgConfiguration;
    }

}
