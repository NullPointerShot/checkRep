package com.senla.main.repository;

import com.senla.main.config.TestConfig;
import com.senla.main.model.Profile;
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
public class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private EntityManager entityManager;

    private Profile profile;

    @BeforeEach
    void setUp() {
        profile = new Profile();
        profile.setName("Jane Doe");
        profile.setPassword("password");

        entityManager.persist(profile);
    }

    @Test
    void testFindById() {
        Profile foundProfile = profileRepository.findById(profile.getId()).orElse(null);
        assertThat(foundProfile).isNotNull();
        assertThat(foundProfile.getName()).isEqualTo("Jane Doe");
    }

    @Test
    void testFindAll() {
        List<Profile> profiles = profileRepository.findAll();
        assertThat(profiles).isNotEmpty();
    }
}
