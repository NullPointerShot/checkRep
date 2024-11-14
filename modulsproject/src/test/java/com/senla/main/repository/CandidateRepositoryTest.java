package com.senla.main.repository;

import com.senla.main.config.TestConfig;
import com.senla.main.model.Candidate;
import com.senla.main.model.Skill;
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
public class CandidateRepositoryTest {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private EntityManager entityManager;

    private UUID candidateId;

    @BeforeEach
    public void setUp() {
        Skill skill = new Skill();
        skill.setName("Java");
        entityManager.persist(skill);

        Candidate candidate = new Candidate();
        candidate.setExperience("5 years");
        candidate.getSkills().add(skill);
        entityManager.persist(candidate);
        entityManager.flush();

        candidateId = candidate.getId();
    }

    @Test
    public void testFindByExperience() {
        List<Candidate> candidates = candidateRepository.findByExperience("5 years");
        assertThat(candidates).isNotEmpty();
    }

    @Test
    public void testFindByIdWithSkillsAndProfile() {
        Candidate candidate = candidateRepository.findByIdWithSkillsAndProfile(candidateId);
        assertThat(candidate).isNotNull();
        assertThat(candidate.getSkills()).isNotEmpty();
    }

    @Test
    public void testFindBySkill() {
        List<Candidate> candidates = candidateRepository.findBySkill("Java");
        assertThat(candidates).isNotEmpty();
    }

    @Test
    public void testFindByIdWithSkillsAndProfileCriteria() {
        Candidate candidate = candidateRepository.findByIdWithSkillsAndProfileCriteria(candidateId);
        assertThat(candidate).isNotNull();
        assertThat(candidate.getSkills()).isNotEmpty();
    }

    @Test
    public void testFindByIdWithSkills() {
        Candidate candidate = candidateRepository.findByIdWithSkills(candidateId).orElse(null);
        assertThat(candidate).isNotNull();
        assertThat(candidate.getSkills()).isNotEmpty();
    }
}