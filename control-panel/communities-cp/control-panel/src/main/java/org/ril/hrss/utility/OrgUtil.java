package org.ril.hrss.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ril.hrss.data_security.EncryptDecrypt;
import org.ril.hrss.model.Country;
import org.ril.hrss.model.Org;
import org.ril.hrss.model.OrgConfiguration;
import org.ril.hrss.model.SubOrg;
import org.ril.hrss.model.auth.AdminUser;
import org.ril.hrss.model.auth.AdminUserRole;
import org.ril.hrss.model.content.Content;
import org.ril.hrss.model.content.OrgContent;
import org.ril.hrss.model.content.SubOrgContent;
import org.ril.hrss.model.language.Language;
import org.ril.hrss.model.language.OrgLanguage;
import org.ril.hrss.model.product_hierarchy.OrgProductHierarchy;
import org.ril.hrss.model.product_hierarchy.ProductHierarchy;
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
public class OrgUtil {
    public static Org create(HttpServletRequest httpServletRequest) {
        Org org = new Org();
        org.setName(httpServletRequest.getParameter(Constants.ORG_NAME));
        org.setAbbreviations(httpServletRequest.getParameter(Constants.ORG_ABBREVIATION));
        org.setAddressLine1(httpServletRequest.getParameter(Constants.ADDRESS_ONE));
        org.setAddressLine2(httpServletRequest.getParameter(Constants.ADDRESS_TWO));
        org.setCity(httpServletRequest.getParameter(Constants.CITY));
        org.setState(httpServletRequest.getParameter(Constants.STATE));
        org.setZipCode(httpServletRequest.getParameter(Constants.ZIP));
        org.setCountry(httpServletRequest.getParameter(Constants.COUNTRY));
        org.setPhoneNumber(httpServletRequest.getParameter(Constants.PHONE));
        org.setAppName(httpServletRequest.getParameter(Constants.APP_NAME));
        org.setAppUrl(httpServletRequest.getParameter(Constants.APP_URL));
        org.setApiUrl(httpServletRequest.getParameter(Constants.API_URL));
        org.setCan_create_coummunity(Integer.parseInt(httpServletRequest.getParameter(Constants.CAN_CREATE_COMMUNITY)));
        org.setCan_create_sub_organization(Integer.parseInt(httpServletRequest.getParameter(Constants.CAN_CREATE_SUB_ORG)));

        if (Integer.parseInt(httpServletRequest.getParameter(Constants.CAN_CREATE_COMMUNITY)) == 1) {
            org.setCommunity_limit(Integer.parseInt(httpServletRequest.getParameter(Constants.LIMIT_COMMUNITY)));
        }
        if (Integer.parseInt(httpServletRequest.getParameter(Constants.CAN_CREATE_SUB_ORG)) == 1) {
            org.setSub_organization_limit(Integer.parseInt(httpServletRequest.getParameter(Constants.LIMIT_ORG)));
        }

        org.setIsSsoEnabled(Integer.parseInt(httpServletRequest.getParameter(Constants.IS_SSO_ENABLED)));
        org.setSsoUrl(httpServletRequest.getParameter(Constants.SSO_URL));
        org.setSsoKey(httpServletRequest.getParameter(Constants.SSO_KEY));
        org.setSsoType(httpServletRequest.getParameter(Constants.SSO_TYPE));
        org.setStatus(Constants.ONE);
        org.setIs_approved(Constants.ONE);
        /*org.setLevel_limit(Integer.parseInt(httpServletRequest.getParameter(Constants.LIMIT_LEVELS)));*/
        org.setLevel_limit(1);
        /*org.setGaCode(httpServletRequest.getParameter(Constants.GA_CODE));*/
        /*org.setIsAnalyticsEnabled(Integer.valueOf(httpServletRequest.getParameter(Constants.IS_ANALYTICS_ENABLED)));*/
        return org;
    }

    public static AdminUser createAdminUser(HttpServletRequest httpServletRequest, Org org) throws Exception {
        AdminUser adminUser = new AdminUser();
        adminUser.setFirstName(httpServletRequest.getParameter(Constants.FIRST_NAME));
        adminUser.setLastName(httpServletRequest.getParameter(Constants.LAST_NAME));
        adminUser.setEmail(httpServletRequest.getParameter(Constants.EMAIL));
        adminUser.setPassword(httpServletRequest.getParameter(Constants.PASSWORD));
        adminUser.setStatus(Constants.ONE);
        adminUser.setOrg(org);
        adminUser.setAdminType(Constants.ONE);
        return adminUser;
    }

