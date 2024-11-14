package com.senla.main.mapper;

import com.senla.main.dto.JobOfferDTO;
import com.senla.main.model.Candidate;
import com.senla.main.model.JobOffer;
import com.senla.main.model.Vacancy;
import org.springframework.stereotype.Component;

@Component
public class JobOfferMapper {

    public JobOffer toEntity(JobOfferDTO dto, Candidate candidate, Vacancy vacancy) {
        if (dto == null) {
            return null;
        }

        JobOffer entity = new JobOffer();
        entity.setId(dto.getId());
        entity.setCandidate(candidate);
        entity.setVacancy(vacancy);
        entity.setSalary(dto.getSalary());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    public JobOfferDTO toDTO(JobOffer entity) {
        if (entity == null) {
            return null;
        }

        return new JobOfferDTO(
                entity.getId(),
                entity.getCandidate() != null ? entity.getCandidate().getId() : null,
                entity.getVacancy() != null ? entity.getVacancy().getId() : null,
                entity.getSalary(),
                entity.getStatus()
        );
    }

    public void updateEntityFromDTO(JobOfferDTO dto, JobOffer entity, Candidate candidate, Vacancy vacancy) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setCandidate(candidate);
        entity.setVacancy(vacancy);
        entity.setSalary(dto.getSalary());
        entity.setStatus(dto.getStatus());
    }
}
