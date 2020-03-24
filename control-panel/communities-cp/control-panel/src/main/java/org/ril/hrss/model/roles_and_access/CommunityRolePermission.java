package org.ril.hrss.model.roles_and_access;

import javax.persistence.*;

@Entity
@Table(name = "user.community_role_permission")
public class CommunityRolePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "community_role_id")
    private Integer communityRoleId;

    @Column(name = "permission_id")
    private Integer permissionId;

    @Column(name = "org_id")
    private Integer orgId;

    @Column(name = "sub_org_id")
    private Integer subOrgId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommunityRoleId() {
        return communityRoleId;
    }

    public void setCommunityRoleId(Integer communityRoleId) {
        this.communityRoleId = communityRoleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getSubOrgId() {
        return subOrgId;
    }

    public void setSubOrgId(Integer subOrgId) {
        this.subOrgId = subOrgId;
    }

}
