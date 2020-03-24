package org.ril.hrss.repository;

import org.ril.hrss.model.reports.QuizParticipantsCount;
import org.ril.hrss.model.reports.TopQuizs;

import java.util.Date;
import java.util.List;

public interface UserSessionCustomRepository {
    public List<QuizParticipantsCount> getTotalParticipants(Date startDate, Date endDate, List ids);

    public List<TopQuizs> getFilteredTopQuizIds(Date startDate, Date endDate, List<Long> quizIds);
}
