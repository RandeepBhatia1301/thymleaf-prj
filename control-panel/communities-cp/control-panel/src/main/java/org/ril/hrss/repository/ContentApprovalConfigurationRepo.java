package org.ril.hrss.repository;

import org.ril.hrss.model.moderation.ContentApprovalConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ContentApprovalConfigurationRepo extends JpaRepository<ContentApprovalConfiguration, Long> {

    ContentApprovalConfiguration findBySubOrgIdAndCategoryIdAndContentTypeId(@Param("subOrgId") Integer subOrgId, @Param("categoryId") Integer categoryId, @Param("contentTypeId") Integer contentTypeId);

    /*delete all where ids in a LIST*/
    @Transactional
    Integer deleteAllBySubOrgIdAndContentTypeIdAndCategoryIdIn(@Param("subOrgId") Integer subOrgId, @Param("contentTypeId") Integer contentTypeId, @Param("categoryId") List<Integer> categoryId);

    @Transactional
    Integer deleteBySubOrgIdAndContentTypeIdAndCategoryId(@Param("subOrgId") Integer subOrgId, @Param("contentTypeId") Integer contentTypeId, @Param("categoryId") Integer categoryId);

    List<ContentApprovalConfiguration> findAllBySubOrgIdAndContentTypeIdIn(@Param("subOrgId") Integer subOrgId, @Param("contentTypeId") List<Integer> contentTypeId);
}
