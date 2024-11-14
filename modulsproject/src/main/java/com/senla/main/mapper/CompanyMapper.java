package com.senla.main.mapper;

import com.senla.main.dto.CompanyDTO;
import com.senla.main.model.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public Company toEntity(CompanyDTO dto) {
        if (dto == null) {
            return null;
        }

        Company entity = new Company();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setWebsite(dto.getWebsite());
        return entity;
    }

    public CompanyDTO toDTO(Company entity) {
        if (entity == null) {
            return null;
        }

        return new CompanyDTO(
                entity.getId(),
                entity.getName(),
                entity.getWebsite()
        );
    }

    public void updateEntityFromDTO(CompanyDTO dto, Company entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setName(dto.getName());
        entity.setWebsite(dto.getWebsite());
    }
}
