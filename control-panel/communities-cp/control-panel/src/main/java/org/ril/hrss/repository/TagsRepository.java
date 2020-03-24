package org.ril.hrss.repository;

import org.ril.hrss.model.tag.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsRepository extends JpaRepository<Tag, Integer> {
    Page<Tag> findAllByOrgId(@Param(value = "orgId") Integer orgId, Pageable pageable);

}
