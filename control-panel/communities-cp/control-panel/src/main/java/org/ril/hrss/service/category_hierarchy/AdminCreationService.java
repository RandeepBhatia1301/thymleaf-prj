package org.ril.hrss.service.category_hierarchy;

import org.ril.hrss.model.auth.User;
import org.ril.hrss.model.category_hierarchy.CategorySubscription;
import org.ril.hrss.model.roles_and_access.CommunityRole;
import org.ril.hrss.model.roles_and_access.CommunityUserRole;
import org.ril.hrss.repository.CategorySubscriptionRepository;
import org.ril.hrss.repository.CommunityRoleRepository;
import org.ril.hrss.repository.CommunityUserRoleRepository;
import org.ril.hrss.repository.UserRepository;
import org.ril.hrss.service.moderation.PermissionService;
import org.ril.hrss.utility.CategoryHierarchyUtil;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ControlPanelUtil;
import org.ril.hrss.utility.SubOrgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminCreationService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CommunityRoleRepository communityRoleRepository;
    @Autowired
    CommunityUserRoleRepository communityUserRoleRepository;

    @Autowired
    PermissionService permissionService;


    @Autowired
    CategorySubscriptionRepository categorySubscriptionRepository;


    public List<User> createCommunityAdminRegister(HttpServletRequest httpServletRequest, List<String> adminEmails) {
        List<User> users = new ArrayList<>();
        adminEmails.forEach(s -> {
            User user1 = userRepository.findByUserExternalId(s);
            if (user1 == null) {
                User user = userRepository.save(CategoryHierarchyUtil.setUser(httpServletRequest, s));
                users.add(user);
            } else {
                if (user1.getStatus() == 1) {
                    users.add(user1);
                }

            }
        });
        return users;

    }

    public Boolean subscribeUserToCommunity(List<User> userList, Integer categoryHierarchyId, HttpServletRequest httpServletRequest, Integer parentId) {
        try {
            List<CategorySubscription> subscriptions = categorySubscriptionRepository.findByCategoryHierarchyIdAndSubOrgId(categoryHierarchyId, ControlPanelUtil.setSubOrgId(httpServletRequest));

            categorySubscriptionRepository.saveAll(CategoryHierarchyUtil.subscribeUserToCommunity(subscriptions));

            userList.forEach(user -> {
                CategorySubscription categorySubscription = categorySubscriptionRepository.findByUserIdAndCategoryHierarchyId(user.getId(), categoryHierarchyId);

                if (categorySubscription == null) {
                    categorySubscriptionRepository.save(CategoryHierarchyUtil.setCatrgorySubscription(user, categoryHierarchyId, parentId, httpServletRequest));
                } else {
                    categorySubscription.setIsAdmin(Constants.ONE);
                    categorySubscription.setStatus(Constants.ONE);
                    categorySubscription.setAdminRoleId(Integer.valueOf(httpServletRequest.getParameter(Constants.ROLE_ID)));
                    categorySubscriptionRepository.save(categorySubscription);
                }
                if (parentId != 0) {
                    subscribeUserToParentCommunity(userList, httpServletRequest, parentId);
                }

            });
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Boolean subscribeUserToParentCommunity(List<User> userList, HttpServletRequest httpServletRequest, Integer parentId) {
        try {

            userList.forEach(user -> {
                CategorySubscription categorySubscription = categorySubscriptionRepository.findByUserIdAndCategoryHierarchyId(user.getId(), parentId);

                if (categorySubscription == null) {
                    CategorySubscription categorySubscription1 = new CategorySubscription();
                    categorySubscription1.setCategoryHierarchyId(parentId);
                    categorySubscription1.setUserId(user.getId());
                    categorySubscription1.setOrganizationId(user.getOrganizationId());
                    categorySubscription1.setStatus(Constants.ONE);
                    categorySubscription1.setIsActive(Constants.ONE);
                    if (user.getSubOrgId() != null) {
                        categorySubscription1.setSubOrgId(user.getSubOrgId());
                    }
                    categorySubscription1.setParentId(0);
                    categorySubscriptionRepository.save(categorySubscription1);
                } else {
                    categorySubscription.setStatus(Constants.ONE);

                    categorySubscriptionRepository.save(categorySubscription);
                }
            });
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }


    public Boolean subscribeUserToHybridCommunity(List<User> userList, Integer categoryHierarchyId, HttpServletRequest httpServletRequest, Integer parentId) {
        try {
            List<CategorySubscription> subscriptions = categorySubscriptionRepository.findByCategoryHierarchyIdAndOrganizationId(categoryHierarchyId, ControlPanelUtil.setOrgId(httpServletRequest));

            categorySubscriptionRepository.saveAll(CategoryHierarchyUtil.subscribeUserToCommunity(subscriptions));

            userList.forEach(user -> {
                CategorySubscription categorySubscription = categorySubscriptionRepository.findByUserIdAndCategoryHierarchyId(user.getId(), categoryHierarchyId);

                if (categorySubscription == null) {
                    categorySubscriptionRepository.save(CategoryHierarchyUtil.setCatrgorySubscription(user, categoryHierarchyId, parentId, httpServletRequest));
                } else {
                    categorySubscription.setIsAdmin(Constants.ONE);
                    categorySubscription.setStatus(Constants.ONE);
                    categorySubscriptionRepository.save(categorySubscription);
                }
            });

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean createCommunityAdminRole(List<User> userList, Integer roleId) {
        try {
            List<CommunityUserRole> communityRolePermissions = new ArrayList<>();
            userList.forEach(user -> {
                if (communityUserRoleRepository.findByUserIdAndCommunityRoleId(user.getId(), roleId) == null) {
                    CommunityUserRole communityUserRole = new CommunityUserRole();
                    communityUserRole.setCommunityRoleId(roleId);
                    communityUserRole.setUserId(user.getId());
                    communityRolePermissions.add(communityUserRole);
                }
            });
            communityUserRoleRepository.saveAll(communityRolePermissions);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Boolean createCommunityUserRole(List<User> userList, Integer subOrgId, Integer orgId) {
        try {
            CommunityRole communityRole = communityRoleRepository.findByOrgIdAndRoleIdentity(orgId, "USER");
            userList.forEach(user -> {
                if (communityUserRoleRepository.findByUserIdAndCommunityRoleId(user.getId(), communityRole.getId()) == null) {
                    communityUserRoleRepository.save(SubOrgUtil.createSubOrgUserRole(communityRole, user));
                }
            });
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Boolean subscribeSmeUserToCommunity(List<User> userList, Integer categoryHierarchyId, HttpServletRequest httpServletRequest, Integer parentId) {
        try {
            List<CategorySubscription> subscriptions = categorySubscriptionRepository.findByCategoryHierarchyIdAndSubOrgId(categoryHierarchyId, ControlPanelUtil.setSubOrgId(httpServletRequest));

            categorySubscriptionRepository.saveAll(CategoryHierarchyUtil.subscribeSmeUserToCommunity(subscriptions));

            userList.forEach(user -> {
                CategorySubscription categorySubscription = categorySubscriptionRepository.findByUserIdAndCategoryHierarchyId(user.getId(), categoryHierarchyId);

                if (categorySubscription == null) {
                    categorySubscriptionRepository.save(CategoryHierarchyUtil.setSmeCatrgorySubscription(user, categoryHierarchyId, parentId, httpServletRequest));
                } else {
                    categorySubscription.setIsSME(Constants.ONE);
                    categorySubscription.setStatus(Constants.ONE);
                    categorySubscription.setSmeRoleId(Integer.valueOf(httpServletRequest.getParameter(Constants.ROLE_ID_SME)));
                    categorySubscriptionRepository.save(categorySubscription);
                }
            });

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Boolean subscribeSmeUserToHybridCommunity(List<User> userList, Integer categoryHierarchyId, HttpServletRequest httpServletRequest, Integer parentId) {
        try {
            List<CategorySubscription> subscriptions = categorySubscriptionRepository.findByCategoryHierarchyIdAndOrganizationId(categoryHierarchyId, ControlPanelUtil.setOrgId(httpServletRequest));

            categorySubscriptionRepository.saveAll(CategoryHierarchyUtil.subscribeSmeUserToCommunity(subscriptions));

            userList.forEach(user -> {
                CategorySubscription categorySubscription = categorySubscriptionRepository.findByUserIdAndCategoryHierarchyId(user.getId(), categoryHierarchyId);


                if (categorySubscription == null) {
                    categorySubscriptionRepository.save(CategoryHierarchyUtil.setSmeCatrgorySubscription(user, categoryHierarchyId, parentId, httpServletRequest));
                } else {
                    categorySubscription.setIsSME(Constants.ONE);
                    categorySubscription.setStatus(Constants.ONE);
                    categorySubscription.setSmeRoleId(Integer.valueOf(httpServletRequest.getParameter(Constants.ROLE_ID_SME)));
                    categorySubscriptionRepository.save(categorySubscription);
                }
            });

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
