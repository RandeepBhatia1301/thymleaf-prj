package org.ril.hrss.repository;

import org.ril.hrss.model.auth.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Integer countByEmail(@Param("email") String email);

    List<User> findAllByIdIn(@Param("userIds") List<Long> userIds);

    //User findByEmail(@Param("email") String email);

    User findByUserExternalId(String id);

    List<User> findAllByEmailContainingAndStatus(@Param("email") String email, @Param("status") Integer status, Pageable pageable);

    List<User> findAllByEmailContainingAndStatusAndSubOrgId(@Param("email") String email, @Param("status") Integer status, @Param("subOrgId") Integer subOrgId, Pageable pageable);

    User findById(@Param("id") Long id);

    User findByIdAndSubOrgId(@Param("id") Long userId, @Param("subOrgId") Integer subOrgId);

    //User findBySubOrgIdAndEmail(@Param("subOrgId") Integer subOrgId, @Param("email") String email);
    User findBySubOrgIdAndUserExternalId(Integer subOrgId,String id);
}
