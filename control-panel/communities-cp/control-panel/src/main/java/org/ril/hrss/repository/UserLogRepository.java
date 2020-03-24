package org.ril.hrss.repository;

import org.ril.hrss.model.UserActivityLogs;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserLogRepository extends MongoRepository<UserActivityLogs, String>, UserActivityLogsRepositoryCustom {

    List<UserActivityLogs> findBySubOrgId(Integer subOrgId);

    public Integer deleteAllByUserIdAndSubOrgId(Integer userId, Integer subOrgId);

    public Integer deleteAllByUserId(Integer userId);

    Long countDistinctBySubOrgIdAndContentTypeIdInAndActivityTimeBetweenAndActivityCode(Integer subOrgId, List<Integer> contentTypeId, Date startDate, Date endDate, String activityCode);

    Long countDistinctBySubOrgIdAndContentTypeIdAndActivityTimeBetweenAndActivityCode(Integer subOrgId, Integer contentTypeId, Date startDate, Date endDate, String activityCode);


}
