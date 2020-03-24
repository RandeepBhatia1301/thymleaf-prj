package org.ril.hrss.repository;

import org.ril.hrss.model.ContentRead;
import org.ril.hrss.model.ReadTimeBaseCount;
import org.ril.hrss.utility.AnalyticsReportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class ContentReadCustomRepositoryImpl implements ContentReadCustomRepository {

    private final MongoTemplate mongoTemplate;
    @Autowired
    AnalyticsReportUtil analyticsReportUtil;

    @Autowired
    public ContentReadCustomRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<ReadTimeBaseCount> getReadTimeBaseCountByContentIdBetweenStartAndEndDate(Integer contentTypeId, Integer subOrgId, String startDate, String endDate) {
        GroupOperation groupOperation = group("readAt").count().as("total");
        MatchOperation matchCondition = Aggregation.match(Criteria.where("sub_org_id").is(subOrgId).andOperator(Criteria.where("content_type_id").is(contentTypeId)));
        ProjectionOperation projectionOperation = Aggregation.project("total").and("readAt").previousOperation();
        MatchOperation dateCondition = Aggregation.match(new Criteria().andOperator(Criteria.where("read_at").lte(analyticsReportUtil.getISTDateFormat(endDate)).gte(analyticsReportUtil.getISTDateFormat(startDate))));
        Aggregation agg = newAggregation(
                matchCondition,
                dateCondition,
                Aggregation.project().and("contentId").as("contentId").and("readAt").dateAsFormattedString("%Y-%m-%d").as("readAt"),
                groupOperation,
                projectionOperation,
                sort(Sort.Direction.ASC, "readAt")
        );
        AggregationResults<ReadTimeBaseCount> groupResults = mongoTemplate.aggregate(agg, ContentRead.class, ReadTimeBaseCount.class);
        return groupResults.getMappedResults();
    }

    @Override
    public Integer getCountReadTimeBaseCountByContentIdBetweenStartAndEndDate(Integer contentTypeId, Integer subOrgId, String startDate, String endDate) {
        MatchOperation matchCondition = Aggregation.match(Criteria.where("sub_org_id").is(subOrgId).andOperator(Criteria.where("content_type_id").is(contentTypeId)));
        MatchOperation dateCondition = Aggregation.match(new Criteria().andOperator(Criteria.where("read_at").lte(analyticsReportUtil.getISTDateFormat(endDate)).gte(analyticsReportUtil.getISTDateFormat(startDate))));
        Aggregation agg = newAggregation(
                matchCondition,
                dateCondition,
                Aggregation.project().and("contentId").as("contentId").and("readAt").dateAsFormattedString("%Y-%m-%d").as("readAt")
        );
        AggregationResults<ReadTimeBaseCount> groupResults = mongoTemplate.aggregate(agg, ContentRead.class, ReadTimeBaseCount.class);
        return groupResults.getMappedResults().size();
    }
}
