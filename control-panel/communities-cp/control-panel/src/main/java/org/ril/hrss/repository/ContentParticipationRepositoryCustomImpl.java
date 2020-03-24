package org.ril.hrss.repository;

import org.ril.hrss.model.ContentParticipant;
import org.ril.hrss.model.reports.EventParticipantsCount;
import org.ril.hrss.model.reports.EventUserActionsCount;
import org.ril.hrss.model.reports.EventValueBasedActionsCount;
import org.ril.hrss.model.reports.TopEvents;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.ril.hrss.utility.Constants.*;


@Repository
@Transactional(readOnly = true)
public class ContentParticipationRepositoryCustomImpl implements ContentParticipationRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    public List<EventParticipantsCount> fetchTotalReactionsCount(Integer contentTypeId, List<Integer> userActionList, List<Long> contentIdsList, Date startDate, Date endDate, Integer orgId, Integer subOrgId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<EventParticipantsCount> userActionQuery = builder.createQuery(EventParticipantsCount.class);
        Root<ContentParticipant> userActionRoot = userActionQuery.from(ContentParticipant.class);
        List<Predicate> userActionPredicates = new ArrayList<>();
        userActionPredicates.add(builder.equal(userActionRoot.get(ORG_ID), orgId));
        userActionPredicates.add(builder.equal(userActionRoot.get(SUB_ORG_ID), subOrgId));
        userActionPredicates.add(builder.equal(userActionRoot.get(CONTENT_TYPE_ID), contentTypeId));
        if (contentIdsList != null && !contentIdsList.isEmpty()) {
            Expression<String> parentExpression = userActionRoot.get(CONTENT_ID);
            userActionPredicates.add(parentExpression.in(contentIdsList));
        }
        userActionPredicates.add(builder.between(userActionRoot.<Date>get(UPDATED_AT), startDate, endDate));
        userActionPredicates.add(userActionRoot.get(USER_ACTION).in(userActionList));
        userActionQuery.where(builder.and(userActionPredicates.toArray(new Predicate[userActionPredicates.size()])));
        userActionQuery.groupBy(userActionRoot.get(UPDATED_AT).as(java.sql.Date.class));
        return entityManager.createQuery(userActionQuery.multiselect(userActionRoot.get(UPDATED_AT).as(java.sql.Date.class),
                builder.count(userActionRoot.get(ID)).alias(TOTAL)))
                .getResultList();
    }

    public List<EventUserActionsCount> fetchTotalUserActionsCount(Integer contentTypeId, List<Integer> userActionList, List<Long> contentIdsList, Date startDate, Date endDate, Integer orgId, Integer subOrgId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<EventUserActionsCount> userActionQuery = builder.createQuery(EventUserActionsCount.class);
        Root<ContentParticipant> userActionRoot = userActionQuery.from(ContentParticipant.class);
        List<Predicate> userActionPredicates = new ArrayList<>();
        userActionPredicates.add(builder.equal(userActionRoot.get(ORG_ID), orgId));
        userActionPredicates.add(builder.equal(userActionRoot.get(SUB_ORG_ID), subOrgId));
        userActionPredicates.add(builder.equal(userActionRoot.get(CONTENT_TYPE_ID), contentTypeId));
        if (contentIdsList != null && !contentIdsList.isEmpty()) {
            Expression<String> parentExpression = userActionRoot.get(CONTENT_ID);
            userActionPredicates.add(parentExpression.in(contentIdsList));
        }
        userActionPredicates.add(builder.between(userActionRoot.<Date>get(UPDATED_AT), startDate, endDate));
        userActionPredicates.add(userActionRoot.get(USER_ACTION).in(userActionList));
        userActionQuery.where(builder.and(userActionPredicates.toArray(new Predicate[userActionPredicates.size()])));
        userActionQuery.groupBy(userActionRoot.get(UPDATED_AT).as(java.sql.Date.class), userActionRoot.get(USER_ACTION));
        return entityManager.createQuery(userActionQuery.multiselect(userActionRoot.get(UPDATED_AT).as(java.sql.Date.class), userActionRoot.get(USER_ACTION),
                builder.count(userActionRoot.get(ID)).alias(TOTAL)))
                .getResultList();
    }

    @Override
    public List<EventValueBasedActionsCount> fetchValueBasedUserActionsCount(Integer contentTypeId, List<Integer> userActionList, List<Long> contentIdsList, Date startDate, Date endDate, Integer orgId, Integer subOrgId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<EventValueBasedActionsCount> userActionQuery = builder.createQuery(EventValueBasedActionsCount.class);
        Root<ContentParticipant> userActionRoot = userActionQuery.from(ContentParticipant.class);
        List<Predicate> userActionPredicates = new ArrayList<>();
        userActionPredicates.add(builder.equal(userActionRoot.get(ORG_ID), orgId));
        userActionPredicates.add(builder.equal(userActionRoot.get(SUB_ORG_ID), subOrgId));
        userActionPredicates.add(builder.equal(userActionRoot.get(CONTENT_TYPE_ID), contentTypeId));
        if (contentIdsList != null && !contentIdsList.isEmpty()) {
            Expression<String> parentExpression = userActionRoot.get(CONTENT_ID);
            userActionPredicates.add(parentExpression.in(contentIdsList));
        }
        userActionPredicates.add(builder.between(userActionRoot.<Date>get(UPDATED_AT), startDate, endDate));
        userActionPredicates.add(userActionRoot.get(USER_ACTION).in(userActionList));
        userActionQuery.where(builder.and(userActionPredicates.toArray(new Predicate[userActionPredicates.size()])));
        userActionQuery.groupBy(userActionRoot.get(USER_ACTION));
        return entityManager.createQuery(userActionQuery.multiselect(userActionRoot.get(USER_ACTION),
                builder.count(userActionRoot.get(ID)).alias(TOTAL)))
                .getResultList();
    }

    @Override
    public Long fetchValueBasedReactionsCount(Integer contentTypeId, List<Integer> userActionList, List<Long> contentIdsList, Date startDate, Date endDate, Integer orgId, Integer subOrgId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> userActionQuery = builder.createQuery(Long.class);
        Root<ContentParticipant> userActionRoot = userActionQuery.from(ContentParticipant.class);
        List<Predicate> userActionPredicates = new ArrayList<>();
        userActionPredicates.add(builder.equal(userActionRoot.get(ORG_ID), orgId));
        userActionPredicates.add(builder.equal(userActionRoot.get(SUB_ORG_ID), subOrgId));
        userActionPredicates.add(builder.equal(userActionRoot.get(CONTENT_TYPE_ID), contentTypeId));
        if (contentIdsList != null && !contentIdsList.isEmpty()) {
            Expression<String> parentExpression = userActionRoot.get(CONTENT_ID);
            userActionPredicates.add(parentExpression.in(contentIdsList));
        }
        userActionPredicates.add(builder.between(userActionRoot.<Date>get(UPDATED_AT), startDate, endDate));
        userActionPredicates.add(userActionRoot.get(USER_ACTION).in(userActionList));
        userActionQuery.where(builder.and(userActionPredicates.toArray(new Predicate[userActionPredicates.size()])));
        return entityManager.createQuery(userActionQuery.select(builder.count(userActionRoot))).getSingleResult();
    }

    @Override
    public List<TopEvents> getFilteredTopEventIds(Date startDateFrmt, Date endDateFrmt, List<Long> eventIdsList, Integer orgId, Integer subOrgId, Integer contentTypeId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TopEvents> query = builder.createQuery(TopEvents.class);
        Root<ContentParticipant> c = query.from(ContentParticipant.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(c.get(CONTENT_ID).in(eventIdsList));
        predicates.add(builder.equal(c.get(ORG_ID), orgId));
        predicates.add(builder.equal(c.get(SUB_ORG_ID), subOrgId));
        predicates.add(builder.equal(c.get(USER_ACTION), GOING_STATUS));
        predicates.add(builder.equal(c.get(CONTENT_TYPE_ID), contentTypeId));
        predicates.add(builder.between(c.<Date>get(UPDATED_AT), startDateFrmt, endDateFrmt));
        query.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
        query.multiselect(c.get(CONTENT_ID), builder.count(c.get(ID)).alias(COUNT));
        query.groupBy(c.get(CONTENT_ID));
        List<Order> orders = new ArrayList<>();
        orders.add(builder.desc(builder.count(c.get(ID))));
        query.orderBy(orders);
        TypedQuery<TopEvents> countsList = entityManager.createQuery(query).setMaxResults(5);
        return countsList.getResultList();
    }
}
