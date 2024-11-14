package com.senla.main.repository;

import com.senla.main.config.TestConfig;
import com.senla.main.model.JobOffer;
import com.senla.main.model.Vacancy;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
public class JobOfferRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private JobOfferRepository jobOfferRepository;

    private JobOffer jobOffer;
    private Vacancy vacancy;

    @BeforeEach
    void setUp() {
        vacancy = new Vacancy();
        vacancy.setTitle("Software Engineer");
        vacancy.setSalary(50000);
        entityManager.persist(vacancy);

        jobOffer = new JobOffer();
        jobOffer.setVacancy(vacancy);
        jobOffer.setSalary(60000);
        jobOffer.setStatus("Accepted");
        entityManager.persist(jobOffer);
    }

    @Test
    void testFindAll() {
        assertThat(jobOfferRepository.findAll()).containsExactly(jobOffer);
    }

    @Test
    void testFindById() {
        JobOffer foundJobOffer = jobOfferRepository.findById(jobOffer.getId()).orElse(null);
        assertThat(foundJobOffer).isNotNull();
        assertThat(foundJobOffer.getSalary()).isEqualTo(60000);
    }

    @Test
    void testSave() {
        JobOffer newJobOffer = new JobOffer();
        newJobOffer.setVacancy(vacancy);
        newJobOffer.setSalary(70000);
        newJobOffer.setStatus("Pending");
        jobOfferRepository.save(newJobOffer);

        assertThat(jobOfferRepository.findAll()).contains(newJobOffer);
    }

    @Test
    void testDelete() {
        jobOfferRepository.delete(jobOffer.getId());
        assertThat(jobOfferRepository.findById(jobOffer.getId()).isEmpty()).isTrue();
    }
}
