package com.senla.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecruiterDTO {
    private UUID id;
    private UUID profileId;
    private UUID companyId;
}
