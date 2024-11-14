package com.senla.main.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.main.config.TestConfig;
import com.senla.main.dto.JobOfferDTO;
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
class JobOfferControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private JobOfferDTO jobOfferDTO;

    @BeforeEach
    void setUp() {
        jobOfferDTO = new JobOfferDTO(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 50000, "Open");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createJobOffer() throws Exception {
        mockMvc.perform(post("/job-offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobOfferDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.salary").value(50000));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getJobOfferById() throws Exception {
        mockMvc.perform(post("/job-offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobOfferDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/job-offers/{id}", jobOfferDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.salary").value(50000));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateJobOffer() throws Exception {
        mockMvc.perform(post("/job-offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobOfferDTO)))
                .andExpect(status().isCreated());

        jobOfferDTO.setSalary(60000);

        mockMvc.perform(put("/job-offers/{id}", jobOfferDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobOfferDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.salary").value(60000));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteJobOffer() throws Exception {
        mockMvc.perform(post("/job-offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobOfferDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/job-offers/{id}", jobOfferDTO.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAllJobOffers() throws Exception {
        mockMvc.perform(post("/job-offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobOfferDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/job-offers"))
                .andExpect(status().isOk());
    }
}
