package com.senla.main.repository;

import com.senla.main.config.TestConfig;
import com.senla.main.model.Skill;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
public class SkillRepositoryTest {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private EntityManager entityManager;

    private Skill skill;

    @BeforeEach
    void setUp() {
        skill = new Skill();
        skill.setName("Spring");

        entityManager.persist(skill);
    }

    @Test
    void testFindById() {
        Skill foundSkill = skillRepository.findById(skill.getId()).orElse(null);
        assertThat(foundSkill).isNotNull();
        assertThat(foundSkill.getName()).isEqualTo("Spring");
    }

    @Test
    void testFindAll() {
        List<Skill> skills = skillRepository.findAll();
        assertThat(skills).isNotEmpty();
    }
}
