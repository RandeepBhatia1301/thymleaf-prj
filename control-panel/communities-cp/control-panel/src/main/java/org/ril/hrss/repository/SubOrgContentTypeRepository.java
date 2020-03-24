package org.ril.hrss.repository;

import org.ril.hrss.model.content.SubOrgContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubOrgContentTypeRepository extends JpaRepository<SubOrgContent, Integer> {

    SubOrgContent findByIdAndSubOrgId(@Param("id") Integer id, @Param("subOrgId") Integer subOrgId);

    List<SubOrgContent> findBySubOrgIdAndIsAvailable(@Param("subOrgId") Integer subOrgId, @Param("isAvailable") Integer isAvailable);

    List<SubOrgContent> findBySubOrgIdAndIsAvailableAndIsConfigurable(@Param("subOrgId") Integer subOrgId, @Param("isAvailable") Integer isAvailable, @Param("isConfigurable") Integer isConfigurable);

    List<SubOrgContent> findAllByOrgId(@Param("orgId") Integer orgId);

    List<SubOrgContent> findAllByOrgContentTypeIdIn(@Param("orgContentTypeId") List<Integer> orgContentTypeId);

    SubOrgContent findBySubOrgIdAndContentTypeId(@Param("subOrgId") Integer subOrgId, @Param("contentTypeId") Integer contentTypeId);

    SubOrgContent findByDisplayOrderAndSubOrgId(@Param("displayOrder") Integer displayOrder, @Param("subOrgId") Integer subOrgId);

}
