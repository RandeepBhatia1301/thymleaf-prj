package org.ril.hrss.service.sub_org_management;

import org.ril.hrss.model.Country;
import org.ril.hrss.model.Org;
import org.ril.hrss.model.SubOrg;
import org.ril.hrss.model.auth.AdminUser;
import org.ril.hrss.model.content.OrgContent;
import org.ril.hrss.model.language.OrgLanguage;
import org.ril.hrss.model.product_hierarchy.OrgProductHierarchy;
import org.ril.hrss.repository.*;
import org.ril.hrss.service.gamification.WelcomeScreenService;
import org.ril.hrss.service.org_management.CountryService;
import org.ril.hrss.service.org_management.EmailServiceDemo;
import org.ril.hrss.service.org_management.OrgService;
import org.ril.hrss.service.product_hierarchy.OrgProductHierarchyService;
import org.ril.hrss.service.rest_api_services.StorageContainer;
import org.ril.hrss.service.rest_api_services.UploadClient;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ControlPanelUtil;
import org.ril.hrss.utility.SubOrgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.ril.hrss.utility.Constants.TRENDING_CONFIG;
import static org.ril.hrss.utility.SubOrgUtil.floatTrendingData;

@Service
public class SubOrgService {

    @Autowired
    EmailServiceDemo emailService;
    @Autowired
    UploadClient uploadClient;
    @Autowired
    RoleMasterRepository roleMasterRepository;
    @Autowired
    CommunityRoleRepository communityRoleRepository;
    @Autowired
    RolePermissionMasterRepository rolePermissionMasterRepository;
    @Autowired
    CommunityRolePermissionRepository communityRolePermissionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommunityUserRoleRepository communityUserRoleRepository;
    @Autowired
    SubOrgwelcomeScreenRepository subOrgwelcomeScreenRepository;
    @Autowired
    WelcomeScreenService welcomeScreenService;
    @Autowired
    OrgService orgService;
    @Autowired
    CountryService countryService;
    @Autowired
    StorageContainer storageContainer;
    @Autowired
    private SubOrgRepository subOrgRepository;
    @Autowired
    private AdminUserRepository adminUserRepository;
    @Autowired
    private AdminHasRoleRepository adminHasRoleRepository;
    @Autowired
    private OrgRepository orgRepository;
    @Autowired
    private OrgProductHierarchyService orgProductHierarchyService;
    @Autowired
    private SubOrgProductHierarchyRepository subOrgProductHierarchyRepository;
    @Autowired
    private SubOrgLanguageRepository subOrgLanguageRepository;
    @Autowired
    private OrgLanguageRepository orgLanguageRepository;
    @Autowired
    private OrgContentTypeRepository orgContentTypeRepository;
    @Autowired
    private SubOrgContentTypeRepository subOrgContentTypeRepository;
    @Autowired
    private SubOrgConfigurationRepository subOrgConfigurationRepository;
    @Autowired
    StorageConfigurationRepository storageConfigurationRepository;


    public String index(HttpServletRequest httpServletRequest, Model model, String query) {
        Integer canCreateSubOrg = 0;
        if (query != null) {
            return SubOrgUtil.subOrgListingIndex(getSubOrganizations(query, httpServletRequest), model, query, canCreateSubOrg);
        }

        Optional<Org> org = orgRepository.findById(ControlPanelUtil.setOrgId(httpServletRequest));
        if (org.isPresent()) {
            canCreateSubOrg = org.get().getCan_create_sub_organization();
        }
        return SubOrgUtil.subOrgListingIndex(getSubOrganizations(ControlPanelUtil.setOrgId(httpServletRequest), httpServletRequest), model, query, canCreateSubOrg);
    }

    public String createModel(HttpServletRequest httpServletRequest, Model model) {
        return SubOrgUtil.createModel(countryService.getAllCountries(), model, orgRepository.findById(ControlPanelUtil.setOrgId(httpServletRequest)).get());
    }

    public String editModel(HttpServletRequest httpServletRequest, Model model, Integer subOrgId) {
        SubOrg subOrg = this.findBySubOrgId(subOrgId);
        Org org1 = new Org();
        Optional<Org> org = orgRepository.findById(subOrg.getOrgId());
        if (org.isPresent()) {
            org1 = org.get();
        }

        AdminUser admin = subOrg.getAdminUser().isEmpty() ? new AdminUser() : subOrg.getAdminUser().get(Constants.ZERO);

        List<Country> countries = countryService.getAllCountries();
        Map map = storageContainer.getAzureData(Constants.ONE, subOrg.getOrgId());
        return SubOrgUtil.editModel(subOrg, model, admin, countries, subOrgId, map, org1);
    }

