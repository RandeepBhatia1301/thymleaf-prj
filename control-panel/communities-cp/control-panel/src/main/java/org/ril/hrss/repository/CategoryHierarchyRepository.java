package org.ril.hrss.repository;


import org.ril.hrss.model.category_hierarchy.CategoryHierarchy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryHierarchyRepository extends JpaRepository<CategoryHierarchy, Integer> {
    Page<CategoryHierarchy> findByOrganizationIdAndParentIdAndIsHybrid(@Param(value = "orgId") Integer orgId, @Param(value = "parentId") Integer parentId, @Param(value = "isHybrid") Integer isHybrid, Pageable pageable);

    CategoryHierarchy findByIdAndOrganizationId(@Param(value = "id") Integer id, @Param(value = "organization_id") Integer orgId);
/*
    @Query(value = "SELECT * from organization.category_hierarchy WHERE organization_id=:orgId AND parent_id !=0 AND is_hybrid=:isHybrid ", nativeQuery = true)
    List<CategoryHierarchy> findByOrganizationIdAndParentIdAndIsHybridLevel2(@Param(value = "orgId") Integer orgId, @Param(value = "isHybrid") Integer isHybrid);*/

    Page<CategoryHierarchy> findByOrganizationIdAndIsHybridAndParentIdNot(@Param(value = "orgId") Integer orgId, @Param(value = "isHybrid") Integer isHybrid, @Param(value = "parentId") Integer parentId, Pageable pageable);

    List<CategoryHierarchy> findByOrganizationIdAndParentIdAndIsHybridAndIsPrivate(@Param(value = "orgId") Integer orgId, @Param(value = "parentId") Integer parentId, @Param(value = "isHybrid") Integer isHybrid, @Param(value = "isPrivate") Integer isPrivate);

    /*for listing*/
    Page<CategoryHierarchy> findBySuborgIdAndParentIdOrderByIdDesc(@Param(value = "subOrgId") Integer subOrgId, @Param(value = "parentId") Integer parentId, Pageable pageable);

    /*test for hybrid listing */
    Page<CategoryHierarchy> findBySuborgIdAndParentIdAndIsHybridOrderByIdDesc(@Param(value = "subOrgId") Integer subOrgId, @Param(value = "parentId") Integer parentId, @Param(value = "isHybrid") Integer isHybrid, Pageable pageable);

    /*for moderation*/
    List<CategoryHierarchy> findBySuborgIdAndParentId(@Param(value = "subOrgId") Integer subOrgId, @Param(value = "parentId") Integer parentId);

    /*for lisiting page*/
    Page<CategoryHierarchy> findBySuborgIdAndParentIdNot(@Param(value = "SubOrgId") Integer SubOrgId, @Param(value = "parentId") Integer parentId, Pageable pageable);

    /*for moderation*/
    List<CategoryHierarchy> findBySuborgIdAndParentIdNot(@Param(value = "SubOrgId") Integer SubOrgId, @Param(value = "parentId") Integer parentId);

    List<CategoryHierarchy> findBySuborgIdAndParentIdNotAndStatus(@Param(value = "SubOrgId") Integer SubOrgId, @Param(value = "parentId") Integer parentId, @Param("status") Integer status);

    List<CategoryHierarchy> findBySuborgIdAndParentIdAndIsPrivate(@Param(value = "orgId") Integer orgId, @Param(value = "parentId") Integer parentId, @Param(value = "isPrivate") Integer isPrivate);

    /*get private AOI for moderation*/
    List<CategoryHierarchy> findBySuborgIdAndIsPrivateAndParentIdNot(@Param(value = "SubOrgId") Integer SubOrgId, @Param(value = "isPrivate") Integer isPrivate, @Param(value = "parentId") Integer parentId);

    /*forCreateCommunityPermission*/
    Integer countByOrganizationIdAndParentIdAndIsHybrid(@Param(value = "organization_id") Integer orgId, @Param(value = "parentId") Integer parentId, @Param(value = "isHybrid") Integer isHybrid);

    List<CategoryHierarchy> findByOrganizationIdAndSuborgIdAndParentId(@Param(value = "orgId") Integer orgId, @Param(value = "subOrgId") Integer subOrgId, @Param(value = "parentId") Integer parentId);

    List<CategoryHierarchy> findByOrganizationIdAndSuborgIdAndParentIdAndStatus(@Param(value = "orgId") Integer orgId, @Param(value = "subOrgId") Integer subOrgId, @Param(value = "parentId") Integer parentId, @Param("status") Integer status);

    List<CategoryHierarchy> findAllByIdIn(@Param("id") List<Long> categoryIds);

    CategoryHierarchy findByTitle(@Param(value = "title") String title);

    Integer countBySuborgIdAndParentIdAndStatus(@Param("suborgId") Integer suborgId, @Param("parentId") Integer parentId, @Param("status") Integer status);

    List<CategoryHierarchy> findAllByIdInAndStatus(@Param("id") List<Integer> categoryIds, @Param("status") Integer status);

}
