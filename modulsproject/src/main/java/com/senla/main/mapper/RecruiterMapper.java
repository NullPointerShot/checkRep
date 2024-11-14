package com.senla.main.mapper;

import com.senla.main.dto.RecruiterDTO;
import com.senla.main.model.Company;
import com.senla.main.model.Profile;
import com.senla.main.model.Recruiter;
import org.springframework.stereotype.Component;

@Component
public class RecruiterMapper {

    public Recruiter toEntity(RecruiterDTO dto, Profile profile, Company company) {
        if (dto == null) {
            return null;
        }

        Recruiter entity = new Recruiter();
        entity.setId(dto.getId());
        entity.setProfile(profile);
        entity.setCompany(company);
        return entity;
    }

    public RecruiterDTO toDTO(Recruiter entity) {
        if (entity == null) {
            return null;
        }

        return new RecruiterDTO(
                entity.getId(),
                entity.getProfile() != null ? entity.getProfile().getId() : null,
                entity.getCompany() != null ? entity.getCompany().getId() : null
        );
    }

    public void updateEntityFromDTO(RecruiterDTO dto, Recruiter entity, Profile profile, Company company) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setProfile(profile);
        entity.setCompany(company);
    }
}