    public String showModel(HttpServletRequest httpServletRequest, Model model, Integer subOrgId) {
        SubOrg subOrg = this.findBySubOrgId(subOrgId);

        AdminUser admin = subOrg.getAdminUser().isEmpty() ? new AdminUser() : subOrg.getAdminUser().get(Constants.ZERO);

        List<Country> countries = countryService.getAllCountries();

        Map map = storageContainer.getAzureData(Constants.ONE, subOrg.getOrgId());
        return SubOrgUtil.showModel(subOrg, model, admin, countries, subOrgId, map);
    }

    public String createSubOrgHandling(Map<String, String> reqParam, MultipartFile file, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        Integer orgId = (Integer) httpServletRequest.getSession().getAttribute(Constants.ORG_ID);
        Integer adminId = (Integer) httpServletRequest.getSession().getAttribute(Constants.ADMIN_ID);
        try {
            Boolean status = this.createSubOrg(reqParam, orgId, adminId, file, httpServletRequest);
            if (status) {
                return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, Constants.SUB_ORG_CREATE_MESSAGE, "redirect:/sub-org?v=467543557hr");
            }
        } catch (Exception ex) {
            String referer = httpServletRequest.getHeader(Constants.REFERER);
            ex.printStackTrace();
            if (ex instanceof DataIntegrityViolationException) {
                ex.printStackTrace();
                return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.SUB_ORG_ALREADY_EXISTS, Constants.REDIRECT + referer); // User Already exist.
            }
            ex.printStackTrace();
            return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.SOMETHING_WENT_WRONG, Constants.REDIRECT + referer); // something went wrong
        }
        return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.SUB_ORG_CREATION_LIMIT, "redirect:/sub-org?v=467543557hr");
    }

    @Transactional
    public boolean createSubOrg(Map<String, String> reqParam, Integer orgId, Integer adminId, MultipartFile file, HttpServletRequest httpServletRequest) throws Exception {
        String filePath = null;
        Boolean getSubOrgCount = this.checkSubOrgCount(orgId);
        if (getSubOrgCount) {
            SubOrg subOrg = subOrgRepository.save(SubOrgUtil.create(reqParam, orgId, adminId));

            this.createSubOrgProductHierarchy(subOrg, orgId);

            this.createSubOrgLanguage(subOrg, orgId);

            this.createSubOrgContent(subOrg, orgId);

            Org org = orgRepository.findById(orgId).get();
            AdminUser adminUser = adminUserRepository.save(SubOrgUtil.createAdminUser(reqParam, subOrg, org));

            adminHasRoleRepository.save(SubOrgUtil.createAdminUserRole(adminUser));

            /*this.sendEmail(adminUser.getEmail(),adminUser.getFirstName());*/

            if (!file.isEmpty()) {
                filePath = this.upload(file, subOrg.getId(), orgId, httpServletRequest);
            }

            this.createWelcomeScreenData(orgId, subOrg.getId());
            this.setImagePath(subOrg.getId(), filePath);
            reportMessagesforSubOrg(subOrg);
            floatTrendingSettings(subOrg);

            return true;
        }

        return false;
    }

    /*sub org listing*/
    public Page<SubOrg> getSubOrganizations(Integer orgId, HttpServletRequest httpServletRequest) {

        Page<SubOrg> subOrgs = subOrgRepository.findAllByOrgId(orgId, SubOrgUtil.paginationSubOrgListing(httpServletRequest));
        return listSanitization(subOrgs);
    }

    /*for search box*/
    public Page<SubOrg> getSubOrganizations(String query, HttpServletRequest httpServletRequest) {
        query = Constants.PERCENTAGE_SYMBOL + query + Constants.PERCENTAGE_SYMBOL;
        Page<SubOrg> subOrgs = null;
        if (!query.equals(Constants.NULL)) {
            subOrgs = subOrgRepository.findByNameContainingAndOrgId(query, ControlPanelUtil.setOrgId(httpServletRequest), SubOrgUtil.paginationSubOrgSearchBox(httpServletRequest));
            return listSanitization(subOrgs);
        }
        return listSanitization(subOrgs);
    }

    public SubOrg findBySubOrgId(Integer subOrgId) {
        return subOrgRepository.getOrgdata(subOrgId);

    }

    public String updateOrgHandling(Map<String, String> reqParam, MultipartFile file, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        Integer orgId = (Integer) httpServletRequest.getSession().getAttribute(Constants.ORG_ID);
        Integer adminId = (Integer) httpServletRequest.getSession().getAttribute(Constants.ADMIN_ID);
        try {
            this.update(reqParam, orgId, adminId, file, httpServletRequest);
            return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, Constants.SUB_ORG_UPDATE_SUCCESS, "redirect:/sub-org?v=467543557hr");
        } catch (Exception ex) {
            String referer = httpServletRequest.getHeader(Constants.REFERER);
            ex.printStackTrace();
            if (ex instanceof DataIntegrityViolationException) {
                ex.printStackTrace();
                return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.SUB_ORG_ALREADY_EXISTS, Constants.REDIRECT + referer); // User Already exist.
            }
            return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.SOMETHING_WENT_WRONG, Constants.REDIRECT + referer); // something went wrong
        }
    }

    public Integer update(Map<String, String> reqParam, Integer orgId, Integer adminId, MultipartFile file, HttpServletRequest httpServletRequest) throws Exception {
        String filePath = null;
        if (!file.isEmpty()) {
            filePath = this.upload(file, SubOrgUtil.setSubOrgId(httpServletRequest), orgId, httpServletRequest);
        }
        SubOrg subOrg = this.findBySubOrgId(SubOrgUtil.setSubOrgId(httpServletRequest));
        subOrgRepository.save(SubOrgUtil.update(reqParam, orgId, adminId, subOrg, filePath));

        adminUserRepository.save(SubOrgUtil.updateSubOrgAdminUser(subOrg, reqParam));
        return SubOrgUtil.setSubOrgId(httpServletRequest);
    }

    public Boolean setActivation(Integer id, HttpServletRequest httpServletRequest, Integer value) {
        Integer count = subOrgRepository.setActivation(id, value);
        List<AdminUser> adminUsers = adminUserRepository.findAllBySubOrgId(id);
        adminUserRepository.saveAll(SubOrgUtil.setActivationAdmins(adminUsers, value));
        if (count > Constants.ZERO) {
            return true;
        }
        return false;
    }

    /*populate user select sub org box*/
    public List<SubOrg> getSubOrgList(HttpServletRequest httpServletRequest) {
        return subOrgRepository.findByOrgIdAndStatus(ControlPanelUtil.setOrgId(httpServletRequest), 1);
    }

    private Page<SubOrg> listSanitization(Page<SubOrg> subOrgs) {
        return SubOrgUtil.listSanitization(subOrgs);
    }

    private void createSubOrgProductHierarchy(SubOrg subOrg, Integer orgId) {
        try {
            List<OrgProductHierarchy> orgProductHierarchies = orgProductHierarchyService.getProductData(orgId);
            subOrgProductHierarchyRepository.saveAll(SubOrgUtil.createSubOrgProductHierarchy(orgProductHierarchies, subOrg, orgId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createSubOrgLanguage(SubOrg subOrg, Integer orgId) {
        try {
            List<OrgLanguage> orgLanguages = orgLanguageRepository.findAllByOrgId(orgId);
            subOrgLanguageRepository.saveAll(SubOrgUtil.createSubOrgLanguage(orgLanguages, subOrg, orgId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Boolean checkSubOrgCount(Integer orgId) {
        Integer currentCount = subOrgRepository.countDistinctByOrgId(orgId);
        Optional<Org> org = orgRepository.findById(orgId);
        return SubOrgUtil.checkSubOrgCount(org, currentCount);
    }

    private Boolean createSubOrgContent(SubOrg subOrg, Integer orgId) {
        try {
            List<OrgContent> contentsAvailable = orgContentTypeRepository.findAllByOrgId(orgId);
            subOrgContentTypeRepository.saveAll(SubOrgUtil.createSubOrgContent(contentsAvailable, subOrg, orgId));
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    Boolean sendEmail(String email, String firstName, String password) {
        return emailService.sendMail(email, firstName, password);
    }

    public String upload(MultipartFile file, Integer subOrgId, Integer orgId, HttpServletRequest httpServletRequest) {
        String relativePath = Constants.EMPTY;
        ResponseEntity<Map> response = uploadClient.UploadClient(SubOrgUtil.uploadCreate(orgId, subOrgId), file);
        Map map = response.getBody();
        if (map != null) {
            Integer is_org_level = Constants.ONE;
            relativePath = uploadClient.getRelativePath(map.get(Constants.PATH).toString(), httpServletRequest, is_org_level, orgId);
        }
        return relativePath;
    }

    private void setImagePath(Integer id, String filePath) {
        subOrgRepository.save(SubOrgUtil.setImagePath(subOrgRepository.getOrgdata(id), filePath));
    }


    private void createWelcomeScreenData(Integer orgId, Integer subOrgId) {
        subOrgwelcomeScreenRepository.saveAll(SubOrgUtil.createWelcomeScreenData(orgId, subOrgId, welcomeScreenService.getData()));
    }

    private void reportMessagesforSubOrg(SubOrg subOrg) {
        subOrgConfigurationRepository.save(SubOrgUtil.floatReportContentData(subOrg));
    }

    private void floatTrendingSettings(SubOrg subOrg) {
        subOrgConfigurationRepository.save(floatTrendingData(subOrg, storageConfigurationRepository.findBySettingName(TRENDING_CONFIG)));

    }


}