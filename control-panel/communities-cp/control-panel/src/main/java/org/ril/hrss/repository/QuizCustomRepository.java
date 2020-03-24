package org.ril.hrss.repository;

import java.util.Date;
import java.util.List;

public interface QuizCustomRepository {
    public List<Long> getfilteredQuizs(Integer categoryId, Integer subCategoryId, Date startDate, Date endDate, Integer subOrgId);

    public List<Long> getOverallQuizs(Integer categoryId, Date startDate, Date endDate, Integer subOrgId);
}
