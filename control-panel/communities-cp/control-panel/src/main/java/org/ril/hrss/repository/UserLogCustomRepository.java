package org.ril.hrss.repository;

import org.ril.hrss.model.CommentTimeBaseCount;
import org.ril.hrss.model.TopBlog;

import java.util.List;

public interface UserLogCustomRepository {

    public List<CommentTimeBaseCount> getUserCommentTimeBaseCountByContentIdBetweenStartAndEndDate(Integer contentTypeId, Integer subOrgId, String startDate, String endDate);

    public Integer getCountCommentTimeBaseCountByContentIdBetweenStartAndEndDate(Integer contentTypeId, Integer subOrgId, String startDate, String endDate);

    public List<TopBlog> getTopCommentByContentIdBetweenStartAndEndDate(Integer contentTypeId, Integer subOrgId, String startDate, String endDate);
}
