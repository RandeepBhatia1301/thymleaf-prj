package org.ril.hrss.utility;

import org.ril.hrss.model.roles_and_access.CommunityRolePermission;
import org.ril.hrss.model.roles_and_access.Permission;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class PermissionUtil {
    public static Set<Integer> getRoleByPermission(List<Permission> permissions) {
        Set<Integer> roleId = new HashSet<>();
        permissions.forEach(permission -> {
            List<CommunityRolePermission> communityRoles = permission.getCommunityRolePermissions();
            communityRoles.forEach(communityRolePermission -> {
                roleId.add(communityRolePermission.getCommunityRoleId());
            });
        });
        return roleId;
    }

    public static List<String> setPermissionValue() {
        List<String> permissionValue = new ArrayList<>();
        permissionValue.add("approval_to_join_community");
        permissionValue.add("apprvoe_reject_invite_request_polls");
        permissionValue.add("apprvoe_reject_invite_request_discussion");
        permissionValue.add("apprvoe_reject_blog");
        permissionValue.add("apprvoe_reject_invite_request_event");
        permissionValue.add("apprvoe_reject_micro_blog");
        permissionValue.add("approve_quiz");

        return permissionValue;

    }

}
