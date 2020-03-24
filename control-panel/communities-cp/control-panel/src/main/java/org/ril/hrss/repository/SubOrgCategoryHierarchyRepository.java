package org.ril.hrss.repository;

import org.ril.hrss.model.category_hierarchy.SubOrgCategoryHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface SubOrgCategoryHierarchyRepository extends JpaRepository<SubOrgCategoryHierarchy, Integer> {
    @Transactional
    Integer deleteByOrgIdAndCategoryId(@Param("orgId") Integer orgId, @Param("categoryId") Integer categoryId);

    List<SubOrgCategoryHierarchy> findByOrgIdAndCategoryId(@Param("orgId") Integer orgId, @Param("categoryId") Integer categoryId);

    List<SubOrgCategoryHierarchy> findBySubOrgId(@Param("subOrgId") Integer subOrgId);

}
