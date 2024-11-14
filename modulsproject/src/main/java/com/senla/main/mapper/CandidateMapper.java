package com.senla.main.mapper;

import com.senla.main.dto.CandidateDTO;
import com.senla.main.model.Candidate;
import com.senla.main.model.Skill;
import com.senla.main.model.Profile;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CandidateMapper {

    public Candidate toEntity(CandidateDTO dto, Profile profile) {
        if (dto == null) {
            return null;
        }

        Candidate entity = new Candidate();
        entity.setId(dto.getId());
        entity.setProfile(profile);
        entity.setExperience(dto.getExperience());
        return entity;
    }

    public CandidateDTO toDTO(Candidate entity) {
        if (entity == null) {
            return null;
        }

        Set<String> skillNames = entity.getSkills() != null ?
                entity.getSkills().stream()
                        .map(Skill::getName)
                        .collect(Collectors.toSet()) : null;

        return new CandidateDTO(
                entity.getId(),
                entity.getProfile() != null ? entity.getProfile().getId() : null,
                entity.getExperience(),
                skillNames
        );
    }

    public void updateEntityFromDTO(CandidateDTO dto, Candidate entity, Profile profile) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setProfile(profile);
        entity.setExperience(dto.getExperience());
    }
}
