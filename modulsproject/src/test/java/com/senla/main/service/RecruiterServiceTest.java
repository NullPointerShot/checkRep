package com.senla.main.service;

import com.senla.main.dto.RecruiterDTO;
import com.senla.main.exception.InvalidRequestException;
import com.senla.main.exception.NotFoundException;
import com.senla.main.mapper.RecruiterMapper;
import com.senla.main.model.Company;
import com.senla.main.model.Profile;
import com.senla.main.model.Recruiter;
import com.senla.main.repository.CompanyRepository;
import com.senla.main.repository.ProfileRepository;
import com.senla.main.repository.RecruiterRepository;
import com.senla.main.service.RecruiterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RecruiterServiceTest {

    @InjectMocks
    private RecruiterService recruiterService;

    @Mock
    private RecruiterRepository recruiterRepository;

    @Mock
    private RecruiterMapper recruiterMapper;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private CompanyRepository companyRepository;

    private Recruiter recruiter;
    private RecruiterDTO recruiterDTO;
    private Profile profile;
    private Company company;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        UUID id = UUID.randomUUID();
        recruiter = new Recruiter();
        recruiter.setId(id);
        recruiterDTO = new RecruiterDTO();
        recruiterDTO.setProfileId(UUID.randomUUID());
        recruiterDTO.setCompanyId(UUID.randomUUID());
        profile = new Profile();
        company = new Company();
    }

    @Test
    void create_ShouldCreateRecruiter_WhenRecruiterDTOIsValid() {
        when(profileRepository.findById(recruiterDTO.getProfileId())).thenReturn(Optional.of(profile));
        when(companyRepository.findById(recruiterDTO.getCompanyId())).thenReturn(Optional.of(company));
        when(recruiterMapper.toEntity(recruiterDTO, profile, company)).thenReturn(recruiter);
        when(recruiterRepository.save(any(Recruiter.class))).thenReturn(recruiter);
        when(recruiterMapper.toDTO(recruiter)).thenReturn(recruiterDTO);

        RecruiterDTO createdRecruiter = recruiterService.create(recruiterDTO);

        assertEquals(recruiterDTO, createdRecruiter);
        verify(recruiterRepository).save(any(Recruiter.class));
    }

    @Test
    void create_ShouldThrowInvalidRequestException_WhenRecruiterDTOIsNull() {
        assertThrows(InvalidRequestException.class, () -> recruiterService.create(null));
    }

    @Test
    void getById_ShouldReturnRecruiterDTO_WhenRecruiterExists() {
        when(recruiterRepository.findByIdWithProfileAndFeedbacksEntityGraph(any(UUID.class))).thenReturn(recruiter);
        when(recruiterMapper.toDTO(recruiter)).thenReturn(recruiterDTO);

        RecruiterDTO result = recruiterService.getById(recruiter.getId());

        assertEquals(recruiterDTO, result);
    }

    @Test
    void getById_ShouldThrowNotFoundException_WhenRecruiterDoesNotExist() {
        when(recruiterRepository.findByIdWithProfileAndFeedbacksEntityGraph(any(UUID.class))).thenReturn(null);
        assertThrows(NotFoundException.class, () -> recruiterService.getById(UUID.randomUUID()));
    }

    @Test
    void update_ShouldUpdateRecruiter_WhenRecruiterExists() {
        when(recruiterRepository.findById(recruiter.getId())).thenReturn(Optional.of(recruiter));
        when(profileRepository.findById(recruiterDTO.getProfileId())).thenReturn(Optional.of(profile));
        when(companyRepository.findById(recruiterDTO.getCompanyId())).thenReturn(Optional.of(company));
        when(recruiterMapper.toDTO(recruiter)).thenReturn(recruiterDTO);

        RecruiterDTO updatedRecruiter = recruiterService.update(recruiter.getId(), recruiterDTO);

        assertEquals(recruiterDTO, updatedRecruiter);
        verify(recruiterRepository).save(recruiter);
    }

    @Test
    void update_ShouldThrowNotFoundException_WhenRecruiterDoesNotExist() {
        when(recruiterRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> recruiterService.update(UUID.randomUUID(), recruiterDTO));
    }

    @Test
    void delete_ShouldDeleteRecruiter_WhenRecruiterExists() {
        UUID id = recruiter.getId();
        when(recruiterRepository.findById(id)).thenReturn(Optional.of(recruiter));

        recruiterService.delete(id);

        verify(recruiterRepository).delete(id);
    }

    @Test
    void delete_ShouldThrowInvalidRequestException_WhenIdIsNull() {
        assertThrows(InvalidRequestException.class, () -> recruiterService.delete(null));
    }

    @Test
    void findAll_ShouldReturnAllRecruiters() {
        when(recruiterRepository.findAll()).thenReturn(List.of(recruiter));
        when(recruiterMapper.toDTO(recruiter)).thenReturn(recruiterDTO);

        Collection<RecruiterDTO> result = recruiterService.findAll();

        assertEquals(1, result.size());
        assertEquals(recruiterDTO, result.iterator().next());
    }
}
