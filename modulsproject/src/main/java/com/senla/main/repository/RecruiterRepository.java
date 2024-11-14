package com.senla.main.repository;

import com.senla.main.dao.AbstractDao;
import com.senla.main.model.Recruiter;
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
public class RecruiterRepository extends AbstractDao<Recruiter, UUID> {

    public RecruiterRepository() {
        super(Recruiter.class);
    }


    public List<Recruiter> findRecruitersByCompanyId(UUID companyId) {
        String jpql = "SELECT r FROM Recruiter r WHERE r.company.id = :companyId";
        TypedQuery<Recruiter> query = entityManager.createQuery(jpql, Recruiter.class);
        query.setParameter("companyId", companyId);
        return query.getResultList();
    }


    public List<Recruiter> findRecruitersByCompanyIdCriteria(UUID companyId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recruiter> query = cb.createQuery(Recruiter.class);
        Root<Recruiter> recruiter = query.from(Recruiter.class);
        query.select(recruiter).where(cb.equal(recruiter.get("company").get("id"), companyId));
        return entityManager.createQuery(query).getResultList();
    }


    public Recruiter findByIdWithProfileAndFeedbacks(UUID id) {
        String jpql = "SELECT r FROM Recruiter r " +
                "LEFT JOIN FETCH r.profile " +
                "LEFT JOIN FETCH r.feedbacks " +
                "WHERE r.id = :id";
        TypedQuery<Recruiter> query = entityManager.createQuery(jpql, Recruiter.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }


    public Recruiter findByIdWithProfileAndFeedbacksCriteria(UUID id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recruiter> query = cb.createQuery(Recruiter.class);
        Root<Recruiter> recruiter = query.from(Recruiter.class);

        recruiter.fetch("profile");
        recruiter.fetch("feedbacks");

        query.select(recruiter).where(cb.equal(recruiter.get("id"), id));
        TypedQuery<Recruiter> typedQuery = entityManager.createQuery(query);
        return typedQuery.getSingleResult();
    }


    public Recruiter findByIdWithProfileAndFeedbacksEntityGraph(UUID id) {
        EntityGraph<Recruiter> entityGraph = entityManager.createEntityGraph(Recruiter.class);
        entityGraph.addAttributeNodes("profile", "feedbacks");

        return entityManager.find(Recruiter.class, id, Map.of("jakarta.persistence.fetchgraph", entityGraph));
    }
}
