package org.ril.hrss.repository;

import org.ril.hrss.model.roles_and_access.CommunityRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CommunityRoleRepository extends JpaRepository<CommunityRole, Integer> {
  /*  List<CommunityRole> findByOrgIdAndSubOrgIdAndStatus(@Param("orgId") Integer orgId, @Param("subOrgId") Integer subOrgId, @Param("status") Integer status);*/

    CommunityRole findByOrgIdAndRoleIdentity(@Param("orgId") Integer orgId, @Param("roleIdentity") String roleIdentity);

    List<CommunityRole> findByIdInAndOrgIdAndStatus(@Param("id") Set<Integer> id, @Param("orgId") Integer orgId, @Param("status") Integer status);

    List<CommunityRole> findByOrgIdAndStatus(@Param("orgId") Integer orgId,@Param("status") Integer status);
}
