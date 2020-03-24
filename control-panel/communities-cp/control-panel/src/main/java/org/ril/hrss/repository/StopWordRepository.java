package org.ril.hrss.repository;

import org.ril.hrss.model.StopWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StopWordRepository extends JpaRepository<StopWord, Integer> {

    List<StopWord> findAllByOrganizationId(@Param(value = "orgId") Integer orgId);

}
