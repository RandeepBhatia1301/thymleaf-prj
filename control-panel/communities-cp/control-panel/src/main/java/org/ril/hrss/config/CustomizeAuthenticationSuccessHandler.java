/*
package com.ril.svc.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        //set our response to OK status
        response.setStatus(HttpServletResponse.SC_OK);

        Integer admin = 0;

        logger.info("AT onAuthenticationSuccess(...) function!");

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if ("ROLE_SAAS_ADMIN".equals(auth.getAuthority())){
                admin = 1;
            }else if ("ROLE_CLIENT_ADMIN".equals(auth.getAuthority())){
                admin=2;
            }
        }

        if(admin==1){
            response.sendRedirect("/client");
        }else if(admin==2){
            response.sendRedirect("/organization");
        }else if(admin==0){
            response.sendRedirect("/organization");
        }
    }
}
*/
