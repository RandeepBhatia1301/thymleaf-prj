package org.ril.hrss.repository;

import org.ril.hrss.model.Org;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrgRepository extends JpaRepository<Org, Integer> {

    Page<Org> findByNameContaining(String query, Pageable pageable);
/*
    @Query(value = "SELECT * FROM organization.org_master WHERE is_approved = 1 AND status != -1", nativeQuery = true)
    Page<Org> findPagedAllByIs_approved(Pageable pageable);*/

    Optional<Org> findById(@Param("orgId") Integer orgId);


/*
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update organization.org_master set is_approved=1 WHERE org_master.id=:id", nativeQuery = true)
    Integer setApproval(@Param("id") Integer id);*/
/*
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update organization.org_master set is_approved=-1 WHERE org_master.id=:id", nativeQuery = true)
    Integer setRejection(@Param("id") Integer id);*/

    /* @Transactional
     @Modifying(clearAutomatically = true)
     @Query(value = "update organization.org_master set status=-1 WHERE org_master.id=:id", nativeQuery = true)
     Integer delete(@Param("id") Integer id);
 */
    /* STATUS= active=1 inactive=0*/
   /* @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update organization.org_master set status=:status WHERE org_master.id=:id", nativeQuery = true)
    Integer setActivation(@Param("id") Integer id, @Param("status") Integer status);*/
/*
    @Query(value = "Select count(*) from org_master where org_master.name=:name", nativeQuery = true)
    Integer getNameCount(@Param("name") String name);*/


    Integer countByName(@Param("name") String name);

   /* @Query(value = "SELECT  COUNT(*) from organization.org_master WHERE org_master.is_approved = 1 AND org_master.status != -1", nativeQuery = true)
    Integer getOrgCount();*/

    /*Integer countByIs_approvedAndStatusNot(@Param("is_approved") Integer is_approved, @Param("status") Integer status);*/

    List<Org> findAllByStatus(@Param("status") Integer status);


}
