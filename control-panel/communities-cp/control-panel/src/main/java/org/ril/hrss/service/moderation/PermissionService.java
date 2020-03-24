package org.ril.hrss.service.moderation;

import org.ril.hrss.model.roles_and_access.CommunityRole;
import org.ril.hrss.model.roles_and_access.Permission;
import org.ril.hrss.repository.CommunityRoleRepository;
import org.ril.hrss.repository.PermissionRepository;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ControlPanelUtil;
import org.ril.hrss.utility.PermissionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class PermissionService {
    @Autowired
    CommunityRoleRepository communityRoleRepository;

    @Autowired
    PermissionRepository permissionRepository;


    public List<CommunityRole> getRolesByPermission(HttpServletRequest httpServletRequest, List<String> permissionValue) {
        List<Permission> permissions = permissionRepository.findByPermissionValueIn(permissionValue);
        return communityRoleRepository.findByIdInAndOrgIdAndStatus(PermissionUtil.getRoleByPermission(permissions), ControlPanelUtil.setOrgId(httpServletRequest), Constants.ONE);
    }
}
