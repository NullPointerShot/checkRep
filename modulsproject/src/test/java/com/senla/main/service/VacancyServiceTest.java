package com.senla.main.service;

import com.senla.main.dto.VacancyDTO;
import com.senla.main.exception.InvalidRequestException;
import com.senla.main.exception.NotFoundException;
import com.senla.main.mapper.VacancyMapper;
import com.senla.main.model.Company;
import com.senla.main.model.Skill;
import com.senla.main.model.Vacancy;
import com.senla.main.repository.VacancyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VacancyServiceTest {

    @InjectMocks
    private VacancyService vacancyService;

    @Mock
    private VacancyRepository vacancyRepository;

    @Mock
    private VacancyMapper vacancyMapper;

    private VacancyDTO vacancyDTO;
    private Vacancy vacancy;
    private Company company;
    private Set<Skill> skills;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vacancyDTO = new VacancyDTO();
        vacancy = new Vacancy();
        company = new Company();
        skills = new HashSet<>();


        vacancyDTO.setId(UUID.randomUUID());
        vacancyDTO.setTitle("Software Engineer");
        vacancy.setId(vacancyDTO.getId());
        vacancy.setTitle(vacancyDTO.getTitle());
    }

    @Test
    void create_ShouldThrowInvalidRequestException_WhenVacancyDTOIsNull() {
        assertThrows(InvalidRequestException.class, () -> vacancyService.create(null, company, skills));
    }

    @Test
    void create_ShouldReturnVacancyDTO_WhenSuccessful() {
        when(vacancyMapper.toEntity(vacancyDTO, company, skills)).thenReturn(vacancy);
        when(vacancyRepository.save(any(Vacancy.class))).thenReturn(vacancy);
        when(vacancyMapper.toDTO(vacancy)).thenReturn(vacancyDTO);

        VacancyDTO result = vacancyService.create(vacancyDTO, company, skills);

        assertNotNull(result);
        verify(vacancyRepository).save(any(Vacancy.class));
    }

    @Test
    void getById_ShouldThrowInvalidRequestException_WhenIdIsNull() {
        assertThrows(InvalidRequestException.class, () -> vacancyService.getById(null));
    }

    @Test
    void getById_ShouldThrowNotFoundException_WhenVacancyNotFound() {
        UUID id = UUID.randomUUID();
        when(vacancyRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> vacancyService.getById(id));
    }

    @Test
    void getById_ShouldReturnVacancyDTO_WhenSuccessful() {
        UUID id = UUID.randomUUID();
        when(vacancyRepository.findById(id)).thenReturn(Optional.of(vacancy));
        when(vacancyMapper.toDTO(vacancy)).thenReturn(vacancyDTO);

        VacancyDTO result = vacancyService.getById(id);

        assertNotNull(result);
        verify(vacancyRepository).findById(id);
    }

    @Test
    void update_ShouldThrowInvalidRequestException_WhenIdOrVacancyDTOIsNull() {
        assertThrows(InvalidRequestException.class, () -> vacancyService.update(null, vacancyDTO, company, skills));
        assertThrows(InvalidRequestException.class, () -> vacancyService.update(UUID.randomUUID(), null, company, skills));
    }

    @Test
    void update_ShouldThrowNotFoundException_WhenVacancyNotFound() {
        UUID id = UUID.randomUUID();
        when(vacancyRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> vacancyService.update(id, vacancyDTO, company, skills));
    }

    @Test
    void update_ShouldReturnVacancyDTO_WhenSuccessful() {
        UUID id = UUID.randomUUID();
        when(vacancyRepository.findById(id)).thenReturn(Optional.of(vacancy));
        when(vacancyMapper.toDTO(vacancy)).thenReturn(vacancyDTO);
        doNothing().when(vacancyMapper).updateEntityFromDTO(any(VacancyDTO.class), any(Vacancy.class), any(), any());
        when(vacancyRepository.save(any(Vacancy.class))).thenReturn(vacancy);

        VacancyDTO result = vacancyService.update(id, vacancyDTO, company, skills);

        assertNotNull(result);
        verify(vacancyRepository).save(any(Vacancy.class));
    }

    @Test
    void delete_ShouldThrowInvalidRequestException_WhenIdIsNull() {
        assertThrows(InvalidRequestException.class, () -> vacancyService.delete(null));
    }

    @Test
    void delete_ShouldCallRepositoryDelete_WhenSuccessful() {
        UUID id = UUID.randomUUID();
        vacancyService.delete(id);
        verify(vacancyRepository).delete(id);
    }

    @Test
    void findAll_ShouldReturnCollectionOfVacancyDTOs_WhenSuccessful() {
        when(vacancyRepository.findAll()).thenReturn(List.of(vacancy));
        when(vacancyMapper.toDTO(vacancy)).thenReturn(vacancyDTO);

        Collection<VacancyDTO> result = vacancyService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findVacanciesByCompanyId_ShouldReturnCollectionOfVacancyDTOs_WhenSuccessful() {
        UUID companyId = UUID.randomUUID();
        when(vacancyRepository.findVacanciesByCompanyId(companyId)).thenReturn(List.of(vacancy));
        when(vacancyMapper.toDTO(vacancy)).thenReturn(vacancyDTO);

        Collection<VacancyDTO> result = vacancyService.findVacanciesByCompanyId(companyId);

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
