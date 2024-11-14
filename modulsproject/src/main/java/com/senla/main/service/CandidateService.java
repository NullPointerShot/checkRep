package com.senla.main.service;

import com.senla.main.dto.CandidateDTO;
import com.senla.main.exception.InvalidRequestException;
import com.senla.main.exception.NotFoundException;
import com.senla.main.mapper.CandidateMapper;
import com.senla.main.model.Candidate;
import com.senla.main.model.Profile;
import com.senla.main.repository.CandidateRepository;
import com.senla.main.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final ProfileRepository profileRepository;
    private final CandidateMapper candidateMapper;

    public CandidateService(CandidateRepository candidateRepository, ProfileRepository profileRepository, CandidateMapper candidateMapper) {
        this.candidateRepository = candidateRepository;
        this.profileRepository = profileRepository;
        this.candidateMapper = candidateMapper;
    }

    @Transactional
    public CandidateDTO create(CandidateDTO candidateDTO) {
        if (Objects.isNull(candidateDTO)) {
            throw new InvalidRequestException("CandidateDTO cannot be null");
        }

        Profile profile = profileRepository.findById(candidateDTO.getProfileId())
                .orElseThrow(() -> new NotFoundException("Profile with ID " + candidateDTO.getProfileId() + " not found"));

        Candidate candidate = candidateMapper.toEntity(candidateDTO, profile);
        candidateRepository.save(candidate);

        return candidateMapper.toDTO(candidate);
    }

    @Transactional(readOnly = true)
    public CandidateDTO getById(UUID id) {
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID cannot be null");
        }

        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidate with ID " + id + " not found"));

        return candidateMapper.toDTO(candidate);
    }

    @Transactional
    public CandidateDTO update(UUID id, CandidateDTO candidateDTO) {
        if (Objects.isNull(id) || Objects.isNull(candidateDTO)) {
            throw new InvalidRequestException("ID and CandidateDTO cannot be null");
        }

        Candidate existingCandidate = candidateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidate with ID " + id + " not found"));

        Profile profile = profileRepository.findById(candidateDTO.getProfileId())
                .orElseThrow(() -> new NotFoundException("Profile with ID " + candidateDTO.getProfileId() + " not found"));

        candidateMapper.updateEntityFromDTO(candidateDTO, existingCandidate, profile);
        candidateRepository.save(existingCandidate);

        return candidateMapper.toDTO(existingCandidate);
    }

    @Transactional
    public void delete(UUID id) {
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID cannot be null");
        }

        candidateRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public Collection<CandidateDTO> findAll() {
        return candidateRepository.findAll().stream()
                .map(candidateMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CandidateDTO> findByExperience(String experience) {
        return candidateRepository.findByExperience(experience).stream()
                .map(candidateMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CandidateDTO> findBySkill(String skillName) {
        return candidateRepository.findBySkill(skillName).stream()
                .map(candidateMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CandidateDTO getByIdWithSkills(UUID id) {
        return candidateRepository.findByIdWithSkills(id)
                .map(candidateMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Candidate with ID " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public CandidateDTO getByIdWithSkillsAndProfile(UUID id) {
        Candidate candidate = candidateRepository.findByIdWithSkillsAndProfile(id);
        return candidateMapper.toDTO(candidate);
    }

    @Transactional(readOnly = true)
    public CandidateDTO getByIdWithSkillsAndProfileCriteria(UUID id) {
        Candidate candidate = candidateRepository.findByIdWithSkillsAndProfileCriteria(id);
        return candidateMapper.toDTO(candidate);
    }
}
