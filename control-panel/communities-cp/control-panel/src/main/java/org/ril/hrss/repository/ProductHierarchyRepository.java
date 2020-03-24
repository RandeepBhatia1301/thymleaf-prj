package org.ril.hrss.repository;

import org.ril.hrss.model.product_hierarchy.ProductHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductHierarchyRepository extends JpaRepository<ProductHierarchy, Integer> {

    ProductHierarchy findAllById(@Param("id") Integer id);

    List<ProductHierarchy> findAllByOrderByLevel();

    List<ProductHierarchy> findAllByStatusOrderByLevel(@Param("status") Integer status);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update organization.product_hierarchy_master t set t.name =:name , t.description = :description where t.id= :id ", nativeQuery = true)
    Integer editProductHierarchy(@Param("name") String name, @Param("description") String description, @Param("id") Integer id);

    ProductHierarchy findByLevel(@Param("level") Integer level);

    /* STATUS= active=1 inactive=0*/
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update organization.product_hierarchy_master set status=:status WHERE organization.product_hierarchy_master.id=:id", nativeQuery = true)
    Integer setActivation(@Param("id") Integer id, @Param("status") Integer status);


}
