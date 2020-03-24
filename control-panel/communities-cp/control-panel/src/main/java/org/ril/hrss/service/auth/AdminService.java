/*
package com.ril.svc.service;

import AdminUser;
import AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    AdminUserRepository adminUserRepository;

    public AdminUser getAdminUserById(Integer adminId)
    {

        Optional<AdminUser> adminUser = adminUserRepository.findById(adminId);

        return adminUser.get();
    }
}
*/
