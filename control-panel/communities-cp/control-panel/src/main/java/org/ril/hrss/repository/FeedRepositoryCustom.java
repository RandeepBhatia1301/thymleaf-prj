package org.ril.hrss.repository;

import org.ril.hrss.model.ContentTimeBaseCount;
import org.ril.hrss.model.reports.AnalyticsContentCount;

import java.util.Date;
import java.util.List;

public interface FeedRepositoryCustom {
    public List<ContentTimeBaseCount> getContentTimeBaseCountByContentIdBetweenStartAndEndDate(Integer contentTypeId, Integer subOrgId, String startDate, String endDate);

    public List<ContentTimeBaseCount> getContentTimeBaseCountByContentIdBetweenStartAndEndDate(Integer contentTypeId, Integer subOrgId, Long categoryId, Long subCategoryId, String startDate, String endDate);

    public Integer getContentCountByContentIdBetweenStartAndEndDate(Integer contentTypeId, Integer subOrgId, String startDate, String endDate);

    public Integer getContentCountByContentIdBetweenStartAndEndDate(Integer contentTypeId, Integer subOrgId, Long categoryId, Long subCategoryId, String startDate, String endDate);

    public List<AnalyticsContentCount> findOverAllCountList(Integer categoryId, List<Integer> subCategoryId, Integer contentTypeId, List<String> activityType,
                                                            String startDate, String endDate, Integer subOrgId);

    public long getPostCount(String activityType, Integer contentTypeId, Date startDate, Date endDate, Integer categoryId, List<Integer> subCategoryIds, Integer subOrgId);
}
