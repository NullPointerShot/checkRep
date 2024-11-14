package com.senla.main.repository;

import com.senla.main.config.TestConfig;
import com.senla.main.model.Candidate;
import com.senla.main.model.Feedback;
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
public class FeedbackRepositoryTest {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private EntityManager entityManager;

    private UUID feedbackId;
    private UUID candidateId;

    @BeforeEach
    void setUp() {
        Candidate candidate = new Candidate();
        candidate.setExperience("5 years");
        entityManager.persist(candidate);
        candidateId = candidate.getId();

        Feedback feedback = new Feedback();
        feedback.setCandidate(candidate);
        entityManager.persist(feedback);
        feedbackId = feedback.getId();
    }

    @Test
    void testFindFeedbacksByCandidateId() {
        List<Feedback> feedbacks = feedbackRepository.findFeedbacksByCandidateId(candidateId);
        assertThat(feedbacks).isNotEmpty();
    }

    @Test
    void testFindByIdWithCandidateAndVacancy() {
        Feedback feedback = feedbackRepository.findByIdWithCandidateAndVacancy(feedbackId);
        assertThat(feedback).isNotNull();
        assertThat(feedback.getCandidate()).isNotNull();
    }
}
