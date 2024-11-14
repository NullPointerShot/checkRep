package com.senla.main.repository;

import com.senla.main.config.TestConfig;
import com.senla.main.model.Company;
import com.senla.main.model.Skill;
import com.senla.main.model.Vacancy;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
public class VacancyRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private VacancyRepository vacancyRepository;

    private Vacancy vacancy;
    private Company company;
    private Skill skill;

    @BeforeEach
    void setUp() {
        company = new Company();
        company.setName("Tech Corp");
        entityManager.persist(company);

        skill = new Skill();
        skill.setName("Java");
        entityManager.persist(skill);

        vacancy = new Vacancy();
        vacancy.setTitle("Software Engineer");
        vacancy.setSalary(50000);
        vacancy.setCompany(company);
        vacancy.setSkills(Set.of(skill));
        entityManager.persist(vacancy);
    }

    @Test
    void testFindAll() {
        assertThat(vacancyRepository.findAll()).containsExactly(vacancy);
    }

    @Test
    void testFindByIdWithSkillsAndFeedbacksEntityGraph() {
        Vacancy foundVacancy = vacancyRepository.findByIdWithSkillsAndFeedbacksEntityGraph(vacancy.getId());
        assertThat(foundVacancy).isNotNull();
        assertThat(foundVacancy.getSkills()).containsExactly(skill);
    }

    @Test
    void testFindVacanciesByCompanyId() {
        assertThat(vacancyRepository.findVacanciesByCompanyId(vacancy.getCompany().getId())).containsExactly(vacancy);
    }

    @Test
    void testFindVacanciesByCompanyIdCriteria() {
        assertThat(vacancyRepository.findVacanciesByCompanyIdCriteria(vacancy.getCompany().getId())).containsExactly(vacancy);
    }
}
