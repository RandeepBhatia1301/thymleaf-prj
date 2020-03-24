package org.ril.hrss.repository;

import org.ril.hrss.model.content.discussion.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiscussionRepository extends JpaRepository<Discussion, Long> {

    @Query(value = "SELECT title FROM content.discussion WHERE id = :id", nativeQuery = true)
    public String getDiscussionTitleByBlogIds(@Param("id") Long id);
}
