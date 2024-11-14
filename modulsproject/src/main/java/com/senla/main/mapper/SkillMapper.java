package com.senla.main.mapper;

import com.senla.main.dto.SkillDTO;
import com.senla.main.model.Skill;
import org.springframework.stereotype.Component;

@Component
public class SkillMapper {

    public Skill toEntity(SkillDTO dto, Skill parent) {
        if (dto == null) {
            return null;
        }

        Skill entity = new Skill();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setParent(parent);
        return entity;
    }

    public SkillDTO toDTO(Skill entity) {
        if (entity == null) {
            return null;
        }

        return new SkillDTO(
                entity.getId(),
                entity.getName(),
                entity.getParent() != null ? entity.getParent().getId() : null
        );
    }

    public void updateEntityFromDTO(SkillDTO dto, Skill entity, Skill parent) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setName(dto.getName());
        entity.setParent(parent);
    }
}
