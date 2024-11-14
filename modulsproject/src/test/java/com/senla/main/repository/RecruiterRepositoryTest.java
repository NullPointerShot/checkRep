package com.senla.main.repository;

import com.senla.main.config.TestConfig;
import com.senla.main.model.Company;
import com.senla.main.model.Profile;
import com.senla.main.model.Recruiter;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
public class RecruiterRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RecruiterRepository recruiterRepository;

    private Recruiter recruiter;
    private Profile profile;
    private Company company;

    @BeforeEach
    void setUp() {
        profile = new Profile();
        profile.setName("John Doe");
        entityManager.persist(profile);

        company = new Company();
        company.setName("Tech Corp");
        entityManager.persist(company);

        recruiter = new Recruiter();
        recruiter.setProfile(profile);
        recruiter.setCompany(company);
        entityManager.persist(recruiter);
    }

    @Test
    void testFindAll() {
        assertThat(recruiterRepository.findAll()).containsExactly(recruiter);
    }

    @Test
    void testFindByIdWithProfileAndFeedbacksEntityGraph() {
        Recruiter foundRecruiter = recruiterRepository.findByIdWithProfileAndFeedbacksEntityGraph(recruiter.getId());
        assertThat(foundRecruiter).isNotNull();
        assertThat(foundRecruiter.getProfile()).isNotNull();
        assertThat(foundRecruiter.getCompany()).isNotNull();
    }

    @Test
    void testFindRecruitersByCompanyId() {
        assertThat(recruiterRepository.findRecruitersByCompanyId(recruiter.getCompany().getId())).containsExactly(recruiter);
    }

    @Test
    void testFindRecruitersByCompanyIdCriteria() {
        assertThat(recruiterRepository.findRecruitersByCompanyIdCriteria(recruiter.getCompany().getId())).containsExactly(recruiter);
    }
}
