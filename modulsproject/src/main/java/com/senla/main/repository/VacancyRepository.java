package com.senla.main.repository;

import com.senla.main.dao.AbstractDao;
import com.senla.main.model.Vacancy;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class VacancyRepository extends AbstractDao<Vacancy, UUID> {

    public VacancyRepository() {
        super(Vacancy.class);
    }


    public List<Vacancy> findVacanciesByCompanyId(UUID companyId) {
        String jpql = "SELECT v FROM Vacancy v WHERE v.company.id = :companyId";
        TypedQuery<Vacancy> query = entityManager.createQuery(jpql, Vacancy.class);
        query.setParameter("companyId", companyId);
        return query.getResultList();
    }


    public List<Vacancy> findVacanciesByCompanyIdCriteria(UUID companyId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Vacancy> query = cb.createQuery(Vacancy.class);
        Root<Vacancy> vacancy = query.from(Vacancy.class);
        query.select(vacancy).where(cb.equal(vacancy.get("company").get("id"), companyId));
        return entityManager.createQuery(query).getResultList();
    }


    public Vacancy findByIdWithSkillsAndFeedbacks(UUID id) {
        String jpql = "SELECT v FROM Vacancy v " +
                "LEFT JOIN FETCH v.skills " +
                "LEFT JOIN FETCH v.feedbacks " +
                "WHERE v.id = :id";
        TypedQuery<Vacancy> query = entityManager.createQuery(jpql, Vacancy.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }


    public Vacancy findByIdWithSkillsAndFeedbacksCriteria(UUID id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Vacancy> query = cb.createQuery(Vacancy.class);
        Root<Vacancy> vacancy = query.from(Vacancy.class);

        vacancy.fetch("skills");
        vacancy.fetch("feedbacks");

        query.select(vacancy).where(cb.equal(vacancy.get("id"), id));
        TypedQuery<Vacancy> typedQuery = entityManager.createQuery(query);
        return typedQuery.getSingleResult();
    }


    public Vacancy findByIdWithSkillsAndFeedbacksEntityGraph(UUID id) {
        EntityGraph<Vacancy> entityGraph = entityManager.createEntityGraph(Vacancy.class);
        entityGraph.addAttributeNodes("skills", "feedbacks");

        return entityManager.find(Vacancy.class, id, Map.of("jakarta.persistence.fetchgraph", entityGraph));
    }
}
