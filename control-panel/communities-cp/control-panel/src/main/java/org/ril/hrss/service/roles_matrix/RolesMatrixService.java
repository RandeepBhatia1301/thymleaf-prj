package org.ril.hrss.service.roles_matrix;

import org.ril.hrss.model.roles_and_access.CommunityRolePermission;
import org.ril.hrss.model.roles_and_access.Permission;
import org.ril.hrss.repository.CommunityRolePermissionRepository;
import org.ril.hrss.repository.CommunityRoleRepository;
import org.ril.hrss.repository.PermissionRepository;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ControlPanelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolesMatrixService {

    @Autowired
    CommunityRoleRepository communityRoleRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    CommunityRolePermissionRepository communityRolePermissionRepository;

    public String rolesList(HttpServletRequest httpServletRequest, Model model) {
        model.addAttribute("roleList", communityRoleRepository.findByOrgIdAndStatus(ControlPanelUtil.setOrgId(httpServletRequest), Constants.ONE));
        return "role_matrix/list";

    }

    public String viewPermissionByRoleId(Integer id, HttpServletRequest httpServletRequest, Model model) {

        List<Permission> systemPermissions = permissionRepository.findAll();

        List<CommunityRolePermission> rolePermissions = communityRolePermissionRepository.findByCommunityRoleId(id);

        List<Integer> permissionIdForSelectedRole = rolePermissions.parallelStream()
                .map(CommunityRolePermission::getPermissionId).collect(Collectors.toList());
        model.addAttribute("systemPermission", systemPermissions);
        model.addAttribute("userPermission", permissionIdForSelectedRole);
        return "role_matrix/permission";

    }


}
