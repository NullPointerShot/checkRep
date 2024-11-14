package com.senla.main.service;

import com.senla.main.dto.CompanyDTO;
import com.senla.main.exception.InvalidRequestException;
import com.senla.main.exception.NotFoundException;
import com.senla.main.mapper.CompanyMapper;
import com.senla.main.model.Company;
import com.senla.main.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    @Transactional
    public CompanyDTO create(CompanyDTO companyDTO) {
        if (Objects.isNull(companyDTO)) {
            throw new InvalidRequestException("CompanyDTO cannot be null");
        }

        Company company = companyMapper.toEntity(companyDTO);
        companyRepository.save(company);
        return companyMapper.toDTO(company);
    }

    @Transactional(readOnly = true)
    public CompanyDTO getById(UUID id) {
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID cannot be null");
        }

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Company with ID " + id + " not found"));

        return companyMapper.toDTO(company);
    }

    @Transactional
    public CompanyDTO update(UUID id, CompanyDTO companyDTO) {
        if (Objects.isNull(id) || Objects.isNull(companyDTO)) {
            throw new InvalidRequestException("ID and CompanyDTO cannot be null");
        }

        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Company with ID " + id + " not found"));

        companyMapper.updateEntityFromDTO(companyDTO, existingCompany);
        companyRepository.save(existingCompany);

        return companyMapper.toDTO(existingCompany);
    }

    @Transactional
    public void delete(UUID id) {
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID cannot be null");
        }

        companyRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public Collection<CompanyDTO> findAll() {
        return companyRepository.findAll().stream()
                .map(companyMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CompanyDTO> findByName(String name) {
        return companyRepository.findCompaniesByName(name).stream()
                .map(companyMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CompanyDTO getByIdWithVacanciesAndRecruiters(UUID id) {
        Company company = companyRepository.findByIdWithVacanciesAndRecruiters(id);
        return companyMapper.toDTO(company);
    }

    @Transactional(readOnly = true)
    public CompanyDTO getByIdWithVacanciesAndRecruitersCriteria(UUID id) {
        Company company = companyRepository.findByIdWithVacanciesAndRecruitersCriteria(id);
        return companyMapper.toDTO(company);
    }

    @Transactional(readOnly = true)
    public Company getEntityById(UUID id) {
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID cannot be null");
        }

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Company with ID " + id + " not found"));

        return company;
    }
}
