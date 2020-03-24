package org.ril.hrss.repository;


import org.ril.hrss.model.product_hierarchy.SubOrgProductHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubOrgProductHierarchyRepository extends JpaRepository<SubOrgProductHierarchy, Integer> {

    List<SubOrgProductHierarchy> findBySubOrgId(@Param(value = "subOrgId") Integer subOrgId);

    SubOrgProductHierarchy findBySubOrgIdAndId(@Param(value = "subOrgId") Integer subOrgId, @Param(value = "id") Integer id);

}
