package org.ril.hrss.utility;

import org.ril.hrss.model.TopBlog;
import org.ril.hrss.model.UserViewTimeBaseCount;
import org.ril.hrss.model.category_hierarchy.*;
import org.ril.hrss.model.reports.*;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class AnalyticsReportUtil {

    public static String indexBlog(String startDate, String endDate, Long blogCount, Integer commentCount, List<CategoryHierarchy> categoryHierarchies, Model model, List<Map<String, Object>> countUser, List<TopBlog> topBlogList, HttpServletRequest httpServletRequest, List<CategoryHierarchy> aoiListing, List<Map<String, Object>> readRatio) {
        model.addAttribute(Constants.DATA_POINT_USER, countUser);
        model.addAttribute(Constants.READ_RATIO, readRatio);
        model.addAttribute(Constants.CATEGORY_LIST, categoryHierarchies);
        model.addAttribute(Constants.TOP_BLOG, topBlogList);
        model.addAttribute(Constants.POST_COUNT, blogCount);
        model.addAttribute(Constants.COMMENT_COUNT, commentCount);
        //model.addAttribute("visitorRatio", visitorRatio);
        if (httpServletRequest.getParameter(Constants.CATEGORY_NAME) != null) {
            model.addAttribute(Constants.CURRENT_COMMUNITY, httpServletRequest.getParameter(Constants.CATEGORY_NAME));
        }
        if (httpServletRequest.getParameter(Constants.AOI_NAME) != null) {
            model.addAttribute(Constants.AOI_LIST, aoiListing);
            model.addAttribute(Constants.CURRENT_AOI, httpServletRequest.getParameter(Constants.AOI_NAME));
        }

        if (httpServletRequest.getParameter(Constants.FROM_DATE) != null) {
            model.addAttribute(Constants.CURRENT_START_DATE, httpServletRequest.getParameter(Constants.FROM_DATE));
        } else {
            model.addAttribute(Constants.CURRENT_START_DATE, startDate.replace(" 00:00:00.000", ""));
        }
        if (httpServletRequest.getParameter(Constants.TO_DATE) != null) {
            model.addAttribute(Constants.CURRENT_END_DATE, httpServletRequest.getParameter(Constants.TO_DATE));
        } else {
            model.addAttribute(Constants.CURRENT_END_DATE, endDate.replace(" 00:00:00.000", ""));
        }
        return "analytics/blog";
    }

    public static String indexDiscussion(String startDate, String endDate, Long discussionCount, Integer commentCount, List<CategoryHierarchy> categoryHierarchies, Model model, List<Map<String, Object>> countUser, List<TopBlog> topBlogList, HttpServletRequest httpServletRequest, List<CategoryHierarchy> aoiListing) {
        model.addAttribute(Constants.DATA_POINT_USER, countUser);
        model.addAttribute(Constants.CATEGORY_LIST, categoryHierarchies);
        model.addAttribute(Constants.TOP_DISCUSSION, topBlogList);
        model.addAttribute(Constants.POST_COUNT, discussionCount);
        model.addAttribute(Constants.COMMENT_COUNT, commentCount);

        if (httpServletRequest.getParameter(Constants.CATEGORY_NAME) != null) {
            model.addAttribute(Constants.CURRENT_COMMUNITY, httpServletRequest.getParameter(Constants.CATEGORY_NAME));
        }
        if (httpServletRequest.getParameter(Constants.AOI_NAME) != null) {
            model.addAttribute(Constants.AOI_LIST, aoiListing);
            model.addAttribute(Constants.CURRENT_AOI, httpServletRequest.getParameter(Constants.AOI_NAME));
        }

        if (httpServletRequest.getParameter(Constants.FROM_DATE) != null) {
            model.addAttribute(Constants.CURRENT_START_DATE, httpServletRequest.getParameter(Constants.FROM_DATE));
        } else {
            model.addAttribute(Constants.CURRENT_START_DATE, startDate.replace(" 00:00:00.000", ""));
        }
        if (httpServletRequest.getParameter(Constants.TO_DATE) != null) {
            model.addAttribute(Constants.CURRENT_END_DATE, httpServletRequest.getParameter(Constants.TO_DATE));
        } else {
            model.addAttribute(Constants.CURRENT_END_DATE, endDate.replace(" 00:00:00.000", ""));
        }
        return "analytics/discussion";
    }

    public static List<Map<Object, Object>> indexFiltered(Long countPoll, Long countDiscussion, Long countEvent, Long countQuiz, Long countBlog) {
        Map<Object, Object> map = new HashMap<>();
        Map<Object, Object> map1 = new HashMap<>();
        Map<Object, Object> map2 = new HashMap<>();
        Map<Object, Object> map3 = new HashMap<>();
        Map<Object, Object> map4 = new HashMap<>();

        List<Map<Object, Object>> dataPoints1 = new ArrayList<Map<Object, Object>>();

        map.put(Constants.LABEL, Constants.BLOG);
        map.put(Constants.Y_AXIS, countBlog);
        dataPoints1.add(map);

        map1.put(Constants.LABEL, Constants.POLL);
        map1.put(Constants.Y_AXIS, countPoll);
        dataPoints1.add(map1);


        map2.put(Constants.LABEL, Constants.DISCUSSION);
        map2.put(Constants.Y_AXIS, countDiscussion);
        dataPoints1.add(map2);

        map3.put(Constants.LABEL, Constants.EVENT);
        map3.put(Constants.Y_AXIS, countEvent);
        dataPoints1.add(map3);

        map4.put(Constants.LABEL, Constants.QUIZ);
        map4.put(Constants.Y_AXIS, countQuiz);
        dataPoints1.add(map4);
        return dataPoints1;

    }

    public static String index(Model model, List<Map<Object, Object>> countUser, List<TopCommunity> topCommunity, List<TopAOI> topAOI, List<CategoryHierarchy> categoryHierarchies, List<TopContributors> topContributors, Long postCount, List<LinkedHashMap> hashMapList, HttpServletRequest httpServletRequest, List<CategoryHierarchy> aoiListing) {
        model.addAttribute("dataPointsList", hashMapList);
        model.addAttribute("dataPointsUser", countUser);
        model.addAttribute("topCommunity", topCommunity);
        model.addAttribute("categoryList", categoryHierarchies);
        model.addAttribute("topAOI", topAOI);
        model.addAttribute("topContributors", topContributors);
        model.addAttribute("postCount", postCount);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date currentdate = calendar.getTime();
        String toDate = format.format(currentdate);

        calendar.add(Calendar.MONTH, -1);
        Date date1 = calendar.getTime();
        String fromDate = format.format(date1);
        model.addAttribute("currentStartDate", fromDate);
        model.addAttribute("currentEndDate", toDate);

        if (httpServletRequest.getParameter("categoryName") != null) {
            model.addAttribute("currentCommunity", httpServletRequest.getParameter("categoryName"));
        }
        if (httpServletRequest.getParameter("aoiName") != null) {
            model.addAttribute("aoiList", aoiListing);
            model.addAttribute("currentAOI", httpServletRequest.getParameter("aoiName"));
        }

        if (httpServletRequest.getParameter("fromDate") != null) {
            model.addAttribute("currentStartDate", httpServletRequest.getParameter("fromDate"));
        }
        if (httpServletRequest.getParameter("toDate") != null) {
            model.addAttribute("currentEndDate", httpServletRequest.getParameter("toDate"));
        }
        return "analytics/reports";

    }

    public static List<Map<Object, Object>> indexFilteredUser(Long countUser) {

        List<Map<Object, Object>> dataPointsUser = new ArrayList<Map<Object, Object>>();
        Map<Object, Object> mapUser = new HashMap<>();

        mapUser.put(Constants.LABEL, "User");
        mapUser.put(Constants.Y_AXIS, countUser);
        dataPointsUser.add(mapUser);
        return dataPointsUser;
    }

    public static Integer setDefault(List<CategoryHierarchy> categoryHierarchies) {
        Integer categoryId = 19;
        if (!categoryHierarchies.isEmpty()) {
            CategoryHierarchy categoryHierarchy = categoryHierarchies.get(0);
            categoryId = categoryHierarchy.getId();
        }
        return categoryId;
    }

    public static Date getInitialDate(String date) {
        if (date == null)
            return new Date();
        return manupulateDateByTime(getISTDateFormat(date), Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO);
    }

    public static Date getDayEndDate(String date) {
        if (date == null)
            return new Date();
        return manupulateDateByTime(getISTDateFormat(date), Constants.ZERO, Constants.MAX_SECONDS, Constants.MAX_MINUTS, Constants.MAX_HOURS);
    }

    private static Date manupulateDateByTime(Date date, Integer mSec, Integer sec, Integer min, Integer hrs) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
            calendar.set(Calendar.MILLISECOND, mSec);
            calendar.set(Calendar.SECOND, sec);
            calendar.set(Calendar.MINUTE, min);
            calendar.set(Calendar.HOUR_OF_DAY, hrs);
        }
        return calendar.getTime();
    }

    public static Date getISTDateFormat(String dateStr) {
        Date formattedDate = null;
        if (dateStr != null) {
            try {
                SimpleDateFormat istDateformatter = new SimpleDateFormat(Constants.DATE_TIME_PATTERN_SOAP);
                istDateformatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                formattedDate = istDateformatter.parse(dateStr);
            } catch (ParseException e) {
            }
        }
        return formattedDate;
    }

    public static MatchOperation createMatchCondition(Integer categoryId, Integer subCategoryId) {
        MatchOperation matchCondition = null;

        if (subCategoryId == Constants.ZERO) {
            matchCondition = Aggregation.match(Criteria.where(Constants.CATEGORY_ID).ne(null));
            if (categoryId != Constants.ZERO) {
                matchCondition = Aggregation.match(Criteria.where(Constants.CATEGORY_ID).is(categoryId));
            }
        } else {
            matchCondition = Aggregation.match(Criteria.where(Constants.SUB_CATEGORY_ID).in(subCategoryId));
        }
        return matchCondition;

    }

    public static Map getStartDateAndEndDate(HttpServletRequest httpServletRequest) {
        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT_ANALYTICS);
        Calendar calendar = Calendar.getInstance();
        Date currentdate = calendar.getTime();
        String currentdateStr = format.format(currentdate);
        calendar.add(Calendar.MONTH, Constants.MINUS_ONE);
        Date lastMonthDate = calendar.getTime();
        String lastMonthDateStr = format.format(lastMonthDate);
        Date startDate = getISTDateFormat(
                lastMonthDateStr + Constants.ZERO_HOUR);
        Date endDate = getISTDateFormat(
                currentdateStr + Constants.DAY_END_HOUR);
        if (httpServletRequest.getParameter(Constants.FROM_DATE) != null && httpServletRequest.getParameter(Constants.TO_DATE) != null) {
            startDate = getISTDateFormat(
                    httpServletRequest.getParameter(Constants.FROM_DATE) + Constants.ZERO_HOUR);
            endDate = getISTDateFormat(
                    httpServletRequest.getParameter(Constants.TO_DATE) + Constants.DAY_END_HOUR);
        }
        Map map = new HashMap();
        map.put(Constants.START_DATE, startDate);
        map.put(Constants.END_DATE, endDate);
        return map;
    }

    public static List<GraphJson> createGraphJsonForAllDates(List<String> dateSequence, List<GraphJson> graphJsons, List<Integer> contentTypeId) {
        List<GraphJson> graphJsons1 = new ArrayList<>();
        List<LinkedHashMap> linkedHashMap = new ArrayList<>();
        contentTypeId.forEach(integer -> {
            LinkedHashMap map1 = new LinkedHashMap();
            map1.put(Constants.CONTENT_TYPE_ID, integer);
            map1.put(Constants.COUNT, Constants.ZERO);
            linkedHashMap.add(map1);
        });
        dateSequence.forEach(dS -> {
            graphJsons.forEach(id -> {
                if (id.get_id().equals(dS)) {
                    List<String> ids = graphJsons1.stream()
                            .map(GraphJson::get_id).collect(Collectors.toList());
                    if (!ids.contains(dS)) {
                        graphJsons1.add(id);
                    }
                } else {
                    List<String> ids = graphJsons1.stream()
                            .map(GraphJson::get_id).collect(Collectors.toList());
                    List<String> ids1 = graphJsons.stream()
                            .map(GraphJson::get_id).collect(Collectors.toList());
                    if (!ids.contains(dS) && !ids1.contains(dS)) {
                        GraphJson graphJson = new GraphJson();
                        graphJson.set_id(dS);
                        graphJson.setResult(linkedHashMap);
                        graphJsons1.add(graphJson);
                    }
                }
            });
        });
        return graphJsons1;
    }

    public static List<LinkedHashMap> createListOfMap(List<GraphJson> graphJsons1) {
        List<LinkedHashMap> mapList = new ArrayList<>();
        graphJsons1.forEach(graphJson ->
        {
            LinkedHashMap map = new LinkedHashMap();
            map.put(Constants.DATE_DB, graphJson.get_id());
            List<LinkedHashMap> maps = graphJson.getResult();
            maps.forEach(hashMap -> {
                map.put(hashMap.get(Constants.CONTENT_TYPE_ID), hashMap.get(Constants.COUNT));
            });
            mapList.add(map);
        });
        return mapList;
    }

    public static List<LinkedHashMap> handleEmptyList(List<String> dateSequence, List<Integer> contentTypeId, List<LinkedHashMap> handleEmptyList) {
        dateSequence.forEach(s -> {
            LinkedHashMap map1 = new LinkedHashMap();
            map1.put(Constants.DATE_DB, s);
            contentTypeId.forEach(integer -> {
                map1.put("1", Constants.ZERO);
                map1.put("2", Constants.ZERO);
                map1.put("3", Constants.ZERO);
                map1.put("4", Constants.ZERO);
                map1.put("8", Constants.ZERO);
            });
            handleEmptyList.add(map1);
        });
        return handleEmptyList;
    }

    public static List<String> getDateSequenceForCommunitiesReport(String startDate, String endDate) {
        List<String> dateRange = new ArrayList<>();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.REPORT_DATE_FORMAT);
            //simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            String start = simpleDateFormat.format(simpleDateFormat.parse(startDate));
            String end = simpleDateFormat.format(simpleDateFormat.parse(endDate));
            LocalDate dStart = LocalDate.parse(start);
            while (!dStart.isEqual(LocalDate.parse(end))) {
                dateRange.add(dStart.toString());
                dStart = dStart.plusDays(Constants.ONE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dateRange;
    }

    public static Map getStartDateAndEndDateForSubscription(HttpServletRequest httpServletRequest) {
        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT_ANALYTICS);
        Calendar calendar = Calendar.getInstance();
        Date currentdate = calendar.getTime();
        String toDate = format.format(currentdate);
        calendar.add(Calendar.MONTH, Constants.MINUS_ONE);
        Date date1 = calendar.getTime();
        String fromDate = format.format(date1);
        Map map = new HashMap();
        map.put(Constants.START_DATE, fromDate);
        map.put(Constants.END_DATE, toDate);
        return map;
    }

    public String indexEvent(HttpServletRequest httpServletRequest, Model model, Long totalEvent, List<TopEvents> topEvents, EventCombinedCounts eventCombinedCounts, List<CategoryHierarchy> list, List<CategoryHierarchy> categoryHierarchies, List<AnalyticsContentCount> eventPostGraphPoints, String startDate, String endDate, List<AnalyticsContentCount> commentsGraphCount, List<EventParticipantsCount> totalReactionsGraphCounts, List<EventUserActionsCount> userActionsCounts) {

       /* model.addAttribute("dataPointsList", hashMapList);
        model.addAttribute("dataPointsUser", countUser);*/
        model.addAttribute("totalEvents", totalEvent);
        model.addAttribute("topEvents", topEvents);
        model.addAttribute("totalReactions", setDefaultZero(eventCombinedCounts.getReactions()));
        model.addAttribute("goingReaction", setDefaultZero(eventCombinedCounts.getGoing()));
        model.addAttribute("notGoingReaction", setDefaultZero(eventCombinedCounts.getNotGoing()));
        model.addAttribute("interestedReaction", setDefaultZero(eventCombinedCounts.getInterested()));
        model.addAttribute("categoryList", categoryHierarchies);
        List<EventGraphPoints> graphPoints = fetchGraphPoints(startDate, endDate, commentsGraphCount, eventPostGraphPoints, totalReactionsGraphCounts, userActionsCounts);
        model.addAttribute("dataPointsList", graphPoints);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date currentdate = calendar.getTime();
        String toDate = format.format(currentdate);

        calendar.add(Calendar.MONTH, -1);
        Date date1 = calendar.getTime();
        String fromDate = format.format(date1);
        model.addAttribute("currentStartDate", fromDate);
        model.addAttribute("currentEndDate", toDate);

        if (httpServletRequest.getParameter("categoryName") != null) {
            model.addAttribute("currentCommunity", httpServletRequest.getParameter("categoryName"));
        }
        if (httpServletRequest.getParameter("aoiName") != null) {
            model.addAttribute("aoiList", list);
            model.addAttribute("currentAOI", httpServletRequest.getParameter("aoiName"));
        }

        if (httpServletRequest.getParameter("fromDate") != null) {
            model.addAttribute("currentStartDate", httpServletRequest.getParameter("fromDate"));
        }
        if (httpServletRequest.getParameter("toDate") != null) {
            model.addAttribute("currentEndDate", httpServletRequest.getParameter("toDate"));
        }
        return "analytics/event-report";

    }

    private long setDefaultZero(Long count) {
        if (count != null) {
            return count;
        }
        return 0;
    }

    public String getPreviousMonthDate() {
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DATE, -Constants.THIRTY_DAYS);
        Date previousMonthDate = cal.getTime();
        String startDate = getFormattedDate(previousMonthDate);

        return startDate;
    }

    public String getFormattedDate(Date date) {
        String formattedDate = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.REPORT_DATE_FORMAT);
            formattedDate = dateFormat.format(date);
        } catch (Exception e) {

        }
        return formattedDate;
    }

    public List<String> getDateSequence(String startDate, String endDate) {
        List<String> dateRange = new ArrayList<>();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.REPORT_DATE_FORMAT);
            String start = simpleDateFormat.format(simpleDateFormat.parse(startDate));
            String end = simpleDateFormat.format(simpleDateFormat.parse(endDate));
            LocalDate dStart = LocalDate.parse(start);
            while (!dStart.isAfter(LocalDate.parse(end))) {
                dateRange.add(dStart.toString());
                dStart = dStart.plusDays(1);
            }
        } catch (Exception ex) {

        }
        return dateRange;
    }

    public List<Map<String, Object>> getTimeBaseMap(List<AnalyticsContentCount> readTimeBaseCounts, List<AnalyticsContentCount> viewTimeBaseCounts, List<UserViewTimeBaseCount> userViewTimeBaseCounts, List<AnalyticsContentCount> contentTimeBaseCounts, List<AnalyticsContentCount> commentTimeBaseCounts, String startDate, String endDate) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<String> dateRange = getDateSequence(startDate, endDate);
        List<String> readDataList = readTimeBaseCounts.stream().map(AnalyticsContentCount::getDate).collect(Collectors.toList());
        List<String> viewDataList = viewTimeBaseCounts.stream().map(AnalyticsContentCount::getDate).collect(Collectors.toList());
        List<String> userViewDataList = userViewTimeBaseCounts.stream().map(UserViewTimeBaseCount::getViewedAt).collect(Collectors.toList());
        List<String> contentDataList = contentTimeBaseCounts.stream().map(AnalyticsContentCount::getDate).collect(Collectors.toList());
        List<String> commentDataList = commentTimeBaseCounts.stream().map(AnalyticsContentCount::getDate).collect(Collectors.toList());
        for (String d : dateRange) {
            Map<String, Object> map = new HashMap<>();
            map.put("label", d);
            if (readDataList.contains(d)) {
                AnalyticsContentCount readTimeBaseCount = readTimeBaseCounts.get(readDataList.indexOf(d));
                map.put("x", readTimeBaseCount.getTotal());
            } else {
                map.put("x", 0);
            }
            if (viewDataList.contains(d)) {
                AnalyticsContentCount viewTimeBaseCount = viewTimeBaseCounts.get(viewDataList.indexOf(d));
                map.put("y", viewTimeBaseCount.getTotal());
            } else {
                map.put("y", 0);
            }
            if (userViewDataList.contains(d)) {
                UserViewTimeBaseCount userViewTimeBaseCount = userViewTimeBaseCounts.get(userViewDataList.indexOf(d));
                map.put("a", userViewTimeBaseCount.getTotal());
            } else {
                map.put("a", 0);
            }
            if (contentDataList.contains(d)) {
                AnalyticsContentCount contentTimeBaseCount = contentTimeBaseCounts.get(contentDataList.indexOf(d));
                map.put("b", contentTimeBaseCount.getTotal());
            } else {
                map.put("b", 0);
            }
            if (commentDataList.contains(d)) {
                AnalyticsContentCount commentTimeBaseCount = commentTimeBaseCounts.get(commentDataList.indexOf(d));
                map.put("z", commentTimeBaseCount.getTotal());
            } else {
                map.put("z", 0);
            }

            mapList.add(map);
        }
        return mapList;
    }

    public List<Map<String, Object>> getTimeBasePercentageMap(List<AnalyticsContentCount> readTimeBaseCounts, List<AnalyticsContentCount> viewTimeBaseCounts, String startDate, String endDate) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<String> dateRange = getDateSequence(startDate, endDate);
        List<String> readDataList = readTimeBaseCounts.stream().map(AnalyticsContentCount::getDate).collect(Collectors.toList());
        List<String> viewDataList = viewTimeBaseCounts.stream().map(AnalyticsContentCount::getDate).collect(Collectors.toList());
        for (String d : dateRange) {
            Map<String, Object> map = new HashMap<>();
            map.put("label", d);
            if (readDataList.contains(d) && viewDataList.contains(d)) {
                AnalyticsContentCount readTimeBaseCount = readTimeBaseCounts.get(readDataList.indexOf(d));
                AnalyticsContentCount viewTimeBaseCount = viewTimeBaseCounts.get(viewDataList.indexOf(d));
                map.put("readRatio", Math.round(readTimeBaseCount.getTotal().floatValue() * 100 / viewTimeBaseCount.getTotal().floatValue()));
            } else {
                map.put("readRatio", 0);
            }
            mapList.add(map);
        }
        return mapList;
    }

    public List<Map<String, Object>> getTimeBaseMap(List<AnalyticsContentCount> contentTimeBaseCounts, List<AnalyticsContentCount> commentTimeBaseCounts, String startDate, String endDate) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<String> dateRange = getDateSequence(startDate, endDate);
        List<String> contentDataList = contentTimeBaseCounts.stream().map(AnalyticsContentCount::getDate).collect(Collectors.toList());
        List<String> commentDataList = commentTimeBaseCounts.stream().map(AnalyticsContentCount::getDate).collect(Collectors.toList());
        for (String d : dateRange) {
            Map<String, Object> map = new HashMap<>();
            map.put("label", d);
            if (contentDataList.contains(d)) {
                AnalyticsContentCount contentTimeBaseCount = contentTimeBaseCounts.get(contentDataList.indexOf(d));
                map.put("x", contentTimeBaseCount.getTotal());
            } else {
                map.put("x", 0);
            }
            if (commentDataList.contains(d)) {
                AnalyticsContentCount commentTimeBaseCount = commentTimeBaseCounts.get(commentDataList.indexOf(d));
                map.put("y", commentTimeBaseCount.getTotal());
            } else {
                map.put("y", 0);
            }

            mapList.add(map);
        }
        return mapList;
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
            startDate = getPreviousMonthDate();
        }
        return startDate;
    }

    public String getEndDate(HttpServletRequest httpServletRequest) {
        String endDate;
        if (httpServletRequest.getParameter(Constants.TO_DATE) != null) {
            endDate = httpServletRequest.getParameter(Constants.TO_DATE);
        } else {
            endDate = getFormattedDate(new Date());
        }
        return endDate;
    }

    private List<EventGraphPoints> fetchGraphPoints(String startDate, String endDate, List<AnalyticsContentCount> commentsGraphCount, List<AnalyticsContentCount> postGraphCount, List<EventParticipantsCount> reactionsGraphCount, List<EventUserActionsCount> userActionsCounts) {
        List<EventGraphPoints> eventGraphPoints = new ArrayList<>();
        List<String> dateSequence = getDateSequence(startDate, endDate);
        dateSequence.stream().forEach(contentDate -> {
            EventGraphPoints eventGraphPoint = new EventGraphPoints();
            eventGraphPoint.setDate(contentDate);
            String commentCount = commentsGraphCount.stream().filter(c -> c.getDate().equals(contentDate)).map(c -> c.getTotal().toString()).collect(Collectors.joining(","));
            if (commentCount != null && !commentCount.isEmpty())
                eventGraphPoint.setCommentCount(Integer.parseInt(commentCount));
            else
                eventGraphPoint.setCommentCount(Constants.ZERO);
            String postCount = postGraphCount.stream().filter(c -> c.getDate().equals(contentDate)).map(c -> c.getTotal().toString()).collect(Collectors.joining(","));
            if (postCount != null && !postCount.isEmpty())
                eventGraphPoint.setPostCount(Integer.parseInt(postCount));
            else
                eventGraphPoint.setPostCount(Constants.ZERO);
            String reactionCount = reactionsGraphCount.stream().filter(c -> getFormattedDate(c.getDate()).equals(contentDate)).map(c -> String.valueOf(c.getTotal())).collect(Collectors.joining(","));
            if (reactionCount != null && !reactionCount.isEmpty())
                eventGraphPoint.setReactionsCount(Integer.parseInt(reactionCount));
            else
                eventGraphPoint.setReactionsCount(Constants.ZERO);
            String goingCount = userActionsCounts.stream().filter(c -> getFormattedDate(c.getDate()).equals(contentDate) && c.getUserAction().equals(Constants.GOING_STATUS)).map(c -> String.valueOf(c.getTotal())).collect(Collectors.joining(","));
            if (goingCount != null && !goingCount.isEmpty())
                eventGraphPoint.setGoingCount(Integer.parseInt(goingCount));
            else
                eventGraphPoint.setGoingCount(Constants.ZERO);
            String notGoingCount = userActionsCounts.stream().filter(c -> getFormattedDate(c.getDate()).equals(contentDate) && c.getUserAction().equals(Constants.NOT_GOING_STATUS)).map(c -> String.valueOf(c.getTotal())).collect(Collectors.joining(","));
            if (notGoingCount != null && !notGoingCount.isEmpty())
                eventGraphPoint.setNotGoingCount(Integer.parseInt(notGoingCount));
            else
                eventGraphPoint.setNotGoingCount(Constants.ZERO);
            String interestedCount = userActionsCounts.stream().filter(c -> getFormattedDate(c.getDate()).equals(contentDate) && c.getUserAction().equals(Constants.INTERESTED_STATUS)).map(c -> String.valueOf(c.getTotal())).collect(Collectors.joining(","));
            if (interestedCount != null && !interestedCount.isEmpty())
                eventGraphPoint.setInterestedCount(Integer.parseInt(interestedCount));
            else
                eventGraphPoint.setInterestedCount(Constants.ZERO);
            eventGraphPoints.add(eventGraphPoint);
        });
        return eventGraphPoints;
    }

    public List<Map<String, Object>> getViewVisitorAndReadRatio(List<AnalyticsContentCount> readTimeBaseCounts,
                                                                List<AnalyticsContentCount> totalViewTimeBaseCounts,
                                                                List<UserViewTimeBaseCount> uniqueUserViewCounts, String startDate, String endDate) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<String> dateRange = getDateSequence(startDate, endDate);
        List<String> readDataList = readTimeBaseCounts.stream().map(AnalyticsContentCount::getDate).collect(Collectors.toList());
        List<String> uniqueUserViewDataList =
                uniqueUserViewCounts.stream().map(UserViewTimeBaseCount::getViewedAt).collect(Collectors.toList());
        List<String> totalViewDataList = totalViewTimeBaseCounts.stream().map(AnalyticsContentCount::getDate).collect(Collectors.toList());
        for (String d : dateRange) {
            Map<String, Object> map = new HashMap<>();
            map.put("label", d);
            if (uniqueUserViewDataList.contains(d) && totalViewDataList.contains(d)) {
                UserViewTimeBaseCount uniqueUserViewCount = uniqueUserViewCounts.get(uniqueUserViewDataList.indexOf(d));
                AnalyticsContentCount totalViewTimeBaseCount = totalViewTimeBaseCounts.get(totalViewDataList.indexOf(d));
                map.put("visitorRatio",
                        Math.round(totalViewTimeBaseCount.getTotal() / uniqueUserViewCount.getTotal()));
            } else {
                map.put("visitorRatio", 0);
            }

            if (readDataList.contains(d) && totalViewDataList.contains(d)) {
                AnalyticsContentCount readTimeBaseCount = readTimeBaseCounts.get(readDataList.indexOf(d));
                AnalyticsContentCount viewTimeBaseCount = totalViewTimeBaseCounts.get(totalViewDataList.indexOf(d));
                map.put("readRatio", Math.round(readTimeBaseCount.getTotal().floatValue() * 100 / viewTimeBaseCount.getTotal().floatValue()));
            } else {
                map.put("readRatio", 0);
            }

            mapList.add(map);
        }
        return mapList;
    }

}
