package com.senla.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateDTO {
    private UUID id;
    private UUID profileId;
    private String experience;
    private Set<String> skills;
}
