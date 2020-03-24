package org.ril.hrss.repository;

import org.ril.hrss.model.ContentView;
import org.ril.hrss.model.TopBlog;
import org.ril.hrss.model.UserViewTimeBaseCount;
import org.ril.hrss.model.ViewTimeBaseCount;
import org.ril.hrss.utility.AnalyticsReportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class ContentViewCustomRepositoryImpl implements ContentViewCustomRepository {

    private final MongoTemplate mongoTemplate;
    @Autowired
    AnalyticsReportUtil analyticsReportUtil;

    @Autowired
    public ContentViewCustomRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<ViewTimeBaseCount> getViewTimeBaseCountByContentIdBetweenStartAndEndDate(Integer contentTypeId, Integer subOrgId, String startDate, String endDate) {
        GroupOperation groupOperation = group("viewedAt").count().as("total");
        MatchOperation matchCondition = Aggregation.match(Criteria.where("sub_org_id").is(subOrgId).andOperator(Criteria.where("content_type_id").is(contentTypeId)));
        ProjectionOperation projectionOperation = Aggregation.project("total").and("viewedAt").previousOperation();
        MatchOperation dateCondition = Aggregation.match(new Criteria().andOperator(Criteria.where("viewed_at").lte(analyticsReportUtil.getISTDateFormat(endDate)).gte(analyticsReportUtil.getISTDateFormat(startDate))));
        Aggregation agg = newAggregation(
                matchCondition,
                dateCondition,
                Aggregation.project().and("contentId").as("contentId").and("viewedAt").dateAsFormattedString("%Y-%m-%d").as("viewedAt"),
                groupOperation,
                projectionOperation,
                sort(Sort.Direction.ASC, "viewedAt")
        );
        AggregationResults<ViewTimeBaseCount> groupResults = mongoTemplate.aggregate(agg, ContentView.class, ViewTimeBaseCount.class);
        return groupResults.getMappedResults();
    }

    @Override
    public Integer getCountViewTimeBaseCountByContentIdBetweenStartAndEndDate(Integer contentTypeId, Integer subOrgId, String startDate, String endDate) {
        MatchOperation matchCondition = Aggregation.match(Criteria.where("sub_org_id").is(subOrgId).andOperator(Criteria.where("content_type_id").is(contentTypeId)));
        MatchOperation dateCondition = Aggregation.match(new Criteria().andOperator(Criteria.where("viewed_at").lte(analyticsReportUtil.getISTDateFormat(endDate)).gte(analyticsReportUtil.getISTDateFormat(startDate))));
        Aggregation agg = newAggregation(
                matchCondition,
                dateCondition,
                Aggregation.project().and("contentId").as("contentId").and("viewedAt").dateAsFormattedString("%Y-%m-%d").as("viewedAt")
        );
        AggregationResults<ViewTimeBaseCount> groupResults = mongoTemplate.aggregate(agg, ContentView.class, ViewTimeBaseCount.class);
        return groupResults.getMappedResults().size();
    }

    @Override
    public List<UserViewTimeBaseCount> getUserViewTimeBaseCountByContentIdBetweenStartAndEndDate(Integer contentTypeId, Integer subOrgId, String startDate, String endDate) {
        GroupOperation groupOperation = group("viewedAt").count().as("total");
        MatchOperation matchCondition = Aggregation.match(Criteria.where("sub_org_id").is(subOrgId).andOperator(Criteria.where("content_type_id").is(contentTypeId)));
        ProjectionOperation projectionOperation = Aggregation.project("total").and("viewedAt").previousOperation();
        MatchOperation dateCondition = Aggregation.match(new Criteria().andOperator(Criteria.where("viewed_at").lte(analyticsReportUtil.getISTDateFormat(endDate)).gte(analyticsReportUtil.getISTDateFormat(startDate))));
        Aggregation agg = newAggregation(
                matchCondition,
                dateCondition,
                Aggregation.project().and("contentViewedById").as("contentViewedById").and("viewedAt").dateAsFormattedString("%Y-%m-%d").as("viewedAt"),
                group("viewedAt", "contentViewedById"),
                groupOperation,
                projectionOperation,
                sort(Sort.Direction.ASC, "viewedAt")
        );
        AggregationResults<UserViewTimeBaseCount> groupResults = mongoTemplate.aggregate(agg, ContentView.class, UserViewTimeBaseCount.class);
        return groupResults.getMappedResults();
    }

    @Override
    public List<TopBlog> getTopViewByContentIdBetweenStartAndEndDate(Integer contentTypeId, Integer subOrgId, String startDate, String endDate) {
        GroupOperation groupOperation = group("contentId").count().as("count");
        MatchOperation matchCondition = Aggregation.match(Criteria.where("sub_org_id").is(subOrgId).andOperator(Criteria.where("content_type_id").is(contentTypeId)));
        ProjectionOperation projectionOperation = Aggregation.project("count").and("blogId").previousOperation();
        MatchOperation dateCondition = Aggregation.match(new Criteria().andOperator(Criteria.where("viewed_at").lte(analyticsReportUtil.getISTDateFormat(endDate)).gte(analyticsReportUtil.getISTDateFormat(startDate))));
        Aggregation agg = newAggregation(
                matchCondition,
                dateCondition,
                groupOperation,
                projectionOperation,
                sort(Sort.Direction.DESC, "count"),
                limit(5)
        );
        AggregationResults<TopBlog> groupResults = mongoTemplate.aggregate(agg, ContentView.class, TopBlog.class);
        return groupResults.getMappedResults();
    }
}
