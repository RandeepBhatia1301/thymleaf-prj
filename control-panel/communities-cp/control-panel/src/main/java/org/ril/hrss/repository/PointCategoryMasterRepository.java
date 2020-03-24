package org.ril.hrss.repository;

import org.ril.hrss.model.gamification.PointCategoryMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointCategoryMasterRepository extends JpaRepository<PointCategoryMaster, Integer> {

    List<PointCategoryMaster> findAllByOrgId(@Param(value = "orgId") Integer orgId);

}
