package org.ril.hrss.repository;

import org.ril.hrss.model.ContentTimeBaseCount;
import org.ril.hrss.model.Feed;
import org.ril.hrss.model.reports.AnalyticsContentCount;
import org.ril.hrss.utility.AnalyticsReportUtil;
import org.ril.hrss.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class FeedRepositoryCustomImpl implements FeedRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    AnalyticsReportUtil analyticsReportUtil;

    @Autowired
    public FeedRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<ContentTimeBaseCount> getContentTimeBaseCountByContentIdBetweenStartAndEndDate(Integer contentTypeId, Integer subOrgId, String startDate, String endDate) {
        GroupOperation groupOperation = group("date").count().as("total");
        MatchOperation matchCondition = Aggregation.match(Criteria.where("sub_org_id").is(subOrgId).andOperator(Criteria.where("content_type_id").is(contentTypeId), Criteria.where("activity_type").is("POST")));
        ProjectionOperation projectionOperation = Aggregation.project("total").and("date").previousOperation();
        MatchOperation dateCondition = Aggregation.match(new Criteria().andOperator(Criteria.where("date").lte(analyticsReportUtil.getISTDateFormat(endDate)).gte(analyticsReportUtil.getISTDateFormat(startDate))));
        Aggregation agg = newAggregation(
                matchCondition,
                dateCondition,
                Aggregation.project().and("contentId").as("contentId").and("date").dateAsFormattedString("%Y-%m-%d").as("date"),
                groupOperation,
                projectionOperation,
                sort(Sort.Direction.ASC, "date")
        );
        AggregationResults<ContentTimeBaseCount> groupResults = mongoTemplate.aggregate(agg, Feed.class, ContentTimeBaseCount.class);
        return groupResults.getMappedResults();
    }

    @Override
    public List<ContentTimeBaseCount> getContentTimeBaseCountByContentIdBetweenStartAndEndDate(Integer contentTypeId, Integer subOrgId, Long categoryId, Long subCategoryId, String startDate, String endDate) {
        GroupOperation groupOperation = group("date").count().as("total");
        MatchOperation matchCondition = Aggregation.match(Criteria.where("sub_org_id").is(subOrgId).andOperator(Criteria.where("content_type_id").is(contentTypeId), Criteria.where("activity_type").is("POST"), Criteria.where("category_id").is(categoryId), Criteria.where("sub_category_id").is(subCategoryId)));
        ProjectionOperation projectionOperation = Aggregation.project("total").and("date").previousOperation();
        MatchOperation dateCondition = Aggregation.match(new Criteria().andOperator(Criteria.where("date").lte(analyticsReportUtil.getISTDateFormat(endDate)).gte(analyticsReportUtil.getISTDateFormat(startDate))));
        Aggregation agg = newAggregation(
                matchCondition,
                dateCondition,
                Aggregation.project().and("contentId").as("contentId").and("date").dateAsFormattedString("%Y-%m-%d").as("date"),
                groupOperation,
                projectionOperation,
                sort(Sort.Direction.ASC, "date")
        );
        AggregationResults<ContentTimeBaseCount> groupResults = mongoTemplate.aggregate(agg, Feed.class, ContentTimeBaseCount.class);
        return groupResults.getMappedResults();
    }

    @Override
    public Integer getContentCountByContentIdBetweenStartAndEndDate(Integer contentTypeId, Integer subOrgId, String startDate, String endDate) {
        MatchOperation matchCondition = Aggregation.match(Criteria.where("sub_org_id").is(subOrgId).andOperator(Criteria.where("content_type_id").is(contentTypeId), Criteria.where("activity_type").is("POST")));
        MatchOperation dateCondition = Aggregation.match(new Criteria().andOperator(Criteria.where("date").lte(analyticsReportUtil.getISTDateFormat(endDate)).gte(analyticsReportUtil.getISTDateFormat(startDate))));
        Aggregation agg = newAggregation(
                matchCondition,
                dateCondition,
                Aggregation.project().and("contentId").as("contentId").and("date").dateAsFormattedString("%Y-%m-%d").as("date")
        );
        AggregationResults<ContentTimeBaseCount> groupResults = mongoTemplate.aggregate(agg, Feed.class, ContentTimeBaseCount.class);
        return groupResults.getMappedResults().size();
    }

    @Override
    public Integer getContentCountByContentIdBetweenStartAndEndDate(Integer contentTypeId, Integer subOrgId, Long categoryId, Long subCategoryId, String startDate, String endDate) {
        MatchOperation matchCondition = Aggregation.match(Criteria.where("sub_org_id").is(subOrgId).andOperator(Criteria.where("content_type_id").is(contentTypeId), Criteria.where("activity_type").is("POST"), Criteria.where("category_id").is(categoryId), Criteria.where("sub_category_id").is(subCategoryId)));
        MatchOperation dateCondition = Aggregation.match(new Criteria().andOperator(Criteria.where("date").lte(analyticsReportUtil.getISTDateFormat(endDate)).gte(analyticsReportUtil.getISTDateFormat(startDate))));
        Aggregation agg = newAggregation(
                matchCondition,
                dateCondition,
                Aggregation.project().and("contentId").as("contentId").and("date").dateAsFormattedString("%Y-%m-%d").as("date")
        );
        AggregationResults<ContentTimeBaseCount> groupResults = mongoTemplate.aggregate(agg, Feed.class, ContentTimeBaseCount.class);
        return groupResults.getMappedResults().size();
    }

    public List<AnalyticsContentCount> findOverAllCountList(Integer categoryId, List<Integer> subCategoryId, Integer contentTypeId, List<String> activityType,
                                                            String startDate, String endDate, Integer subOrgId) {

        GroupOperation groupOperation = Aggregation.group(Constants.DATE_DB).count().as(Constants.TOTAL);
        MatchOperation matchCondition;
        if (categoryId != null && !categoryId.equals(Constants.ZERO) && subCategoryId != null && !subCategoryId.isEmpty()) {
            matchCondition = Aggregation.match(
                    Criteria.where(Constants.FIELD_CATEGORY_ID).is(categoryId)
                            .andOperator(Criteria.where(Constants.FIELD_CONTENT_TYPE_ID).is(contentTypeId),
                                    Criteria.where(Constants.SUB_ORG_ID).is(subOrgId),
                                    Criteria.where(Constants.ACTIVITY_TYPE_DB).in(activityType),
                                    Criteria.where(Constants.SUB_CATEGORY_ID_DB).in(subCategoryId)));
        } else if (categoryId != null && !categoryId.equals(Constants.ZERO) && (subCategoryId == null || subCategoryId.isEmpty())) {
            matchCondition = Aggregation.match(
                    Criteria.where(Constants.FIELD_CATEGORY_ID).is(categoryId)
                            .andOperator(Criteria.where(Constants.FIELD_CONTENT_TYPE_ID).is(contentTypeId),
                                    Criteria.where(Constants.SUB_ORG_ID).is(subOrgId),
                                    Criteria.where(Constants.ACTIVITY_TYPE_DB).in(activityType)));
        } else {
            matchCondition = Aggregation.match(
                    Criteria.where(Constants.ACTIVITY_TYPE_DB).in(activityType)
                            .andOperator(Criteria.where(Constants.FIELD_CONTENT_TYPE_ID).is(contentTypeId),
                                    Criteria.where(Constants.SUB_ORG_ID).is(subOrgId)));
        }
        ProjectionOperation projectionOperation =
                Aggregation.project(Constants.TOTAL).and(Constants.DATE_DB).previousOperation();

        Date startDateFmt = AnalyticsReportUtil.getInitialDate(startDate + Constants.DATE_INITAIL_TIME);
        Date endDateFmt = AnalyticsReportUtil.getDayEndDate(endDate + Constants.DATE_INITAIL_TIME);
        MatchOperation dateCondition = Aggregation.match(new Criteria()
                .andOperator(Criteria.where(Constants.DATE_DB).lt(endDateFmt).gte(startDateFmt)));

        Aggregation agg = newAggregation(
                matchCondition,
                dateCondition,
                Aggregation.project().and(Constants.DATE_DB).dateAsFormattedString(Constants.PROFILE_STAT_DATE_FORMAT).as(Constants.DATE_DB),
                groupOperation,
                projectionOperation,
                Aggregation.sort(Sort.Direction.ASC, Constants.DATE_DB)
        );
        AggregationResults<AnalyticsContentCount> groupResults = mongoTemplate.aggregate(agg, Feed.class,
                AnalyticsContentCount.class);
        return groupResults.getMappedResults();
    }

    public long getPostCount(String activityType, Integer contentTypeId, Date startDate, Date endDate, Integer categoryId, List<Integer> subCategoryIds, Integer subOrgId) {
        Query query = new Query();
        if (categoryId != null && !categoryId.equals(Constants.ZERO)) {
            query.addCriteria(Criteria.where(Constants.CATEGORY_ID).is(categoryId));
        }
        if (subCategoryIds != null && !subCategoryIds.isEmpty()) {
            query.addCriteria(Criteria.where(Constants.SUB_CATEGORY_ID).in(subCategoryIds));
        }
        query.addCriteria(Criteria.where(Constants.SUB_ORG_ID).is(subOrgId));
        query.addCriteria(Criteria.where(Constants.ACTIVITY_TYPE_PARAM).is(activityType));
        query.addCriteria(Criteria.where(Constants.CONTENT_TYPE_ID).is(contentTypeId));
        query.addCriteria(Criteria.where(Constants.DATE_DB).lte(endDate).gte(startDate));
        return mongoTemplate.count(query, Feed.class);
    }
}
