package org.ril.hrss.repository;

import org.ril.hrss.model.content.blog.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    @Query(value = "SELECT title FROM content.blog WHERE id = :id", nativeQuery = true)
    public String getBlogTitleByBlogIds(@Param("id") Long id);

}
