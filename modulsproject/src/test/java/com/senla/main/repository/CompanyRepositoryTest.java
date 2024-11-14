package com.senla.main.repository;

import com.senla.main.config.TestConfig;
import com.senla.main.model.Company;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EntityManager entityManager;

    private UUID companyId;

    @BeforeEach
    void setUp() {
        Company company = new Company();
        company.setName("TechCorp");
        entityManager.persist(company);

        companyId = company.getId();
    }

    @Test
    void testFindCompaniesByName() {
        List<Company> companies = companyRepository.findCompaniesByName("TechCorp");
        assertThat(companies).isNotEmpty();
        assertThat(companies.get(0).getName()).isEqualTo("TechCorp");
    }

    @Test
    void testFindByIdWithVacanciesAndRecruiters() {
        Company company = companyRepository.findByIdWithVacanciesAndRecruiters(companyId);
        assertThat(company).isNotNull();
    }
}
