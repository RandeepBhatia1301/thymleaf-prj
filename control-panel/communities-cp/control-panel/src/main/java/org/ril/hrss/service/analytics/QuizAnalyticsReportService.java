package org.ril.hrss.service.analytics;

import org.ril.hrss.model.category_hierarchy.CategoryHierarchy;
import org.ril.hrss.model.content.quiz.Quiz;
import org.ril.hrss.model.reports.AnalyticsContentCount;
import org.ril.hrss.model.reports.QuizParticipantsCount;
import org.ril.hrss.model.reports.TopQuizs;
import org.ril.hrss.repository.*;
import org.ril.hrss.utility.AnalyticsReportUtil;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ControlPanelUtil;
import org.ril.hrss.utility.QuizAnalyticsReportUtil;
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
public class QuizAnalyticsReportService {

    @Autowired
    CategoryHierarchyRepository categoryHierarchyRepository;

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private AnalyticsReportUtil analyticsReportUtil;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Autowired
    private UserLogRepository userLogRepository;

    @Autowired
    private QuizAnalyticsReportUtil quizAnalyticsReportUtil;

    public List<CategoryHierarchy> getCommunityList(HttpServletRequest httpServletRequest, Integer parentId) {
        return categoryHierarchyRepository.findBySuborgIdAndParentId(ControlPanelUtil.setSubOrgId(httpServletRequest), parentId);
    }

    public String customizedQuizReports(HttpServletRequest httpServletRequest, Model model) {
        Integer categoryId = quizAnalyticsReportUtil.getCategoryId(httpServletRequest);
        Integer subCategoryId = quizAnalyticsReportUtil.getSubCategoryId(httpServletRequest);
        String startDate = quizAnalyticsReportUtil.getStartDate(httpServletRequest);
        String endDate = quizAnalyticsReportUtil.getEndDate(httpServletRequest);
        Date startDateFmt = AnalyticsReportUtil.getInitialDate(startDate + Constants.DATE_INITAIL_TIME);
        Date endDateFmt = AnalyticsReportUtil.getDayEndDate(endDate + Constants.DATE_INITAIL_TIME);
        Integer subOrgId = ControlPanelUtil.setSubOrgId(httpServletRequest);
        List<Integer> subCategoryIds = null;
        if (subCategoryId != null && !subCategoryId.equals(Constants.ZERO)) {
            subCategoryIds = Arrays.asList(subCategoryId);
        }
        long overallPostCount = feedRepository.getPostCount(Constants.POST, Constants.QUIZ_CONTENT_TYPE_ID, startDateFmt, endDateFmt, categoryId, subCategoryIds, subOrgId);

        List<AnalyticsContentCount> postGraphCounts = feedRepository.findOverAllCountList(categoryId, subCategoryIds, Constants.QUIZ_CONTENT_TYPE_ID, Arrays.asList(Constants.POST), startDate, endDate, subOrgId);
        List<AnalyticsContentCount> commentsGraphCounts = userLogRepository.findCommentCountList(categoryId, subCategoryIds, Constants.QUIZ_CONTENT_TYPE_ID, Arrays.asList(Constants.COMMENTED, Constants.REPLIED), startDate, endDate, subOrgId);
        List<Long> quizIds;
        if (subCategoryId != null && !subCategoryId.equals(Constants.ZERO)) {
            quizIds = quizRepository.getfilteredQuizs(categoryId, subCategoryId, startDateFmt, endDateFmt, subOrgId);
        } else {
            quizIds = quizRepository.getOverallQuizs(categoryId, startDateFmt, endDateFmt, subOrgId);
        }
        List<TopQuizs> topQuizs = new ArrayList<>();
        List<QuizParticipantsCount> participantGraphCounts = new ArrayList<>();
        if (quizIds != null && !quizIds.isEmpty()) {
            topQuizs = userSessionRepository.getFilteredTopQuizIds(startDateFmt, endDateFmt, quizIds);
            participantGraphCounts = userSessionRepository.getTotalParticipants(startDateFmt, endDateFmt, quizIds);
            if (topQuizs != null && topQuizs.size() > 0) {
                List<Long> topQuizIds = topQuizs.stream().map(TopQuizs::getQuizId).collect(Collectors.toList());
                List<Quiz> quizDetails = quizRepository.findByIdIn(topQuizIds);
                topQuizs.stream().forEach(quiz -> {
                    quiz.setQuesText(quizDetails.stream().filter(p -> p.getId().equals(quiz.getQuizId())).map(text -> text.getTitle()).collect(Collectors.joining()));
                });
            }
        }
        List<CategoryHierarchy> list = new ArrayList<>();
        if (subCategoryId != null && categoryId != null) {
            list = getCommunityList(httpServletRequest, categoryId);
        }
        return quizAnalyticsReportUtil.quizReportsModel(startDate, endDate, overallPostCount, this.getCommunityList(httpServletRequest, Constants.ZERO), commentsGraphCounts, model, postGraphCounts, topQuizs, participantGraphCounts, httpServletRequest, list);
    }
}
