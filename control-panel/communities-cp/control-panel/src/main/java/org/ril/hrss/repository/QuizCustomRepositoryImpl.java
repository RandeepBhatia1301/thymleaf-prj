package org.ril.hrss.repository;

import org.ril.hrss.model.content.quiz.Quiz;
import org.ril.hrss.model.content.quiz.QuizSubCategory;
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
public class QuizCustomRepositoryImpl implements QuizCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Long> getfilteredQuizs(Integer categoryId, Integer subCategoryId, Date startDate, Date endDate, Integer subOrgId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> data = builder.createQuery(Long.class);
        Root<Quiz> quizRoot = data.from(Quiz.class);
        Join<Quiz, QuizSubCategory> root = quizRoot.join(Constants.QUIZ_CATEGORY_HIERARCHY_LIST);
        data.select(quizRoot.get(Constants.ID));
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(quizRoot.get(Constants.SUB_ORG_ID), subOrgId));
        Predicate startDatePred = builder.between(quizRoot.<Date>get(Constants.START_DATE_TIME), startDate, endDate);
        Predicate endDatePred = builder.between(quizRoot.<Date>get(Constants.END_DATE_TIME), startDate, endDate);
        predicates.add(builder.or(startDatePred, endDatePred));
        if (categoryId != null && !categoryId.equals(Constants.ZERO)) {
            predicates.add(builder.equal(quizRoot.get(Constants.CATEGORY_ID), categoryId));
        }
        if (subCategoryId != null && !subCategoryId.equals(Constants.ZERO)) {
            predicates.add(builder.equal(root.get(Constants.SUB_CATEGORY_ID), subCategoryId));
        }
        data.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Long> countsList = entityManager.createQuery(data);
        return countsList.getResultList();
    }

    @Override
    public List<Long> getOverallQuizs(Integer categoryId, Date startDate, Date endDate, Integer subOrgId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> data = builder.createQuery(Long.class);
        Root<Quiz> pollRoot = data.from(Quiz.class);
        data.select(pollRoot.get(Constants.ID));
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(pollRoot.get(Constants.SUB_ORG_ID), subOrgId));
        Predicate startDatePred = builder.between(pollRoot.<Date>get(Constants.START_DATE_TIME), startDate, endDate);
        Predicate endDatePred = builder.between(pollRoot.<Date>get(Constants.END_DATE_TIME), startDate, endDate);
        predicates.add(builder.or(startDatePred, endDatePred));
        if (categoryId != null && !categoryId.equals(Constants.ZERO)) {
            predicates.add(builder.equal(pollRoot.get(Constants.CATEGORY_ID), categoryId));
        }
        data.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Long> countsList = entityManager.createQuery(data);
        return countsList.getResultList();
    }

}
