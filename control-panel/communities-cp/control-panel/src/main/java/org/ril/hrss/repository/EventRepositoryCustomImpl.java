package org.ril.hrss.repository;

import org.ril.hrss.model.content.event.Event;
import org.ril.hrss.model.content.event.EventSubCategory;
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
public class EventRepositoryCustomImpl implements EventRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Long> getOverallEvents(Integer categoryId, Date startDate, Date endDate, Integer subOrgId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> data = builder.createQuery(Long.class);
        Root<Event> eventRoot = data.from(Event.class);
        data.select(eventRoot.get(Constants.ID));
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(eventRoot.get(Constants.SUB_ORG_ID), subOrgId));
        Predicate startDatePred = builder.between(eventRoot.<Date>get(Constants.EVENT_START_DATE), startDate, endDate);
        Predicate endDatePred = builder.between(eventRoot.<Date>get(Constants.EVENT_END_DATE), startDate, endDate);
        predicates.add(builder.or(startDatePred, endDatePred));
        if (categoryId != null && !categoryId.equals(Constants.ZERO)) {
            predicates.add(builder.equal(eventRoot.get(Constants.CATEGORY_ID), categoryId));
        }
        data.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Long> countsList = entityManager.createQuery(data);
        return countsList.getResultList();
    }

    @Override
    public List<Long> getfilteredEvents(Integer categoryId, Integer subCategoryId, Date startDate, Date endDate, Integer subOrgId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> data = builder.createQuery(Long.class);
        Root<Event> eventRoot = data.from(Event.class);
        Join<Event, EventSubCategory> root = eventRoot.join(Constants.EVENT_CATEGORY_HIERARCHY_LIST);
        data.select(eventRoot.get(Constants.ID));
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(eventRoot.get(Constants.SUB_ORG_ID), subOrgId));
        Predicate startDatePred = builder.between(eventRoot.<Date>get(Constants.EVENT_START_DATE), startDate, endDate);
        Predicate endDatePred = builder.between(eventRoot.<Date>get(Constants.EVENT_END_DATE), startDate, endDate);
        predicates.add(builder.or(startDatePred, endDatePred));
        if (categoryId != null && !categoryId.equals(Constants.ZERO)) {
            predicates.add(builder.equal(eventRoot.get(Constants.CATEGORY_ID), categoryId));
        }
        if (subCategoryId != null && !subCategoryId.equals(Constants.ZERO)) {
            predicates.add(builder.equal(root.get(Constants.SUB_CATEGORY_ID), subCategoryId));
        }
        data.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Long> countsList = entityManager.createQuery(data);
        return countsList.getResultList();
    }
}
