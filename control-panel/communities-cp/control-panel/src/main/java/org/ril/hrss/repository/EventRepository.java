package org.ril.hrss.repository;

import org.ril.hrss.model.content.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom {


    List<Event> findByIdIn(List<Long> ids);

    List<Event> findFirst5ByIdInAndCategoryId(@Param("id") List<Long> ids, @Param("categoryId") Long categoryId);

    List<Event> findFirst5ByIdInAndEventSubCategoriesIn(@Param("id") List<Long> ids, @Param("subCategoryId") List<Long> subCategoryId);
}
