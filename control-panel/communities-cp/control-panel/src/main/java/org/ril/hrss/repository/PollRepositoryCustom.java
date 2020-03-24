package org.ril.hrss.repository;

import java.util.Date;
import java.util.List;

public interface PollRepositoryCustom {
    public List<Long> getOverallPolls(Integer categoryId, Date startDate, Date endDate, Integer subOrgId);

    public List<Long> getfilteredPolls(Integer categoryId, Integer subCategoryId, Date startDate, Date endDate, Integer subOrgId);
}
