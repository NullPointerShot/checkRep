package com.senla.main.repository;

import com.senla.main.dao.AbstractDao;
import com.senla.main.model.Feedback;
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
public class FeedbackRepository extends AbstractDao<Feedback, UUID> {

    public FeedbackRepository() {
        super(Feedback.class);
    }

    public List<Feedback> findFeedbacksByCandidateId(UUID candidateId) {
        String jpql = "SELECT f FROM Feedback f WHERE f.candidate.id = :candidateId";
        TypedQuery<Feedback> query = entityManager.createQuery(jpql, Feedback.class);
        query.setParameter("candidateId", candidateId);
        return query.getResultList();
    }

    public List<Feedback> findFeedbacksByCandidateIdCriteria(UUID candidateId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Feedback> query = cb.createQuery(Feedback.class);
        Root<Feedback> feedback = query.from(Feedback.class);
        query.select(feedback).where(cb.equal(feedback.get("candidate").get("id"), candidateId));
        return entityManager.createQuery(query).getResultList();
    }

    public Feedback findByIdWithCandidateAndVacancy(UUID id) {
        String jpql = "SELECT f FROM Feedback f " +
                "LEFT JOIN FETCH f.candidate " +
                "LEFT JOIN FETCH f.vacancy " +
                "WHERE f.id = :id";
        TypedQuery<Feedback> query = entityManager.createQuery(jpql, Feedback.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public Feedback findByIdWithCandidateAndVacancyCriteria(UUID id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Feedback> query = cb.createQuery(Feedback.class);
        Root<Feedback> feedback = query.from(Feedback.class);

        feedback.fetch("candidate");
        feedback.fetch("vacancy");

        query.select(feedback).where(cb.equal(feedback.get("id"), id));
        return entityManager.createQuery(query).getSingleResult();
    }

    public Feedback findByIdWithCandidateAndVacancyEntityGraph(UUID id) {
        EntityGraph<Feedback> entityGraph = entityManager.createEntityGraph(Feedback.class);
        entityGraph.addAttributeNodes("candidate", "vacancy");

        return entityManager.find(Feedback.class, id, Map.of("jakarta.persistence.fetchgraph", entityGraph));
    }
}
