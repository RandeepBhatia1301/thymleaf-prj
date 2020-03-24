package org.ril.hrss.repository;

import org.ril.hrss.model.content.OrgContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrgContentTypeRepository extends JpaRepository<OrgContent, Integer> {

    List<OrgContent> findAllByOrgId(@Param("org_id") Integer orgId);

    List<OrgContent> findAllByOrgIdAndStatusAndIsConfigurable(@Param("org_id") Integer orgId, @Param("status") Integer status, @Param("isConfigurable") Integer isConfigurable);

    OrgContent findByIdAndOrgId(@Param("id") Integer id, @Param("org_id") Integer orgId);

    /*List<OrgContent> findAllByOrgIdAndIsAvailable(@Param("orgId") Integer orgId, @Param("isAvailable") Integer isAvailable);*/

    List<OrgContent> findAllByOrgIdAndIsAvailableAndIsConfigurable(@Param("orgId") Integer orgId, @Param("isAvailable") Integer isAvailable, @Param("isConfigurable") Integer isConfigurable);
}
