package org.ril.hrss.service.analytics;

import org.ril.hrss.model.category_hierarchy.CategoryHierarchy;
import org.ril.hrss.model.content.poll.Poll;
import org.ril.hrss.model.reports.AnalyticsContentCount;
import org.ril.hrss.model.reports.PollParticipantsCount;
import org.ril.hrss.model.reports.TopPolls;
import org.ril.hrss.repository.*;
import org.ril.hrss.utility.AnalyticsReportUtil;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ControlPanelUtil;
import org.ril.hrss.utility.PollAnalyticsReportUtil;
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
public class PollAnalyticsReportService {

    @Autowired
    CategoryHierarchyRepository categoryHierarchyRepository;

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private AnalyticsReportUtil analyticsReportUtil;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private PollResultRepository pollResultRepository;

    @Autowired
    private UserLogRepository userLogRepository;

    @Autowired
    private PollAnalyticsReportUtil pollAnalyticsReportUtil;

    public List<CategoryHierarchy> getCommunityList(HttpServletRequest httpServletRequest, Integer parentId) {
        return categoryHierarchyRepository.findBySuborgIdAndParentId(ControlPanelUtil.setSubOrgId(httpServletRequest), parentId);
    }

    public String customizedPollReports(HttpServletRequest httpServletRequest, Model model) {
        Integer categoryId = pollAnalyticsReportUtil.getCategoryId(httpServletRequest);
        Integer subCategoryId = pollAnalyticsReportUtil.getSubCategoryId(httpServletRequest);
        String startDate = pollAnalyticsReportUtil.getStartDate(httpServletRequest);
        String endDate = pollAnalyticsReportUtil.getEndDate(httpServletRequest);
        Date startDateFmt = AnalyticsReportUtil.getInitialDate(startDate + Constants.DATE_INITAIL_TIME);
        Date endDateFmt = AnalyticsReportUtil.getDayEndDate(endDate + Constants.DATE_INITAIL_TIME);
        List<Integer> subCategoryIds = null;
        if (subCategoryId != null && !subCategoryId.equals(Constants.ZERO)) {
            subCategoryIds = Arrays.asList(subCategoryId);
        }
        Integer subOrgId = ControlPanelUtil.setSubOrgId(httpServletRequest);
        long overallPostCount = feedRepository.getPostCount(Constants.POST, Constants.POLL_CONTENT_TYPE_ID, startDateFmt, endDateFmt, categoryId, subCategoryIds, subOrgId);

        List<AnalyticsContentCount> postGraphCounts = feedRepository.findOverAllCountList(categoryId, subCategoryIds, Constants.POLL_CONTENT_TYPE_ID, Arrays.asList(Constants.POST), startDate, endDate, subOrgId);
        List<AnalyticsContentCount> commentsGraphCounts = userLogRepository.findCommentCountList(categoryId, subCategoryIds, Constants.POLL_CONTENT_TYPE_ID, Arrays.asList(Constants.COMMENTED, Constants.REPLIED), startDate, endDate, subOrgId);
        List<Long> pollIds;
        if (subCategoryId != null && !subCategoryId.equals(Constants.ZERO)) {
            pollIds = pollRepository.getfilteredPolls(categoryId, subCategoryId, startDateFmt, endDateFmt, subOrgId);
        } else {
            pollIds = pollRepository.getOverallPolls(categoryId, startDateFmt, endDateFmt, subOrgId);
        }
        List<TopPolls> topPolls = new ArrayList<>();
        List<PollParticipantsCount> participantGraphCounts = new ArrayList<>();
        if (pollIds != null && !pollIds.isEmpty()) {
            participantGraphCounts = pollResultRepository.getTotalParticipants(startDateFmt, endDateFmt, pollIds);
            topPolls = pollResultRepository.getFilteredTopPollIds(startDateFmt, endDateFmt, pollIds);
            if (topPolls != null && topPolls.size() > 0) {
                List<Long> topPollIds = topPolls.stream().map(TopPolls::getPollId).collect(Collectors.toList());
                List<Poll> pollDetails = pollRepository.findByIdIn(topPollIds);
                topPolls.stream().forEach(poll -> {
                    poll.setQuesText(pollDetails.stream().filter(p -> p.getId().equals(poll.getPollId())).map(text -> text.getQuesText()).collect(Collectors.joining()));
                });
            }
        }
        List<CategoryHierarchy> list = new ArrayList<>();
        if (subCategoryId != null && categoryId != null) {
            list = getCommunityList(httpServletRequest, categoryId);
        }
        return pollAnalyticsReportUtil.pollReportsModel(startDate, endDate, overallPostCount, this.getCommunityList(httpServletRequest, Constants.ZERO), commentsGraphCounts, model, postGraphCounts, topPolls, participantGraphCounts, httpServletRequest, list);
    }
}
