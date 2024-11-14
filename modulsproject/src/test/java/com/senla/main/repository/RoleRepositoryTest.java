package com.senla.main.repository;

import com.senla.main.config.TestConfig;
import com.senla.main.model.Role;
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
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        Role role = new Role();
        role.setName("Admin");
        entityManager.persist(role);
    }

    @Test
    void testFindByName() {
        List<Role> roles = roleRepository.findByName("Admin");
        assertThat(roles).isNotEmpty();
        Role role = roles.get(0);
        assertThat(role.getName()).isEqualTo("Admin");
    }

    @Test
    void testFindAll() {
        List<Role> roles = roleRepository.findAll();
        assertThat(roles).isNotEmpty();
    }
}
