package com.senla.main.mapper;

import com.senla.main.dto.FeedbackDTO;
import com.senla.main.model.Candidate;
import com.senla.main.model.Feedback;
import com.senla.main.model.Recruiter;
import com.senla.main.model.Vacancy;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;

@Component
public class FeedbackMapper {

    public Feedback toEntity(FeedbackDTO dto, Candidate candidate, Recruiter recruiter, Vacancy vacancy) {
        if (dto == null) {
            return null;
        }

        Feedback entity = new Feedback();
        entity.setId(dto.getId());
        entity.setCandidate(candidate);
        entity.setRecruiter(recruiter);
        entity.setVacancy(vacancy);
        entity.setDate(dto.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        entity.setFeedback(dto.getFeedback());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    public FeedbackDTO toDTO(Feedback entity) {
        if (entity == null) {
            return null;
        }

        return new FeedbackDTO(
                entity.getId(),
                entity.getCandidate() != null ? entity.getCandidate().getId() : null,
                entity.getRecruiter() != null ? entity.getRecruiter().getId() : null,
                entity.getVacancy() != null ? entity.getVacancy().getId() : null,
                Date.from(entity.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                entity.getFeedback(),
                entity.getStatus()
        );
    }

    public void updateEntityFromDTO(FeedbackDTO dto, Feedback entity, Candidate candidate, Recruiter recruiter, Vacancy vacancy) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setCandidate(candidate);
        entity.setRecruiter(recruiter);
        entity.setVacancy(vacancy);
        entity.setDate(dto.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        entity.setFeedback(dto.getFeedback());
        entity.setStatus(dto.getStatus());
    }
}
