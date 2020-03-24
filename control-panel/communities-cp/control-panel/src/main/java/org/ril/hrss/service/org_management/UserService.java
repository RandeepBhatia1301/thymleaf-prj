package org.ril.hrss.service.org_management;

import org.ril.hrss.model.Org;
import org.ril.hrss.model.SubOrg;
import org.ril.hrss.model.auth.AdminUser;
import org.ril.hrss.model.auth.AdminUserRole;
import org.ril.hrss.model.auth.User;
import org.ril.hrss.repository.*;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ControlPanelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AdminUserRepository adminUserRepository;

    @Autowired
    AdminHasRoleRepository adminHasRoleRepository;

    @Autowired
    OrgRepository orgRepository;

    @Autowired
    SubOrgRepository subOrgRepository;

    public Page<AdminUser> getAdminUsers(HttpServletRequest httpServletRequest) {
        Integer page = httpServletRequest.getParameter(Constants.PAGE) != null ? Integer.valueOf(httpServletRequest.getParameter(Constants.PAGE)) : 0;
        Integer limit = 10;
        Pageable pageable = PageRequest.of(page, limit);
        Page<AdminUser> userPage;
        List<AdminUser> users = new ArrayList<>();
        HttpSession httpSession = httpServletRequest.getSession();
        Integer adminType;

        if (httpSession.getAttribute(Constants.ROLE) == Constants.ORG_ADMIN) {
            /* sub org admin lisitng*/
            Integer orgId = (Integer) httpServletRequest.getSession().getAttribute(Constants.ORG_ID);
            Org org = orgRepository.findById(orgId).get();
            adminType = 2;
            /*userPage = adminUserRepository.findAdminUserByType(adminType, pageable);*/
            userPage = adminUserRepository.findByOrgAndAdminType(org, adminType, pageable);
            return userPage;
        }
        /* org admin lisitng*/
        adminType = 1;
        userPage = adminUserRepository.findByAdminType(adminType, pageable);

        return userPage;
    }

    public boolean createUser(HttpServletRequest httpServletRequest) throws Exception {
        try {
            AdminUser adminUser = new AdminUser();
            String email = httpServletRequest.getParameter("email");
            String firstName = httpServletRequest.getParameter("firstName");
            String lastName = httpServletRequest.getParameter("lastName");
            String password = httpServletRequest.getParameter("password");

            if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SAAS_ADMIN) {
                Integer orgId = Integer.valueOf(httpServletRequest.getParameter("orgList"));
                Org org = orgRepository.findById(orgId).get();
                adminUser.setFirstName(firstName);
                adminUser.setLastName(lastName);
                adminUser.setEmail(email);
                adminUser.setPassword(password);
                adminUser.setStatus(1);
                adminUser.setOrg(org);
                adminUser.setAdminType(1);
                adminUserRepository.save(adminUser);
                AdminUserRole adminUserRole = new AdminUserRole();
                adminUserRole.setAdminUserId(adminUser.getId());
                adminUserRole.setRoleId(Constants.ORG_ADMIN_ROLE_ID);
                adminHasRoleRepository.save(adminUserRole);

            }
            if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.ORG_ADMIN) {
            /*if the user is an org admin*/
                Integer subOrgId = Integer.valueOf(httpServletRequest.getParameter("orgList"));
                adminUser.setFirstName(firstName);
                adminUser.setLastName(lastName);
                adminUser.setEmail(email);
                adminUser.setPassword(password);
                adminUser.setStatus(1);
                adminUser.setAdminType(2);
                adminUser.setSubOrgId(subOrgId);
                Optional<SubOrg> subOrg = subOrgRepository.findById(subOrgId);/*find sub org by id and fetch orgId*/
                SubOrg subOrg1 = subOrg.get();
                Org org = orgRepository.findById((subOrg1.getOrgId())).get();/*find org by orgId*/
                adminUser.setOrg(org);
                adminUserRepository.save(adminUser);

                AdminUserRole adminUserRole = new AdminUserRole();
                adminUserRole.setAdminUserId(adminUser.getId());
                adminUserRole.setRoleId(Constants.SUB_ORG_ADMIN_ROLE_ID);
                adminHasRoleRepository.save(adminUserRole);
            }
      /*  try {
            adminUserRepository.save(adminUser);
            AdminUserRole adminUserRole = new AdminUserRole();
            adminUserRole.setAdminUserId(adminUser.getId());
            adminUserRole.setRoleId(3);
            if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.ORG_ADMIN) {
                adminUserRole.setRoleId(Constants.ORG_ADMIN_ROLE_ID);
            }
            adminHasRoleRepository.save(adminUserRole);
            return true;
        } catch (Exception ex) {
            if (ex instanceof DataIntegrityViolationException) {
                *//*find the user by email and update values*//*
                adminUser = adminUserRepository.findByEmail(email);
                *//*check if the user is deleted or existing*//*
                if (adminUser.getStatus() == 1) {
                    return false;
                }
                adminUser.setFirstName(firstName);
                adminUser.setLastName(lastName);
                adminUser.setPassword(password);
                adminUser.setOrg(org);
                adminUser.setStatus(1);
                adminUserRepository.save(adminUser);
                return true;
            }
        }*/
            return true;
        } catch (Exception ex) {
            if (ex instanceof DataIntegrityViolationException) {
                return false;
            }
            return false;
        }
    }

    public AdminUser getUserdatabyId(Integer id) {
        List<AdminUser> userData = adminUserRepository.findAllById(id);
        return userData.get(0);
    }

    public Boolean updateUser(HttpServletRequest httpServletRequest) throws Exception {
        try {
            Integer adminId = Integer.valueOf(httpServletRequest.getParameter("id"));
            AdminUser adminUser = adminUserRepository.findAdminUserById(adminId);
            adminUser.setFirstName(httpServletRequest.getParameter("firstName"));
            adminUser.setLastName(httpServletRequest.getParameter("lastName"));
            adminUser.setPassword(httpServletRequest.getParameter("password"));
            adminUser.setEmail(httpServletRequest.getParameter("email"));
            adminUser.setStatus(1);
            if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SAAS_ADMIN) {
                Org org = orgRepository.findById(Integer.valueOf(httpServletRequest.getParameter("orgList"))).get();
                adminUser.setOrg(org);
                adminUser.setAdminType(1);
            }
            if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.ORG_ADMIN) {
            /*if the user is an org admin*/
                Integer subOrgId = Integer.valueOf(httpServletRequest.getParameter("orgList"));
                adminUser.setAdminType(2);
                adminUser.setSubOrgId(subOrgId);
                Optional<SubOrg> subOrg = subOrgRepository.findById(subOrgId);/*find sub org by id and fetch orgId*/
                SubOrg subOrg1 = subOrg.get();
                Org org = orgRepository.findById(subOrg1.getOrgId()).get();/*find org by orgId*/
                adminUser.setOrg(org);
            }
            adminUserRepository.save(adminUser);
            return true;
        } catch (Exception ex) {
            if (ex instanceof DataIntegrityViolationException) {
                return false;
            }

        }
        return false;
    }

 /*   public Boolean delete(Integer id) {
        Integer count = adminUserRepository.delete(id);
        if (count > 0) {
            return true;
        }
        return false;
    }*/

    public Boolean setActivation(Integer id, HttpServletRequest httpServletRequest, Integer value) {
        Integer status = value;
        Integer count = adminUserRepository.setActivation(id, status);
        if (count > 0) {
            return true;
        }
        return false;
    }

 /*   public Boolean checkUniqueEmail(String email) {
        Integer emailCount = adminUserRepository.getEmailCount(email);
        if (emailCount > 0) {
            return false;
        }
        return true;
    }
*/

    public Page<AdminUser> getAdminUsers(String query, HttpServletRequest httpServletRequest, HttpSession httpSession) {
        Integer page = httpServletRequest.getParameter(Constants.PAGE) != null ? Integer.valueOf(httpServletRequest.getParameter(Constants.PAGE)) : 0;
        Integer adminType = 1;
        if (httpSession.getAttribute(Constants.ROLE) == Constants.ORG_ADMIN) {
            /* sub org admin lisitng*/
            adminType = 2;
        }
        Integer limit = 50;
        Pageable pageable = PageRequest.of(page, limit);
        query = "%" + query + "%";
        Page<AdminUser> userPage;

        userPage = adminUserRepository.findByAdminTypeAndFirstNameContaining(adminType, pageable, query);
        return userPage;
    }

    public List<User> getUserByEmail(String email, HttpServletRequest httpServletRequest) {
        Pageable pageable = PageRequest.of(0, 5);
        List<User> user = new ArrayList<>();
        try {
            user = userRepository.findAllByEmailContainingAndStatusAndSubOrgId(email, 1, ControlPanelUtil.setSubOrgId(httpServletRequest), pageable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user == null || user.isEmpty()) {
            return null;
        }
        return user;
    }


}
