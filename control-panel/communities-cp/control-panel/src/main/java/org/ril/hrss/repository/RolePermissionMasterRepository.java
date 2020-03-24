package org.ril.hrss.repository;

import org.ril.hrss.model.roles_and_access.RolePermissionMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionMasterRepository extends JpaRepository<RolePermissionMaster, Integer> {

    List<RolePermissionMaster> findAllByMasterRoleId(@Param("master_role_id") Integer masterRoleId);

}
