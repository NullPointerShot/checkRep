package com.senla.main.repository;

import com.senla.main.dao.AbstractDao;
import com.senla.main.model.Candidate;
import com.senla.main.model.Skill;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CandidateRepository extends AbstractDao<Candidate, UUID> {

    public CandidateRepository() {
        super(Candidate.class);
    }


    public List<Candidate> findByExperience(String experience) {
        String jpql = "SELECT c FROM Candidate c WHERE c.experience = :experience";
        return entityManager.createQuery(jpql, Candidate.class)
                .setParameter("experience", experience)
                .getResultList();
    }


    public Candidate findByIdWithSkillsAndProfile(UUID id) {
        String jpql = "SELECT c FROM Candidate c " +
                "LEFT JOIN FETCH c.skills " +
                "LEFT JOIN FETCH c.profile " +
                "WHERE c.id = :id";
        TypedQuery<Candidate> query = entityManager.createQuery(jpql, Candidate.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }


    public List<Candidate> findBySkill(String skillName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Candidate> cq = cb.createQuery(Candidate.class);
        Root<Candidate> candidate = cq.from(Candidate.class);
        Join<Candidate, Skill> skills = candidate.join("skills");
        cq.select(candidate).where(cb.equal(skills.get("name"), skillName));
        TypedQuery<Candidate> query = entityManager.createQuery(cq);
        return query.getResultList();
    }


    public Candidate findByIdWithSkillsAndProfileCriteria(UUID id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Candidate> cq = cb.createQuery(Candidate.class);
        Root<Candidate> candidate = cq.from(Candidate.class);

        candidate.fetch("skills", JoinType.LEFT);
        candidate.fetch("profile", JoinType.LEFT);

        cq.select(candidate).where(cb.equal(candidate.get("id"), id));
        TypedQuery<Candidate> query = entityManager.createQuery(cq);
        return query.getSingleResult();
    }


    public Optional<Candidate> findByIdWithSkills(UUID id) {
        EntityGraph<Candidate> entityGraph = entityManager.createEntityGraph(Candidate.class);
        entityGraph.addAttributeNodes("skills");

        Candidate candidate = entityManager.find(Candidate.class, id,
                Map.of("jakarta.persistence.fetchgraph", entityGraph));
        return Optional.ofNullable(candidate);
    }
}
