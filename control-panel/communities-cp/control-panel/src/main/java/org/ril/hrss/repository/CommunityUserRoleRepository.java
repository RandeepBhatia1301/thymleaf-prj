package org.ril.hrss.repository;

import org.ril.hrss.model.roles_and_access.CommunityUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityUserRoleRepository extends JpaRepository<CommunityUserRole, Integer> {
    CommunityUserRole findByUserIdAndCommunityRoleId(@Param(value = "userId") Long userId, @Param(value = "roleId") Integer roleId);
}
