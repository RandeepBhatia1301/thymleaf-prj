package org.ril.hrss.repository;

import org.ril.hrss.model.language.Language;
import org.ril.hrss.model.language.OrgLanguage;
import org.ril.hrss.model.language.SubOrgLanguage;
import org.ril.hrss.utility.Constants;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

@Service
public class LanguageCustomImpl implements LanguageCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public String findLanguage(Integer id, HttpServletRequest httpServletRequest, Model model) {
        List<Predicate> predicates = new LinkedList<Predicate>();
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SAAS_ADMIN) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Language> cq = cb.createQuery(Language.class);
            Root<?> root = cq.from(Language.class);
            cq.where(cb.equal(root.get(Constants.ID), id));

            Language language = em.createQuery(cq).getSingleResult();
            model.addAttribute(Constants.CONTENT, language.getContent());
            model.addAttribute(Constants.LANGUAGE_DATA, language);

        } else if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.ORG_ADMIN) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<OrgLanguage> cq = cb.createQuery(OrgLanguage.class);
            Integer orgId = (Integer) httpServletRequest.getSession().getAttribute(Constants.ORG_ID);
            Root<?> root = cq.from(OrgLanguage.class);
            predicates.add(cb.equal(root.get(Constants.ORG_ID), orgId));
            predicates.add(cb.equal(root.get(Constants.ID), id));
            Predicate[] predArray = new Predicate[predicates.size()];
            predicates.toArray(predArray);
            cq.where(predArray);
            OrgLanguage orgLanguage = em.createQuery(cq).getSingleResult();
            model.addAttribute(Constants.CONTENT, orgLanguage.getContent());
            model.addAttribute(Constants.LANGUAGE_DATA, orgLanguage);
        } else {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<SubOrgLanguage> cq = cb.createQuery(SubOrgLanguage.class);
            Integer subOrgId = (Integer) httpServletRequest.getSession().getAttribute(Constants.SUB_ORG_ID);
            Root<?> root = cq.from(SubOrgLanguage.class);

            predicates.add(cb.equal(root.get("suborgId"), subOrgId));
            predicates.add(cb.equal(root.get(Constants.ID), id));
            Predicate[] predArray = new Predicate[predicates.size()];
            predicates.toArray(predArray);
            cq.where(predArray);
            SubOrgLanguage subOrgLanguage = em.createQuery(cq).getSingleResult();
            model.addAttribute(Constants.CONTENT, subOrgLanguage.getContent());
            model.addAttribute(Constants.LANGUAGE_DATA, subOrgLanguage);
        }
        return "language/edit";
    }

    @Override
    public String listLanguage(HttpServletRequest httpServletRequest, Model model) {
        List<Predicate> predicates = new LinkedList<Predicate>();
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SAAS_ADMIN) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Language> cq = cb.createQuery(Language.class);
            Root<Language> root = cq.from(Language.class);
            cq.select(root);
            List<Language> languages = em.createQuery(cq).getResultList();
            model.addAttribute(Constants.LANGUAGE_LIST, languages);
            model.addAttribute(Constants.PAGE, languages);

        } else if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.ORG_ADMIN) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<OrgLanguage> cq = cb.createQuery(OrgLanguage.class);
            Integer orgId = (Integer) httpServletRequest.getSession().getAttribute(Constants.ORG_ID);
            Root<?> root = cq.from(OrgLanguage.class);
            predicates.add(cb.equal(root.get(Constants.ORG_ID), orgId));
            Predicate[] predArray = new Predicate[predicates.size()];
            predicates.toArray(predArray);
            cq.where(predArray);
            List<OrgLanguage> orgLanguages = em.createQuery(cq).getResultList();
            model.addAttribute(Constants.LANGUAGE_LIST, orgLanguages);
        } else {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<SubOrgLanguage> cq = cb.createQuery(SubOrgLanguage.class);
            Integer subOrgId = (Integer) httpServletRequest.getSession().getAttribute(Constants.SUB_ORG_ID);
            Root<?> root = cq.from(SubOrgLanguage.class);

            predicates.add(cb.equal(root.get(Constants.SUBORGID), subOrgId));
            Predicate[] predArray = new Predicate[predicates.size()];
            predicates.toArray(predArray);
            cq.where(predArray);
            List<SubOrgLanguage> subOrgLanguages = em.createQuery(cq).getResultList();
            model.addAttribute(Constants.LANGUAGE_LIST, subOrgLanguages);
        }
        return "language/list-language";
    }

   /* public String updateLanguage(HttpServletRequest httpServletRequest, Model model) {
        List<Predicate> predicates = new LinkedList<Predicate>();
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SAAS_ADMIN) {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaUpdate<Language> update = cb.createCriteriaUpdate(Language.class);
            Root<Language> root = update.from(Language.class);
            language.setContent(jsonData);
            update.set("amount", newAmount);
            update.where(cb.greaterThanOrEqualTo(e.get("amount"), oldAmount));
            cq.select(root);

            model.addAttribute(Constants.LANGUAGE_LIST, languages);
            model.addAttribute(Constants.PAGE, languages);

        }*//* else if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.ORG_ADMIN) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<OrgLanguage> cq = cb.createQuery(OrgLanguage.class);
            Integer orgId = (Integer) httpServletRequest.getSession().getAttribute(Constants.ORG_ID);
            Root<?> root = cq.from(OrgLanguage.class);
            predicates.add(cb.equal(root.get(Constants.ORG_ID), orgId));
            Predicate[] predArray = new Predicate[predicates.size()];
            predicates.toArray(predArray);
            cq.where(predArray);
            List<OrgLanguage> orgLanguages = em.createQuery(cq).getResultList();
            model.addAttribute(Constants.LANGUAGE_LIST, orgLanguages);
        } else {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<SubOrgLanguage> cq = cb.createQuery(SubOrgLanguage.class);
            Integer subOrgId = (Integer) httpServletRequest.getSession().getAttribute(Constants.SUB_ORG_ID);
            Root<?> root = cq.from(SubOrgLanguage.class);

            predicates.add(cb.equal(root.get(Constants.SUB_ORG_ID), subOrgId));
            Predicate[] predArray = new Predicate[predicates.size()];
            predicates.toArray(predArray);
            cq.where(predArray);
            List<SubOrgLanguage> subOrgLanguages = em.createQuery(cq).getResultList();
            model.addAttribute(Constants.LANGUAGE_LIST, subOrgLanguages);
        }*//*
        return "language/list-language";
    }*/
}
