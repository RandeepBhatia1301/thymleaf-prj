package org.ril.hrss.repository;

import org.ril.hrss.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findByOrgIdAndSubOrgId(@Param("orgId") Integer orgId, @Param("subOrgId") Integer subOrgId);
}
