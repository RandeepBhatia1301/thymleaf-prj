package org.ril.hrss.service.category_hierarchy;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ril.hrss.kafka.SendNotification;
import org.ril.hrss.model.Department;
import org.ril.hrss.model.SubOrg;
import org.ril.hrss.model.auth.User;
import org.ril.hrss.model.category_hierarchy.CategoryHierarchy;
import org.ril.hrss.model.category_hierarchy.CategoryHierarchyDepartment;
import org.ril.hrss.model.category_hierarchy.CategorySubscription;
import org.ril.hrss.model.category_hierarchy.SubOrgCategoryHierarchy;
import org.ril.hrss.model.moderation.ContentConsent;
import org.ril.hrss.repository.*;
import org.ril.hrss.service.moderation.PermissionService;
import org.ril.hrss.service.rest_api_services.ElasticUploadService;
import org.ril.hrss.service.rest_api_services.StorageContainer;
import org.ril.hrss.service.rest_api_services.UploadClient;
import org.ril.hrss.utility.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HierarchyLevel1Service {

    @Autowired
    CategoryHierarchyRepository categoryHierarchyRepository;

    @Autowired
    SubOrgRepository subOrgRepository;

    @Autowired
    OrgRepository orgRepository;

    @Autowired
    CategoryTypeService categoryTypeService;

    @Autowired
    CategorySubscriptionRepository categorySubscriptionRepository;

    @Autowired
    ElasticUploadService elasticUploadService;

    @Autowired
    StorageContainer storageContainer;

    @Autowired
    UploadClient uploadClient;
    @Autowired
    CategoryTypeRepository categoryTypeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PermissionService permissionService;

    @Autowired
    AdminCreationService adminCreationService;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CategoryHierarchyDepartmentRepository categoryHierarchyDepartmentRepository;

    @Autowired
    SendNotification sendNotification;

    @Autowired
    SubOrgCategoryHierarchyRepository subOrgCategoryHierarchyRepository;

    @Autowired
    ContentConsentRepository contentConsentRepository;

    public String index(HttpServletRequest httpServletRequest, Model model) {

        List<SubOrgCategoryHierarchy> subOrgCategoryHierarchies = subOrgCategoryHierarchyRepository.findBySubOrgId(ControlPanelUtil.setSubOrgId(httpServletRequest));
        List<Integer> ids = subOrgCategoryHierarchies.parallelStream()
                .map(SubOrgCategoryHierarchy::getCategoryId).collect(Collectors.toList());
        List<CategoryHierarchy> categoryHierarchiesHybrid = categoryHierarchyRepository.findAllById(ids);

        return HierarchyLevel1Util.indexModel(model, categoryHierarchyRepository.findBySuborgIdAndParentIdAndIsHybridOrderByIdDesc(ControlPanelUtil.setSubOrgId(httpServletRequest), Constants.ZERO, Constants.ZERO, HierarchyLevel1Util.paginate(httpServletRequest)), categoryHierarchiesHybrid);
    }

    public String indexSubOrgHybrid(HttpServletRequest httpServletRequest, Model model) {

        List<SubOrgCategoryHierarchy> subOrgCategoryHierarchies = subOrgCategoryHierarchyRepository.findBySubOrgId(ControlPanelUtil.setSubOrgId(httpServletRequest));
        List<Integer> ids = subOrgCategoryHierarchies.parallelStream()
                .map(SubOrgCategoryHierarchy::getCategoryId).collect(Collectors.toList());

        List<CategoryHierarchy> categoryHierarchiesHybrid = categoryHierarchyRepository.findAllById(ids);
        return HierarchyLevel1Util.indexSubOrgHybridModel(model, categoryHierarchiesHybrid);
    }

    public String indexHybrid(HttpServletRequest httpServletRequest, Model model) {
        return HierarchyLevel1Util.indexHybridModel(model, HierarchyLevel1Util.checkCreateCommunityPermission(orgRepository.findById(ControlPanelUtil.setOrgId(httpServletRequest))), categoryHierarchyRepository.findByOrganizationIdAndParentIdAndIsHybrid(ControlPanelUtil.setOrgId(httpServletRequest), Constants.ZERO, Constants.ONE, HierarchyLevel1Util.paginate(httpServletRequest)));
    }

    public String createModel(HttpServletRequest httpServletRequest, Model model) {
        return HierarchyLevel1Util.createModel(model, subOrgRepository.findAllByOrgId(ControlPanelUtil.setOrgId(httpServletRequest)), categoryTypeService.getCategoryByOrgId(ControlPanelUtil.setOrgId(httpServletRequest)), permissionService.getRolesByPermission(httpServletRequest, PermissionUtil.setPermissionValue()), departmentRepository.findByOrgIdAndSubOrgId(ControlPanelUtil.setOrgId(httpServletRequest), ControlPanelUtil.setSubOrgId(httpServletRequest)));
    }

    public String editModel(HttpServletRequest httpServletRequest, Model model, Integer id) throws JsonProcessingException {
        List<CategorySubscription> categorySubscriptions = categorySubscriptionRepository.findByCategoryHierarchyIdAndSubOrgIdAndStatusAndIsAdmin(id, ControlPanelUtil.setSubOrgId(httpServletRequest), Constants.ONE, Constants.ONE);
        Integer adminRoleId = 0;
        if (!categorySubscriptions.isEmpty()) {
            adminRoleId = categorySubscriptions.get(Constants.ZERO).getAdminRoleId();

        }

        List<CategorySubscription> categorySubscriptionsSME = categorySubscriptionRepository.findByCategoryHierarchyIdAndSubOrgIdAndStatusAndIsSME(id, ControlPanelUtil.setSubOrgId(httpServletRequest), Constants.ONE, Constants.ONE);
        Integer smeRoleId = 0;
        if (!categorySubscriptionsSME.isEmpty()) {
            smeRoleId = categorySubscriptionsSME.get(Constants.ZERO).getSmeRoleId();

        }
        List<Department> departments = departmentRepository.findByOrgIdAndSubOrgId(ControlPanelUtil.setOrgId(httpServletRequest), ControlPanelUtil.setSubOrgId(httpServletRequest));
        List<CategoryHierarchyDepartment> list = categoryHierarchyDepartmentRepository.findByCategoryHierarchyId(id);

        return HierarchyLevel1Util.editModel(model, id, subOrgRepository.findAllByOrgId(ControlPanelUtil.setOrgId(httpServletRequest)), categoryTypeService.getCategoryByOrgId(ControlPanelUtil.setOrgId(httpServletRequest)), this.getCategoryById(id), storageContainer.getAzureData(Constants.ONE, ControlPanelUtil.setOrgId(httpServletRequest)), userRepository.findAllByIdIn(CategoryHierarchyUtil.streamId(categorySubscriptions)), permissionService.getRolesByPermission(httpServletRequest, PermissionUtil.setPermissionValue()), userRepository.findAllByIdIn(CategoryHierarchyUtil.streamId(categorySubscriptionsSME)), departments, list, adminRoleId, smeRoleId);
    }

    public String editHybridModel(HttpServletRequest httpServletRequest, Model model, Integer id) throws JsonProcessingException {
        List<CategorySubscription> categorySubscriptions = categorySubscriptionRepository.findByCategoryHierarchyIdAndOrganizationIdAndStatusAndIsAdmin(id, ControlPanelUtil.setOrgId(httpServletRequest), Constants.ONE, Constants.ONE);
        List<CategorySubscription> categorySubscriptionsSME = categorySubscriptionRepository.findByCategoryHierarchyIdAndOrganizationIdAndStatusAndIsSME(id, ControlPanelUtil.setOrgId(httpServletRequest), Constants.ONE, Constants.ONE);

        List<SubOrgCategoryHierarchy> subOrgCategoryHierarchies = subOrgCategoryHierarchyRepository.findByOrgIdAndCategoryId(ControlPanelUtil.setOrgId(httpServletRequest), id);
        Integer adminRoleId = categorySubscriptions.get(Constants.ZERO).getAdminRoleId();
        Integer smeRoleId = categorySubscriptionsSME.get(Constants.ZERO).getSmeRoleId();

        List<SubOrg> list = subOrgRepository.findAllById(subOrgCategoryHierarchies.stream()
                .map(SubOrgCategoryHierarchy::getSubOrgId).collect(Collectors.toList()));

        return HierarchyLevel1Util.editHybridModel(model, id, subOrgRepository.findAllByOrgId(ControlPanelUtil.setOrgId(httpServletRequest)), categoryTypeService.getCategoryByOrgId(ControlPanelUtil.setOrgId(httpServletRequest)), this.getCategoryById(id), storageContainer.getAzureData(Constants.ONE, ControlPanelUtil.setOrgId(httpServletRequest)), userRepository.findAllByIdIn(CategoryHierarchyUtil.streamId(categorySubscriptions)), permissionService.getRolesByPermission(httpServletRequest, PermissionUtil.setPermissionValue()), userRepository.findAllByIdIn(CategoryHierarchyUtil.streamId(categorySubscriptionsSME)), list, adminRoleId, smeRoleId);
    }

    @Transactional
    public String create(HttpServletRequest httpServletRequest, List<String> adminEmails, List<String> smeEmails, MultipartFile coverImg, MultipartFile bannerImg, MultipartFile svgImg, RedirectAttributes redirectAttributes, List<Integer> dept) {
        try {
            adminEmails.removeAll(Collections.singleton(""));
            smeEmails.removeAll(Collections.singleton(""));
            Set<String> stringsEmails = new HashSet<>();
            stringsEmails.addAll(adminEmails);
            stringsEmails.addAll(smeEmails);
            stringsEmails.remove(null);
            stringsEmails.remove("");
            // TODO: 18-07-2019 handle exit criteria check 
            for (String s : stringsEmails) {
                User user = userRepository.findBySubOrgIdAndUserExternalId(ControlPanelUtil.setSubOrgId(httpServletRequest), s);
                if (user != null && user.getStatus() == 0) {
                    String referer = httpServletRequest.getHeader(Constants.REFERER);
                    return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, "The email " + user.getEmail() + " is not a part of the system. Please try again using a different email", Constants.REDIRECT + referer);
                }
            }

            CategoryHierarchy categoryHierarchy = categoryHierarchyRepository.save(HierarchyLevel1Util.create(httpServletRequest, this.setImageDemo(httpServletRequest, coverImg, bannerImg, svgImg), ControlPanelUtil.setOrgId(httpServletRequest), ControlPanelUtil.setAdminId(httpServletRequest), Constants.ZERO, ControlPanelUtil.setSubOrgId(httpServletRequest), categoryTypeRepository.findById(Integer.valueOf(httpServletRequest.getParameter(Constants.CATEGORY_TYPE))).get()));

            Boolean userRoleCreation = false;
            Boolean adminRoleCreation = false;
            List<User> userList = adminCreationService.createCommunityAdminRegister(httpServletRequest, adminEmails);
            if (userList != null && !userList.isEmpty()) {
                userRoleCreation = adminCreationService.createCommunityUserRole(userList, ControlPanelUtil.setSubOrgId(httpServletRequest), ControlPanelUtil.setOrgId(httpServletRequest));
            }
            if (userRoleCreation) {
                adminRoleCreation = adminCreationService.createCommunityAdminRole(userList, Integer.valueOf(httpServletRequest.getParameter(Constants.ROLE_ID)));
            }
            if (adminRoleCreation) {
                adminCreationService.subscribeUserToCommunity(userList, categoryHierarchy.getId(), httpServletRequest, Constants.ZERO);
            }
            List<User> smeUserList = adminCreationService.createCommunityAdminRegister(httpServletRequest, smeEmails);
            if (smeUserList != null && !smeUserList.isEmpty()) {
                userRoleCreation = adminCreationService.createCommunityUserRole(smeUserList, ControlPanelUtil.setSubOrgId(httpServletRequest), ControlPanelUtil.setOrgId(httpServletRequest));
            }
            if (userRoleCreation) {
                adminRoleCreation = adminCreationService.createCommunityAdminRole(smeUserList, Integer.valueOf(httpServletRequest.getParameter(Constants.ROLE_ID_SME)));
            }
            if (adminRoleCreation) {
                adminCreationService.subscribeSmeUserToCommunity(smeUserList, categoryHierarchy.getId(), httpServletRequest, Constants.ZERO);
            }
            if (dept != null && !dept.isEmpty()) {
                this.departmentMapping(categoryHierarchy, dept);
            }

            sendNotification.sendNotification(categoryHierarchy.getTitle(), Constants.ZERO, ControlPanelUtil.setOrgId(httpServletRequest), ControlPanelUtil.setSubOrgId(httpServletRequest), Constants.ONE, Constants.AOI, categoryHierarchy.getId());
            this.elasticDataEntry(categoryHierarchy, httpServletRequest);
            return HierarchyLevel1Util.storeSuccessModel(Constants.TWO, redirectAttributes);
        } catch (Exception ex) {
            ex.printStackTrace();
            return HierarchyLevel1Util.storeSuccessModel(Constants.THREE, redirectAttributes);
        }
    }

    private CategoryHierarchy getCategoryById(Integer id) {
        CategoryHierarchy hierarchy = new CategoryHierarchy();
        Optional<CategoryHierarchy> categoryHierarchy = categoryHierarchyRepository.findById(id);
        if (categoryHierarchy.isPresent()) {
            hierarchy = categoryHierarchy.get();
        }
        return hierarchy;
    }

    public String update(HttpServletRequest httpServletRequest, MultipartFile coverImg, MultipartFile bannerImg, MultipartFile svgImg, List<String> adminEmails, List<String> smeEmails, RedirectAttributes redirectAttributes, List<Integer> dept) {

        try {
            adminEmails.removeAll(Collections.singleton(""));
            smeEmails.removeAll(Collections.singleton(""));
            Set<String> stringsEmails = new HashSet<>();
            stringsEmails.addAll(adminEmails);
            stringsEmails.addAll(smeEmails);
            stringsEmails.remove(null);
            stringsEmails.remove("");
            for (String s : stringsEmails) {
                User user = userRepository.findBySubOrgIdAndUserExternalId(ControlPanelUtil.setSubOrgId(httpServletRequest), s);
                if (user != null && user.getStatus() == 0) {
                    String referer = httpServletRequest.getHeader(Constants.REFERER);
                    return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, "The email " + user.getEmail() + " is not a part of the system. Please try again using a different email", Constants.REDIRECT + referer);
                }
            }

            CategoryHierarchy categoryHierarchy = categoryHierarchyRepository.save(HierarchyLevel1Util.update(categoryHierarchyRepository.findByIdAndOrganizationId(Integer.valueOf(httpServletRequest.getParameter(Constants.ID)), ControlPanelUtil.setOrgId(httpServletRequest)), httpServletRequest, this.updateImageJson(httpServletRequest, coverImg, bannerImg, svgImg), categoryTypeRepository.findById(Integer.valueOf(httpServletRequest.getParameter(Constants.CATEGORY_TYPE))).get()));

            Boolean userRoleCreation = false;
            Boolean adminRoleCreation = false;
            List<User> userList = adminCreationService.createCommunityAdminRegister(httpServletRequest, adminEmails);
            if (userList != null && !userList.isEmpty()) {
                userRoleCreation = adminCreationService.createCommunityUserRole(userList, ControlPanelUtil.setSubOrgId(httpServletRequest), ControlPanelUtil.setOrgId(httpServletRequest));
            }
            if (userRoleCreation) {
                adminRoleCreation = adminCreationService.createCommunityAdminRole(userList, Integer.valueOf(httpServletRequest.getParameter(Constants.ROLE_ID)));
            }
            if (adminRoleCreation) {
                adminCreationService.subscribeUserToCommunity(userList, categoryHierarchy.getId(), httpServletRequest, Constants.ZERO);
            }

            List<User> smeUserList = adminCreationService.createCommunityAdminRegister(httpServletRequest, smeEmails);
            if (smeUserList != null && !smeUserList.isEmpty()) {
                userRoleCreation = adminCreationService.createCommunityUserRole(smeUserList, ControlPanelUtil.setSubOrgId(httpServletRequest), ControlPanelUtil.setOrgId(httpServletRequest));
            }
            if (userRoleCreation) {
                adminRoleCreation = adminCreationService.createCommunityAdminRole(smeUserList, Integer.valueOf(httpServletRequest.getParameter(Constants.ROLE_ID_SME)));
            }
            if (adminRoleCreation) {
                adminCreationService.subscribeSmeUserToCommunity(smeUserList, categoryHierarchy.getId(), httpServletRequest, Constants.ZERO);
            }
            if (dept != null && !dept.isEmpty()) {
                this.departmentMapping(categoryHierarchy, dept);
            }
            this.elasticDataEntry(categoryHierarchy, httpServletRequest);

            if (Integer.valueOf(httpServletRequest.getParameter(Constants.IS_PRIVATE)) == 0) {

                cancelJoinRequestForPrivateCommunity(categoryHierarchy.getId(), httpServletRequest);
                cancelJoinRequestForPrivateCommunityInContentConsent(categoryHierarchy.getId(), httpServletRequest);
            }
            return HierarchyLevel1Util.updateSuccessModel(Boolean.TRUE, redirectAttributes);
        } catch (Exception ex) {
            ex.printStackTrace();
            return HierarchyLevel1Util.updateSuccessModel(Boolean.FALSE, redirectAttributes);
        }
    }

    public String updateHybrid(HttpServletRequest httpServletRequest, MultipartFile coverImg, MultipartFile bannerImg, MultipartFile svgImg, List<Integer> suborgIds, List<String> adminEmails, List<String> smeEmails, RedirectAttributes redirectAttributes) {
        try {
            adminEmails.removeAll(Collections.singleton(""));
            smeEmails.removeAll(Collections.singleton(""));
            CategoryHierarchy categoryHierarchy = HierarchyLevel1Util.update(categoryHierarchyRepository.findByIdAndOrganizationId(Integer.valueOf(httpServletRequest.getParameter(Constants.ID)), ControlPanelUtil.setOrgId(httpServletRequest)), httpServletRequest, this.updateImageJson(httpServletRequest, coverImg, bannerImg, svgImg), categoryTypeRepository.findById(Integer.valueOf(httpServletRequest.getParameter(Constants.CATEGORY_TYPE))).get());
            categoryHierarchyRepository.save(categoryHierarchy);
            this.mapSubOrgToCommunity(suborgIds, httpServletRequest, (Integer.valueOf(httpServletRequest.getParameter(Constants.ID))));
            Boolean userRoleCreation = false;
            Boolean adminRoleCreation = false;
            List<User> userList = adminCreationService.createCommunityAdminRegister(httpServletRequest, adminEmails);
            if (userList != null && !userList.isEmpty()) {
                userRoleCreation = adminCreationService.createCommunityUserRole(userList, ControlPanelUtil.setSubOrgId(httpServletRequest), ControlPanelUtil.setOrgId(httpServletRequest));
            }
            if (userRoleCreation) {
                adminRoleCreation = adminCreationService.createCommunityAdminRole(userList, Integer.valueOf(httpServletRequest.getParameter(Constants.ROLE_ID)));
            }
            if (adminRoleCreation) {
                adminCreationService.subscribeUserToHybridCommunity(userList, categoryHierarchy.getId(), httpServletRequest, Constants.ZERO);
            }

            List<User> smeUserList = adminCreationService.createCommunityAdminRegister(httpServletRequest, smeEmails);
            if (smeUserList != null && !smeUserList.isEmpty()) {
                userRoleCreation = adminCreationService.createCommunityUserRole(smeUserList, ControlPanelUtil.setSubOrgId(httpServletRequest), ControlPanelUtil.setOrgId(httpServletRequest));
            }
            if (userRoleCreation) {
                adminRoleCreation = adminCreationService.createCommunityAdminRole(smeUserList, Integer.valueOf(httpServletRequest.getParameter(Constants.ROLE_ID_SME)));
            }
            if (adminRoleCreation) {
                adminCreationService.subscribeSmeUserToCommunity(smeUserList, categoryHierarchy.getId(), httpServletRequest, Constants.ZERO);
            }

            this.elasticDataEntry(categoryHierarchy, httpServletRequest);
            hybridCommunityCheckAoi(httpServletRequest, suborgIds);
            cancelJoinRequestForPrivateCommunity(categoryHierarchy.getId(), httpServletRequest);
            cancelJoinRequestForPrivateCommunityInContentConsent(categoryHierarchy.getId(), httpServletRequest);
            return HierarchyLevel1Util.updateHybridSuccessModel(Boolean.TRUE, redirectAttributes);
        } catch (Exception ex) {
            ex.printStackTrace();
            return HierarchyLevel1Util.updateHybridSuccessModel(Boolean.FALSE, redirectAttributes);
        }
    }

    public String setActivation(Integer id, HttpServletRequest httpServletRequest, Integer value, RedirectAttributes redirectAttributes) {
        String referer = httpServletRequest.getHeader(Constants.REFERER);
        List<CategoryHierarchy> aoi = new ArrayList<>();
        try {
            CategoryHierarchy categoryHierarchy = categoryHierarchyRepository.findByIdAndOrganizationId(id, ControlPanelUtil.setOrgId(httpServletRequest));
            categoryHierarchy.setStatus(value);
            categoryHierarchyRepository.save(categoryHierarchy);
            if (categoryHierarchy != null) {
                aoi = categoryHierarchyRepository.findBySuborgIdAndParentId(ControlPanelUtil.setSubOrgId(httpServletRequest), categoryHierarchy.getId());
                List<CategoryHierarchy> list = new ArrayList<>();
                aoi.forEach(categoryHierarchy1 -> {
                    categoryHierarchy1.setStatus(value);
                    list.add(categoryHierarchy1);
                });
                categoryHierarchyRepository.saveAll(list);
            }


            this.deactivateCommunitySetUserSubscription(id, httpServletRequest, value);

            if (value == Constants.ZERO) {
                //trying to deactivate the community : delete from index
                elasticUploadService.elasticDelete(Constants.ELASTIC_COMMUNITY_CONTENT_TYPE_ID, id);
                if (!aoi.isEmpty() && aoi != null) {
                    aoi.forEach(categoryHierarchy1 -> {
                        elasticUploadService.elasticDelete(Constants.ELASTIC_AOI_CONTENT_TYPE_ID, categoryHierarchy1.getId());
                    });

                }


            } else if (value == Constants.ONE) {
                //trying to activate the community create a new entry
                this.elasticDataEntry(categoryHierarchy, httpServletRequest);
                if (!aoi.isEmpty() && aoi != null) {
                    aoi.forEach(categoryHierarchy1 -> {
                        this.elasticDataEntryForAOI(categoryHierarchy1, httpServletRequest);
                    });
                }

            }
            return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, Constants.STATUS_UPDATE_SUCCESS, Constants.REDIRECT + referer);

        } catch (Exception ex) {
            return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.SOMETHING_WENT_WRONG, Constants.REDIRECT + referer);
        }

    }

    private void elasticDataEntry(CategoryHierarchy categoryHierarchy, HttpServletRequest httpServletRequest) {
        elasticUploadService.elasticClient(CategoryHierarchyUtil.setElasticMap(categoryHierarchy, categoryTypeService.getCategoryData(categoryHierarchy.getCategoryType().getId(), categoryHierarchy.getOrganizationId()), Constants.CONTENT_ID_COMMUNITY, httpServletRequest));
    }

    public String elasticIndexRefresh(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {

        List<CategoryHierarchy> categoryHierarchies = categoryHierarchyRepository.findByOrganizationIdAndSuborgIdAndParentIdAndStatus(ControlPanelUtil.setOrgId(httpServletRequest), ControlPanelUtil.setSubOrgId(httpServletRequest), Constants.ZERO, Constants.ONE);
        try {
            for (CategoryHierarchy categoryHierarchy : categoryHierarchies) {
                this.elasticDataEntry(categoryHierarchy, httpServletRequest);
            }
            return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, Constants.DATA_REFRESHED_SUCCESSFULLY, "redirect:/community?v=%deee$fe");
        } catch (Exception e) {
            e.printStackTrace();
            return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.SOMETHING_WENT_WRONG, "redirect:/community?v=%$fe6578675");
        }
    }

    public String upload(MultipartFile file, Integer orgId, Integer subOrgId) {
        String relativePath = Constants.EMPTY;
        ResponseEntity<Map> response = uploadClient.UploadClient(HierarchyLevel1Util.upload(orgId, subOrgId), file);
        Map map = response.getBody();
        if (map != null) {
            relativePath = map.get(Constants.PATH).toString();
        }
        return relativePath;
    }

    private void departmentMapping(CategoryHierarchy categoryHierarchy, List<Integer> dept) {
        categoryHierarchyDepartmentRepository.deleteByCategoryHierarchyId(categoryHierarchy.getId());
        categoryHierarchyDepartmentRepository.saveAll(CategoryHierarchyUtil.departmentMapping(categoryHierarchy, dept));
    }

    /*
        public String createHybridCommunity(HttpServletRequest httpServletRequest, List<Integer> suborgIds, RedirectAttributes redirectAttributes, List<String> adminEmails, List<String> smeEmails, MultipartFile coverImg, MultipartFile bannerImg, MultipartFile svgImg) {

            if (!HierarchyLevel1Util.checkCommunityCount(orgRepository.findById(setOrgId(httpServletRequest)), categoryHierarchyRepository.countByOrganizationIdAndParentIdAndIsHybrid(setOrgId(httpServletRequest), ZERO, ONE))) {
                return HierarchyLevel1Util.storeSuccessModel(ONE, redirectAttributes);
            }
            List<CategoryHierarchy> list = new ArrayList<>();
            suborgIds.forEach(subOrgId -> {

                CategoryHierarchy categoryHierarchy = HierarchyLevel1Util.create(httpServletRequest, this.setImageDemo(httpServletRequest, coverImg, bannerImg, svgImg), setOrgId(httpServletRequest), ControlPanelUtil.setAdminId(httpServletRequest), ONE, subOrgId, categoryTypeRepository.findById(Integer.valueOf(httpServletRequest.getParameter(Constants.CATEGORY_TYPE))).get());

                list.add(categoryHierarchy);

            });
            List<CategoryHierarchy> hierarchyList = categoryHierarchyRepository.saveAll(list);

            if (!hierarchyList.isEmpty() && hierarchyList != null) {
                hierarchyList.forEach(categoryHierarchy -> {
                    this.mapSubOrgToCommunity(suborgIds, httpServletRequest, categoryHierarchy.getId());
                });
            }

            Boolean userRoleCreation = false;
            Boolean adminRoleCreation = false;
            List<User> userList = adminCreationService.createCommunityAdminRegister(httpServletRequest, adminEmails);
            if (userList != null && !userList.isEmpty()) {
                userRoleCreation = adminCreationService.createCommunityUserRole(userList, setSubOrgId(httpServletRequest), setOrgId(httpServletRequest));
            }
            if (userRoleCreation) {
                adminRoleCreation = adminCreationService.createCommunityAdminRole(userList, Integer.valueOf(httpServletRequest.getParameter(Constants.ROLE_ID)));
            }
            if (adminRoleCreation) {
                hierarchyList.forEach(categoryHierarchy -> {
                    adminCreationService.subscribeUserToHybridCommunity(userList, categoryHierarchy.getId(), httpServletRequest, ZERO);
                });

            }

            List<User> smeUserList = adminCreationService.createCommunityAdminRegister(httpServletRequest, smeEmails);
            if (smeUserList != null && !smeUserList.isEmpty()) {
                userRoleCreation = adminCreationService.createCommunityUserRole(smeUserList, setSubOrgId(httpServletRequest), setOrgId(httpServletRequest));
            }
            if (userRoleCreation) {
                adminRoleCreation = adminCreationService.createCommunityAdminRole(smeUserList, Integer.valueOf(httpServletRequest.getParameter(Constants.ROLE_ID_SME)));
            }
            if (adminRoleCreation) {
                hierarchyList.forEach(categoryHierarchy -> {
                    adminCreationService.subscribeSmeUserToHybridCommunity(smeUserList, categoryHierarchy.getId(), httpServletRequest, ZERO);
                });

            }
            hierarchyList.forEach(categoryHierarchy -> {
                elasticDataEntry(categoryHierarchy);
            });

            return HierarchyLevel1Util.storeHybridSuccessModel(Constants.TWO, redirectAttributes);
        }
    */
    public String createHybridCommunity(HttpServletRequest httpServletRequest, List<Integer> suborgIds, RedirectAttributes redirectAttributes, List<String> adminEmails, List<String> smeEmails, MultipartFile coverImg, MultipartFile bannerImg, MultipartFile svgImg) {

        if (!HierarchyLevel1Util.checkCommunityCount(orgRepository.findById(ControlPanelUtil.setOrgId(httpServletRequest)), categoryHierarchyRepository.countByOrganizationIdAndParentIdAndIsHybrid(ControlPanelUtil.setOrgId(httpServletRequest), Constants.ZERO, Constants.ONE))) {
            return HierarchyLevel1Util.storeHybridSuccessModel(Constants.ONE, redirectAttributes);
        }
        adminEmails.removeAll(Collections.singleton(""));
        smeEmails.removeAll(Collections.singleton(""));
        CategoryHierarchy categoryHierarchy = HierarchyLevel1Util.create(httpServletRequest, this.setImageDemo(httpServletRequest, coverImg, bannerImg, svgImg), ControlPanelUtil.setOrgId(httpServletRequest), ControlPanelUtil.setAdminId(httpServletRequest), Constants.ONE, ControlPanelUtil.setSubOrgId(httpServletRequest), categoryTypeRepository.findById(Integer.valueOf(httpServletRequest.getParameter(Constants.CATEGORY_TYPE))).get());

        CategoryHierarchy c = categoryHierarchyRepository.save(categoryHierarchy);
        this.mapSubOrgToCommunity(suborgIds, httpServletRequest, c.getId());
        Boolean userRoleCreation = false;
        Boolean adminRoleCreation = false;
        List<User> userList = adminCreationService.createCommunityAdminRegister(httpServletRequest, adminEmails);
        if (userList != null && !userList.isEmpty()) {
            userRoleCreation = adminCreationService.createCommunityUserRole(userList, ControlPanelUtil.setSubOrgId(httpServletRequest), ControlPanelUtil.setOrgId(httpServletRequest));
        }
        if (userRoleCreation) {
            adminRoleCreation = adminCreationService.createCommunityAdminRole(userList, Integer.valueOf(httpServletRequest.getParameter(Constants.ROLE_ID)));
        }
        if (adminRoleCreation) {
            adminCreationService.subscribeUserToHybridCommunity(userList, c.getId(), httpServletRequest, Constants.ZERO);
        }

        List<User> smeUserList = adminCreationService.createCommunityAdminRegister(httpServletRequest, smeEmails);
        if (smeUserList != null && !smeUserList.isEmpty()) {
            userRoleCreation = adminCreationService.createCommunityUserRole(smeUserList, ControlPanelUtil.setSubOrgId(httpServletRequest), ControlPanelUtil.setOrgId(httpServletRequest));
        }
        if (userRoleCreation) {
            adminRoleCreation = adminCreationService.createCommunityAdminRole(smeUserList, Integer.valueOf(httpServletRequest.getParameter(Constants.ROLE_ID_SME)));
        }
        if (adminRoleCreation) {
            adminCreationService.subscribeSmeUserToHybridCommunity(smeUserList, c.getId(), httpServletRequest, Constants.ZERO);
        }
        this.elasticDataEntry(categoryHierarchy, httpServletRequest);
        return HierarchyLevel1Util.storeHybridSuccessModel(Constants.TWO, redirectAttributes);
    }


    private String setImageDemo(HttpServletRequest httpServletRequest, MultipartFile coverImg, MultipartFile bannerImg, MultipartFile svgImg) {
        String json = null;
        try {
            json = HierarchyLevel1Util.setFilePath(upload(coverImg, ControlPanelUtil.setOrgId(httpServletRequest), ControlPanelUtil.setSubOrgId(httpServletRequest)), upload(bannerImg, ControlPanelUtil.setOrgId(httpServletRequest), ControlPanelUtil.setSubOrgId(httpServletRequest)), upload(svgImg, ControlPanelUtil.setOrgId(httpServletRequest), ControlPanelUtil.setSubOrgId(httpServletRequest)), httpServletRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    private String updateImageJson(HttpServletRequest httpServletRequest, MultipartFile coverImg, MultipartFile bannerImg, MultipartFile svgImg) {
        String imgUrl = null;
        String bannerImgUrl = null;
        String svgImgUrl = null;
        String json = null;

        if (!coverImg.isEmpty()) {
            imgUrl = upload(coverImg, ControlPanelUtil.setOrgId(httpServletRequest), ControlPanelUtil.setSubOrgId(httpServletRequest));
        }
        if (!bannerImg.isEmpty()) {
            bannerImgUrl = upload(bannerImg, ControlPanelUtil.setOrgId(httpServletRequest), ControlPanelUtil.setSubOrgId(httpServletRequest));
        }
        if (!svgImg.isEmpty()) {
            svgImgUrl = upload(svgImg, ControlPanelUtil.setOrgId(httpServletRequest), ControlPanelUtil.setSubOrgId(httpServletRequest));
        }
        try {
            json = HierarchyLevel1Util.setFilePath(imgUrl, bannerImgUrl, svgImgUrl, httpServletRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public String createHybridModel(HttpServletRequest httpServletRequest, Model model) {
        return HierarchyLevel1Util.createHybridModel(model, subOrgRepository.findAllByOrgId(ControlPanelUtil.setOrgId(httpServletRequest)), categoryTypeService.getCategoryByOrgId(ControlPanelUtil.setOrgId(httpServletRequest)), permissionService.getRolesByPermission(httpServletRequest, PermissionUtil.setPermissionValue()));
    }

    private void mapSubOrgToCommunity(List<Integer> subOrgIds, HttpServletRequest httpServletRequest, Integer id) {
        if (subOrgIds != null) {
            subOrgCategoryHierarchyRepository.deleteByOrgIdAndCategoryId(ControlPanelUtil.setOrgId(httpServletRequest), id);
            subOrgCategoryHierarchyRepository.saveAll(CategoryHierarchyUtil.mapSubOrgToCommunity(subOrgIds, httpServletRequest, id));
        }
    }

    private void hybridCommunityCheckAoi(HttpServletRequest httpServletRequest, List<Integer> suborgIds) {

        List<CategoryHierarchy> categoryHierarchies = categoryHierarchyRepository.findBySuborgIdAndParentId(Integer.valueOf(httpServletRequest.getParameter(Constants.ID)), Integer.valueOf(httpServletRequest.getParameter(Constants.ID)));
        categoryHierarchies.forEach(categoryHierarchy -> {
            categoryHierarchy.setStatus(0);
            categoryHierarchyRepository.save(categoryHierarchy);
        });

        suborgIds.forEach(integer -> {
            List<CategoryHierarchy> categoryHierarchies1 = categoryHierarchyRepository.findBySuborgIdAndParentId(integer, Integer.valueOf(httpServletRequest.getParameter(Constants.ID)));
            categoryHierarchies1.forEach(categoryHierarchy -> {
                categoryHierarchy.setStatus(1);
                categoryHierarchyRepository.save(categoryHierarchy);
            });

        });

    }

    private void deactivateCommunitySetUserSubscription(Integer categoryId, HttpServletRequest httpServletRequest, Integer status) {

        List<CategorySubscription> categorySubscriptions = categorySubscriptionRepository.findByCategoryHierarchyId(categoryId);

        List<CategorySubscription> subscriptions = categorySubscriptionRepository.findByParentId(categoryId);

        Set<CategorySubscription> set = new HashSet<>();
        set.addAll(categorySubscriptions);
        set.addAll(subscriptions);

        List<CategorySubscription> list = new ArrayList<>();

        set.forEach(categorySubscription -> {
            categorySubscription.setIsActive(status);
            list.add(categorySubscription);
        });
        categorySubscriptionRepository.saveAll(list);
    }


    private void cancelJoinRequestForPrivateCommunity(Integer categoryHierarchyId, HttpServletRequest httpServletRequest) {
        List<CategorySubscription> subscriptions = categorySubscriptionRepository.findByCategoryHierarchyIdAndStatusAndOrganizationId(categoryHierarchyId, 2, ControlPanelUtil.setOrgId(httpServletRequest));
        List<CategorySubscription> list = new ArrayList<>();

        subscriptions.forEach(categorySubscription -> {
            categorySubscription.setStatus(1);
            list.add(categorySubscription);
        });
        categorySubscriptionRepository.saveAll(list);
    }


    private void cancelJoinRequestForPrivateCommunityInContentConsent(Integer categoryHierarchyId, HttpServletRequest httpServletRequest) {
        List<ContentConsent> contentConsents = contentConsentRepository.findByContentTypeIdAndCategoryIdAndPendingStatus(5, categoryHierarchyId, 1);
        List<ContentConsent> list = new ArrayList<>();
        contentConsents.forEach(contentConsent -> {
            contentConsent.setPendingStatus(-1);
            contentConsent.setPublishStatus(-1);
            list.add(contentConsent);
        });
        contentConsentRepository.saveAll(list);
    }

    private void elasticDataEntryForAOI(CategoryHierarchy categoryHierarchy, HttpServletRequest httpServletRequest) {
        Optional<CategoryHierarchy> categoryHierarchyOptional = categoryHierarchyRepository.findById(categoryHierarchy.getParentId());

        elasticUploadService.elasticClient(CategoryHierarchyUtil.setElasticMap(categoryHierarchy, categoryTypeService.getCategoryData(CategoryHierarchyUtil.getCategoryHierarchyById(categoryHierarchyOptional).getCategoryType().getId(), categoryHierarchy.getOrganizationId()), Constants.CONTENT_ID_AOI, httpServletRequest));
    }


}
