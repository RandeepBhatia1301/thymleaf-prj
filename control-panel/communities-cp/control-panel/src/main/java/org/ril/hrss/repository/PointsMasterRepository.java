package org.ril.hrss.repository;

import org.ril.hrss.model.gamification.PointsMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PointsMasterRepository extends JpaRepository<PointsMaster, Integer> {

   /* List<PointsMaster> findAllByOrgId(@Param(value = "orgId") Integer orgId);

    List<PointsMaster> findAllByIdAndOrgId(@Param(value = "id") Integer id, @Param(value = "orgId") Integer orgId);*/


}
