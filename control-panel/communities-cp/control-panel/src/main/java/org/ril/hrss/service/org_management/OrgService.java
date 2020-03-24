package org.ril.hrss.service.org_management;

import org.ril.hrss.model.Country;
import org.ril.hrss.model.Org;
import org.ril.hrss.model.SubOrg;
import org.ril.hrss.model.auth.AdminUser;
import org.ril.hrss.model.auth.RoleMaster;
import org.ril.hrss.model.content.Content;
import org.ril.hrss.model.content.OrgContent;
import org.ril.hrss.model.content.SubOrgContent;
import org.ril.hrss.model.language.Language;
import org.ril.hrss.model.product_hierarchy.ProductHierarchy;
import org.ril.hrss.model.roles_and_access.CommunityRole;
import org.ril.hrss.model.roles_and_access.RolePermissionMaster;
import org.ril.hrss.repository.*;
import org.ril.hrss.service.content.ContentService;
import org.ril.hrss.service.content.OrgContentsService;
import org.ril.hrss.service.rest_api_services.StorageConfigService;
import org.ril.hrss.service.rest_api_services.StorageContainer;
import org.ril.hrss.service.rest_api_services.UploadClient;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ControlPanelUtil;
import org.ril.hrss.utility.OrgUtil;
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
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class OrgService {

    @Autowired
    ContentService contentService;
    @Autowired
    CountryService countryService;
    @Autowired
    OrgContentsService orgContentsService;
    @Autowired
    StorageContainer storageContainer;
    @Autowired
    RoleMasterRepository roleMasterRepository;
    @Autowired
    CommunityRoleRepository communityRoleRepository;
    @Autowired
    RolePermissionMasterRepository rolePermissionMasterRepository;
    @Autowired
    CommunityRolePermissionRepository communityRolePermissionRepository;
    @Autowired
    private OrgRepository orgRepository;
    @Autowired
    private AdminUserRepository adminUserRepository;
    @Autowired
    private AdminHasRoleRepository adminHasRoleRepository;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private ProductHierarchyRepository productHierarchyRepository;
    @Autowired
    private OrgProductHierarchyRepository orgProductHierarchyRepository;
    @Autowired
    private OrgLanguageRepository orgLanguageRepository;
    @Autowired
    private SubOrgRepository subOrgRepository;
    @Autowired
    private OrgContentTypeRepository orgContentTypeRepository;
    @Autowired
    private SubOrgContentTypeRepository subOrgContentTypeRepository;
    @Autowired
    private EmailServiceDemo emailService;
    @Autowired
    private UploadClient uploadClient;
    @Autowired
    private OrgConfigurationRepository orgConfigurationRepository;
    @Autowired
    private StorageConfigService storageConfigService;

    public String index(HttpServletRequest httpServletRequest, Model model, String query) {

        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        if (query != null) {
            Page<Org> orgs = this.getOrgList(query, httpServletRequest);
            return OrgUtil.orgListingIndex(model, orgs, query);
        }
        Page<Org> orgList = this.getOrgList(httpServletRequest);
        return OrgUtil.orgListingIndex(model, orgList, query);
    }

    public String createModel(HttpServletRequest httpServletRequest, Model model) {

        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        List<Content> contents = contentService.getContentForOrgCreation();
        //  List<Module> modules = moduleService.findByStatus(Constants.ONE, Module.class);
        List<Country> countries = countryService.getAllCountries();
        return OrgUtil.createModel(contents, countries, model);
    }

    public String createOrgHandling(HttpServletRequest httpServletRequest, List contentsIds, MultipartFile file, RedirectAttributes redirectAttributes) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        try {
            this.createOrg(httpServletRequest, contentsIds, file, redirectAttributes);
            return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, Constants.ORG_CREATE_SUCCESS, "redirect:/org?v=467543557hr");
        } catch (Exception ex) {
            String referer = httpServletRequest.getHeader(Constants.REFERER);
            ex.printStackTrace();
            if (ex instanceof DataIntegrityViolationException) {
                ex.printStackTrace();
                return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.ORG_ALREADY_EXISTS, Constants.REDIRECT + referer); // User Already exist.
            }
            return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.ORG_USER_ALREADY_EXIST, Constants.REDIRECT + referer); // something went wrong
        }
    }

    @Transactional
    public void createOrg(HttpServletRequest httpServletRequest, List contentsIds, MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {
        Org org = orgRepository.save(OrgUtil.create(httpServletRequest));
        this.createOrgProductHierarchy(org);
        this.createOrgLanguage(org);
        this.createOrgContent(org, contentsIds);
        Boolean imageStorage = false;
        if (httpServletRequest.getParameter(Constants.ACTIVE_STORAGE).equals(Constants.AZURE)) {
            imageStorage = this.createStorageConfig(org, httpServletRequest);
        }
        AdminUser adminUser = adminUserRepository.save(OrgUtil.createAdminUser(httpServletRequest, org));
        adminHasRoleRepository.save(OrgUtil.createAdminUserRole(adminUser));
            /*this.sendEmail(adminUser.getEmail(),adminUser.getFirstName(),httpServletRequest.getParameter("password"));*/
        List<Map> roleIdList = this.roleConfiguration(org.getId());
        if (roleIdList != null) {
            this.createRolePermission(org.getId(), roleIdList);
        }
        if (imageStorage) {
            this.setImagePath(org.getId(), file, httpServletRequest);
        }
    }

    public List getOrgList() {
        return orgRepository.findAllByStatus(Constants.ONE);

    }

    /*for search box*/
    public Page<Org> getOrgList(String query, HttpServletRequest httpServletRequest) {
        query = Constants.PERCENTAGE_SYMBOL + query + Constants.PERCENTAGE_SYMBOL;
        Page<Org> orgs = null;
        if (!query.equals(Constants.NULL)) {
            orgs = orgRepository.findByNameContaining(query, OrgUtil.paginationOrgSearchBox(httpServletRequest));
            return listSanitization(orgs);
        }
        return listSanitization(orgs);
    }

    /*for org listing page*/
    public Page<Org> getOrgList(HttpServletRequest httpServletRequest) {
        Page<Org> orgPage = orgRepository.findAll(OrgUtil.paginationOrgListing(httpServletRequest));
        return listSanitization(orgPage);
    }


    public Org findByOrgId(Integer orgId) {
        Org org = new Org();
        if (orgRepository.findById(orgId).isPresent()) {
            return orgRepository.findById(orgId).get();
        }
        return org;

    }

    public Page<Org> listSanitization(Page<Org> orgs) {
        return OrgUtil.listSanitization(orgs);
    }

    public String updateOrgHandling(HttpServletRequest httpServletRequest, List contentsIds, MultipartFile file, RedirectAttributes redirectAttributes) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        try {
            this.update(contentsIds, httpServletRequest, file);
            return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, Constants.ORG_UPDATE_SUCCESS, "redirect:/org?v=467543557hr");
        } catch (Exception ex) {
            String referer = httpServletRequest.getHeader(Constants.REFERER);
            ex.printStackTrace();
            if (ex instanceof DataIntegrityViolationException) {
                ex.printStackTrace();
                return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.ORG_ALREADY_EXISTS, Constants.REDIRECT + referer); // User Already exist.
            }
            System.out.println(ex);
            return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.SOMETHING_WENT_WRONG, Constants.REDIRECT + referer); // something went wrong
        }
    }

    @Transactional
    public Integer update(List contentsIds, HttpServletRequest httpServletRequest, MultipartFile file) throws Exception {
        Integer orgId = Integer.parseInt(httpServletRequest.getParameter(Constants.ORG_ID));
        String filePath = null;
        if (!file.isEmpty()) {
            filePath = this.uploadUpdate(file, orgId, httpServletRequest);
        }
        Org org = this.findByOrgId(orgId);
        orgRepository.save(OrgUtil.update(httpServletRequest, org, filePath));
        this.updateOrgContent(org, contentsIds);
        adminUserRepository.save(OrgUtil.updateAdminUser(httpServletRequest, org));
        storageConfigService.update(httpServletRequest, orgId);
        return orgId;
    }

    public Org getOrgById(Integer id) {
        Org org = null;
        Optional<Org> o = orgRepository.findById(id);
        if (o.isPresent()) {
            return o.get();
        }
        return org;
    }

    public String setActivation(Integer id, Integer value, HttpServletRequest httpServletRequest, RedirectAttributes
            redirectAttributes) throws Exception {
        Integer count = 1;
        Org org = orgRepository.findById(id).get();
        org.setStatus(value);
        Org org1 = orgRepository.save(org);
        Integer setActivationAdmins = adminUserRepository.deactivateAll(id, value);
        List<SubOrg> subOrgs = subOrgRepository.findAllByOrgId(id);
        subOrgRepository.saveAll(OrgUtil.deActivateSubOrgs(subOrgs, value));
        return OrgUtil.deactivationCount(count, setActivationAdmins, httpServletRequest, redirectAttributes);
    }

    public Boolean checkUniqueName(String name) {
        Integer nameCount = orgRepository.countByName(name);
        return OrgUtil.checkNameCount(nameCount);
    }

    public Org getCurrentOrg(HttpSession httpSession) {
        Optional<Org> org = orgRepository.findById((Integer) httpSession.getAttribute(Constants.ORG_ID));
        return org.get();
    }

    private void createOrgProductHierarchy(Org org) {
        try {
            List<ProductHierarchy> productHierarchies = productHierarchyRepository.findAllByStatusOrderByLevel(Constants.ONE);
            orgProductHierarchyRepository.saveAll(OrgUtil.createProductHierarchy(productHierarchies, org));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createOrgLanguage(Org org) {
        try {
            List<Language> languageList = languageRepository.findAllByStatus(Constants.ONE);
            orgLanguageRepository.saveAll(OrgUtil.createOrgLanguage(languageList, org));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createOrgContent(Org org, List contentsIds) {
        try {
            List<Content> contentsAvailable = contentRepository.findAllById(contentsIds);
            List<Content> contentList = contentRepository.findAllByStatus(Constants.ONE);
            orgContentTypeRepository.saveAll(OrgUtil.createOrgContent(contentList, contentsAvailable, org));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateOrgContent(Org org, List contentsIds) {
        try {
            List<OrgContent> contentsExisting = orgContentTypeRepository.findAllByOrgIdAndStatusAndIsConfigurable(org.getId(), Constants.ONE, Constants.ONE);
            orgContentTypeRepository.saveAll(OrgUtil.updateOrgContentSetExisting(contentsExisting));
            List<OrgContent> contentsAvailable = orgContentTypeRepository.findAllById(contentsIds);
            orgContentTypeRepository.saveAll(OrgUtil.updateOrgContentSetSelected(contentsAvailable));
            List<SubOrgContent> subOrgContents = subOrgContentTypeRepository.findAllByOrgId(org.getId());
            subOrgContentTypeRepository.saveAll(OrgUtil.updateSubOrgContentSetExisting(subOrgContents));
            List<SubOrgContent> subOrgContentList = subOrgContentTypeRepository.findAllByOrgContentTypeIdIn(contentsIds);
            subOrgContentTypeRepository.saveAll(OrgUtil.updateSubOrgContentSetSelected(subOrgContentList));
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    Boolean sendEmail(String email, String firstName, String password) {
        return emailService.sendMail(email, firstName, password);
    }

    public String uploadCreate(MultipartFile file, HttpServletRequest httpServletRequest) {
        String relativePath = Constants.EMPTY;
        ResponseEntity<Map> response = uploadClient.UploadClient(OrgUtil.uploadCreate(), file);
        Map map = response.getBody();
        Integer is_org_level = Constants.ZERO;
        Integer orgId = Constants.ZERO;
        if (map != null) {
            relativePath = uploadClient.getRelativePath(map.get(Constants.PATH).toString(), httpServletRequest, is_org_level, orgId);
        }
        return relativePath;
    }

    public String uploadUpdate(MultipartFile file, Integer orgId, HttpServletRequest httpServletRequest) {
        String relativePath = Constants.EMPTY;
        ResponseEntity<Map> response = uploadClient.UploadClient(OrgUtil.uploadUpdate(orgId), file);
        Map map = response.getBody();
        if (map != null) {
            relativePath = map.get(Constants.PATH).toString();
        }
        return relativePath;
    }

    public Boolean setImagePath(Integer id, MultipartFile file, HttpServletRequest httpServletRequest) {
        String filePath = null;
        if (!file.isEmpty()) {/*if the config has been stored only then call upload*/
            filePath = this.uploadCreate(file, httpServletRequest);
        }
        try {
            Org org = this.findByOrgId(id);
            org.setLogoImage(filePath);
            orgRepository.save(org);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }


    private Boolean createStorageConfig(Org org, HttpServletRequest httpServletRequest) {
        try {
            orgConfigurationRepository.save(OrgUtil.createStorageForOrg(httpServletRequest, org));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String show(Integer orgId, HttpServletRequest httpServletRequest, Model model) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        Org org = this.findByOrgId(orgId);
        AdminUser admin = org.getAdminUser().get(Constants.ZERO);
        List<OrgContent> contents = orgContentsService.getOrgContents(orgId);
        List<Integer> ids = OrgUtil.addAvailableContents(contents).stream().map(OrgContent::getId).collect(Collectors.toList());
        List<Country> countries = countryService.getAllCountries();
        Map map = storageContainer.getAzureData(Constants.ONE, orgId);
        return OrgUtil.showModel(contents, org, admin, ids, orgId, countries, model, map);
    }

    public String edit(Integer orgId, HttpServletRequest httpServletRequest, Model model) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        Org org = this.findByOrgId(orgId);
        AdminUser admin = org.getAdminUser().isEmpty() ? new AdminUser() : org.getAdminUser().get(Constants.ZERO);
        List<OrgContent> contents = orgContentTypeRepository.findAllByOrgIdAndStatusAndIsConfigurable(orgId, Constants.ONE, Constants.ONE);
        List<Integer> ids = OrgUtil.addAvailableContents(contents).stream().map(OrgContent::getId).collect(Collectors.toList());
        List<Country> countries = countryService.getAllCountries();
        Map map = storageContainer.getAzureData(Constants.ONE, orgId);
        OrgUtil.setAzureMap(model, map);
        return OrgUtil.editModel(contents, org, admin, ids, orgId, countries, model, map, OrgUtil.setDecryptedPassword(admin));

    }

    public List roleConfiguration(Integer orgId) {
        List<Map> list = new ArrayList();
        try {
            List<RoleMaster> roleMasters = roleMasterRepository.findByIsConfigurable(Constants.ONE);
            for (RoleMaster roleMaster : roleMasters) {
                Map map = new HashMap();
                CommunityRole communityRole = new CommunityRole();
                communityRole.setName(roleMaster.getName());
                communityRole.setOrgId(orgId);
                communityRole.setRoleIdentity(roleMaster.getRoleIdentity());
                communityRole.setStatus(roleMaster.getStatus());
                communityRoleRepository.save(communityRole);
                SubOrgUtil.roleConfiguration(roleMaster, communityRole, map, list);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void createRolePermission(Integer orgId, List<Map> roleIdList) {
        try {
            for (Map map : roleIdList) {
                List<RolePermissionMaster> rolePermissionMasters = rolePermissionMasterRepository.findAllByMasterRoleId((Integer) map.get(Constants.MASTER_ROLE_ID));
                communityRolePermissionRepository.saveAll(SubOrgUtil.createRolePermission(rolePermissionMasters, orgId, map));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
