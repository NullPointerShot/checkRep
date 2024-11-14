package com.senla.main.service;

import com.senla.main.dto.SkillDTO;
import com.senla.main.exception.InvalidRequestException;
import com.senla.main.exception.NotFoundException;
import com.senla.main.mapper.SkillMapper;
import com.senla.main.model.Skill;
import com.senla.main.repository.SkillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SkillService {

    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;

    public SkillService(SkillRepository skillRepository, SkillMapper skillMapper) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    @Transactional
    public SkillDTO create(SkillDTO skillDTO, Skill parent) {
        if (Objects.isNull(skillDTO)) {
            throw new InvalidRequestException("SkillDTO cannot be null");
        }

        Skill skill = skillMapper.toEntity(skillDTO, parent);
        skillRepository.save(skill);
        return skillMapper.toDTO(skill);
    }

    @Transactional(readOnly = true)
    public SkillDTO getById(UUID id) {
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID cannot be null");
        }

        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Skill with ID " + id + " not found"));

        return skillMapper.toDTO(skill);
    }

    @Transactional(readOnly = true)
    public Skill getSkillById(UUID id) {
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID cannot be null");
        }

        return skillRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Skill with ID " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public Set<Skill> getSkillsByIds(Set<UUID> skillIds) {
        return skillIds.stream()
                .map(skillRepository::findById)
                .map(optionalSkill -> optionalSkill.orElseThrow(() -> new NotFoundException("Skill not found")))
                .collect(Collectors.toSet());
    }

    @Transactional
    public SkillDTO update(UUID id, SkillDTO skillDTO, Skill parent) {
        if (Objects.isNull(id) || Objects.isNull(skillDTO)) {
            throw new InvalidRequestException("ID and SkillDTO cannot be null");
        }

        Skill existingSkill = skillRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Skill with ID " + id + " not found"));

        skillMapper.updateEntityFromDTO(skillDTO, existingSkill, parent);
        skillRepository.save(existingSkill);

        return skillMapper.toDTO(existingSkill);
    }

    @Transactional
    public void delete(UUID id) {
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID cannot be null");
        }

        skillRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public Collection<SkillDTO> findAll() {
        return skillRepository.findAll().stream()
                .map(skillMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Collection<SkillDTO> findByName(String name) {
        return skillRepository.findByName(name).stream()
                .map(skillMapper::toDTO)
                .collect(Collectors.toList());
    }
}
