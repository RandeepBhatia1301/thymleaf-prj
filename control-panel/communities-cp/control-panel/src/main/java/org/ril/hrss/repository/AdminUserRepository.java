package org.ril.hrss.repository;

import org.ril.hrss.model.Org;
import org.ril.hrss.model.auth.AdminUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Integer> {
    /*only user with status = 1 can login*/
    @Query(value = "SELECT au.id as id,au.first_name as firstname,au.last_name as lastname,au.org_id,au.sub_org_id,au.email as email, au.status as status,au.password as password FROM organization.admin_user au where Binary au.email=:email and au.password=:password and status=1", nativeQuery = true)
    Map findAdminUserByUserNameAndPassword(@Param("email") String email, @Param("password") String password);

    @Query(value = "SELECT r.role_identity as role\n" +
            "FROM user.role r\n" +
            "INNER JOIN organization.admin_user_role aur ON r.id=aur.role_id\n" +
            "where aur.admin_user_id=:adminId ", nativeQuery = true)
    List<String> findRolesbyAdminUserId(@Param("adminId") Integer adminId);


    Page<AdminUser> findByOrgAndAdminType(@Param("org") Org org, @Param("admin_type") Integer admin_type, Pageable pageable);

    Page<AdminUser> findByAdminType(@Param("admin_type") Integer admin_type, Pageable pageable);

    Page<AdminUser> findByAdminTypeAndFirstNameContaining(@Param("admin_type") Integer admin_type, Pageable pageable, @Param("query") String query);

    List<AdminUser> findAllById(@Param("id") Integer id);

    AdminUser findAdminUserById(@Param("id") Integer id);

/*    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update organization.admin_user set status=-1 WHERE admin_user.id=:id", nativeQuery = true)
    Integer delete(@Param("id") Integer id);*/

    AdminUser findByEmail(@Param("email") String email);

    /* STATUS= active=1 inactive=0*/
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update organization.admin_user set status=:status WHERE admin_user.id=:id", nativeQuery = true)
    Integer setActivation(@Param("id") Integer id, @Param("status") Integer status);

  /*  @Query(value = "Select count(*) from organization.admin_user where admin_user.email=:email", nativeQuery = true)
    Integer getEmailCount(@Param("email") String email);
*/
 /*   @Query(value = "SELECT  COUNT(*) from organization.admin_user WHERE status IN (1,0)", nativeQuery = true)
    Integer getUserCount();*/

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update organization.admin_user set status=:status WHERE admin_user.org_id=:orgId", nativeQuery = true)
    Integer deactivateAll(@Param("orgId") Integer orgId, @Param("status") Integer status);

    List<AdminUser> findAllBySubOrgId(@Param(value = "subOrgId") Integer subOrgId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update organization.admin_user set first_name=:firstName,last_name=:lastName,email=:email,password=:password WHERE admin_user.email=:currentEmail ", nativeQuery = true)
    Integer updateAdminUser(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("email") String email, @Param("password") String password, @Param("currentEmail") String currentEmail);

}
