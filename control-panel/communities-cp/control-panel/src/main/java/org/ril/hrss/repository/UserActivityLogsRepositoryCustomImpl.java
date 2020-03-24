package org.ril.hrss.repository;

import org.ril.hrss.model.CommentTimeBaseCount;
import org.ril.hrss.model.TopBlog;
import org.ril.hrss.model.UserActivityLogs;
import org.ril.hrss.model.UserViewTimeBaseCount;
import org.ril.hrss.model.reports.AnalyticsContentCount;
import org.ril.hrss.utility.AnalyticsReportUtil;
import org.ril.hrss.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Component
public class UserActivityLogsRepositoryCustomImpl implements UserActivityLogsRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    AnalyticsReportUtil analyticsReportUtil;

    @Autowired
    public UserActivityLogsRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<AnalyticsContentCount> findCommentCountList(Integer categoryId, List<Integer> subCategoryId, Integer contentTypeId, List<String> activityType,
                                                            String startDate, String endDate, Integer subOrgId) {

        GroupOperation groupOperation = Aggregation.group(Constants.ACTIVITY_TIME).count().as(Constants.TOTAL);
        MatchOperation matchCondition;
        if (categoryId != null && !categoryId.equals(Constants.ZERO) && subCategoryId != null && !subCategoryId.isEmpty()) {
            matchCondition = Aggregation.match(
                    Criteria.where(Constants.FIELD_CATEGORY_ID).is(categoryId)
                            .andOperator(Criteria.where(Constants.FIELD_CONTENT_TYPE_ID).is(contentTypeId),
                                    Criteria.where(Constants.SUB_ORG_ID).is(subOrgId),
                                    Criteria.where(Constants.ACTIVITY_CODE_DB).in(activityType),
                                    Criteria.where(Constants.SUB_CATEGORY_ID_DB).in(subCategoryId)));
        } else if (categoryId != null && !categoryId.equals(Constants.ZERO) && (subCategoryId == null || subCategoryId.isEmpty())) {
            matchCondition = Aggregation.match(
                    Criteria.where(Constants.FIELD_CATEGORY_ID).is(categoryId)
                            .andOperator(Criteria.where(Constants.FIELD_CONTENT_TYPE_ID).is(contentTypeId),
                                    Criteria.where(Constants.SUB_ORG_ID).is(subOrgId),
                                    Criteria.where(Constants.ACTIVITY_CODE_DB).in(activityType)));
        } else {
            matchCondition = Aggregation.match(
                    Criteria.where(Constants.ACTIVITY_CODE_DB).in(activityType)
                            .andOperator(Criteria.where(Constants.FIELD_CONTENT_TYPE_ID).is(contentTypeId),
                                    Criteria.where(Constants.SUB_ORG_ID).is(subOrgId)));
        }
        ProjectionOperation projectionOperation =
                Aggregation.project(Constants.TOTAL).and(Constants.DATE_DB).previousOperation();

        Date startDateFmt = AnalyticsReportUtil.getInitialDate(startDate + Constants.DATE_INITAIL_TIME);
        Date endDateFmt = AnalyticsReportUtil.getDayEndDate(endDate + Constants.DATE_INITAIL_TIME);
        MatchOperation dateCondition = Aggregation.match(new Criteria()
                .andOperator(Criteria.where(Constants.ACTIVITY_TIME_DB).lt(endDateFmt).gte(startDateFmt)));

        Aggregation agg = newAggregation(
                matchCondition,
                dateCondition,
                Aggregation.project().and(Constants.ACTIVITY_TIME).dateAsFormattedString(Constants.PROFILE_STAT_DATE_FORMAT).as(Constants.ACTIVITY_TIME),
                groupOperation,
                projectionOperation,
                Aggregation.sort(Sort.Direction.ASC, Constants.DATE_DB)
        );
        AggregationResults<AnalyticsContentCount> groupResults = mongoTemplate.aggregate(agg, UserActivityLogs.class,
                AnalyticsContentCount.class);
        return groupResults.getMappedResults();
    }

    @Override
    public List<TopBlog> getTopCommentByContentIdBetweenStartAndEndDate(Integer categoryId, List<Integer> subCategoryId, Integer contentTypeId, Integer subOrgId, List<String> activityType, String startDate, String endDate) {
        GroupOperation groupOperation = group("contentId").count().as("count");
        MatchOperation matchCondition;
        if (categoryId != null && !categoryId.equals(Constants.ZERO) && subCategoryId != null && !subCategoryId.isEmpty()) {
            matchCondition = Aggregation.match(
                    Criteria.where(Constants.FIELD_CATEGORY_ID).is(categoryId)
                            .andOperator(Criteria.where(Constants.FIELD_CONTENT_TYPE_ID).is(contentTypeId),
                                    Criteria.where(Constants.ACTIVITY_CODE_DB).in(activityType),
                                    Criteria.where(Constants.SUB_CATEGORY_ID_DB).in(subCategoryId)));
        } else if (categoryId != null && !categoryId.equals(Constants.ZERO) && (subCategoryId == null || subCategoryId.isEmpty())) {
            matchCondition = Aggregation.match(
                    Criteria.where(Constants.FIELD_CATEGORY_ID).is(categoryId)
                            .andOperator(Criteria.where(Constants.FIELD_CONTENT_TYPE_ID).is(contentTypeId),
                                    Criteria.where(Constants.ACTIVITY_CODE_DB).in(activityType)));
        } else {
            matchCondition = Aggregation.match(
                    Criteria.where(Constants.ACTIVITY_CODE_DB).in(activityType)
                            .andOperator(Criteria.where(Constants.FIELD_CONTENT_TYPE_ID).is(contentTypeId)));
        }
        ProjectionOperation projectionOperation =
                Aggregation.project("count").and("blogId").previousOperation();
        Date startDateFmt = AnalyticsReportUtil.getInitialDate(startDate + Constants.DATE_INITAIL_TIME);
        Date endDateFmt = AnalyticsReportUtil.getDayEndDate(endDate + Constants.DATE_END_TIME);
        MatchOperation dateCondition = Aggregation.match(new Criteria()
                .andOperator(Criteria.where(Constants.ACTIVITY_TIME_DB).lt(endDateFmt).gte(startDateFmt)));
        Aggregation agg = newAggregation(
                matchCondition,
                dateCondition,
                groupOperation,
                projectionOperation,
                sort(Sort.Direction.DESC, "count"),
                limit(5)
        );
        AggregationResults<TopBlog> groupResults = mongoTemplate.aggregate(agg, UserActivityLogs.class, TopBlog.class);
        return groupResults.getMappedResults();
    }

    @Override
    public Integer getCountCommentTimeBaseCountByContentIdBetweenStartAndEndDate(Integer categoryId, List<Integer> subCategoryId, Integer contentTypeId, Integer subOrgId, List<String> activityType, String startDate, String endDate) {
        MatchOperation matchCondition;
        if (categoryId != null && !categoryId.equals(Constants.ZERO) && subCategoryId != null && !subCategoryId.isEmpty()) {
            matchCondition = Aggregation.match(
                    Criteria.where(Constants.FIELD_CATEGORY_ID).is(categoryId)
                            .andOperator(Criteria.where(Constants.FIELD_CONTENT_TYPE_ID).is(contentTypeId),
                                    Criteria.where(Constants.ACTIVITY_CODE_DB).in(activityType),
                                    Criteria.where(Constants.SUB_CATEGORY_ID_DB).in(subCategoryId)));
        } else if (categoryId != null && !categoryId.equals(Constants.ZERO) && (subCategoryId == null || subCategoryId.isEmpty())) {
            matchCondition = Aggregation.match(
                    Criteria.where(Constants.FIELD_CATEGORY_ID).is(categoryId)
                            .andOperator(Criteria.where(Constants.FIELD_CONTENT_TYPE_ID).is(contentTypeId),
                                    Criteria.where(Constants.ACTIVITY_CODE_DB).in(activityType)));
        } else {
            matchCondition = Aggregation.match(
                    Criteria.where(Constants.ACTIVITY_CODE_DB).in(activityType)
                            .andOperator(Criteria.where(Constants.FIELD_CONTENT_TYPE_ID).is(contentTypeId)));
        }

        Date startDateFmt = AnalyticsReportUtil.getInitialDate(startDate + Constants.DATE_INITAIL_TIME);
        Date endDateFmt = AnalyticsReportUtil.getDayEndDate(endDate + Constants.DATE_END_TIME);
        MatchOperation dateCondition = Aggregation.match(new Criteria()
                .andOperator(Criteria.where(Constants.ACTIVITY_TIME_DB).lt(endDateFmt).gte(startDateFmt)));

        Aggregation agg = newAggregation(
                matchCondition,
                dateCondition
        );
        AggregationResults<CommentTimeBaseCount> groupResults = mongoTemplate.aggregate(agg, UserActivityLogs.class, CommentTimeBaseCount.class);
        return groupResults.getMappedResults().size();

    }

    @Override
    public Integer getContentCountByContentIdBetweenStartAndEndDate(Integer categoryId, List<Integer> subCategoryId, Integer contentTypeId, Integer subOrgId, List<String> activityType, String startDate, String endDate) {
        MatchOperation matchCondition;
        if (categoryId != null && !categoryId.equals(Constants.ZERO) && subCategoryId != null && !subCategoryId.isEmpty()) {
            matchCondition = Aggregation.match(
                    Criteria.where(Constants.FIELD_CATEGORY_ID).is(categoryId)
                            .andOperator(Criteria.where(Constants.FIELD_CONTENT_TYPE_ID).is(contentTypeId),
                                    Criteria.where(Constants.ACTIVITY_CODE_DB).in(activityType),
                                    Criteria.where(Constants.SUB_CATEGORY_ID_DB).in(subCategoryId)));
        } else if (categoryId != null && !categoryId.equals(Constants.ZERO) && (subCategoryId == null || subCategoryId.isEmpty())) {
            matchCondition = Aggregation.match(
                    Criteria.where(Constants.FIELD_CATEGORY_ID).is(categoryId)
                            .andOperator(Criteria.where(Constants.FIELD_CONTENT_TYPE_ID).is(contentTypeId),
                                    Criteria.where(Constants.ACTIVITY_CODE_DB).in(activityType)));
        } else {
            matchCondition = Aggregation.match(
                    Criteria.where(Constants.ACTIVITY_CODE_DB).in(activityType)
                            .andOperator(Criteria.where(Constants.FIELD_CONTENT_TYPE_ID).is(contentTypeId)));
        }


        Date startDateFmt = AnalyticsReportUtil.getInitialDate(startDate + Constants.DATE_INITAIL_TIME);
        Date endDateFmt = AnalyticsReportUtil.getDayEndDate(endDate + Constants.DATE_END_TIME);
        MatchOperation dateCondition = Aggregation.match(new Criteria()
                .andOperator(Criteria.where(Constants.ACTIVITY_TIME_DB).lt(endDateFmt).gte(startDateFmt)));

        Aggregation agg = newAggregation(
                matchCondition,
                dateCondition
        );
        AggregationResults<CommentTimeBaseCount> groupResults = mongoTemplate.aggregate(agg, UserActivityLogs.class, CommentTimeBaseCount.class);
        return groupResults.getMappedResults().size();
    }

    @Override
    public List<UserViewTimeBaseCount> getUserViewTimeBaseCountByContentIdBetweenStartAndEndDate(Integer categoryId, List<Integer> subCategoryId, Integer contentTypeId, Integer subOrgId, List<String> activityType, String startDate, String endDate) {
        GroupOperation groupOperation = Aggregation.group(Constants.ACTIVITY_TIME).count().as(Constants.TOTAL);
        MatchOperation matchCondition;
        if (categoryId != null && !categoryId.equals(Constants.ZERO) && subCategoryId != null && !subCategoryId.isEmpty()) {
            matchCondition = Aggregation.match(
                    Criteria.where(Constants.FIELD_CATEGORY_ID).is(categoryId)
                            .andOperator(Criteria.where(Constants.FIELD_CONTENT_TYPE_ID).is(contentTypeId),
                                    Criteria.where(Constants.ACTIVITY_CODE_DB).in(activityType),
                                    Criteria.where(Constants.SUB_CATEGORY_ID_DB).in(subCategoryId)));
        } else if (categoryId != null && !categoryId.equals(Constants.ZERO) && (subCategoryId == null || subCategoryId.isEmpty())) {
            matchCondition = Aggregation.match(
                    Criteria.where(Constants.FIELD_CATEGORY_ID).is(categoryId)
                            .andOperator(Criteria.where(Constants.FIELD_CONTENT_TYPE_ID).is(contentTypeId),
                                    Criteria.where(Constants.ACTIVITY_CODE_DB).in(activityType)));
        } else {
            matchCondition = Aggregation.match(
                    Criteria.where(Constants.ACTIVITY_CODE_DB).in(activityType)
                            .andOperator(Criteria.where(Constants.FIELD_CONTENT_TYPE_ID).is(contentTypeId)));
        }
        ProjectionOperation projectionOperation = Aggregation.project(Constants.TOTAL).and("viewedAt").previousOperation();

        Date startDateFmt = AnalyticsReportUtil.getInitialDate(startDate + Constants.DATE_INITAIL_TIME);
        Date endDateFmt = AnalyticsReportUtil.getDayEndDate(endDate + Constants.DATE_END_TIME);
        MatchOperation dateCondition = Aggregation.match(new Criteria().andOperator(Criteria.where(Constants.ACTIVITY_TIME_DB).lt(endDateFmt).gte(startDateFmt)));

        Aggregation agg = newAggregation(
                matchCondition,
                dateCondition,
                Aggregation.project().and("userId").as("userId").and(Constants.ACTIVITY_TIME).dateAsFormattedString(Constants.PROFILE_STAT_DATE_FORMAT).as(Constants.ACTIVITY_TIME),
                group(Constants.ACTIVITY_TIME, "userId"),
                groupOperation,
                projectionOperation,
                sort(Sort.Direction.ASC, "viewedAt")
        );
        AggregationResults<UserViewTimeBaseCount> groupResults = mongoTemplate.aggregate(agg, UserActivityLogs.class, UserViewTimeBaseCount.class);
        return groupResults.getMappedResults();
    }

}
