package org.ril.hrss.repository;


import org.ril.hrss.model.category_hierarchy.CategorySubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorySubscriptionRepository extends JpaRepository<CategorySubscription, Integer> {

    List<CategorySubscription> findByIsAdmin(@Param("isAdmin") Integer isAdmin);

    CategorySubscription findByUserIdAndCategoryHierarchyId(@Param("userId") Long userId, @Param("categoryId") Integer categoryId);

    List<CategorySubscription> findByCategoryHierarchyIdAndSubOrgId(@Param("categoryId") Integer categoryId, @Param("suborgId") Integer suborgId);

    List<CategorySubscription> findByCategoryHierarchyIdAndOrganizationId(@Param("categoryId") Integer categoryId, @Param("orgId") Integer orgId);

    List<CategorySubscription> findByCategoryHierarchyIdAndSubOrgIdAndStatusAndIsAdmin(@Param("categoryId") Integer categoryId, @Param("suborgId") Integer suborgId, @Param("status") Integer status, @Param("isAdmin") Integer isAdmin);

    List<CategorySubscription> findByCategoryHierarchyIdAndOrganizationIdAndStatusAndIsAdmin(@Param("categoryId") Integer categoryId, @Param("orgId") Integer orgId, @Param("status") Integer status, @Param("isAdmin") Integer isAdmin);

    List<CategorySubscription> findByCategoryHierarchyIdAndSubOrgIdAndStatusAndIsSME(@Param("categoryId") Integer categoryId, @Param("suborgId") Integer suborgId, @Param("status") Integer status, @Param("isSME") Integer isSME);

    List<CategorySubscription> findByCategoryHierarchyIdAndOrganizationIdAndStatusAndIsSME(@Param("categoryId") Integer categoryId, @Param("orgId") Integer orgId, @Param("status") Integer status, @Param("isSME") Integer isSME);

    List<CategorySubscription> findByUserIdAndSubOrgId(Long userId, Integer subOrgId);

    List<CategorySubscription> findByCategoryHierarchyId(@Param("categoryId") Integer categoryId);

    List<CategorySubscription> findByParentId(@Param("categoryId") Integer categoryId);


    //List<CategorySubscription> findByCategoryHierarchyIdAndStatusAndSubOrgId(@Param("categoryId") Integer categoryId, @Param("status") Integer status, @Param("subOrgId") Integer subOrgId);
    List<CategorySubscription> findByCategoryHierarchyIdAndStatusAndOrganizationId(@Param("categoryId") Integer categoryId, @Param("status") Integer status, @Param("organizationId") Integer organizationId);

    Integer countByCategoryHierarchyIdAndStatusAndOrganizationId(@Param("categoryId") Integer categoryId,@Param("status") Integer status,@Param("organizationId") Integer organizationId);

}