    public static AdminUserRole createAdminUserRole(AdminUser adminUser) throws Exception {
        AdminUserRole adminUserRole = new AdminUserRole();
        adminUserRole.setAdminUserId(adminUser.getId());
        adminUserRole.setRoleId(Constants.ORG_ADMIN_ROLE_ID);
        return adminUserRole;
    }

    public static List<OrgProductHierarchy> createProductHierarchy(List<ProductHierarchy> productHierarchies, Org org) throws Exception {
        List<OrgProductHierarchy> orgProductHierarchies = new ArrayList<>();

        for (ProductHierarchy productHierarchy : productHierarchies) {
            OrgProductHierarchy orgProductHierarchy = new OrgProductHierarchy();
            orgProductHierarchy.setOrgId(org.getId());
            orgProductHierarchy.setProductId(productHierarchy.getId());
            orgProductHierarchy.setName(productHierarchy.getName());
            orgProductHierarchy.setDescription(productHierarchy.getDescription());
            orgProductHierarchy.setStatus(Constants.ONE);
            orgProductHierarchy.setLevel(productHierarchy.getLevel());

            orgProductHierarchies.add(orgProductHierarchy);
        }
        return orgProductHierarchies;
    }

    public static List<OrgLanguage> createOrgLanguage(List<Language> languageList, Org org) throws Exception {
        List<OrgLanguage> orgLanguages = new ArrayList<>();
        for (Language language : languageList) {
            OrgLanguage orgLanguage = new OrgLanguage();
            orgLanguage.setOrgId(org.getId());
            orgLanguage.setLanguageId(language.getId());
            orgLanguage.setStatus(language.getStatus());
            orgLanguage.setContent(language.getContent());
            orgLanguage.setName(language.getName());
            orgLanguage.setDefaultContent(language.getContent());
            orgLanguage.setLangCode(language.getLangCode());

            orgLanguages.add(orgLanguage);
        }
        return orgLanguages;
    }

    public static List<OrgContent> createOrgContent(List<Content> contentList, List<Content> contentsAvailable, Org org) throws Exception {
        List<OrgContent> orgContents = new ArrayList<>();

        for (Content content : contentList) {
            OrgContent orgContent = new OrgContent();
            orgContent.setOrgId(org.getId());
            orgContent.setContentTypeId(content.getId());
            orgContent.setDescription(content.getDescription());
            orgContent.setName(content.getName());
            orgContent.setStatus(content.getStatus());
            orgContent.setIsAvailable(Constants.ZERO);
            orgContent.setContentSetting(content.getContentSetting());
            orgContent.setDefaultSetting(content.getContentSetting());
            orgContent.setIsConfigurable(content.getIsConfigurable());
            for (Content content1 : contentsAvailable) {
                if (content1.getId().equals(content.getId())) {
                    orgContent.setIsAvailable(Constants.ONE);
                }


            }
            if (content.getIsConfigurable() == 0) {
                orgContent.setIsAvailable(Constants.ONE);
            }
            orgContents.add(orgContent);
        }
        return orgContents;
    }

    public static List<OrgContent> updateOrgContentSetExisting(List<OrgContent> contentsExisting) throws Exception {
        List<OrgContent> orgContents = new ArrayList<>();

        for (OrgContent orgContent : contentsExisting) {
            orgContent.setIsAvailable(Constants.ZERO);
            orgContents.add(orgContent);
        }
        return orgContents;
    }

    public static List<OrgContent> updateOrgContentSetSelected(List<OrgContent> contentsAvailable) throws Exception {
        List<OrgContent> orgContents = new ArrayList<>();

        for (OrgContent orgContent : contentsAvailable) {
            orgContent.setIsAvailable(Constants.ONE);
            orgContents.add(orgContent);
        }
        return orgContents;
    }

