package org.ril.hrss.repository;

import org.ril.hrss.model.gamification.SubOrgwelcomeScreen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SubOrgwelcomeScreenRepository extends JpaRepository<SubOrgwelcomeScreen, Integer> {

    List<SubOrgwelcomeScreen> findByOrgIdAndSubOrgId(@Param("orgId") Integer orgId, @Param("suborgId") Integer subOrgId);

}
