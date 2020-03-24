package org.ril.hrss.repository;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class TagCustomImpl implements TagCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Long getTagFrequency(Class<?> classType, Integer id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<?> module = cq.from(classType);
        Predicate moduleStatusPredicate = cb.equal(module.get("tagId"), id);
        cq.select(cb.count(module));
        cq.where(moduleStatusPredicate);
        return em.createQuery(cq).getSingleResult();
    }
}
