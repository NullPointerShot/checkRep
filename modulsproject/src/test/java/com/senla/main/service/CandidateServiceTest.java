package com.senla.main.service;

import com.senla.main.dto.CandidateDTO;
import com.senla.main.exception.InvalidRequestException;
import com.senla.main.exception.NotFoundException;
import com.senla.main.mapper.CandidateMapper;
import com.senla.main.model.Candidate;
import com.senla.main.model.Profile;
import com.senla.main.repository.CandidateRepository;
import com.senla.main.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CandidateServiceTest {

    @InjectMocks
    private CandidateService candidateService;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private CandidateMapper candidateMapper;

    private CandidateDTO candidateDTO;
    private Candidate candidate;
    private Profile profile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        candidateDTO = new CandidateDTO();
        candidate = new Candidate();
        profile = new Profile();
        candidateDTO.setProfileId(UUID.randomUUID());
        candidate.setProfile(profile);
    }

    @Test
    void create_ShouldThrowInvalidRequestException_WhenCandidateDTOIsNull() {
        assertThrows(InvalidRequestException.class, () -> candidateService.create(null));
    }

    @Test
    void create_ShouldThrowNotFoundException_WhenProfileNotFound() {
        when(profileRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> candidateService.create(candidateDTO));
    }

    @Test
    void create_ShouldReturnCandidateDTO_WhenSuccessful() {
        when(profileRepository.findById(any())).thenReturn(Optional.of(profile));
        when(candidateMapper.toEntity(candidateDTO, profile)).thenReturn(candidate);
        when(candidateRepository.save(any())).thenReturn(candidate);
        when(candidateMapper.toDTO(candidate)).thenReturn(candidateDTO);

        CandidateDTO result = candidateService.create(candidateDTO);

        assertNotNull(result);
        verify(candidateRepository).save(any());
    }

    @Test
    void getById_ShouldThrowInvalidRequestException_WhenIdIsNull() {
        assertThrows(InvalidRequestException.class, () -> candidateService.getById(null));
    }

    @Test
    void getById_ShouldThrowNotFoundException_WhenCandidateNotFound() {
        UUID id = UUID.randomUUID();
        when(candidateRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> candidateService.getById(id));
    }

    @Test
    void getById_ShouldReturnCandidateDTO_WhenSuccessful() {
        UUID id = UUID.randomUUID();
        when(candidateRepository.findById(id)).thenReturn(Optional.of(candidate));
        when(candidateMapper.toDTO(candidate)).thenReturn(candidateDTO);

        CandidateDTO result = candidateService.getById(id);

        assertNotNull(result);
        verify(candidateRepository).findById(id);
    }

    @Test
    void update_ShouldThrowInvalidRequestException_WhenIdOrCandidateDTOIsNull() {
        assertThrows(InvalidRequestException.class, () -> candidateService.update(null, candidateDTO));
        assertThrows(InvalidRequestException.class, () -> candidateService.update(UUID.randomUUID(), null));
    }

    @Test
    void update_ShouldThrowNotFoundException_WhenCandidateNotFound() {
        UUID id = UUID.randomUUID();
        when(candidateRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> candidateService.update(id, candidateDTO));
    }

    @Test
    void update_ShouldReturnCandidateDTO_WhenSuccessful() {
        UUID id = UUID.randomUUID();

        when(candidateRepository.findById(id)).thenReturn(Optional.of(candidate));
        when(profileRepository.findById(any())).thenReturn(Optional.of(profile));
        when(candidateMapper.toDTO(candidate)).thenReturn(candidateDTO);
        doNothing().when(candidateMapper).updateEntityFromDTO(any(CandidateDTO.class), any(Candidate.class), any(Profile.class));
        when(candidateRepository.save(any())).thenReturn(candidate);

        CandidateDTO result = candidateService.update(id, candidateDTO);

        assertNotNull(result);
        verify(candidateRepository).save(any());
    }


    @Test
    void delete_ShouldThrowInvalidRequestException_WhenIdIsNull() {
        assertThrows(InvalidRequestException.class, () -> candidateService.delete(null));
    }

    @Test
    void delete_ShouldCallRepositoryDelete_WhenSuccessful() {
        UUID id = UUID.randomUUID();
        candidateService.delete(id);
        verify(candidateRepository).delete(id);
    }
}
