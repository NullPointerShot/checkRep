package com.senla.main.service;

import com.senla.main.dto.FeedbackDTO;
import com.senla.main.exception.InvalidRequestException;
import com.senla.main.exception.NotFoundException;
import com.senla.main.mapper.FeedbackMapper;
import com.senla.main.model.Feedback;
import com.senla.main.model.Candidate;
import com.senla.main.model.Vacancy;
import com.senla.main.model.Recruiter;
import com.senla.main.repository.FeedbackRepository;
import com.senla.main.repository.CandidateRepository;
import com.senla.main.repository.VacancyRepository;
import com.senla.main.repository.RecruiterRepository;
import com.senla.main.service.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FeedbackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private VacancyRepository vacancyRepository;

    @Mock
    private RecruiterRepository recruiterRepository;

    @Mock
    private FeedbackMapper feedbackMapper;

    @InjectMocks
    private FeedbackService feedbackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateFeedback() {
        FeedbackDTO feedbackDTO = new FeedbackDTO();


        Candidate candidate = new Candidate();
        Vacancy vacancy = new Vacancy();
        Recruiter recruiter = new Recruiter();
        when(candidateRepository.findById(feedbackDTO.getCandidateId())).thenReturn(Optional.of(candidate));
        when(vacancyRepository.findById(feedbackDTO.getVacancyId())).thenReturn(Optional.of(vacancy));
        when(recruiterRepository.findById(feedbackDTO.getRecruiterId())).thenReturn(Optional.of(recruiter));

        Feedback feedback = new Feedback();
        when(feedbackMapper.toEntity(feedbackDTO, candidate, recruiter, vacancy)).thenReturn(feedback);
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);
        when(feedbackMapper.toDTO(feedback)).thenReturn(feedbackDTO);

        FeedbackDTO createdFeedback = feedbackService.create(feedbackDTO);

        verify(feedbackRepository, times(1)).save(any(Feedback.class));
        assertNotNull(createdFeedback);
    }

    @Test
    void testGetById() {
        UUID feedbackId = UUID.randomUUID();
        Feedback feedback = new Feedback();
        when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.of(feedback));
        when(feedbackMapper.toDTO(feedback)).thenReturn(new FeedbackDTO());

        FeedbackDTO foundFeedback = feedbackService.getById(feedbackId);

        verify(feedbackRepository, times(1)).findById(feedbackId);
        assertNotNull(foundFeedback);
    }

    @Test
    void testUpdateFeedback() {
        UUID feedbackId = UUID.randomUUID();
        FeedbackDTO feedbackDTO = new FeedbackDTO();


        Feedback existingFeedback = new Feedback();
        when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.of(existingFeedback));
        when(candidateRepository.findById(feedbackDTO.getCandidateId())).thenReturn(Optional.of(new Candidate()));
        when(vacancyRepository.findById(feedbackDTO.getVacancyId())).thenReturn(Optional.of(new Vacancy()));
        when(recruiterRepository.findById(feedbackDTO.getRecruiterId())).thenReturn(Optional.of(new Recruiter()));
        doNothing().when(feedbackMapper).updateEntityFromDTO(feedbackDTO, existingFeedback, any(), any(), any());
        when(feedbackRepository.save(existingFeedback)).thenReturn(existingFeedback);
        when(feedbackMapper.toDTO(existingFeedback)).thenReturn(feedbackDTO);

        FeedbackDTO updatedFeedback = feedbackService.update(feedbackId, feedbackDTO);

        verify(feedbackRepository, times(1)).findById(feedbackId);
        verify(feedbackRepository, times(1)).save(existingFeedback);
        assertNotNull(updatedFeedback);
    }

    @Test
    void testDeleteFeedback() {
        UUID feedbackId = UUID.randomUUID();
        feedbackService.delete(feedbackId);

        verify(feedbackRepository, times(1)).delete(feedbackId);
    }
}
