package org.ril.hrss.repository;

import org.ril.hrss.model.CommentTimeBaseCount;
import org.ril.hrss.model.TopBlog;
import org.ril.hrss.model.UserActivityLogs;
import org.ril.hrss.utility.AnalyticsReportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class UserLogCustomRepositoryImpl implements UserLogCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    AnalyticsReportUtil analyticsReportUtil;

    public UserLogCustomRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public List<CommentTimeBaseCount> getUserCommentTimeBaseCountByContentIdBetweenStartAndEndDate(Integer contentTypeId, Integer subOrgId, String startDate, String endDate) {
        GroupOperation groupOperation = group("commentedAt").count().as("total");
        MatchOperation matchCondition = Aggregation.match(Criteria.where("sub_org_id").is(subOrgId).andOperator(Criteria.where("content_type_id").is(contentTypeId)));
        ProjectionOperation projectionOperation = Aggregation.project("total").and("commentedAt").previousOperation();
        MatchOperation dateCondition = Aggregation.match(new Criteria().andOperator(Criteria.where("commented_at").lte(analyticsReportUtil.getISTDateFormat(endDate)).gte(analyticsReportUtil.getISTDateFormat(startDate))));
        Aggregation agg = newAggregation(
                matchCondition,
                dateCondition,
                Aggregation.project().and("contentId").as("contentId").and("commentedAt").dateAsFormattedString("%Y-%m-%d").as("commentedAt"),
                groupOperation,
                projectionOperation,
                sort(Sort.Direction.ASC, "commentedAt")
        );
        AggregationResults<CommentTimeBaseCount> groupResults = mongoTemplate.aggregate(agg, UserActivityLogs.class, CommentTimeBaseCount.class);
        return groupResults.getMappedResults();
    }

    @Override
    public Integer getCountCommentTimeBaseCountByContentIdBetweenStartAndEndDate(Integer contentTypeId, Integer subOrgId, String startDate, String endDate) {
        MatchOperation matchCondition = Aggregation.match(Criteria.where("sub_org_id").is(subOrgId).andOperator(Criteria.where("content_type_id").is(contentTypeId)));
        MatchOperation dateCondition = Aggregation.match(new Criteria().andOperator(Criteria.where("commented_at").lte(analyticsReportUtil.getISTDateFormat(endDate)).gte(analyticsReportUtil.getISTDateFormat(startDate))));
        Aggregation agg = newAggregation(
                matchCondition,
                dateCondition,
                Aggregation.project().and("contentId").as("contentId").and("commentedAt").dateAsFormattedString("%Y-%m-%d").as("commentedAt")
        );
        AggregationResults<CommentTimeBaseCount> groupResults = mongoTemplate.aggregate(agg, UserActivityLogs.class, CommentTimeBaseCount.class);
        return groupResults.getMappedResults().size();
    }

    @Override
    public List<TopBlog> getTopCommentByContentIdBetweenStartAndEndDate(Integer contentTypeId, Integer subOrgId, String startDate, String endDate) {
        GroupOperation groupOperation = group("contentId").count().as("count");
        MatchOperation matchCondition = Aggregation.match(Criteria.where("sub_org_id").is(subOrgId).andOperator(Criteria.where("content_type_id").is(contentTypeId)));
        ProjectionOperation projectionOperation = Aggregation.project("count").and("blogId").previousOperation();
        MatchOperation dateCondition = Aggregation.match(new Criteria().andOperator(Criteria.where("commented_at").lte(analyticsReportUtil.getISTDateFormat(endDate)).gte(analyticsReportUtil.getISTDateFormat(startDate))));
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
}
