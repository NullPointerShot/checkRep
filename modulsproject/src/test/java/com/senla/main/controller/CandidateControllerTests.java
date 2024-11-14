package com.senla.main.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.main.config.TestConfig;
import com.senla.main.dto.CandidateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
class CandidateControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private CandidateDTO candidateDTO;

    @BeforeEach
    void setUp() {
        candidateDTO = new CandidateDTO(UUID.randomUUID(), UUID.randomUUID(), "3 years", Set.of("Java"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createCandidate() throws Exception {
        mockMvc.perform(post("/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.experience").value("3 years"))
                .andExpect(jsonPath("$.skills[0]").value("Java"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getCandidateById() throws Exception {
        mockMvc.perform(post("/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidateDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/candidates/{id}", candidateDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.experience").value("3 years"))
                .andExpect(jsonPath("$.skills[0]").value("Java"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateCandidate() throws Exception {
        mockMvc.perform(post("/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidateDTO)))
                .andExpect(status().isCreated());

        candidateDTO.setExperience("5 years");
        candidateDTO.setSkills(Set.of("Spring"));

        mockMvc.perform(put("/candidates/{id}", candidateDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.experience").value("5 years"))
                .andExpect(jsonPath("$.skills[0]").value("Spring"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteCandidate() throws Exception {
        mockMvc.perform(post("/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidateDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/candidates/{id}", candidateDTO.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getCandidatesByExperience() throws Exception {
        CandidateDTO candidate1 = new CandidateDTO(UUID.randomUUID(), UUID.randomUUID(), "2 years", Set.of("Java"));
        CandidateDTO candidate2 = new CandidateDTO(UUID.randomUUID(), UUID.randomUUID(), "3 years", Set.of("Spring"));

        mockMvc.perform(post("/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidate1)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidate2)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/candidates/experience/{experience}", "3 years"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].experience").value("3 years"))
                .andExpect(jsonPath("$[0].skills[0]").value("Spring"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getCandidatesBySkill() throws Exception {
        candidateDTO.setSkills(Collections.singleton("Java"));

        mockMvc.perform(post("/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidateDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/candidates/skill/{skillName}", "Java"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].skills[0]").value("Java"));
    }


    @Test
    void createCandidateWithoutAuth() throws Exception {
        mockMvc.perform(post("/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidateDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getCandidateByIdWithoutAuth() throws Exception {
        mockMvc.perform(get("/candidates/{id}", candidateDTO.getId()))
                .andExpect(status().isUnauthorized());
    }
}
