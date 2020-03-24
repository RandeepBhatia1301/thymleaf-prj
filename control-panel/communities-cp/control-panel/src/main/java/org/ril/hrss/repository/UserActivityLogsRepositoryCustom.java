package org.ril.hrss.repository;

import org.ril.hrss.model.TopBlog;
import org.ril.hrss.model.UserViewTimeBaseCount;
import org.ril.hrss.model.reports.AnalyticsContentCount;

import java.util.List;

public interface UserActivityLogsRepositoryCustom {
    public List<AnalyticsContentCount> findCommentCountList(Integer categoryId, List<Integer> subCategoryId, Integer contentTypeId, List<String> activityType,
                                                            String startDate, String endDate, Integer subOrgId);

    public List<TopBlog> getTopCommentByContentIdBetweenStartAndEndDate(Integer categoryId, List<Integer> subCategoryId, Integer contentTypeId, Integer subOrgId, List<String> activityType, String startDate, String endDate);

    public Integer getCountCommentTimeBaseCountByContentIdBetweenStartAndEndDate(Integer categoryId, List<Integer> subCategoryId, Integer contentTypeId, Integer subOrgId, List<String> activityType, String startDate, String endDate);

    public Integer getContentCountByContentIdBetweenStartAndEndDate(Integer categoryId, List<Integer> subCategoryId, Integer contentTypeId, Integer subOrgId, List<String> activityType, String startDate, String endDate);

    public List<UserViewTimeBaseCount> getUserViewTimeBaseCountByContentIdBetweenStartAndEndDate(Integer categoryId, List<Integer> subCategoryId, Integer contentTypeId, Integer subOrgId, List<String> activityType, String startDate, String endDate);
}
