package org.ril.hrss.repository;

import org.ril.hrss.model.category_hierarchy.CategorySubscription;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ControlPanelUtil;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;

import static org.ril.hrss.utility.AnalyticsReportUtil.getStartDateAndEndDateForSubscription;

@Service
public class ContentCustomImpl implements ContentCustom {

    @PersistenceContext
    private EntityManager em;

    private static Timestamp localToTimeStamp(LocalDate date) {
        return Timestamp.from(date.atStartOfDay().toInstant(ZoneOffset.UTC));
    }

    private long findUserSubscriptionCategoryHierarchy(HttpServletRequest httpServletRequest, LocalDate date, Integer communityId, Integer aoiId) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<CategorySubscription> root = criteriaQuery.from(CategorySubscription.class);
        List<Predicate> predicates = new LinkedList<Predicate>();
        predicates.add(criteriaBuilder.equal(criteriaBuilder.function(Constants.DATE_DB, Date.class, root.get(Constants.UPDATED_AT)), localToTimeStamp(date)));
        if (communityId != Constants.ZERO) {
            predicates.add(criteriaBuilder.equal(root.get(Constants.CATEGORY_HIERARCHY_ID), communityId));
        }
        predicates.add(criteriaBuilder.equal(root.get(Constants.SUB_ORG_ID), ControlPanelUtil.setSubOrgId(httpServletRequest)));
        predicates.add(criteriaBuilder.equal(root.get(Constants.STATUS), Constants.ONE));
        predicates.add(criteriaBuilder.equal(root.get(Constants.PARENT_ID), Constants.ZERO));
        criteriaQuery.select(criteriaBuilder.count(root));
        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
        return em.createQuery(criteriaQuery).getSingleResult();
    }

    private long findUserSubscriptionCategoryHierarchyAOI(HttpServletRequest httpServletRequest, LocalDate date, Integer communityId, Integer aoiId) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<CategorySubscription> root = criteriaQuery.from(CategorySubscription.class);
        List<Predicate> predicates = new LinkedList<Predicate>();
        predicates.add(criteriaBuilder.equal(criteriaBuilder.function(Constants.DATE_DB, Date.class, root.get(Constants.UPDATED_AT)), localToTimeStamp(date)));
        if (aoiId != Constants.ZERO) {
            predicates.add(criteriaBuilder.equal(root.get(Constants.CATEGORY_HIERARCHY_ID), aoiId));
        }
        predicates.add(criteriaBuilder.equal(root.get(Constants.SUB_ORG_ID), ControlPanelUtil.setSubOrgId(httpServletRequest)));
        predicates.add(criteriaBuilder.equal(root.get(Constants.STATUS), Constants.ONE));
        if (communityId != Constants.ZERO) {
            predicates.add(criteriaBuilder.equal(root.get(Constants.PARENT_ID), communityId));
        } else {
            predicates.add(criteriaBuilder.notEqual(root.get(Constants.PARENT_ID), Constants.ZERO));
        }
        criteriaQuery.select(criteriaBuilder.count(root));
        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
        return em.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public List<Map<Object, Object>> findUserSubscriptionTotal(HttpServletRequest httpServletRequest) {
        Integer communityId = Constants.ZERO;
        Integer aoiId = Constants.ZERO;
        if (httpServletRequest.getParameter(Constants.CATEGORY_NAME) != null) {
            communityId = Integer.valueOf(httpServletRequest.getParameter(Constants.CATEGORY_NAME));
        }
        if (httpServletRequest.getParameter(Constants.AOI_NAME) != null) {
            aoiId = Integer.valueOf(httpServletRequest.getParameter(Constants.AOI_NAME));
        }
        List<Map<Object, Object>> dataPoints1 = new ArrayList<Map<Object, Object>>();
        Map mapDate = getStartDateAndEndDateForSubscription(httpServletRequest);
        String fromDate = mapDate.get(Constants.START_DATE).toString();
        String toDate = mapDate.get(Constants.END_DATE).toString();
        if (httpServletRequest.getParameter(Constants.FROM_DATE) != null && httpServletRequest.getParameter(Constants.TO_DATE) != null) {
            fromDate = httpServletRequest.getParameter(Constants.FROM_DATE);
            toDate = httpServletRequest.getParameter(Constants.TO_DATE);
        }
        for (LocalDate date = LocalDate.parse(fromDate); !date.isAfter(LocalDate.parse(toDate)); date = date.plusDays(1)) {
            Map map = new HashMap();
            Long communityCount = this.findUserSubscriptionCategoryHierarchy(httpServletRequest, date, communityId, aoiId);
            Long aoiCount = this.findUserSubscriptionCategoryHierarchyAOI(httpServletRequest, date, communityId, aoiId);
            map.put(Constants.DATE_DB, date);
            map.put(Constants.COMMUNITY_COUNT, communityCount);
            map.put(Constants.AOI_COUNT, aoiCount);
            dataPoints1.add(map);
        }
        return dataPoints1;
    }

}
