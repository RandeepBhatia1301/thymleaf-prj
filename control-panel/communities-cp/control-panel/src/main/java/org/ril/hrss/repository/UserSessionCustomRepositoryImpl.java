package org.ril.hrss.repository;

import org.ril.hrss.model.content.quiz.UserSession;
import org.ril.hrss.model.reports.QuizParticipantsCount;
import org.ril.hrss.model.reports.TopQuizs;
import org.ril.hrss.utility.Constants;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UserSessionCustomRepositoryImpl implements UserSessionCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<QuizParticipantsCount> getTotalParticipants(Date startDate, Date endDate, List ids) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<QuizParticipantsCount> query = builder.createQuery(QuizParticipantsCount.class);
        Root<UserSession> c = query.from(UserSession.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(c.get(Constants.QUIZ_ID).in(ids));
        predicates.add(builder.between(c.<Date>get(Constants.SUBMIT_TIME_DATE), startDate, endDate));
        query.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
        query.multiselect(c.get(Constants.SUBMIT_TIME_DATE).as(java.sql.Date.class),
                builder.count(c.get(Constants.ID)).alias(Constants.TOTAL));
        query.groupBy(c.get(Constants.SUBMIT_TIME_DATE).as(java.sql.Date.class));
        TypedQuery<QuizParticipantsCount> countsList = entityManager.createQuery(query);
        return countsList.getResultList();
    }

    @Override
    public List<TopQuizs> getFilteredTopQuizIds(Date startDate, Date endDate, List<Long> quizIds) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TopQuizs> query = builder.createQuery(TopQuizs.class);
        Root<UserSession> c = query.from(UserSession.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(c.get(Constants.QUIZ_ID).in(quizIds));
        predicates.add(builder.between(c.<Date>get(Constants.SUBMIT_TIME_DATE), startDate, endDate));
        query.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
        query.multiselect(c.get(Constants.QUIZ_ID), builder.count(c.get(Constants.ID)).alias(Constants.COUNT));
        query.groupBy(c.get(Constants.QUIZ_ID));
        List<Order> orders = new ArrayList<>();
        orders.add(builder.desc(builder.count(c.get(Constants.ID))));
        query.orderBy(orders);
        TypedQuery<TopQuizs> countsList = entityManager.createQuery(query).setMaxResults(5);
        return countsList.getResultList();
    }

}
