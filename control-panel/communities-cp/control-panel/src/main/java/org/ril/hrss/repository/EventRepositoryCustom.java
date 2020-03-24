package org.ril.hrss.repository;

import java.util.Date;
import java.util.List;

public interface EventRepositoryCustom {
    public List<Long> getOverallEvents(Integer categoryId, Date startDate, Date endDate, Integer subOrgId);

    public List<Long> getfilteredEvents(Integer categoryId, Integer subCategoryId, Date startDate, Date endDate, Integer subOrgId);
}
