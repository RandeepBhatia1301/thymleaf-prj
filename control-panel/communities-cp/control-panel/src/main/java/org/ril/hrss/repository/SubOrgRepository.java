package org.ril.hrss.repository;

import org.ril.hrss.model.SubOrg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface SubOrgRepository extends JpaRepository<SubOrg, Integer> {
/*
    @Query(value = "SELECT organization.sub_org_master.*,admin_user.email FROM organization.sub_org_master LEFT JOIN organization.admin_user ON admin_user.sub_org_id = sub_org_master.id WHERE sub_org_master.org_id = :org_id", nativeQuery = true)
    Page<SubOrg> getOrgList(@Param("org_id")Integer org_id,Pageable pageable);*/

    @Query(value = "SELECT * FROM organization.sub_org_master  WHERE id = :subOrgId", nativeQuery = true)
    SubOrg getOrgdata(@Param("subOrgId") Integer subOrgId);

    /* STATUS= active=1 inactive=0*/
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update organization.sub_org_master set status=:status WHERE sub_org_master.id=:id", nativeQuery = true)
    Integer setActivation(@Param("id") Integer id, @Param("status") Integer status);

    @Query(value = "SELECT * FROM organization.sub_org_master WHERE status != -1", nativeQuery = true)
    List<SubOrg> findAllByIs_approved();

    Optional<SubOrg> findById(@Param("subOrgId") Integer subOrgId);

    @Query(value = "SELECT * FROM organization.sub_org_master WHERE organization.sub_org_master.org_id=:orgId", nativeQuery = true)
    List<SubOrg> findAllByOrgId(@Param(value = "orgId") Integer orgId);

    Page<SubOrg> findAllByOrgId(@Param(value = "orgId") Integer orgId, Pageable pageable);

    Integer countDistinctByOrgId(@Param(value = "orgId") Integer orgId);

    Page<SubOrg> findByNameContaining(String query, Pageable pageable);

    Page<SubOrg> findByNameContainingAndOrgId(String query, Integer orgId, Pageable pageable);

    List<SubOrg> findByOrgIdAndStatus(@Param(value = "orgId") Integer orgId, @Param(value = "status") Integer status);
}
