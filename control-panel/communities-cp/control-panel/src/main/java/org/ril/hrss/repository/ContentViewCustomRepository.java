package org.ril.hrss.repository;

import org.ril.hrss.model.TopBlog;
import org.ril.hrss.model.UserViewTimeBaseCount;
import org.ril.hrss.model.ViewTimeBaseCount;

import java.util.List;

public interface ContentViewCustomRepository {

    public List<ViewTimeBaseCount> getViewTimeBaseCountByContentIdBetweenStartAndEndDate(Integer contentTypeId, Integer subOrgId, String startDate, String endDate);

    public Integer getCountViewTimeBaseCountByContentIdBetweenStartAndEndDate(Integer contentTypeId, Integer subOrgId, String startDate, String endDate);

    public List<UserViewTimeBaseCount> getUserViewTimeBaseCountByContentIdBetweenStartAndEndDate(Integer contentTypeId, Integer subOrgId, String startDate, String endDate);

    public List<TopBlog> getTopViewByContentIdBetweenStartAndEndDate(Integer contentTypeId, Integer subOrgId, String startDate, String endDate);
}
