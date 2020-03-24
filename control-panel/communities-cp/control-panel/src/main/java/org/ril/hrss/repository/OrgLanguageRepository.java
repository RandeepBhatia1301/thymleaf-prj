package org.ril.hrss.repository;

import org.ril.hrss.model.language.OrgLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrgLanguageRepository extends JpaRepository<OrgLanguage, Integer> {

    OrgLanguage findByOrgIdAndId(@Param("org_id") Integer orgId, @Param("id") Integer id);

    /* STATUS= active=1 inactive=0*//*
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update organization.org_language set status=:status WHERE language_id=:id AND org_id=:org_id", nativeQuery = true)
    Integer setActivation(@Param("id") Integer id, @Param("status") Integer status, @Param("org_id") Integer org_id);*/

    List<OrgLanguage> findAllByOrgId(@Param("org_id") Integer orgId);
}
