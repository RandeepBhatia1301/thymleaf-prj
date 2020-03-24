package org.ril.hrss.repository;

import org.ril.hrss.model.OrgPoints;
import org.ril.hrss.model.gamification.PointsMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrgPointsRepository extends JpaRepository<OrgPoints, Integer> {

    List<PointsMaster> findAllByOrgId(@Param(value = "orgId") Integer orgId);

    List<PointsMaster> findAllByIdAndOrgId(@Param(value = "id") Integer id, @Param(value = "orgId") Integer orgId);


}
