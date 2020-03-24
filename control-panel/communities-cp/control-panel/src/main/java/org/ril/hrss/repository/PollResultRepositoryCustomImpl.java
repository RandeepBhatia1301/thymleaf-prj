package org.ril.hrss.repository;

import org.ril.hrss.model.content.poll.PollResult;
import org.ril.hrss.model.reports.PollParticipantsCount;
import org.ril.hrss.model.reports.TopPolls;
import org.ril.hrss.utility.Constants;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class PollResultRepositoryCustomImpl implements PollResultRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PollParticipantsCount> getTotalParticipants(Date startDate, Date endDate, List ids) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        List<PollParticipantsCount> participantsCounts;
        CriteriaQuery<PollParticipantsCount> query = builder.createQuery(PollParticipantsCount.class);
        Root<PollResult> c = query.from(PollResult.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(c.get(Constants.POLL_ID).in(ids));
        predicates.add(builder.between(c.<Date>get(Constants.CREATED_AT), startDate, endDate));
        query.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
        query.multiselect(c.get(Constants.CREATED_AT).as(java.sql.Date.class),
                builder.count(c.get(Constants.ID)).alias(Constants.TOTAL));
        query.groupBy(c.get(Constants.CREATED_AT).as(java.sql.Date.class));
        TypedQuery<PollParticipantsCount> countsList = entityManager.createQuery(query);
        participantsCounts = countsList.getResultList();
        return participantsCounts;
    }

    @Override
    public List<TopPolls> getOverallTopPollIds(Date startDate, Date endDate) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TopPolls> query = builder.createQuery(TopPolls.class);
        Root<PollResult> c = query.from(PollResult.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.between(c.<Date>get(Constants.CREATED_AT), startDate, endDate));
        query.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
        query.multiselect(c.get(Constants.POLL_ID), builder.count(c.get(Constants.ID)).alias(Constants.COUNT));
        query.groupBy(c.get(Constants.POLL_ID));
        List<Order> orders = new ArrayList<>();
        orders.add(builder.desc(builder.count(c.get(Constants.ID))));
        query.orderBy(orders);
        TypedQuery<TopPolls> countsList = entityManager.createQuery(query).setMaxResults(5);
        return countsList.getResultList();
    }

    @Override
    public List<TopPolls> getFilteredTopPollIds(Date startDate, Date endDate, List<Long> pollIds) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TopPolls> query = builder.createQuery(TopPolls.class);
        Root<PollResult> c = query.from(PollResult.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(c.get(Constants.POLL_ID).in(pollIds));
        predicates.add(builder.between(c.<Date>get(Constants.CREATED_AT), startDate, endDate));
        query.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
        query.multiselect(c.get(Constants.POLL_ID), builder.count(c.get(Constants.ID)).alias(Constants.COUNT));
        query.groupBy(c.get(Constants.POLL_ID));
        List<Order> orders = new ArrayList<>();
        orders.add(builder.desc(builder.count(c.get(Constants.ID))));
        query.orderBy(orders);
        TypedQuery<TopPolls> countsList = entityManager.createQuery(query).setMaxResults(5);
        return countsList.getResultList();
    }


}
