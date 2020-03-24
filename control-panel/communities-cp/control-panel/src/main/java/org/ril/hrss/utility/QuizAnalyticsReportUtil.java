package org.ril.hrss.utility;

import org.ril.hrss.model.category_hierarchy.CategoryHierarchy;
import org.ril.hrss.model.reports.AnalyticsContentCount;
import org.ril.hrss.model.reports.QuizGraphPoints;
import org.ril.hrss.model.reports.QuizParticipantsCount;
import org.ril.hrss.model.reports.TopQuizs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuizAnalyticsReportUtil {

    @Autowired
    private AnalyticsReportUtil analyticsReportUtil;

    public String quizReportsModel(String startDate, String endDate, long countQuiz,
                                   List<CategoryHierarchy> categoryHierarchies,
                                   List<AnalyticsContentCount> commentsGraphCount, Model model,
                                   List<AnalyticsContentCount> postGraphCount,
                                   List<TopQuizs> topQuiz, List<QuizParticipantsCount> participantGraphCount,
                                   HttpServletRequest httpServletRequest, List<CategoryHierarchy> aoiListing) {

        List<QuizGraphPoints> graphPoints = fetchGraphPoints(startDate, endDate, commentsGraphCount, postGraphCount, participantGraphCount);
        model.addAttribute("overallPosts", countQuiz);
        model.addAttribute("postTimeBasePoints", graphPoints);
        model.addAttribute("categoryList", categoryHierarchies);
        model.addAttribute("topQuiz", topQuiz);
        model.addAttribute("currentStartDate", startDate);
        model.addAttribute("currentEndDate", endDate);
        if (httpServletRequest.getParameter(Constants.CATEGORY_NAME) != null) {
            model.addAttribute("currentCommunity", httpServletRequest.getParameter(Constants.CATEGORY_NAME));
        }
        if (httpServletRequest.getParameter("aoiName") != null) {
            model.addAttribute("aoiList", aoiListing);
            model.addAttribute("currentAOI", httpServletRequest.getParameter("aoiName"));
        }

        if (httpServletRequest.getParameter(Constants.FROM_DATE) != null) {
            model.addAttribute("currentStartDate", httpServletRequest.getParameter(Constants.FROM_DATE));
        }
        if (httpServletRequest.getParameter(Constants.TO_DATE) != null) {
            model.addAttribute("currentEndDate", httpServletRequest.getParameter(Constants.TO_DATE));
        }
        return "analytics/quiz-reports";
    }

    private List<QuizGraphPoints> fetchGraphPoints(String startDate, String endDate, List<AnalyticsContentCount> commentsGraphCount, List<AnalyticsContentCount> postGraphCount, List<QuizParticipantsCount> participantsGraphCount) {
        List<QuizGraphPoints> quizGraphPoints = new ArrayList<>();
        List<String> dateSequence = analyticsReportUtil.getDateSequence(startDate, endDate);
        dateSequence.stream().forEach(contentDate -> {
            QuizGraphPoints quizGraphPoint = new QuizGraphPoints();
            quizGraphPoint.setDate(contentDate);
            String commentCount = commentsGraphCount.stream().filter(c -> c.getDate().equals(contentDate)).map(c -> c.getTotal().toString()).collect(Collectors.joining(","));
            if (commentCount != null && !commentCount.isEmpty())
                quizGraphPoint.setCommentCount(Integer.parseInt(commentCount));
            else
                quizGraphPoint.setCommentCount(Constants.ZERO);
            String postCount = postGraphCount.stream().filter(c -> c.getDate().equals(contentDate)).map(c -> c.getTotal().toString()).collect(Collectors.joining(","));
            if (postCount != null && !postCount.isEmpty())
                quizGraphPoint.setPostCount(Integer.parseInt(postCount));
            else
                quizGraphPoint.setPostCount(Constants.ZERO);

            String participantsCount = participantsGraphCount.stream().filter(c -> analyticsReportUtil.getFormattedDate(c.getDate()).equals(contentDate)).map(c -> String.valueOf(c.getTotal())).collect(Collectors.joining(","));
            if (participantsCount != null && !participantsCount.isEmpty())
                quizGraphPoint.setParticipantCount(Integer.parseInt(participantsCount));
            else
                quizGraphPoint.setParticipantCount(Constants.ZERO);
            quizGraphPoints.add(quizGraphPoint);
        });
        return quizGraphPoints;
    }

    public Integer getCategoryId(HttpServletRequest httpServletRequest) {
        Integer categoryId = null;
        if (httpServletRequest.getParameter(Constants.CATEGORY_NAME) != null) {
            categoryId = Integer.valueOf(httpServletRequest.getParameter(Constants.CATEGORY_NAME));
        }
        return categoryId;
    }

    public Integer getSubCategoryId(HttpServletRequest httpServletRequest) {
        Integer subCategoryId = null;
        if (httpServletRequest.getParameter("aoiName") != null) {
            subCategoryId = Integer.valueOf(httpServletRequest.getParameter("aoiName"));
        }
        return subCategoryId;
    }

    public String getStartDate(HttpServletRequest httpServletRequest) {
        String startDate;
        if (httpServletRequest.getParameter(Constants.FROM_DATE) != null) {
            startDate = httpServletRequest.getParameter(Constants.FROM_DATE);
        } else {
            startDate = analyticsReportUtil.getPreviousMonthDate();
        }
        return startDate;
    }

    public String getEndDate(HttpServletRequest httpServletRequest) {
        String endDate;
        if (httpServletRequest.getParameter(Constants.TO_DATE) != null) {
            endDate = httpServletRequest.getParameter(Constants.TO_DATE);
        } else {
            endDate = analyticsReportUtil.getFormattedDate(new Date());
        }
        return endDate;
    }
}
