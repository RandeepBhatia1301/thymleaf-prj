package org.ril.hrss.repository;

import org.ril.hrss.model.auth.AdminUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminHasRoleRepository extends JpaRepository<AdminUserRole, Long> {

}
