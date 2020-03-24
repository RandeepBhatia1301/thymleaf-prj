package org.ril.hrss.repository;

import org.ril.hrss.model.moderation.HierarchyModeration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface ModerationRepository extends JpaRepository<HierarchyModeration, Integer> {
    HierarchyModeration findByCatergoryHierarchyId(@Param(value = "categoryHierarchyId") Integer categoryHierarchyId);

    @Transactional
    Integer deleteAllByOrgIdAndCatergoryHierarchyIdIn(@Param("orgId") Integer orgId, @Param("categoryIds") List<Integer> categoryIds);

}
