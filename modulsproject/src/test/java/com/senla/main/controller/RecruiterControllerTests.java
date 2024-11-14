package com.senla.main.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.main.config.TestConfig;
import com.senla.main.dto.RecruiterDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
class RecruiterControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private RecruiterDTO recruiterDTO;

    @BeforeEach
    void setUp() {
        recruiterDTO = new RecruiterDTO(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createRecruiter() throws Exception {
        mockMvc.perform(post("/recruiters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recruiterDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Recruiter"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getRecruiterById() throws Exception {
        mockMvc.perform(post("/recruiters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recruiterDTO)))
                .andExpect(status().isCreated());



        mockMvc.perform(get("/recruiters/{id}", recruiterDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Recruiter"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateRecruiter() throws Exception {

        mockMvc.perform(post("/recruiters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recruiterDTO)))
                .andExpect(status().isCreated());


        UUID newProfileId = UUID.randomUUID();
        RecruiterDTO updatedRecruiterDTO = new RecruiterDTO(recruiterDTO.getId(), newProfileId, recruiterDTO.getCompanyId());

        mockMvc.perform(put("/recruiters/{id}", recruiterDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRecruiterDTO)))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.profileId").value(newProfileId.toString()));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteRecruiter() throws Exception {
        mockMvc.perform(post("/recruiters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recruiterDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/recruiters/{id}", recruiterDTO.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAllRecruiters() throws Exception {
        mockMvc.perform(post("/recruiters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recruiterDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/recruiters"))
                .andExpect(status().isOk());
    }
}
