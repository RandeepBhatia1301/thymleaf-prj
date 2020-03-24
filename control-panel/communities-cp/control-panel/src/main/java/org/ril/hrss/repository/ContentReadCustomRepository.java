package org.ril.hrss.repository;

import org.ril.hrss.model.ReadTimeBaseCount;

import java.util.List;

public interface ContentReadCustomRepository {

    public List<ReadTimeBaseCount> getReadTimeBaseCountByContentIdBetweenStartAndEndDate(Integer contentTypeId, Integer subOrgId, String startDate, String endDate);

    public Integer getCountReadTimeBaseCountByContentIdBetweenStartAndEndDate(Integer contentTypeId, Integer subOrgId, String startDate, String endDate);
}
