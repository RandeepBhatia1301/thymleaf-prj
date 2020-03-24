package org.ril.hrss.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.ril.hrss.model.gamification.Level;
import org.ril.hrss.model.gamification.PointCategoryMaster;
import org.ril.hrss.model.gamification.PointsMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PointsUtil {
    public static String indexModel(Page<PointsMaster> pointsMasters, Model model) {
        model.addAttribute(Constants.POINTS_LIST, pointsMasters);
        model.addAttribute(Constants.PAGE, pointsMasters);
        model.addAttribute(Constants.BASE_MULTIPLIER, pointsMasters.getContent().get(0).getBaseMultiplier());
        return "points/points";
    }

    public static List<PointsMaster> updatePointsBaseMultiplier(HttpServletRequest httpServletRequest, List<PointsMaster> pointsMasters) {
        Integer multiplier = Integer.valueOf(httpServletRequest.getParameter(Constants.MULTIPLIER));
        List<PointsMaster> masterList = new ArrayList<>();
        for (PointsMaster pointsMaster : pointsMasters) {
            pointsMaster.setBaseMultiplier(multiplier);
            pointsMaster.setPoints(pointsMaster.getScore() * multiplier);
            masterList.add(pointsMaster);
        }
        return masterList;
    }

    public static Pageable paginate(HttpServletRequest httpServletRequest) {
        int page = httpServletRequest.getParameterMap().containsKey(Constants.PAGE) ? Integer.valueOf(httpServletRequest.getParameter(Constants.PAGE)) : Constants.ZERO;
        Integer perPage = Constants.PAGE_SIZE_FIFTY;
        return PageRequest.of(page, perPage);
    }

    public static String createModel(Model model, List<PointCategoryMaster> pointCategoryMasters) {
        model.addAttribute(Constants.POINTS_CATEGORY_LIST, pointCategoryMasters);
        return "points/add-points";
    }

    public static String editModel(Model model, Integer id, PointsMaster pointsMaster, List<PointCategoryMaster> pointCategoryMasters) {
        model.addAttribute(Constants.ID, id);
        model.addAttribute(Constants.ACTIVITY_DATA, pointsMaster);
        model.addAttribute(Constants.POINTS_CATEGORY_LIST, pointCategoryMasters);
        model.addAttribute(Constants.CATEGORY_ID, pointsMaster.getCategoryId());

        return "points/edit";
    }

    public static String createCategoryModel(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        Integer orgId = (Integer) httpServletRequest.getSession().getAttribute(Constants.ORG_ID);
        return "points/add-category";
    }

    public static PointCategoryMaster storeCategory(HttpServletRequest httpServletRequest) {
        PointCategoryMaster pointCategoryMaster = new PointCategoryMaster();
        pointCategoryMaster.setCategoryName(httpServletRequest.getParameter(Constants.NAME));
        return pointCategoryMaster;
    }

    public static Level setLevel(HttpServletRequest httpServletRequest) {
        Level l = new Level();
        l.setName(httpServletRequest.getParameter(Constants.LEVEL_NAME));
        l.setCounter(httpServletRequest.getParameterValues(Constants.LEVEL_VALUE));

        return l;
    }

    public static PointsMaster create(HttpServletRequest httpServletRequest) {
        Integer c1 = Integer.valueOf(httpServletRequest.getParameter(Constants.C1));
        Integer c2 = Integer.valueOf(httpServletRequest.getParameter(Constants.C2));
        Integer c3 = Integer.valueOf(httpServletRequest.getParameter(Constants.C3));
        Integer c4 = Integer.valueOf(httpServletRequest.getParameter(Constants.C4));
        Integer c5 = Integer.valueOf(httpServletRequest.getParameter(Constants.C5));
        Integer score = c1 + c2 + c3 + c4 + c5;
        Integer points = score * Constants.TEN;

        PointsMaster pointsMaster = new PointsMaster();
        pointsMaster.setCategoryId(Integer.valueOf(httpServletRequest.getParameter(Constants.CATEGORY)));
        pointsMaster.setActivity(httpServletRequest.getParameter(Constants.NAME));
        pointsMaster.setC1(c1);
        pointsMaster.setC2(c2);
        pointsMaster.setC3(c3);
        pointsMaster.setC4(c4);
        pointsMaster.setC5(c5);
        pointsMaster.setScore(score);
        pointsMaster.setPoints(points);
        pointsMaster.setCode(httpServletRequest.getParameter(Constants.CODE));
        return pointsMaster;

    }

    public static PointsMaster update(HttpServletRequest httpServletRequest, Optional<PointsMaster> pointsMasters) {

        PointsMaster pointsMaster = new PointsMaster();
        if (pointsMasters.isPresent()) {
            pointsMaster = pointsMasters.get();
        }
        Integer c1 = Integer.valueOf(httpServletRequest.getParameter(Constants.C1));
        Integer c2 = Integer.valueOf(httpServletRequest.getParameter(Constants.C2));
        Integer c3 = Integer.valueOf(httpServletRequest.getParameter(Constants.C3));
        Integer c4 = Integer.valueOf(httpServletRequest.getParameter(Constants.C4));
        Integer c5 = Integer.valueOf(httpServletRequest.getParameter(Constants.C5));
        Integer score = c1 + c2 + c3 + c4 + c5;
        Integer points = score * Constants.TEN;
        pointsMaster.setCategoryId(Integer.valueOf(httpServletRequest.getParameter(Constants.CATEGORY)));
        pointsMaster.setActivity(httpServletRequest.getParameter(Constants.NAME));
        pointsMaster.setC1(c1);
        pointsMaster.setC2(c2);
        pointsMaster.setC3(c3);
        pointsMaster.setC4(c4);
        pointsMaster.setC5(c5);
        pointsMaster.setScore(score);
        pointsMaster.setPoints(points);
        pointsMaster.setActivityMessage(httpServletRequest.getParameter(Constants.MESSAGE));
        return pointsMaster;
    }


    private static String objectWriter(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(PointsUtil.setLevel(httpServletRequest));
    }

    public static MultiValueMap upload() {
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap();
        multiValueMap.add(Constants.DIR_NAME, Constants.GAMIFICATION);
        return multiValueMap;
    }

}
