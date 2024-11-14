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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final CandidateRepository candidateRepository;
    private final VacancyRepository vacancyRepository;
    private final RecruiterRepository recruiterRepository;
    private final FeedbackMapper feedbackMapper;

    public FeedbackService(FeedbackRepository feedbackRepository,
                           CandidateRepository candidateRepository,
                           VacancyRepository vacancyRepository,
                           RecruiterRepository recruiterRepository,
                           FeedbackMapper feedbackMapper) {
        this.feedbackRepository = feedbackRepository;
        this.candidateRepository = candidateRepository;
        this.vacancyRepository = vacancyRepository;
        this.recruiterRepository = recruiterRepository;
        this.feedbackMapper = feedbackMapper;
    }

    @Transactional
    public FeedbackDTO create(FeedbackDTO feedbackDTO) {
        if (Objects.isNull(feedbackDTO)) {
            throw new InvalidRequestException("FeedbackDTO cannot be null");
        }

        Candidate candidate = candidateRepository.findById(feedbackDTO.getCandidateId())
                .orElseThrow(() -> new NotFoundException("Candidate with ID " + feedbackDTO.getCandidateId() + " not found"));

        Vacancy vacancy = vacancyRepository.findById(feedbackDTO.getVacancyId())
                .orElseThrow(() -> new NotFoundException("Vacancy with ID " + feedbackDTO.getVacancyId() + " not found"));

        Recruiter recruiter = recruiterRepository.findById(feedbackDTO.getRecruiterId())
                .orElseThrow(() -> new NotFoundException("Recruiter with ID " + feedbackDTO.getRecruiterId() + " not found"));

        Feedback feedback = feedbackMapper.toEntity(feedbackDTO, candidate, recruiter, vacancy);
        feedbackRepository.save(feedback);
        return feedbackMapper.toDTO(feedback);
    }

    @Transactional(readOnly = true)
    public FeedbackDTO getById(UUID id) {
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID cannot be null");
        }

        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Feedback with ID " + id + " not found"));

        return feedbackMapper.toDTO(feedback);
    }

    @Transactional
    public FeedbackDTO update(UUID id, FeedbackDTO feedbackDTO) {
        if (Objects.isNull(id) || Objects.isNull(feedbackDTO)) {
            throw new InvalidRequestException("ID and FeedbackDTO cannot be null");
        }

        Feedback existingFeedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Feedback with ID " + id + " not found"));

        Candidate candidate = candidateRepository.findById(feedbackDTO.getCandidateId())
                .orElseThrow(() -> new NotFoundException("Candidate with ID " + feedbackDTO.getCandidateId() + " not found"));

        Vacancy vacancy = vacancyRepository.findById(feedbackDTO.getVacancyId())
                .orElseThrow(() -> new NotFoundException("Vacancy with ID " + feedbackDTO.getVacancyId() + " not found"));

        Recruiter recruiter = recruiterRepository.findById(feedbackDTO.getRecruiterId())
                .orElseThrow(() -> new NotFoundException("Recruiter with ID " + feedbackDTO.getRecruiterId() + " not found"));

        feedbackMapper.updateEntityFromDTO(feedbackDTO, existingFeedback, candidate, recruiter, vacancy);
        feedbackRepository.save(existingFeedback);

        return feedbackMapper.toDTO(existingFeedback);
    }

    @Transactional
    public void delete(UUID id) {
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID cannot be null");
        }

        feedbackRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public Collection<FeedbackDTO> findAll() {
        return feedbackRepository.findAll().stream()
                .map(feedbackMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FeedbackDTO> findByCandidateId(UUID candidateId) {
        return feedbackRepository.findFeedbacksByCandidateId(candidateId).stream()
                .map(feedbackMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FeedbackDTO getByIdWithCandidateAndVacancy(UUID id) {
        Feedback feedback = feedbackRepository.findByIdWithCandidateAndVacancy(id);
        return feedbackMapper.toDTO(feedback);
    }

    @Transactional(readOnly = true)
    public FeedbackDTO getByIdWithCandidateAndVacancyCriteria(UUID id) {
        Feedback feedback = feedbackRepository.findByIdWithCandidateAndVacancyCriteria(id);
        return feedbackMapper.toDTO(feedback);
    }
}