    public static List<SubOrgContent> updateSubOrgContentSetExisting(List<SubOrgContent> subOrgContents) throws Exception {
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

    public static MultiValueMap uploadCreate() {

        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap();
        multiValueMap.add(Constants.DIR_NAME, Constants.DIR_LOGO_IMAGE);
        return multiValueMap;
    }

    public static MultiValueMap uploadUpdate(Integer orgId) {

        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap();
        multiValueMap.add(Constants.ORG_ID, orgId);
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

        map.put("imageClassifyUrl", "imageuat.ril.com");
        map.put(Constants.ACTIVE_STORAGE, httpServletRequest.getParameter(Constants.ACTIVE_STORAGE));
        map.put(Constants.UPLOAD_FOLDER_NAME, httpServletRequest.getParameter(Constants.FOLDER_NAME));
        map.put(Constants.AZURE, azure);
        map.put(Constants.SFTP, sftp);

        return new ObjectMapper().writeValueAsString(map);

    }

    public static OrgConfiguration createStorageForOrg(HttpServletRequest httpServletRequest, Org org) throws JsonProcessingException {
        OrgConfiguration orgConfiguration = new OrgConfiguration();
        orgConfiguration.setOrganizationId(org.getId());
        orgConfiguration.setSettingName(Constants.STORAGE_CONFIG);
        orgConfiguration.setIsEnable(Constants.ONE);
        orgConfiguration.setSettingValue(OrgUtil.createStorageConfig(httpServletRequest));
        return orgConfiguration;
    }

    public static Pageable paginationOrgSearchBox(HttpServletRequest httpServletRequest) {
        Integer page = httpServletRequest.getParameter(Constants.PAGE) != null ? Integer.valueOf(httpServletRequest.getParameter(Constants.PAGE)) : Constants.ZERO;
        Integer limit = Constants.PAGE_SIZE_FIFTY;

        return PageRequest.of(page, limit);
    }

    public static Pageable paginationOrgListing(HttpServletRequest httpServletRequest) {
        Integer page = httpServletRequest.getParameter(Constants.PAGE) != null ? Integer.valueOf(httpServletRequest.getParameter(Constants.PAGE)) : Constants.ZERO;
        Integer limit = Constants.PAGE_SIZE_TEN;
        return PageRequest.of(page, limit, Sort.Direction.DESC, Constants.CREATED_AT);
    }

    public static Page<Org> listSanitization(Page<Org> orgs) {
        for (Org org : orgs) {

            List<AdminUser> au = org.getAdminUser();

            Iterator<AdminUser> adminUserItr = au.iterator();
            if (adminUserItr.hasNext()) {
                AdminUser au2 = au.get(Constants.ZERO);
                org.setRegisteredAdminName(au2.getFirstName() + Constants.SPACE + au2.getLastName());
                org.setRegisteredAdminEmail(au2.getEmail());
            }
        }
        return orgs;
    }

    public static Org update(HttpServletRequest httpServletRequest, Org org, String filePath) {

        org.setName(httpServletRequest.getParameter(Constants.ORG_NAME));
        org.setAbbreviations(httpServletRequest.getParameter(Constants.ORG_ABBREVIATION));
        org.setAddressLine1(httpServletRequest.getParameter(Constants.ADDRESS_ONE));
        org.setAddressLine2(httpServletRequest.getParameter(Constants.ADDRESS_TWO));
        org.setCity(httpServletRequest.getParameter(Constants.CITY));
        org.setState(httpServletRequest.getParameter(Constants.STATE));
        org.setZipCode(httpServletRequest.getParameter(Constants.ZIP));
        org.setCountry(httpServletRequest.getParameter(Constants.COUNTRY));
        org.setPhoneNumber(httpServletRequest.getParameter(Constants.PHONE));
        org.setLogoImage(httpServletRequest.getParameter(Constants.LOGO_IMAGE));
        org.setAppName(httpServletRequest.getParameter(Constants.APP_NAME));
        org.setAppUrl(httpServletRequest.getParameter(Constants.APP_URL));
        org.setApiUrl(httpServletRequest.getParameter(Constants.API_URL));
        org.setIsSsoEnabled(Integer.parseInt(httpServletRequest.getParameter(Constants.IS_SSO_ENABLED)));
        org.setSsoUrl(httpServletRequest.getParameter(Constants.SSO_URL));
        org.setSsoKey(httpServletRequest.getParameter(Constants.SSO_KEY));
        org.setSsoType(httpServletRequest.getParameter(Constants.SSO_TYPE));
        org.setCan_create_coummunity(Integer.parseInt(httpServletRequest.getParameter(Constants.CAN_CREATE_COMMUNITY)));
        org.setCan_create_sub_organization(Integer.parseInt(httpServletRequest.getParameter(Constants.CAN_CREATE_SUB_ORG)));

        if (Integer.parseInt(httpServletRequest.getParameter(Constants.CAN_CREATE_COMMUNITY)) == 1) {
            org.setCommunity_limit(Integer.parseInt(httpServletRequest.getParameter(Constants.LIMIT_COMMUNITY)));
        }
        if (Integer.parseInt(httpServletRequest.getParameter(Constants.CAN_CREATE_SUB_ORG)) == 1) {
            org.setSub_organization_limit(Integer.parseInt(httpServletRequest.getParameter(Constants.LIMIT_ORG)));
        }
        /*org.setLevel_limit(Integer.parseInt(httpServletRequest.getParameter(Constants.LIMIT_LEVELS)));*/
        org.setLevel_limit(1);
        if (filePath != null && !filePath.equals("")) {
            org.setLogoImage(filePath);
        }
        return org;
    }

    public static AdminUser updateAdminUser(HttpServletRequest httpServletRequest, Org org) throws Exception {

        List<AdminUser> adminUsers = org.getAdminUser();
        AdminUser adminUser = adminUsers.get(Constants.ZERO);
        adminUser.setFirstName(httpServletRequest.getParameter(Constants.FIRST_NAME));
        adminUser.setLastName(httpServletRequest.getParameter(Constants.LAST_NAME));
        adminUser.setEmail(httpServletRequest.getParameter(Constants.EMAIL));
        adminUser.setPassword(httpServletRequest.getParameter(Constants.PASSWORD));

        return adminUser;
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

    public static String orgListingIndex(Model model, Page<Org> orgs, String query) {
        model.addAttribute(Constants.ORG_LIST, orgs);
        model.addAttribute(Constants.PAGE, orgs);
        model.addAttribute(Constants.QUERY, query);
        return "organization/org-list";
    }

    public static String createModel(List<Content> contents, List<Country> countries, Model model) {
        model.addAttribute(Constants.CONTENTS, contents);
        model.addAttribute(Constants.COUNTRIES, countries);

        return "organization/create";
    }

    public static List<OrgContent> addAvailableContents(List<OrgContent> contents) {
        List<OrgContent> availableContents = new ArrayList<OrgContent>();
        for (OrgContent orgContent : contents) {
            if (orgContent.getIsAvailable().equals(Constants.ONE) && orgContent.getIsConfigurable().equals(Constants.ONE)) {
                availableContents.add(orgContent);
            }
        }
        return availableContents;
    }

    public static String showModel(List<OrgContent> contents, Org org, AdminUser admin, List<Integer> ids, Integer orgId, List<Country> countries, Model model, Map map) {
        model.addAttribute(Constants.CONTENTS, contents);
        model.addAttribute(Constants.ORG, org);
        model.addAttribute(Constants.ADMIN, admin);
        model.addAttribute(Constants.AVAILABLE_CONTENTS, ids);
        model.addAttribute(Constants.COUNTRIES, countries);
        model.addAttribute(Constants.ORG_ID, orgId);
        model.addAttribute(Constants.ORG_IMAGE_LOCATION, map.get(Constants.DOWNLOAD_BASE_PATH) + Constants.SEPARATOR + map.get(Constants.FOLDER_NAME));

        return "organization/show";
    }

    public static void setAzureMap(Model model, Map map) {
        model.addAttribute(Constants.ORG_IMAGE_LOCATION, Constants.EMPTY);
        if (!map.isEmpty() || map != null) {
            model.addAttribute(Constants.ORG_IMAGE_LOCATION, map.get(Constants.DOWNLOAD_BASE_PATH) + Constants.SEPARATOR + map.get(Constants.FOLDER_NAME));
        }
    }

    public static String editModel(List<OrgContent> contents, Org org, AdminUser admin, List<Integer> ids, Integer orgId, List<Country> countries, Model model, Map map, String decryptedPassword) {
        model.addAttribute(Constants.CONTENTS, contents);
        model.addAttribute(Constants.ORG, org);
        model.addAttribute(Constants.ADMIN, admin);
        model.addAttribute(Constants.AVAILABLE_CONTENTS, ids);
        model.addAttribute(Constants.COUNTRIES, countries);
        model.addAttribute(Constants.ORG_ID, orgId);
        model.addAttribute(Constants.DECRYPTED_PASSWORD, decryptedPassword);
        model.addAttribute(Constants.STORAGE, map);
        return "organization/edit";
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
}
