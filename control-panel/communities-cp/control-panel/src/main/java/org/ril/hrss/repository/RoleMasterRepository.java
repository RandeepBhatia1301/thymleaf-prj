package org.ril.hrss.repository;

import org.ril.hrss.model.auth.RoleMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMasterRepository extends JpaRepository<RoleMaster, Integer> {
    List<RoleMaster> findByIsConfigurable(@Param("is_configurable") Integer is_configurable);

}
