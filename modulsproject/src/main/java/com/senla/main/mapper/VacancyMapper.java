package com.senla.main.mapper;

import com.senla.main.dto.VacancyDTO;
import com.senla.main.model.Company;
import com.senla.main.model.Skill;
import com.senla.main.model.Vacancy;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class VacancyMapper {

    public Vacancy toEntity(VacancyDTO dto, Company company, Set<Skill> skills) {
        if (dto == null) {
            return null;
        }

        Vacancy entity = new Vacancy();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setCompany(company);
        entity.setSalary(dto.getSalary());
        entity.setSkills(skills);
        return entity;
    }

    public VacancyDTO toDTO(Vacancy entity) {
        if (entity == null) {
            return null;
        }

        return new VacancyDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getCompany() != null ? entity.getCompany().getId() : null,
                entity.getSalary()
        );
    }

    public void updateEntityFromDTO(VacancyDTO dto, Vacancy entity, Company company, Set<Skill> skills) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setTitle(dto.getTitle());
        entity.setCompany(company);
        entity.setSalary(dto.getSalary());
        entity.setSkills(skills);
    }
}
