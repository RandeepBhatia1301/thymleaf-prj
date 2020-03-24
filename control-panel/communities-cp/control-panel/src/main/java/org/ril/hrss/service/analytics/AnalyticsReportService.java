package org.ril.hrss.service.analytics;

import org.ril.hrss.model.TopBlog;
import org.ril.hrss.model.category_hierarchy.CategoryHierarchy;
import org.ril.hrss.repository.*;
import org.ril.hrss.utility.AnalyticsReportUtil;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ControlPanelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class AnalyticsReportService {

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

    public String indexCommunities(HttpServletRequest httpServletRequest, Model model) {
        List<LinkedHashMap> contentTypeGraph = userActivityCustom.graphContentType(httpServletRequest);
        List<Map<Object, Object>> userSubscriptionGraph = contentCustom.findUserSubscriptionTotal(httpServletRequest);
        List topCommunityNames = userActivityCustom.getCommunityName(httpServletRequest);
        List topAOINames = userActivityCustom.getAOIName(httpServletRequest);
        List topContributorsNames = userActivityCustom.getUserName(httpServletRequest);
        Long totalPostCount = userActivityCustom.getTotalPostCount(httpServletRequest);
        return AnalyticsReportUtil.index(model, userSubscriptionGraph, topCommunityNames, topAOINames, getCommunityList(httpServletRequest, Constants.ZERO), topContributorsNames, totalPostCount, contentTypeGraph, httpServletRequest, getAOIListByParentId(httpServletRequest));
    }

    public String indexBlog(HttpServletRequest httpServletRequest, Model model) {
        Integer contentTypeId = 1;
        Integer subOrgId = ControlPanelUtil.setSubOrgId(httpServletRequest);
        Integer categoryId = null;
        Integer subCategoryId = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        String endDate = format.format(currentDate);
        calendar.add(Calendar.MONTH, -1);
        Date date1 = calendar.getTime();
        String startDate = format.format(date1);
        List<Integer> subCategoryIdList = new ArrayList<>();
        if (httpServletRequest.getParameter(Constants.FROM_DATE) != null) {
            startDate = httpServletRequest.getParameter(Constants.FROM_DATE);
        }
        if (httpServletRequest.getParameter(Constants.TO_DATE) != null) {
            endDate = httpServletRequest.getParameter(Constants.TO_DATE);
        }
        if ((null != httpServletRequest.getParameter(Constants.CATEGORY_NAME)) && (0 != Integer.valueOf(httpServletRequest.getParameter(Constants.CATEGORY_NAME)))) {
            categoryId = Integer.valueOf(httpServletRequest.getParameter(Constants.CATEGORY_NAME));
        }
        if (null != httpServletRequest.getParameter(Constants.AOI_NAME) && (0 != Integer.valueOf(httpServletRequest.getParameter(Constants.AOI_NAME)))) {
            subCategoryId = Integer.valueOf(httpServletRequest.getParameter(Constants.AOI_NAME));
            subCategoryIdList.add(subCategoryId);
        }
        List<String> activityTypeComment = new ArrayList<>();
        activityTypeComment.add(Constants.COMMENT);
        activityTypeComment.add(Constants.REPLIED);
        List<String> activityTypeRead = new ArrayList<>();
        activityTypeRead.add(Constants.READ);
        List<String> activityTypeView = new ArrayList<>();
        activityTypeView.add(Constants.VIEW);
        List<Map<String, Object>> countUser = analyticsReportUtil.getTimeBaseMap(userLogRepository.findCommentCountList(categoryId, subCategoryIdList, contentTypeId, activityTypeRead, startDate, endDate, subOrgId), userLogRepository.findCommentCountList(categoryId, subCategoryIdList, contentTypeId, activityTypeView, startDate, endDate, subOrgId), userLogRepository.getUserViewTimeBaseCountByContentIdBetweenStartAndEndDate(categoryId, subCategoryIdList, contentTypeId, subOrgId, activityTypeView, startDate, endDate), feedRepository.findOverAllCountList(categoryId, subCategoryIdList, contentTypeId, Arrays.asList(Constants.POST), startDate, endDate, subOrgId), userLogRepository.findCommentCountList(categoryId, subCategoryIdList, contentTypeId, activityTypeComment, startDate, endDate, subOrgId), startDate, endDate);
        List<TopBlog> topBlogList = userLogRepository.getTopCommentByContentIdBetweenStartAndEndDate(categoryId, subCategoryIdList, contentTypeId, subOrgId, activityTypeView, startDate, endDate);
        for (TopBlog ob : topBlogList) {
            ob.setName(blogRepository.getBlogTitleByBlogIds(ob.getBlogId()));
        }
        Date startDateFmt = AnalyticsReportUtil.getInitialDate(startDate + Constants.DATE_INITAIL_TIME);
        Date endDateFmt = AnalyticsReportUtil.getDayEndDate(endDate + Constants.DATE_INITAIL_TIME);

        long blogCount = feedRepository.getPostCount(Constants.POST, contentTypeId, startDateFmt, endDateFmt, categoryId, subCategoryIdList, subOrgId);
        Integer commentCount = userLogRepository.getCountCommentTimeBaseCountByContentIdBetweenStartAndEndDate(categoryId, subCategoryIdList, contentTypeId, subOrgId, activityTypeComment, startDate, endDate);
        List<Map<String, Object>> readRatio = analyticsReportUtil.getTimeBasePercentageMap(userLogRepository.findCommentCountList(categoryId, subCategoryIdList, contentTypeId, activityTypeRead, startDate, endDate, subOrgId), userLogRepository.findCommentCountList(categoryId, subCategoryIdList, contentTypeId, activityTypeView, startDate, endDate, subOrgId), startDate, endDate);
        List<CategoryHierarchy> list = new ArrayList<>();
        if (categoryId != null) {
            list = getCommunityList(httpServletRequest, categoryId);
        }
        return AnalyticsReportUtil.indexBlog(startDate, endDate, blogCount, commentCount, this.getCommunityList(httpServletRequest, Constants.ZERO), model, countUser, topBlogList, httpServletRequest, list, readRatio);
    }

    public String indexDiscussion(HttpServletRequest httpServletRequest, Model model) {
        Integer contentTypeId = 3;
        Integer subOrgId = ControlPanelUtil.setSubOrgId(httpServletRequest);
        Integer categoryId = null;
        Integer subCategoryId = null;
        //  Integer discussionCount;
        Integer commentCount;
        List<Map<String, Object>> countUser;
        List<TopBlog> topDiscussionList;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        String endDate = format.format(currentDate);
        calendar.add(Calendar.MONTH, -1);
        Date date1 = calendar.getTime();
        String startDate = format.format(date1);
        List<Integer> subCategoryIdList = new ArrayList<>();
        if (httpServletRequest.getParameter(Constants.FROM_DATE) != null) {
            startDate = httpServletRequest.getParameter(Constants.FROM_DATE);
        }
        if (httpServletRequest.getParameter(Constants.TO_DATE) != null) {
            endDate = httpServletRequest.getParameter(Constants.TO_DATE);
        }
        if ((null != httpServletRequest.getParameter(Constants.CATEGORY_NAME)) && (0 != Integer.valueOf(httpServletRequest.getParameter(Constants.CATEGORY_NAME)))) {
            categoryId = Integer.valueOf(httpServletRequest.getParameter(Constants.CATEGORY_NAME));
        }
        if (null != httpServletRequest.getParameter(Constants.AOI_NAME) && (0 != Integer.valueOf(httpServletRequest.getParameter(Constants.AOI_NAME)))) {
            subCategoryId = Integer.valueOf(httpServletRequest.getParameter(Constants.AOI_NAME));
            subCategoryIdList.add(subCategoryId);
        }
        List<String> activityTypeComment = new ArrayList<>();
        activityTypeComment.add(Constants.COMMENT);
        activityTypeComment.add(Constants.REPLIED);
        Date startDateFmt = AnalyticsReportUtil.getInitialDate(startDate + Constants.DATE_INITAIL_TIME);
        Date endDateFmt = AnalyticsReportUtil.getDayEndDate(endDate + Constants.DATE_INITAIL_TIME);

        long discussionCount = feedRepository.getPostCount(Constants.POST, contentTypeId, startDateFmt, endDateFmt, categoryId, subCategoryIdList, subOrgId);
        countUser = analyticsReportUtil.getTimeBaseMap(userLogRepository.findCommentCountList(categoryId, subCategoryIdList, contentTypeId, activityTypeComment, startDate, endDate, subOrgId), feedRepository.findOverAllCountList(categoryId, subCategoryIdList, contentTypeId, Arrays.asList(Constants.POST), startDate, endDate, subOrgId), startDate, endDate);
        commentCount = userLogRepository.getCountCommentTimeBaseCountByContentIdBetweenStartAndEndDate(categoryId, subCategoryIdList, contentTypeId, subOrgId, activityTypeComment, startDate, endDate);
        topDiscussionList = userLogRepository.getTopCommentByContentIdBetweenStartAndEndDate(categoryId, subCategoryIdList, contentTypeId, subOrgId, activityTypeComment, startDate, endDate);
        for (TopBlog ob : topDiscussionList) {
            ob.setName(discussionRepository.getDiscussionTitleByBlogIds(ob.getBlogId()));
        }
        List<CategoryHierarchy> list = new ArrayList<>();
        if (categoryId != null) {
            list = getCommunityList(httpServletRequest, categoryId);
        }
        return AnalyticsReportUtil.indexDiscussion(startDate, endDate, discussionCount, commentCount, this.getCommunityList(httpServletRequest, Constants.ZERO), model, countUser, topDiscussionList, httpServletRequest, list);
    }

    public List<CategoryHierarchy> getCommunityList(HttpServletRequest httpServletRequest, Integer parentId) {

        return categoryHierarchyRepository.findBySuborgIdAndParentId(ControlPanelUtil.setSubOrgId(httpServletRequest), parentId);
    }

    private List<CategoryHierarchy> getAOIListByParentId(HttpServletRequest httpServletRequest) {
        List<CategoryHierarchy> list = new ArrayList<>();
        if (httpServletRequest.getParameter(Constants.AOI_NAME) != null) {
            Integer communityId = Integer.valueOf(httpServletRequest.getParameter(Constants.CATEGORY_NAME));
            if (communityId != Constants.ZERO) {
                list = getCommunityList(httpServletRequest, communityId);
            }
        }
        return list;
    }
}
