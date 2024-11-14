package com.senla.main.service;

import com.senla.main.dto.JobOfferDTO;
import com.senla.main.exception.InvalidRequestException;
import com.senla.main.exception.NotFoundException;
import com.senla.main.mapper.JobOfferMapper;
import com.senla.main.model.JobOffer;
import com.senla.main.model.Candidate;
import com.senla.main.model.Vacancy;
import com.senla.main.repository.JobOfferRepository;
import com.senla.main.repository.CandidateRepository;
import com.senla.main.repository.VacancyRepository;
import com.senla.main.service.JobOfferService;
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

class JobOfferServiceTest {

    @Mock
    private JobOfferRepository jobOfferRepository;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private VacancyRepository vacancyRepository;

    @Mock
    private JobOfferMapper jobOfferMapper;

    @InjectMocks
    private JobOfferService jobOfferService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateJobOffer() {
        JobOfferDTO jobOfferDTO = new JobOfferDTO();


        Candidate candidate = new Candidate();
        Vacancy vacancy = new Vacancy();
        when(candidateRepository.findById(jobOfferDTO.getCandidateId())).thenReturn(Optional.of(candidate));
        when(vacancyRepository.findById(jobOfferDTO.getVacancyId())).thenReturn(Optional.of(vacancy));

        JobOffer jobOffer = new JobOffer();
        when(jobOfferMapper.toEntity(jobOfferDTO, candidate, vacancy)).thenReturn(jobOffer);
        when(jobOfferRepository.save(any(JobOffer.class))).thenReturn(jobOffer);
        when(jobOfferMapper.toDTO(jobOffer)).thenReturn(jobOfferDTO);

        JobOfferDTO createdJobOffer = jobOfferService.create(jobOfferDTO);

        verify(jobOfferRepository, times(1)).save(any(JobOffer.class));
        assertNotNull(createdJobOffer);
    }

    @Test
    void testGetById() {
        UUID jobOfferId = UUID.randomUUID();
        JobOffer jobOffer = new JobOffer();
        when(jobOfferRepository.findById(jobOfferId)).thenReturn(Optional.of(jobOffer));
        when(jobOfferMapper.toDTO(jobOffer)).thenReturn(new JobOfferDTO());

        JobOfferDTO foundJobOffer = jobOfferService.getById(jobOfferId);

        verify(jobOfferRepository, times(1)).findById(jobOfferId);
        assertNotNull(foundJobOffer);
    }

    @Test
    void testUpdateJobOffer() {
        UUID jobOfferId = UUID.randomUUID();
        JobOfferDTO jobOfferDTO = new JobOfferDTO();


        JobOffer existingJobOffer = new JobOffer();
        when(jobOfferRepository.findById(jobOfferId)).thenReturn(Optional.of(existingJobOffer));
        when(candidateRepository.findById(jobOfferDTO.getCandidateId())).thenReturn(Optional.of(new Candidate()));
        when(vacancyRepository.findById(jobOfferDTO.getVacancyId())).thenReturn(Optional.of(new Vacancy()));
        doNothing().when(jobOfferMapper).updateEntityFromDTO(jobOfferDTO, existingJobOffer, any(), any());
        when(jobOfferRepository.save(existingJobOffer)).thenReturn(existingJobOffer);
        when(jobOfferMapper.toDTO(existingJobOffer)).thenReturn(jobOfferDTO);

        JobOfferDTO updatedJobOffer = jobOfferService.update(jobOfferId, jobOfferDTO);

        verify(jobOfferRepository, times(1)).findById(jobOfferId);
        verify(jobOfferRepository, times(1)).save(existingJobOffer);
        assertNotNull(updatedJobOffer);
    }

    @Test
    void testDeleteJobOffer() {
        UUID jobOfferId = UUID.randomUUID();
        jobOfferService.delete(jobOfferId);

        verify(jobOfferRepository, times(1)).delete(jobOfferId);
    }
}
