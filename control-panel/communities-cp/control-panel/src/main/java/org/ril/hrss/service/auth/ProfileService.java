package org.ril.hrss.service.auth;

import org.ril.hrss.data_security.EncryptDecrypt;
import org.ril.hrss.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class ProfileService {

    @Autowired
    AdminUserRepository adminUserRepository;

    public Boolean edit(HttpServletRequest httpServletRequest) throws Exception {
        try {

            String currentEmail = (String) httpServletRequest.getSession().getAttribute("email");
            String password = EncryptDecrypt.encrypt(httpServletRequest.getParameter("password"));
            Integer updateStatus = adminUserRepository.updateAdminUser(httpServletRequest.getParameter("firstName"), httpServletRequest.getParameter("lastName"), httpServletRequest.getParameter("email"), password, currentEmail);

            if (updateStatus == 1) {
                HttpSession httpSession = httpServletRequest.getSession();
                httpSession.setAttribute("email", httpServletRequest.getParameter("email"));
                httpSession.setAttribute("firstname", httpServletRequest.getParameter("firstName"));
                httpSession.setAttribute("lastname", httpServletRequest.getParameter("lastName"));
                httpSession.setAttribute("password", password);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
