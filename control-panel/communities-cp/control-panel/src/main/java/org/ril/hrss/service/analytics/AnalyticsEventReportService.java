package org.ril.hrss.service.analytics;

import org.ril.hrss.model.category_hierarchy.CategoryHierarchy;
import org.ril.hrss.model.content.event.Event;
import org.ril.hrss.model.reports.*;
import org.ril.hrss.repository.*;
import org.ril.hrss.utility.AnalyticsReportUtil;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ControlPanelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AnalyticsEventReportService {

    @Autowired
    ContentCustom contentCustom;

    @Autowired
    CategoryHierarchyRepository categoryHierarchyRepository;

    @Autowired
    UserActivityCustom userActivityCustom;

    @Autowired
    UserLogRepository userLogRepository;

    @Autowired
    FeedRepository feedRepository;

    @Autowired
    ContentReadRepository contentReadRepository;

    @Autowired
    ContentViewRepository contentViewRepository;

    @Autowired
    UserCommentRepository userCommentRepository;

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    DiscussionRepository discussionRepository;

    @Autowired
    AnalyticsReportUtil analyticsReportUtil;

    @Autowired
    ContentParticipationRepository contentParticipationRepository;

    @Autowired
    EventRepository eventRepository;


    public String index(HttpServletRequest httpServletRequest, Model model) {
        Integer categoryId = analyticsReportUtil.getCategoryId(httpServletRequest);
        Integer subCategoryId = analyticsReportUtil.getSubCategoryId(httpServletRequest);
        String startDate = analyticsReportUtil.getStartDate(httpServletRequest);
        String endDate = analyticsReportUtil.getEndDate(httpServletRequest);
        Integer subOrgId = ControlPanelUtil.setSubOrgId(httpServletRequest);
        Integer orgId = ControlPanelUtil.setOrgId(httpServletRequest);
        Date startDateFrmt = AnalyticsReportUtil.getInitialDate(startDate + Constants.DATE_INITAIL_TIME);
        Date endDateFrmt = AnalyticsReportUtil.getDayEndDate(endDate + Constants.DATE_INITAIL_TIME);
        List<Integer> subCategoryIds = null;
        if (subCategoryId != null && !subCategoryId.equals(Constants.ZERO)) {
            subCategoryIds = Arrays.asList(subCategoryId);
        }
        long totalEvent = feedRepository.getPostCount(Constants.POST, Constants.EVENT_CONTENT_TYPE_ID, startDateFrmt, endDateFrmt, categoryId, subCategoryIds, subOrgId);

        List<AnalyticsContentCount> postGraphCounts = feedRepository.findOverAllCountList(categoryId, subCategoryIds, Constants.EVENT_CONTENT_TYPE_ID, Arrays.asList(Constants.POST), startDate, endDate, subOrgId);
        List<AnalyticsContentCount> commentsGraphCounts = userLogRepository.findCommentCountList(categoryId, subCategoryIds, Constants.EVENT_CONTENT_TYPE_ID, Arrays.asList(Constants.COMMENTED, Constants.REPLIED), startDate, endDate, subOrgId);
        List<Long> eventIdsList = null;
        if (subCategoryId != null && !subCategoryId.equals(Constants.ZERO)) {
            eventIdsList = eventRepository.getfilteredEvents(categoryId, subCategoryId, startDateFrmt, endDateFrmt, subOrgId);
        } else {
            eventIdsList = eventRepository.getOverallEvents(categoryId, startDateFrmt, endDateFrmt, subOrgId);
        }
        List<EventParticipantsCount> totalReactionsGraphCounts = contentParticipationRepository.fetchTotalReactionsCount(Constants.EVENT_CONTENT_TYPE_ID, Arrays.asList(Constants.GOING_STATUS, Constants.NOT_GOING_STATUS, Constants.INTERESTED_STATUS), eventIdsList, startDateFrmt, endDateFrmt, orgId, subOrgId);
        List<EventUserActionsCount> userActionsCounts = contentParticipationRepository.fetchTotalUserActionsCount(Constants.EVENT_CONTENT_TYPE_ID, Arrays.asList(Constants.GOING_STATUS, Constants.NOT_GOING_STATUS, Constants.INTERESTED_STATUS), eventIdsList, startDateFrmt, endDateFrmt, orgId, subOrgId);
        Long totalReactions = contentParticipationRepository.fetchValueBasedReactionsCount(Constants.EVENT_CONTENT_TYPE_ID, Arrays.asList(Constants.GOING_STATUS, Constants.NOT_GOING_STATUS, Constants.INTERESTED_STATUS), eventIdsList, startDateFrmt, endDateFrmt, orgId, subOrgId);
        EventCombinedCounts eventCombinedCounts = new EventCombinedCounts();
        eventCombinedCounts.setReactions(totalReactions);
        List<EventValueBasedActionsCount> eventValueBasedActionsCounts = contentParticipationRepository.fetchValueBasedUserActionsCount(Constants.EVENT_CONTENT_TYPE_ID, Arrays.asList(Constants.GOING_STATUS, Constants.NOT_GOING_STATUS, Constants.INTERESTED_STATUS), eventIdsList, startDateFrmt, endDateFrmt, orgId, subOrgId);
        eventValueBasedActionsCounts.stream().forEach(count -> {
            if (count.getUserAction().equals(Constants.GOING_STATUS)) {
                eventCombinedCounts.setGoing(count.getTotal());
            } else if (count.getUserAction().equals(Constants.NOT_GOING_STATUS)) {
                eventCombinedCounts.setNotGoing(count.getTotal());
            } else if (count.getUserAction().equals(Constants.INTERESTED_STATUS)) {
                eventCombinedCounts.setInterested(count.getTotal());
            }

        });
        List<TopEvents> topEvents = new ArrayList<>();
        if (eventIdsList != null && !eventIdsList.isEmpty()) {
            topEvents = contentParticipationRepository.getFilteredTopEventIds(startDateFrmt, endDateFrmt, eventIdsList, orgId, subOrgId, Constants.EVENT_CONTENT_TYPE_ID);
            if (topEvents != null && topEvents.size() > 0) {
                List<Long> topEventIds = topEvents.stream().map(TopEvents::getEventId).collect(Collectors.toList());
                List<Event> eventDetails = eventRepository.findByIdIn(topEventIds);
                topEvents.stream().forEach(event -> {
                    event.setTitle(eventDetails.stream().filter(p -> p.getId().equals(event.getEventId())).map(text -> text.getTitle()).collect(Collectors.joining()));
                });
            }
        }
        List<CategoryHierarchy> list = new ArrayList<>();
        if (subCategoryId != null && categoryId != null) {
            list = getCommunityList(httpServletRequest, categoryId);
        }
        return analyticsReportUtil.indexEvent(httpServletRequest, model, totalEvent, topEvents, eventCombinedCounts, list, getCommunityList(httpServletRequest, Constants.ZERO), postGraphCounts, startDate, endDate, commentsGraphCounts, totalReactionsGraphCounts, userActionsCounts);

    }

    private List<CategoryHierarchy> getCommunityList(HttpServletRequest httpServletRequest, Integer parentId) {
        return categoryHierarchyRepository.findBySuborgIdAndParentId(ControlPanelUtil.setSubOrgId(httpServletRequest), parentId);
    }

}
