package org.ril.hrss.repository;

import org.ril.hrss.model.content.event.EventSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventSubCategoryRepository extends JpaRepository<EventSubCategory, Long> {


}
