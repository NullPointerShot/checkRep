package com.senla.main.repository;

import com.senla.main.dao.AbstractDao;
import com.senla.main.model.Company;
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
public class CompanyRepository extends AbstractDao<Company, UUID> {

    public CompanyRepository() {
        super(Company.class);
    }

    public List<Company> findCompaniesByName(String name) {
        String jpql = "SELECT c FROM Company c WHERE c.name = :name";
        TypedQuery<Company> query = entityManager.createQuery(jpql, Company.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    public List<Company> findCompaniesByNameCriteria(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Company> query = cb.createQuery(Company.class);
        Root<Company> company = query.from(Company.class);
        query.select(company).where(cb.equal(company.get("name"), name));
        return entityManager.createQuery(query).getResultList();
    }

    public Company findByIdWithVacanciesAndRecruiters(UUID id) {
        String jpql = "SELECT c FROM Company c " +
                "LEFT JOIN FETCH c.vacancies " +
                "LEFT JOIN FETCH c.recruiters " +
                "WHERE c.id = :id";
        TypedQuery<Company> query = entityManager.createQuery(jpql, Company.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public Company findByIdWithVacanciesAndRecruitersCriteria(UUID id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Company> query = cb.createQuery(Company.class);
        Root<Company> company = query.from(Company.class);

        company.fetch("vacancies");
        company.fetch("recruiters");

        query.select(company).where(cb.equal(company.get("id"), id));
        return entityManager.createQuery(query).getSingleResult();
    }

    public Company findByIdWithVacanciesAndRecruitersEntityGraph(UUID id) {
        EntityGraph<Company> entityGraph = entityManager.createEntityGraph(Company.class);
        entityGraph.addAttributeNodes("vacancies", "recruiters");

        return entityManager.find(Company.class, id, Map.of("jakarta.persistence.fetchgraph", entityGraph));
    }
}
