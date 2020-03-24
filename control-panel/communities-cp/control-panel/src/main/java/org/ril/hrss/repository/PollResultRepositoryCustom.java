package org.ril.hrss.repository;

import org.ril.hrss.model.reports.PollParticipantsCount;
import org.ril.hrss.model.reports.TopPolls;

import java.util.Date;
import java.util.List;

public interface PollResultRepositoryCustom {
    public List<PollParticipantsCount> getTotalParticipants(Date startDate, Date endDate, List ids);

    public List<TopPolls> getOverallTopPollIds(Date startDate, Date endDate);

    public List<TopPolls> getFilteredTopPollIds(Date startDate, Date endDate, List<Long> pollIds);
}
