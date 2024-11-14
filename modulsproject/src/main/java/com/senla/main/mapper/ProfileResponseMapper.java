package com.senla.main.mapper;

import com.senla.main.dto.ProfileResponseDTO;
import com.senla.main.model.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileResponseMapper {

    public ProfileResponseDTO toDTO(Profile entity) {
        if (entity == null) {
            return null;
        }

        return new ProfileResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getRole() != null ? entity.getRole().getId() : null
        );
    }

}
