package com.senla.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {
    private UUID id;
    private UUID candidateId;
    private UUID recruiterId;
    private UUID vacancyId;
    private Date date;
    private String feedback;
    private String status;
}
