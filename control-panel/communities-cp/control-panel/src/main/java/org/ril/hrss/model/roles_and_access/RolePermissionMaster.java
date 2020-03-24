package org.ril.hrss.model.roles_and_access;

import javax.persistence.*;

@Entity
@Table(name = "user.role_permission_master")
public class RolePermissionMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "master_role_id")
    private Integer masterRoleId;

    @Column(name = "permission_id")
    private Integer permissionId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMasterRoleId() {
        return masterRoleId;
    }

    public void setMasterRoleId(Integer masterRoleId) {
        this.masterRoleId = masterRoleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

}
