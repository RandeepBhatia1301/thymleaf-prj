package org.ril.hrss.repository;

import org.ril.hrss.model.reports.EventParticipantsCount;
import org.ril.hrss.model.reports.EventUserActionsCount;
import org.ril.hrss.model.reports.EventValueBasedActionsCount;
import org.ril.hrss.model.reports.TopEvents;

import java.util.Date;
import java.util.List;

public interface ContentParticipationRepositoryCustom {
    public List<EventParticipantsCount> fetchTotalReactionsCount(Integer contentTypeId, List<Integer> userActionList, List<Long> contentIdsList, Date startDate, Date endDate, Integer orgId, Integer subOrgId);

    public List<EventUserActionsCount> fetchTotalUserActionsCount(Integer contentTypeId, List<Integer> userActionList, List<Long> contentIdsList, Date startDate, Date endDate, Integer orgId, Integer subOrgId);

    public List<EventValueBasedActionsCount> fetchValueBasedUserActionsCount(Integer contentTypeId, List<Integer> userActionList, List<Long> contentIdsList, Date startDate, Date endDate, Integer orgId, Integer subOrgId);

    public Long fetchValueBasedReactionsCount(Integer contentTypeId, List<Integer> userActionList, List<Long> contentIdsList, Date startDate, Date endDate, Integer orgId, Integer subOrgId);

    public List<TopEvents> getFilteredTopEventIds(Date startDateFrmt, Date endDateFrmt, List<Long> eventIdsList, Integer orgId, Integer subOrgId, Integer contentTypeId);
}
