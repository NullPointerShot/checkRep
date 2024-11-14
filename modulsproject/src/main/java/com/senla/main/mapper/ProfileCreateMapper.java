package com.senla.main.mapper;

import com.senla.main.dto.ProfileCreateDTO;
import com.senla.main.model.Profile;
import com.senla.main.model.Role;
import org.springframework.stereotype.Component;

@Component
public class ProfileCreateMapper {

    public Profile toEntity(ProfileCreateDTO dto, Role role) {
        if (dto == null) {
            return null;
        }

        Profile entity = new Profile();
        entity.setName(dto.getName());
        entity.setPassword(dto.getPassword());
        entity.setRole(role);
        return entity;
    }

    public void updateEntityFromDTO(ProfileCreateDTO dto, Profile entity, Role role) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setName(dto.getName());
        entity.setPassword(dto.getPassword());
        entity.setRole(role);
    }
}
