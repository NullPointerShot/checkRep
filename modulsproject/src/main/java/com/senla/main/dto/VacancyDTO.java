package com.senla.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacancyDTO {
    private UUID id;
    private String title;
    private UUID companyId;
    private Integer salary;
}
