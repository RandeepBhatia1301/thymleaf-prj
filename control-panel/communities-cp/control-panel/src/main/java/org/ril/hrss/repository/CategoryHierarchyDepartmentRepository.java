package org.ril.hrss.repository;

import org.ril.hrss.model.category_hierarchy.CategoryHierarchyDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CategoryHierarchyDepartmentRepository extends JpaRepository<CategoryHierarchyDepartment, Long> {

    @Transactional
    Integer deleteByCategoryHierarchyId(@Param("categoryId") Integer categoryId);

    List<CategoryHierarchyDepartment> findByCategoryHierarchyId(@Param("categoryId") Integer categoryId);

}
