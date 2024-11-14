package com.senla.main.service;

import com.senla.main.dto.VacancyDTO;
import com.senla.main.exception.InvalidRequestException;
import com.senla.main.exception.NotFoundException;
import com.senla.main.mapper.VacancyMapper;
import com.senla.main.model.Company;
import com.senla.main.model.Skill;
import com.senla.main.model.Vacancy;
import com.senla.main.repository.VacancyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VacancyService {

    private final VacancyRepository vacancyRepository;
    private final VacancyMapper vacancyMapper;

    public VacancyService(VacancyRepository vacancyRepository, VacancyMapper vacancyMapper) {
        this.vacancyRepository = vacancyRepository;
        this.vacancyMapper = vacancyMapper;
    }

    @Transactional
    public VacancyDTO create(VacancyDTO vacancyDTO, Company company, Set<Skill> skills) {
        if (Objects.isNull(vacancyDTO)) {
            throw new InvalidRequestException("VacancyDTO cannot be null");
        }

        Vacancy vacancy = vacancyMapper.toEntity(vacancyDTO, company, skills);
        vacancyRepository.save(vacancy);
        return vacancyMapper.toDTO(vacancy);
    }

    @Transactional(readOnly = true)
    public VacancyDTO getById(UUID id) {
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID cannot be null");
        }

        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vacancy with ID " + id + " not found"));

        return vacancyMapper.toDTO(vacancy);
    }

    @Transactional
    public VacancyDTO update(UUID id, VacancyDTO vacancyDTO, Company company, Set<Skill> skills) {
        if (Objects.isNull(id) || Objects.isNull(vacancyDTO)) {
            throw new InvalidRequestException("ID and VacancyDTO cannot be null");
        }

        Vacancy existingVacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vacancy with ID " + id + " not found"));

        vacancyMapper.updateEntityFromDTO(vacancyDTO, existingVacancy, company, skills);
        vacancyRepository.save(existingVacancy);

        return vacancyMapper.toDTO(existingVacancy);
    }

    @Transactional
    public void delete(UUID id) {
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID cannot be null");
        }

        vacancyRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public Collection<VacancyDTO> findAll() {
        return vacancyRepository.findAll().stream()
                .map(vacancyMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Collection<VacancyDTO> findVacanciesByCompanyId(UUID companyId) {
        return vacancyRepository.findVacanciesByCompanyId(companyId).stream()
                .map(vacancyMapper::toDTO)
                .collect(Collectors.toList());
    }
}
