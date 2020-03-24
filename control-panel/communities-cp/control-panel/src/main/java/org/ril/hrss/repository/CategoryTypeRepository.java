package org.ril.hrss.repository;

import org.ril.hrss.model.category_hierarchy.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryTypeRepository extends JpaRepository<CategoryType, Integer> {

    List<CategoryType> findByOrgId(@Param(value = "orgId") Integer orgId);

    @Query(value = "SELECT MAX(order_by) from organization.category_type where org_master_id=:orgId ", nativeQuery = true)
    Integer getCurrentOrder(@Param(value = "orgId") Integer orgId);

    CategoryType findByIdAndOrgId(@Param(value = "id") Integer id, @Param(value = "orgId") Integer orgId);
}
