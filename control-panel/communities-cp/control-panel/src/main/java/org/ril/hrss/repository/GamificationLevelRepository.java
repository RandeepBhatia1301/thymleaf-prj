package org.ril.hrss.repository;

import org.ril.hrss.model.gamification.GamificationLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GamificationLevelRepository extends JpaRepository<GamificationLevel, Integer> {
    @Query(value = "SELECT level from organization.level_master where (level % 10)=0 ", nativeQuery = true)
    List<Integer> findLevelMultiples();

    List<GamificationLevel> findByIsStage(@Param(value = "isStage") Integer isStage);

}
