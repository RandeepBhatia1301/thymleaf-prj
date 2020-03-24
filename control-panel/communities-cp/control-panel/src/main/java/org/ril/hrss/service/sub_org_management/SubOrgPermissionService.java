/*
package org.ril.hrss.service.sub_org_management;

import org.ril.hrss.repository.CommunityRolePermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubOrgPermissionService {
    @Autowired
    CommunityRolePermissionRepository communityRolePermissionRepository;


    public List getSubOrgPermissions(Integer orgId, Integer subOrgId) {
       */
/* List<CommunityRolePermission> communityRolePermissions = communityRolePermissionRepository.findByOrgIdAndSubOrgIdAndCommunityRoleId(orgId, subOrgId,1);
        List<Integer> permissionIdList = communityRolePermissions.stream()
                .map(CommunityRolePermission::getPermissionId).collect(Collectors.toList());*//*

        List<Integer> permissionIdList = new ArrayList<>();
        permissionIdList.add(10);
        permissionIdList.add(11);
        permissionIdList.add(12);

        return permissionIdList;
    }
}
*/
