package org.ril.hrss.repository;

import com.mongodb.BasicDBObject;
import org.ril.hrss.model.UserActivityLogs;
import org.ril.hrss.model.auth.User;
import org.ril.hrss.model.category_hierarchy.*;
import org.ril.hrss.model.content.OrgContent;
import org.ril.hrss.service.content.SubOrgContentService;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ControlPanelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.ril.hrss.utility.AnalyticsReportUtil.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class UserActivityCustomImpl implements UserActivityCustom {
    private final MongoTemplate mongoTemplate;
    @Autowired
    CategoryHierarchyRepository categoryHierarchyRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SubOrgContentService subOrgContentService;

    public UserActivityCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    List<Integer> getContentId(HttpServletRequest httpServletRequest) {
        List<OrgContent> orgContents = subOrgContentService.getSubOrgContent(ControlPanelUtil.setSubOrgId(httpServletRequest), ControlPanelUtil.setOrgId(httpServletRequest));
        return orgContents.stream().map(OrgContent::getContentTypeId).collect(Collectors.toList());
    }

    private List findTopCommunity(HttpServletRequest httpServletRequest) {
        Map map = getStartDateAndEndDate(httpServletRequest);
        Aggregation agg = newAggregation(
                match(Criteria.where(Constants.ACTIVITY_CODE).is(Constants.POST)),
                match(Criteria.where(Constants.CONTENT_TYPE_ID).in(this.getContentId(httpServletRequest))),
                match(Criteria.where(Constants.ACTIVITY_TIME).lte(map.get(Constants.END_DATE)).gte(map.get(Constants.START_DATE))),
                match(Criteria.where(Constants.SUB_ORG_ID).is(ControlPanelUtil.setSubOrgId(httpServletRequest))),
                Aggregation.group(Constants.CATEGORY_ID).count().as(Constants.COUNT),
                Aggregation.project(Constants.COUNT).and(Constants.CATEGORY_ID).previousOperation(),
                Aggregation.sort(Sort.Direction.DESC, Constants.COUNT),
                Aggregation.skip(Constants.ZERO),
                limit(Constants.TEN));
        return mongoTemplate.aggregate(agg, UserActivityLogs.class,
                TopCommunity.class).getMappedResults();
    }

    @Override
    public List getCommunityName(HttpServletRequest httpServletRequest) {
        List<TopCommunity> logs = this.findTopCommunity(httpServletRequest);
        logs.forEach(topCommunity -> {
            Optional<CategoryHierarchy> categoryHierarchy = categoryHierarchyRepository.findById(topCommunity.getCategoryId());
            if (categoryHierarchy.isPresent()) {
                topCommunity.setName(categoryHierarchy.get().getTitle());
            }
        });
        return logs;
    }

    private List findTopAOI(HttpServletRequest httpServletRequest) {
        Map map = getStartDateAndEndDate(httpServletRequest);
        Aggregation agg = newAggregation(
                match(Criteria.where(Constants.ACTIVITY_CODE).is(Constants.POST)),
                match(Criteria.where(Constants.CONTENT_TYPE_ID).in(this.getContentId(httpServletRequest))),
                match(Criteria.where(Constants.SUB_ORG_ID).is(ControlPanelUtil.setSubOrgId(httpServletRequest))),
                match(Criteria.where(Constants.ACTIVITY_TIME).lte(map.get(Constants.END_DATE)).gte(map.get(Constants.START_DATE))),
                unwind(Constants.SUB_CATEGORY_ID),
                match(Criteria.where(Constants.SUB_CATEGORY_ID).exists(true)),
                match(Criteria.where(Constants.SUB_CATEGORY_ID).ne(Constants.ZERO)),
                match(Criteria.where(Constants.SUB_CATEGORY_ID).not().size(Constants.ZERO)),
                Aggregation.group(Constants.SUB_CATEGORY_ID).count().as(Constants.COUNT),
                Aggregation.project(Constants.COUNT).and(Constants.SUB_CATEGORY_ID).previousOperation(),
                match(Criteria.where(Constants.COUNT).gt(Constants.ZERO)),
                Aggregation.sort(Sort.Direction.DESC, Constants.COUNT),
                Aggregation.skip(Constants.ZERO),
                limit(Constants.TEN)
        );
        return mongoTemplate.aggregate(agg, UserActivityLogs.class,
                TopAOI.class).getMappedResults();
    }

    @Override
    public List getAOIName(HttpServletRequest httpServletRequest) {
        List<TopAOI> logs = this.findTopAOI(httpServletRequest);
        logs.forEach(topAOI -> {
            Optional<CategoryHierarchy> categoryHierarchy = categoryHierarchyRepository.findById(topAOI.getSubCategoryId());
            if (categoryHierarchy.isPresent()) {
                topAOI.setName(categoryHierarchy.get().getTitle());
            }
        });
        return logs;
    }


    private List findTopContributors(HttpServletRequest httpServletRequest) {
        Integer categoryId = Constants.ZERO;
        Integer subCategoryId = Constants.ZERO;
        if (httpServletRequest.getParameter(Constants.CATEGORY_NAME) != null) {
            categoryId = Integer.valueOf(httpServletRequest.getParameter(Constants.CATEGORY_NAME));
        }
        if (httpServletRequest.getParameter(Constants.AOI_NAME) != null) {
            subCategoryId = Integer.valueOf(httpServletRequest.getParameter(Constants.AOI_NAME));
        }
        Map map = getStartDateAndEndDate(httpServletRequest);
        Aggregation agg = newAggregation(
                match(Criteria.where(Constants.ACTIVITY_CODE).is(Constants.POST)),
                match(Criteria.where(Constants.CONTENT_TYPE_ID).in(this.getContentId(httpServletRequest))),
                match(Criteria.where(Constants.SUB_ORG_ID).is(ControlPanelUtil.setSubOrgId(httpServletRequest))),
                match(Criteria.where(Constants.ACTIVITY_TIME).lte(map.get(Constants.END_DATE)).gte(map.get(Constants.START_DATE))),
                createMatchCondition(categoryId, subCategoryId),
                Aggregation.group(Constants.USER_ID).count().as(Constants.COUNT),
                Aggregation.project(Constants.COUNT).and(Constants.USER_ID).previousOperation(),
                Aggregation.sort(Sort.Direction.DESC, Constants.COUNT),
                Aggregation.skip(Constants.ZERO),
                limit(Constants.TEN));
        return mongoTemplate.aggregate(agg, UserActivityLogs.class,
                TopContributors.class).getMappedResults();
    }


    @Override
    public List getUserName(HttpServletRequest httpServletRequest) {
        List<TopContributors> logs = this.findTopContributors(httpServletRequest);
        logs.forEach(topContributors -> {
            User user = userRepository.findById(topContributors.getUserId());
            if (user != null) {
                topContributors.setName(user.getName());
            }
        });
        return logs;
    }

    @Override
    public List<LinkedHashMap> graphContentType(HttpServletRequest httpServletRequest) {
        Integer categoryId = Constants.ZERO;
        Integer subCategoryId = Constants.ZERO;
        if (httpServletRequest.getParameter(Constants.CATEGORY_NAME) != null) {
            categoryId = Integer.valueOf(httpServletRequest.getParameter(Constants.CATEGORY_NAME));
        }
        if (httpServletRequest.getParameter(Constants.AOI_NAME) != null) {
            subCategoryId = Integer.valueOf(httpServletRequest.getParameter(Constants.AOI_NAME));
        }
        Map mapDate = getStartDateAndEndDate(httpServletRequest);
        Aggregation agg = newAggregation(
                match(Criteria.where(Constants.ACTIVITY_CODE).is(Constants.POST)),
                match(Criteria.where(Constants.CONTENT_TYPE_ID).in(this.getContentId(httpServletRequest))),
                match(Criteria.where(Constants.ACTIVITY_TIME).lte(mapDate.get(Constants.END_DATE)).gte(mapDate.get(Constants.START_DATE))),
                match(Criteria.where(Constants.SUB_ORG_ID).is(ControlPanelUtil.setSubOrgId(httpServletRequest))),
                createMatchCondition(categoryId, subCategoryId),
                Aggregation.project().and(Constants.CONTENT_TYPE_ID).as(Constants.CONTENT_TYPE_ID)
                        .and(Constants.ACTIVITY_TIME).dateAsFormattedString(Constants.DATE_STRING).as(Constants.ACTIVITY_TIME),
                group(Constants.ACTIVITY_TIME, Constants.CONTENT_TYPE_ID).count().as(Constants.COUNT),
                Aggregation.project(Constants.COUNT).and(Constants.CONTENT_TYPE_ID).as(Constants.CONTENT_TYPE_ID)
                        .and(Constants.ACTIVITY_TIME).as(Constants.ACTIVITY_TIME),
                Aggregation.group(Constants.ACTIVITY_TIME).push(new BasicDBObject(Constants.CONTENT_TYPE_ID, Constants.MONGO_CONTENT_TYPE_ID)
                        .append(Constants.COUNT, Constants.MONGO_CONTENT_COUNT)).as(Constants.RESULT),
                Aggregation.sort(Sort.Direction.ASC, Constants._ID));
        List<GraphJson> graphJsons = mongoTemplate.aggregate(agg, UserActivityLogs.class,
                GraphJson.class).getMappedResults();
        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT_ANALYTICS);
        List<GraphJson> graphJsons1 = createGraphJsonForAllDates(getDateSequenceForCommunitiesReport(format.format(mapDate.get(Constants.START_DATE)), format.format(mapDate.get(Constants.END_DATE))), graphJsons, getContentId(httpServletRequest));
        List<LinkedHashMap> mapList = createListOfMap(graphJsons1);
        getContentId(httpServletRequest).forEach(integer -> {
            mapList.forEach(hashMap -> {
                hashMap.putIfAbsent(integer, Constants.ZERO);
            });
        });
        List<LinkedHashMap> handleEmptyList = new ArrayList<>();
        if (mapList.isEmpty()) {
            return handleEmptyList(getDateSequenceForCommunitiesReport(format.format(mapDate.get(Constants.START_DATE)), format.format(mapDate.get(Constants.END_DATE))), getContentId(httpServletRequest), handleEmptyList);
        }
        return mapList;
    }

    public Long getTotalPostCount(HttpServletRequest httpServletRequest) {
        Map map = getStartDateAndEndDate(httpServletRequest);
        Query query = new Query();
        Integer categoryId = Constants.ZERO;
        Integer subCategoryId = Constants.ZERO;
        if (httpServletRequest.getParameter(Constants.CATEGORY_NAME) != null) {
            categoryId = Integer.valueOf(httpServletRequest.getParameter(Constants.CATEGORY_NAME));
        }
        if (httpServletRequest.getParameter(Constants.AOI_NAME) != null) {
            subCategoryId = Integer.valueOf(httpServletRequest.getParameter(Constants.AOI_NAME));
        }
        if (categoryId != null && !categoryId.equals(Constants.ZERO)) {
            query.addCriteria(Criteria.where(Constants.CATEGORY_ID).is(categoryId));
        }
        if (subCategoryId != null && !subCategoryId.equals(Constants.ZERO)) {
            query.addCriteria(Criteria.where(Constants.SUB_CATEGORY_ID).in(subCategoryId));
        }
        query.addCriteria(Criteria.where(Constants.SUB_ORG_ID).is(ControlPanelUtil.setSubOrgId(httpServletRequest)));
        query.addCriteria(Criteria.where(Constants.ACTIVITY_CODE).is(Constants.POST));
        query.addCriteria(Criteria.where(Constants.CONTENT_TYPE_ID).in(this.getContentId(httpServletRequest)));
        query.addCriteria(Criteria.where(Constants.ACTIVITY_TIME).lte(map.get(Constants.END_DATE)).gte(map.get(Constants.START_DATE)));
        return mongoTemplate.count(query, UserActivityLogs.class);
    }
}
