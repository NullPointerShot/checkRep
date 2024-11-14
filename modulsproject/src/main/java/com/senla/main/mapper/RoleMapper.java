package com.senla.main.mapper;

import com.senla.main.dto.RoleDTO;
import com.senla.main.model.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public Role toEntity(RoleDTO dto) {
        if (dto == null) {
            return null;
        }

        Role entity = new Role();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    public RoleDTO toDTO(Role entity) {
        if (entity == null) {
            return null;
        }

        return new RoleDTO(
                entity.getId(),
                entity.getName()
        );
    }

    public void updateEntityFromDTO(RoleDTO dto, Role entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setName(dto.getName());
    }
}
