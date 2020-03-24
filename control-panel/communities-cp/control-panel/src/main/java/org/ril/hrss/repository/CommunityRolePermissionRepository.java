package org.ril.hrss.repository;

import org.ril.hrss.model.roles_and_access.CommunityRolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRolePermissionRepository extends JpaRepository<CommunityRolePermission, Integer> {

    List<CommunityRolePermission> findByCommunityRoleId(Integer communityRoleId);

}
