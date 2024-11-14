package com.senla.main.service;

import com.senla.main.dto.JobOfferDTO;
import com.senla.main.exception.InvalidRequestException;
import com.senla.main.exception.NotFoundException;
import com.senla.main.mapper.JobOfferMapper;
import com.senla.main.model.Candidate;
import com.senla.main.model.JobOffer;
import com.senla.main.model.Vacancy;
import com.senla.main.repository.CandidateRepository;
import com.senla.main.repository.JobOfferRepository;
import com.senla.main.repository.VacancyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JobOfferService {

    private final JobOfferRepository jobOfferRepository;
    private final CandidateRepository candidateRepository;
    private final VacancyRepository vacancyRepository;
    private final JobOfferMapper jobOfferMapper;

    public JobOfferService(JobOfferRepository jobOfferRepository,
                           CandidateRepository candidateRepository,
                           VacancyRepository vacancyRepository,
                           JobOfferMapper jobOfferMapper) {
        this.jobOfferRepository = jobOfferRepository;
        this.candidateRepository = candidateRepository;
        this.vacancyRepository = vacancyRepository;
        this.jobOfferMapper = jobOfferMapper;
    }

    @Transactional
    public JobOfferDTO create(JobOfferDTO jobOfferDTO) {
        if (Objects.isNull(jobOfferDTO)) {
            throw new InvalidRequestException("JobOfferDTO cannot be null");
        }

        Candidate candidate = candidateRepository.findById(jobOfferDTO.getCandidateId())
                .orElseThrow(() -> new NotFoundException("Candidate with ID " + jobOfferDTO.getCandidateId() + " not found"));

        Vacancy vacancy = vacancyRepository.findById(jobOfferDTO.getVacancyId())
                .orElseThrow(() -> new NotFoundException("Vacancy with ID " + jobOfferDTO.getVacancyId() + " not found"));

        JobOffer jobOffer = jobOfferMapper.toEntity(jobOfferDTO, candidate, vacancy);
        jobOfferRepository.save(jobOffer);
        return jobOfferMapper.toDTO(jobOffer);
    }

    @Transactional(readOnly = true)
    public JobOfferDTO getById(UUID id) {
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID cannot be null");
        }

        JobOffer jobOffer = jobOfferRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("JobOffer with ID " + id + " not found"));

        return jobOfferMapper.toDTO(jobOffer);
    }

    @Transactional
    public JobOfferDTO update(UUID id, JobOfferDTO jobOfferDTO) {
        if (Objects.isNull(id) || Objects.isNull(jobOfferDTO)) {
            throw new InvalidRequestException("ID and JobOfferDTO cannot be null");
        }

        JobOffer existingJobOffer = jobOfferRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("JobOffer with ID " + id + " not found"));

        Candidate candidate = candidateRepository.findById(jobOfferDTO.getCandidateId())
                .orElseThrow(() -> new NotFoundException("Candidate with ID " + jobOfferDTO.getCandidateId() + " not found"));

        Vacancy vacancy = vacancyRepository.findById(jobOfferDTO.getVacancyId())
                .orElseThrow(() -> new NotFoundException("Vacancy with ID " + jobOfferDTO.getVacancyId() + " not found"));

        jobOfferMapper.updateEntityFromDTO(jobOfferDTO, existingJobOffer, candidate, vacancy);
        jobOfferRepository.save(existingJobOffer);

        return jobOfferMapper.toDTO(existingJobOffer);
    }

    @Transactional
    public void delete(UUID id) {
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID cannot be null");
        }

        jobOfferRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public Collection<JobOfferDTO> findAll() {
        return jobOfferRepository.findAll().stream()
                .map(jobOfferMapper::toDTO)
                .collect(Collectors.toList());
    }
}
