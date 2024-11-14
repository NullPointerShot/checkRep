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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecruiterService {

    private final RecruiterRepository recruiterRepository;
    private final RecruiterMapper recruiterMapper;
    private final ProfileRepository profileRepository;
    private final CompanyRepository companyRepository;

    public RecruiterService(RecruiterRepository recruiterRepository,
                            RecruiterMapper recruiterMapper,
                            ProfileRepository profileRepository,
                            CompanyRepository companyRepository) {
        this.recruiterRepository = recruiterRepository;
        this.recruiterMapper = recruiterMapper;
        this.profileRepository = profileRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional
    public RecruiterDTO create(RecruiterDTO recruiterDTO) {
        if (Objects.isNull(recruiterDTO)) {
            throw new InvalidRequestException("RecruiterDTO cannot be null");
        }
        Profile profile = profileRepository.findById(recruiterDTO.getProfileId())
                .orElseThrow(() -> new NotFoundException("Profile with ID " + recruiterDTO.getProfileId() + " not found"));

        Company company = companyRepository.findById(recruiterDTO.getCompanyId())
                .orElseThrow(() -> new NotFoundException("Company with ID " + recruiterDTO.getCompanyId() + " not found"));

        Recruiter recruiter = recruiterMapper.toEntity(recruiterDTO, profile, company);
        recruiterRepository.save(recruiter);
        return recruiterMapper.toDTO(recruiter);
    }

    @Transactional(readOnly = true)
    public RecruiterDTO getById(UUID id) {
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID cannot be null");
        }

        Recruiter recruiter = recruiterRepository.findByIdWithProfileAndFeedbacksEntityGraph(id);
        if (Objects.isNull(recruiter)) {
            throw new NotFoundException("Recruiter with ID " + id + " not found");
        }

        return recruiterMapper.toDTO(recruiter);
    }

    @Transactional
    public RecruiterDTO update(UUID id, RecruiterDTO recruiterDTO) {
        if (Objects.isNull(id) || Objects.isNull(recruiterDTO)) {
            throw new InvalidRequestException("ID and RecruiterDTO cannot be null");
        }

        Recruiter existingRecruiter = recruiterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recruiter with ID " + id + " not found"));

        Profile profile = profileRepository.findById(recruiterDTO.getProfileId())
                .orElseThrow(() -> new NotFoundException("Profile with ID " + recruiterDTO.getProfileId() + " not found"));

        Company company = companyRepository.findById(recruiterDTO.getCompanyId())
                .orElseThrow(() -> new NotFoundException("Company with ID " + recruiterDTO.getCompanyId() + " not found"));

        recruiterMapper.updateEntityFromDTO(recruiterDTO, existingRecruiter, profile, company);
        recruiterRepository.save(existingRecruiter);

        return recruiterMapper.toDTO(existingRecruiter);
    }

    @Transactional
    public void delete(UUID id) {
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID cannot be null");
        }

        recruiterRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public Collection<RecruiterDTO> findAll() {
        return recruiterRepository.findAll().stream()
                .map(recruiterMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Collection<RecruiterDTO> findRecruitersByCompanyId(UUID companyId) {
        return recruiterRepository.findRecruitersByCompanyId(companyId).stream()
                .map(recruiterMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Collection<RecruiterDTO> findRecruitersByCompanyIdCriteria(UUID companyId) {
        return recruiterRepository.findRecruitersByCompanyIdCriteria(companyId).stream()
                .map(recruiterMapper::toDTO)
                .collect(Collectors.toList());
    }
}
