package org.ril.hrss.repository;

import org.ril.hrss.model.content.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface ContentRepository extends JpaRepository<Content, Integer> {

    List<Content> findAllById(@Param("id") Integer id);

    @Modifying(clearAutomatically = true)
    @Query(value = "update content.content_type t set t.name =:name , t.description = :description where t.id= :id ", nativeQuery = true)
    Integer editContent(@Param("name") String name, @Param("description") String description, @Param("id") Integer id);

    @Modifying(clearAutomatically = true)
    @Query(value = "update content.content_type t set t.status = 0 where t.id= :id ", nativeQuery = true)
    Integer deleteContent(@Param("id") Integer id);

    @Modifying(clearAutomatically = true)
    @Query(value = "update content.content_type t set t.status = 1, t.description = :description where t.name= :name ", nativeQuery = true)
    Integer changeStatus(@Param("description") String description, @Param("name") String name);

    /* STATUS= active=1 inactive=0*/
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update content.content_type set status=:status WHERE content_type.id=:id", nativeQuery = true)
    Integer setActivation(@Param("id") Integer id, @Param("status") Integer status);

    List<Content> findAllByIsConfigurable(@Param("is_configurable") Integer isConfigurable);

    List<Content> findAllByStatusAndIsConfigurable(@Param("status") Integer status, @Param("is_configurable") Integer isConfigurable);

    List<Content> findAllByStatus(@Param("status") Integer status);
}
