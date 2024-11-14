package com.senla.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobOfferDTO {
    private UUID id;
    private UUID candidateId;
    private UUID vacancyId;
    private Integer salary;
    private String status;
}
