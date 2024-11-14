package com.senla.main.service;

import com.senla.main.dto.CompanyDTO;
import com.senla.main.exception.InvalidRequestException;
import com.senla.main.exception.NotFoundException;
import com.senla.main.mapper.CompanyMapper;
import com.senla.main.model.Company;
import com.senla.main.repository.CompanyRepository;
import com.senla.main.service.CompanyService;
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

class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private CompanyMapper companyMapper;

    @InjectMocks
    private CompanyService companyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCompany() {
        CompanyDTO companyDTO = new CompanyDTO();


        Company company = new Company();
        when(companyMapper.toEntity(companyDTO)).thenReturn(company);
        when(companyRepository.save(any(Company.class))).thenReturn(company);
        when(companyMapper.toDTO(company)).thenReturn(companyDTO);

        CompanyDTO createdCompany = companyService.create(companyDTO);

        verify(companyRepository, times(1)).save(any(Company.class));
        assertNotNull(createdCompany);
    }

    @Test
    void testGetById() {
        UUID companyId = UUID.randomUUID();
        Company company = new Company();
        when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));
        when(companyMapper.toDTO(company)).thenReturn(new CompanyDTO());

        CompanyDTO foundCompany = companyService.getById(companyId);

        verify(companyRepository, times(1)).findById(companyId);
        assertNotNull(foundCompany);
    }

    @Test
    void testUpdateCompany() {
        UUID companyId = UUID.randomUUID();
        CompanyDTO companyDTO = new CompanyDTO();

        Company existingCompany = new Company();
        when(companyRepository.findById(companyId)).thenReturn(Optional.of(existingCompany));
        doNothing().when(companyMapper).updateEntityFromDTO(companyDTO, existingCompany);
        when(companyRepository.save(existingCompany)).thenReturn(existingCompany);
        when(companyMapper.toDTO(existingCompany)).thenReturn(companyDTO);

        CompanyDTO updatedCompany = companyService.update(companyId, companyDTO);

        verify(companyRepository, times(1)).findById(companyId);
        verify(companyRepository, times(1)).save(existingCompany);
        assertNotNull(updatedCompany);
    }

    @Test
    void testDeleteCompany() {
        UUID companyId = UUID.randomUUID();
        companyService.delete(companyId);

        verify(companyRepository, times(1)).delete(companyId);
    }
}
