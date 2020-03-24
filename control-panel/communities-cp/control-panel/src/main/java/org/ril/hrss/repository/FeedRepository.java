package org.ril.hrss.repository;

import org.ril.hrss.model.Feed;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface FeedRepository extends MongoRepository<Feed, String>, FeedRepositoryCustom {
    public Integer countByActivityTypeAndContentTypeIdAndDateBetween(String activityType, Integer contentTypeId, Date startDate, Date endDate);

    public Integer countByActivityTypeAndContentTypeIdAndCategoryIdAndSubCategoryIdInAndDateBetween(String activityType, Integer contentTypeId, Integer categoryId, List<Integer> subcategoryId, Date startDate, Date endDate);

    public Integer countByActivityTypeAndContentTypeIdAndCategoryIdAndDateBetween(String activityType, Integer contentTypeId, Integer categoryId, Date startDate, Date endDate);


}
