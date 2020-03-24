package org.ril.hrss.model.roles_and_access;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user.permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private Integer status;

    @Column(name = "permission_value")
    private String permissionValue;

    @Column(name = "permission_group")
    private String permissionGroup;

    @Column(name = "display_order")
    private String displayOrder;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "permission_id")
    private List<CommunityRolePermission> communityRolePermissions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPermissionValue() {
        return permissionValue;
    }

    public void setPermissionValue(String permissionValue) {
        this.permissionValue = permissionValue;
    }

    public String getPermissionGroup() {
        return permissionGroup;
    }

    public void setPermissionGroup(String permissionGroup) {
        this.permissionGroup = permissionGroup;
    }

    public String getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(String displayOrder) {
        this.displayOrder = displayOrder;
    }

    public List<CommunityRolePermission> getCommunityRolePermissions() {
        return communityRolePermissions;
    }

    public void setCommunityRolePermissions(List<CommunityRolePermission> communityRolePermissions) {
        this.communityRolePermissions = communityRolePermissions;
    }
}
