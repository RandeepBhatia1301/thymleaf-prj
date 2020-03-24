package org.ril.hrss.service.category_hierarchy;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ril.hrss.kafka.SendNotification;
import org.ril.hrss.model.auth.User;
import org.ril.hrss.model.category_hierarchy.CategoryHierarchy;
import org.ril.hrss.model.category_hierarchy.CategorySubscription;
import org.ril.hrss.model.category_hierarchy.CategoryType;
import org.ril.hrss.model.category_hierarchy.SubOrgCategoryHierarchy;
import org.ril.hrss.model.moderation.ContentConsent;
import org.ril.hrss.model.roles_and_access.CommunityRole;
import org.ril.hrss.repository.*;
import org.ril.hrss.service.moderation.PermissionService;
import org.ril.hrss.service.rest_api_services.ElasticUploadService;
import org.ril.hrss.service.rest_api_services.StorageContainer;
import org.ril.hrss.service.rest_api_services.UploadClient;
import org.ril.hrss.utility.CategoryHierarchyUtil;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ControlPanelUtil;
import org.ril.hrss.utility.HierarchyLevel2Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HierarchyLevel2Service {
    @Autowired
    CategoryHierarchyRepository categoryHierarchyRepository;

    @Autowired
    ElasticUploadService elasticUploadService;

    @Autowired
    CategoryTypeService categoryTypeService;

    @Autowired
    StorageContainer storageContainer;

    @Autowired
    UploadClient uploadClient;

    @Autowired
    AdminCreationService adminCreationService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategorySubscriptionRepository categorySubscriptionRepository;
    @Autowired
    SendNotification sendNotification;

    @Autowired
    ContentConsentRepository contentConsentRepository;

    @Autowired
    SubOrgCategoryHierarchyRepository subOrgCategoryHierarchyRepository;

    public String index(Model model, HttpServletRequest httpServletRequest, HttpSession httpSession) {
     /*   Page<CategoryHierarchy> categoryHierarchies = this.getCategoryHierarchyLevel2(ControlPanelUtil.setOrgId(httpServletRequest), httpServletRequest);
        model.addAttribute(Constants.CATEGORY_LIST, categoryHierarchies);
        System.out.println(categoryHierarchies.getTotalPages() + "total pages");
        System.out.println(categoryHierarchies.getTotalElements() + "total elements");
        if (categoryHierarchies.getTotalPages() > 0) {
            model.addAttribute(Constants.PAGE, categoryHierarchies);
        }*/
        if (httpSession.getAttribute(Constants.ROLE) == Constants.SUB_ORG_ADMIN) {
            Page<CategoryHierarchy> categoryHierarchies1 = this.getSubOrgCategoryHierarchyLevel2(ControlPanelUtil.setSubOrgId(httpServletRequest), httpServletRequest);
            model.addAttribute(Constants.CATEGORY_LIST, categoryHierarchies1);
            if (categoryHierarchies1.getTotalPages() > 0) {
                model.addAttribute(Constants.PAGE, categoryHierarchies1);
            }
        }
        return "aoi/aoi-list";
    }

    public String createModel(Model model, HttpServletRequest httpServletRequest, String tagName) {

        if (tagName != null) {
            model.addAttribute(Constants.TAG_NAME, tagName);
        }
        Set<CategoryHierarchy> categoryHierarchies;
        categoryHierarchies = this.getCategoryHierarchy(httpServletRequest);

        List<CommunityRole> communityRoles = permissionService.getRolesByPermission(httpServletRequest, HierarchyLevel2Util.setPermissionValue());

        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SUB_ORG_ADMIN) {
            categoryHierarchies = this.getCategoryHierarchy(httpServletRequest);
        }
        model.addAttribute(Constants.CATEGORY_LIST, categoryHierarchies);
        model.addAttribute("roles", communityRoles);
        return "aoi/aoi-create";
    }

    public String editModel(Model model, HttpServletRequest httpServletRequest, Integer id) throws JsonProcessingException {
        Set<CategoryHierarchy> categoryHierarchies = this.getCategoryHierarchy(httpServletRequest);/*list of parent categories*/

        CategoryHierarchy categoryHierarchy = this.getCategoryById(id);/*category to edit*/
        Map map = storageContainer.getAzureData(Constants.ONE, ControlPanelUtil.setOrgId(httpServletRequest));

        List<CommunityRole> communityRoles = permissionService.getRolesByPermission(httpServletRequest, HierarchyLevel2Util.setPermissionValue());
        List<CategorySubscription> categorySubscriptions = categorySubscriptionRepository.findByCategoryHierarchyIdAndSubOrgIdAndStatusAndIsAdmin(id, ControlPanelUtil.setSubOrgId(httpServletRequest), Constants.ONE, Constants.ONE);
        List<CategorySubscription> categorySubscriptionsSME = categorySubscriptionRepository.findByCategoryHierarchyIdAndSubOrgIdAndStatusAndIsSME(id, ControlPanelUtil.setSubOrgId(httpServletRequest), Constants.ONE, Constants.ONE);
        Integer adminRoleId = 0;
        if (!categorySubscriptions.isEmpty()) {
            adminRoleId = categorySubscriptions.get(0).getAdminRoleId();

        }
        Integer smeRoleId = 0;
        if (!categorySubscriptionsSME.isEmpty()) {
            smeRoleId = categorySubscriptionsSME.get(0).getSmeRoleId();
        }

        List<User> users = userRepository.findAllByIdIn(CategoryHierarchyUtil.streamId(categorySubscriptions));
        List<User> usersSme = userRepository.findAllByIdIn(CategoryHierarchyUtil.streamId(categorySubscriptionsSME));
        return HierarchyLevel2Util.editModel(model, id, categoryHierarchy, map, categoryHierarchies, communityRoles, users, usersSme, adminRoleId, smeRoleId);
    }

    /*get List of parent category eg. community hybrid*/
    private Set<CategoryHierarchy> getCategoryHierarchy(HttpServletRequest httpServletRequest) {
        List<SubOrgCategoryHierarchy> subOrgCategoryHierarchies = subOrgCategoryHierarchyRepository.findBySubOrgId(ControlPanelUtil.setSubOrgId(httpServletRequest));
        List<Integer> ids = subOrgCategoryHierarchies.parallelStream()
                .map(SubOrgCategoryHierarchy::getCategoryId).collect(Collectors.toList());


        List<CategoryHierarchy> categoryHierarchiesHybrid = categoryHierarchyRepository.findAllByIdInAndStatus(ids, Constants.ONE);
        List<CategoryHierarchy> categoryHierarchies = categoryHierarchyRepository.findByOrganizationIdAndSuborgIdAndParentIdAndStatus(ControlPanelUtil.setOrgId(httpServletRequest), ControlPanelUtil.setSubOrgId(httpServletRequest), 0, Constants.ONE);
        Set<CategoryHierarchy> hierarchies = new HashSet<>();
        hierarchies.addAll(categoryHierarchiesHybrid);
        hierarchies.addAll(categoryHierarchies);
        return hierarchies;
        // return categoryHierarchyRepository.findByOrganizationIdAndSuborgIdAndParentIdAndStatus(ControlPanelUtil.setOrgId(httpServletRequest), ControlPanelUtil.setSubOrgId(httpServletRequest), 0, ONE);

    }

   /* *//*get List of parent category eg. community sub org*//*
    public List<CategoryHierarchy> getCategoryHierarchyForSelectBox(Integer orgId, Integer subOrgId) {
        return categoryHierarchyRepository.findByOrganizationIdAndSuborgIdAndParentId(orgId, subOrgId, 0);
    }*/

    public String createParam(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, MultipartFile files[], List<String> adminEmails, List<String> smeEmails) throws JsonProcessingException {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        String imgUrl = upload(files[Constants.ZERO], ControlPanelUtil.setOrgId(httpServletRequest), ControlPanelUtil.setSubOrgId(httpServletRequest));
        Integer isHybrid = Constants.ONE;
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SUB_ORG_ADMIN) {
            isHybrid = Constants.ZERO;
        }
        Integer status = this.create(httpServletRequest, ControlPanelUtil.setOrgId(httpServletRequest), ControlPanelUtil.setAdminId(httpServletRequest), HierarchyLevel2Util.setFilePath(imgUrl), isHybrid, ControlPanelUtil.setSubOrgId(httpServletRequest), adminEmails, smeEmails);
        return HierarchyLevel2Util.storeSuccessModel(status, redirectAttributes);
    }

    public Integer create(HttpServletRequest httpServletRequest, Integer orgId, Integer adminId, String filePath, Integer isHybrid, Integer subOrgId, List<String> adminEmails, List<String> smeEmails) {
        if (filePath == null) {
            return 2; // something went wrong
        }
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
                    return 3;
                }
            }
            CategoryHierarchy hierarchyParent = categoryHierarchyRepository.findById(Integer.valueOf(httpServletRequest.getParameter("categoryId"))).get();
            CategoryHierarchy categoryHierarchy = categoryHierarchyRepository.save(HierarchyLevel2Util.create(httpServletRequest, filePath, orgId, adminId, isHybrid, subOrgId, hierarchyParent.getCategoryType()));
            Boolean userRoleCreation = false;
            Boolean adminRoleCreation = false;
            List<User> userList = adminCreationService.createCommunityAdminRegister(httpServletRequest, adminEmails);
            if (userList != null && !userList.isEmpty()) {
                userRoleCreation = adminCreationService.createCommunityUserRole(userList, ControlPanelUtil.setSubOrgId(httpServletRequest), orgId);
            }
            if (userRoleCreation) {
                Integer roleId = Integer.valueOf(httpServletRequest.getParameter("roleId"));
                adminRoleCreation = adminCreationService.createCommunityAdminRole(userList, roleId);
            }
            if (adminRoleCreation) {
                adminCreationService.subscribeUserToCommunity(userList, categoryHierarchy.getId(), httpServletRequest, categoryHierarchy.getParentId());
            }
             /*create SME users----------------------------------------------------------------------------------------------*/
            List<User> smeUserList = adminCreationService.createCommunityAdminRegister(httpServletRequest, smeEmails);
            if (smeUserList != null && !smeUserList.isEmpty()) {
                userRoleCreation = adminCreationService.createCommunityUserRole(smeUserList, subOrgId, orgId);
            }
            if (userRoleCreation) {
                Integer roleId = Integer.valueOf(httpServletRequest.getParameter("roleIdSME"));
                adminRoleCreation = adminCreationService.createCommunityAdminRole(smeUserList, roleId);
            }
            if (adminRoleCreation) {
                adminCreationService.subscribeSmeUserToCommunity(smeUserList, categoryHierarchy.getId(), httpServletRequest, categoryHierarchy.getParentId());
            }

            sendNotification.sendNotification(hierarchyParent.getTitle(), categoryHierarchy.getParentId(), ControlPanelUtil.setOrgId(httpServletRequest), ControlPanelUtil.setSubOrgId(httpServletRequest), categoryHierarchy.getId(), categoryHierarchy.getTitle(), hierarchyParent.getId());
            this.setAOICounter(hierarchyParent, httpServletRequest);
            this.elasticDataEntry(categoryHierarchy, httpServletRequest);
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 2;
        }
    }

    /*get level 2 category listing org level*/
    private Page<CategoryHierarchy> getCategoryHierarchyLevel2(Integer orgId, HttpServletRequest httpServletRequest) {
        return categoryHierarchyRepository.findByOrganizationIdAndIsHybridAndParentIdNot(orgId, Constants.ONE, Constants.ZERO, HierarchyLevel2Util.pagination(httpServletRequest));

    }

    /*get AOI category listing sub org level*/
    private Page<CategoryHierarchy> getSubOrgCategoryHierarchyLevel2(Integer subOrgId, HttpServletRequest httpServletRequest) {

        Page<CategoryHierarchy> categoryHierarchies = categoryHierarchyRepository.findBySuborgIdAndParentIdNot(subOrgId, Constants.ZERO, HierarchyLevel2Util.pagination(httpServletRequest));
        categoryHierarchies.forEach(categoryHierarchy -> {
            Optional<CategoryHierarchy> categoryHierarchy1 = categoryHierarchyRepository.findById(categoryHierarchy.getParentId());
            if (categoryHierarchy1.isPresent()) {
                categoryHierarchy.setParentName(categoryHierarchy1.get().getTitle());
            }
        });
        //return categoryHierarchyRepository.findBySuborgIdAndParentIdNot(subOrgId, Constants.ZERO, HierarchyLevel2Util.pagination(httpServletRequest));
        return categoryHierarchies;
    }

    public List<CategoryHierarchy> getSubOrgCategoryHierarchyLevel2(Integer subOrgId) {
        List<CategoryHierarchy> categoryHierarchies = categoryHierarchyRepository.findBySuborgIdAndParentIdNot(subOrgId, 0);
        return categoryHierarchies;
    }

    private CategoryHierarchy getCategoryById(Integer id) {
        CategoryHierarchy hierarchy = new CategoryHierarchy();
        Optional<CategoryHierarchy> categoryHierarchy = categoryHierarchyRepository.findById(id);
        if (categoryHierarchy.isPresent()) {
            hierarchy = categoryHierarchy.get();
        }
        return hierarchy;
    }

    public String updateParam(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, MultipartFile coverImg, List<String> adminEmails, List<String> smeEmails) throws JsonProcessingException {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }

        String imgUrl = null;
        if (!coverImg.isEmpty()) {
            imgUrl = upload(coverImg, ControlPanelUtil.setOrgId(httpServletRequest), ControlPanelUtil.setSubOrgId(httpServletRequest));
        }

        if (imgUrl == null) {
            imgUrl = httpServletRequest.getParameter(Constants.COVER);
        }

        Integer status = this.update(httpServletRequest, ControlPanelUtil.setOrgId(httpServletRequest), ControlPanelUtil.setAdminId(httpServletRequest), HierarchyLevel2Util.setFilePath(imgUrl), ControlPanelUtil.setSubOrgId(httpServletRequest), adminEmails, smeEmails);
        return HierarchyLevel2Util.updateSuccessModel(status, redirectAttributes);
    }

    public Integer update(HttpServletRequest httpServletRequest, Integer orgId, Integer adminId, String filePath, Integer subOrgId, List<String> adminEmails, List<String> smeEmails) {

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
                    return 3;
                }
            }
            CategoryType categoryType = null;
            Integer id = Integer.valueOf(httpServletRequest.getParameter(Constants.ID));
            CategoryHierarchy categoryHierarchy = categoryHierarchyRepository.findByIdAndOrganizationId(id, orgId);
            CategoryHierarchy hierarchyParent = categoryHierarchyRepository.findById(categoryHierarchy.getParentId()).get();
            if (hierarchyParent.getCategoryType() != null) {
                categoryType = hierarchyParent.getCategoryType();
            }
            categoryHierarchyRepository.save(HierarchyLevel2Util.update(categoryHierarchy, httpServletRequest, filePath, categoryType));

            Boolean userRoleCreation = false;
            Boolean adminRoleCreation = false;
            List<User> userList = adminCreationService.createCommunityAdminRegister(httpServletRequest, adminEmails);
            if (userList != null && !userList.isEmpty()) {
                userRoleCreation = adminCreationService.createCommunityUserRole(userList, ControlPanelUtil.setSubOrgId(httpServletRequest), orgId);
            }
            if (userRoleCreation) {
                Integer roleId = Integer.valueOf(httpServletRequest.getParameter("roleId"));
                adminRoleCreation = adminCreationService.createCommunityAdminRole(userList, roleId);
            }
            if (adminRoleCreation) {
                adminCreationService.subscribeUserToCommunity(userList, categoryHierarchy.getId(), httpServletRequest, categoryHierarchy.getParentId());
            }
               /*create SME users----------------------------------------------------------------------------------------------*/
            List<User> smeUserList = adminCreationService.createCommunityAdminRegister(httpServletRequest, smeEmails);
            if (smeUserList != null && !smeUserList.isEmpty()) {
                userRoleCreation = adminCreationService.createCommunityUserRole(smeUserList, ControlPanelUtil.setSubOrgId(httpServletRequest), orgId);
            }
            if (userRoleCreation) {
                Integer roleId = Integer.valueOf(httpServletRequest.getParameter("roleIdSME"));
                adminRoleCreation = adminCreationService.createCommunityAdminRole(smeUserList, roleId);
            }
            if (adminRoleCreation) {
                adminCreationService.subscribeSmeUserToCommunity(smeUserList, categoryHierarchy.getId(), httpServletRequest, categoryHierarchy.getParentId());

            }
            this.elasticDataEntry(categoryHierarchy, httpServletRequest);
            cancelJoinRequestForPrivateCommunity(categoryHierarchy.getId(), httpServletRequest);
            cancelJoinRequestForPrivateCommunityInContentConsent(categoryHierarchy.getId(), httpServletRequest);
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 2;
        }

    }

    public List<CategoryHierarchy> getClosedCategoryHierarchylevel2SubOrg(Integer subOrgId) {
        return categoryHierarchyRepository.findBySuborgIdAndIsPrivateAndParentIdNot(subOrgId, 1, 0);
    }

    private void elasticDataEntry(CategoryHierarchy categoryHierarchy, HttpServletRequest httpServletRequest) {
        Optional<CategoryHierarchy> categoryHierarchyOptional = categoryHierarchyRepository.findById(categoryHierarchy.getParentId());

        elasticUploadService.elasticClient(CategoryHierarchyUtil.setElasticMap(categoryHierarchy, categoryTypeService.getCategoryData(CategoryHierarchyUtil.getCategoryHierarchyById(categoryHierarchyOptional).getCategoryType().getId(), categoryHierarchy.getOrganizationId()), Constants.CONTENT_ID_AOI, httpServletRequest));
    }

    public String elasticIndexRefresh(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        List<CategoryHierarchy> categoryHierarchies = categoryHierarchyRepository.findBySuborgIdAndParentIdNotAndStatus(ControlPanelUtil.setSubOrgId(httpServletRequest), Constants.ZERO, Constants.ONE);

        for (CategoryHierarchy categoryHierarchy : categoryHierarchies) {
            this.elasticDataEntry(categoryHierarchy, httpServletRequest);
        }
        return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, Constants.DATA_REFRESHED_SUCCESSFULLY, "redirect:/aoi?v=#$67");
      /*  } catch (Exception e) {
            e.printStackTrace();
            return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.SOMETHING_WENT_WRONG, "redirect:/aoi?v=#$67");
        }*/
    }

    public String upload(MultipartFile file, Integer orgId, Integer subOrgId) {
        String relativePath = Constants.EMPTY;
        ResponseEntity<Map> response = uploadClient.UploadClient(HierarchyLevel2Util.upload(orgId, subOrgId), file);
        Map map = response.getBody();
        if (map != null) {
            relativePath = map.get(Constants.PATH).toString();
        }
        return relativePath;
    }

    private void setAOICounter(CategoryHierarchy hierarchy, HttpServletRequest httpServletRequest) {
        Integer count = categoryHierarchyRepository.countBySuborgIdAndParentIdAndStatus(ControlPanelUtil.setSubOrgId(httpServletRequest), hierarchy.getId(), 1);
        hierarchy.setSubcategoryCount(count);
        categoryHierarchyRepository.save(hierarchy);
    }

    public String setActivation(Integer id, HttpServletRequest httpServletRequest, Integer value, RedirectAttributes redirectAttributes) {
        String referer = httpServletRequest.getHeader(Constants.REFERER);
        try {

            CategoryHierarchy categoryHierarchy = categoryHierarchyRepository.findByIdAndOrganizationId(id, ControlPanelUtil.setOrgId(httpServletRequest));
            if (categoryHierarchy == null) {
                return "3";
                //return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, "AOI not found", Constants.REDIRECT + referer);
            }
            if (categoryHierarchy != null) {
                Integer parentId = categoryHierarchy.getParentId();
                if (parentId != 0) {
                    CategoryHierarchy parent = categoryHierarchyRepository.findById(parentId).get();
                    if (parent.getStatus() == 0) {
                        return "2";
                        //  return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, "Community not found", Constants.REDIRECT + referer);
                    }
                }
            }

            categoryHierarchy.setStatus(value);
            categoryHierarchyRepository.save(categoryHierarchy);
            deactivateAOISetUserSubscription(id, httpServletRequest, value);
            if (value == 0) {
                //trying to deactivate the community : delete from index
                elasticUploadService.elasticDelete(Constants.ELASTIC_AOI_CONTENT_TYPE_ID, id);
            } else if (value == 1) {
                //trying to activate the community create a new entry
                this.elasticDataEntry(categoryHierarchy, httpServletRequest);
            }
            return "1";
            // return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, Constants.STATUS_UPDATE_SUCCESS, Constants.REDIRECT + referer);

        } catch (Exception ex) {
            ex.printStackTrace();
            return "0";
            // return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.SOMETHING_WENT_WRONG, Constants.REDIRECT + referer);
        }

    }

    private void deactivateAOISetUserSubscription(Integer categoryId, HttpServletRequest httpServletRequest, Integer status) {

        List<CategorySubscription> categorySubscriptions = categorySubscriptionRepository.findByCategoryHierarchyId(categoryId);

        List<CategorySubscription> list = new ArrayList<>();

        categorySubscriptions.forEach(categorySubscription -> {
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
        List<ContentConsent> contentConsents = contentConsentRepository.findByContentTypeIdAndCategoryIdAndPendingStatus(6, categoryHierarchyId, 1);
        List<ContentConsent> list = new ArrayList<>();
        contentConsents.forEach(contentConsent -> {
            contentConsent.setPendingStatus(-1);
            contentConsent.setPublishStatus(-1);
            list.add(contentConsent);
        });
        contentConsentRepository.saveAll(list);
    }
}
