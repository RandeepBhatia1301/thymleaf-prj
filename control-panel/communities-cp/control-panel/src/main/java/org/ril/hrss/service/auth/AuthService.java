package org.ril.hrss.service.auth;


import org.ril.hrss.data_security.EncryptDecrypt;
import org.ril.hrss.model.Org;
import org.ril.hrss.model.SubOrg;
import org.ril.hrss.repository.AdminUserRepository;
import org.ril.hrss.service.org_management.OrgService;
import org.ril.hrss.service.sub_org_management.SubOrgService;
import org.ril.hrss.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthService {
    @Autowired
    AdminUserRepository adminUserRepository;

    @Autowired
    OrgService orgService;

    @Autowired
    SubOrgService subOrgService;

    public Map login(String email, String password) {
        Map<Object, Object> adminData = null;
        Map userData = new HashMap();
        adminData = adminUserRepository.findAdminUserByUserNameAndPassword(email, password);
        if (!adminData.isEmpty()) {
            List<String> roles = adminUserRepository.findRolesbyAdminUserId((Integer) adminData.get("id"));
            Integer orgId = (Integer) adminData.get(Constants.AUTH_ORG_ID);
            Integer subOrgId = (Integer) adminData.get(Constants.AUTH_SUB_ORG_ID);
            if (orgId != 0) {
                Org org = orgService.findByOrgId((Integer) adminData.get(Constants.AUTH_ORG_ID));
                SubOrg subOrg = subOrgService.findBySubOrgId(subOrgId);
                userData.put(Constants.AUTH_ORG_NAME, org.getName());
                if (subOrg != null) {
                    userData.put(Constants.AUTH_SUB_ORG_NAME, subOrg.getName());
                }
            } else {
                userData.put(Constants.AUTH_ORG_NAME, Constants.AUTH_NONE);
                userData.put(Constants.AUTH_SUB_ORG_NAME, Constants.AUTH_NONE);
            }
            userData.putAll(adminData);
            userData.put(Constants.ROLE, roles);

            return userData;
        }
        return adminData;
    }

    public ModelAndView setUserSessionAfterLogin(HttpServletRequest request) throws Exception {
        String email = request.getParameter(Constants.AUTH_EMAIL);
        String password = EncryptDecrypt.encrypt(request.getParameter(Constants.AUTH_PASSWORD));
        Map userMap = this.login(email, password);
        HttpSession session = request.getSession();
        session.setAttribute(Constants.AUTH_EMAIL, userMap.get(Constants.AUTH_EMAIL));
        session.setAttribute(Constants.ORG_ID, userMap.get(Constants.AUTH_ORG_ID));
        session.setAttribute(Constants.AUTH_FIRST_NAME, userMap.get(Constants.AUTH_FIRST_NAME));
        session.setAttribute(Constants.AUTH_LAST_NAME, userMap.get(Constants.AUTH_LAST_NAME));
        session.setAttribute(Constants.ADMIN_ID, userMap.get(Constants.ID));
        session.setAttribute(Constants.SUB_ORG_ID, userMap.get(Constants.AUTH_SUB_ORG_ID));
        session.setAttribute(Constants.STATUS, userMap.get(Constants.AUTH_STATUS));
        session.setAttribute(Constants.AUTH_ORG_NAME, userMap.get(Constants.AUTH_ORG_NAME));
        session.setAttribute(Constants.AUTH_SUB_ORG_NAME, userMap.get(Constants.AUTH_SUB_ORG_NAME));
        session.setAttribute(Constants.AUTH_PASSWORD, userMap.get(Constants.AUTH_PASSWORD));
        List<String> roleCheck = (List<String>) userMap.get(Constants.ROLE);
        if (userMap == null || userMap.isEmpty()) {
            ModelAndView mv = new ModelAndView();
            mv.setViewName(Constants.AUTH_LOGIN_VIEW_NAME);
            mv.addObject(Constants.ERROR, Constants.AUTH_LOGIN_ERROR);
            return mv;
        } else if (roleCheck.contains(Constants.SAAS_ADMIN)) {
            session.setAttribute(Constants.ROLE, Constants.SAAS_ADMIN);
            return new ModelAndView(Constants.AUTH_REDIRECT_DASHBOARD);
        } else if (roleCheck.contains(Constants.ORG_ADMIN)) {
            session.setAttribute(Constants.ROLE, Constants.ORG_ADMIN);
            return new ModelAndView(Constants.AUTH_REDIRECT_DASHBOARD);
        } else if (roleCheck.contains(Constants.SUB_ORG_ADMIN)) {
            session.setAttribute(Constants.ROLE, Constants.SUB_ORG_ADMIN);
            return new ModelAndView(Constants.AUTH_REDIRECT_DASHBOARD);
        } else {
            ModelAndView mv = new ModelAndView();
            mv.setViewName(Constants.AUTH_LOGIN_VIEW_NAME);
            mv.addObject(Constants.ERROR, Constants.AUTH_LOGIN_NO_ROLE_ERROR);
            return mv;
        }
    }

}


