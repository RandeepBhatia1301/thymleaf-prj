package org.ril.hrss.repository;


import org.ril.hrss.model.product_hierarchy.OrgProductHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrgProductHierarchyRepository extends JpaRepository<OrgProductHierarchy, Integer> {

    List<OrgProductHierarchy> findByOrgId(@Param(value = "orgId") Integer orgId);

    OrgProductHierarchy findByOrgIdAndId(@Param(value = "orgId") Integer orgId, @Param(value = "id") Integer id);


}
