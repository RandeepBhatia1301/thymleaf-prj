package org.ril.hrss.repository;

import org.ril.hrss.model.roles_and_access.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    List<Permission> findByPermissionValueIn(@Param("permisionValue") List<String> permisionValue);

    List<Permission> findByIdIn(List<Integer> id);

}
