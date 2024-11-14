package com.senla.main.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.main.config.TestConfig;
import com.senla.main.dto.FeedbackDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
class FeedbackControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private FeedbackDTO feedbackDTO;

    @BeforeEach
    void setUp() {
        feedbackDTO = new FeedbackDTO(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), new Date(), "Great candidate", "Accepted");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createFeedback() throws Exception {
        mockMvc.perform(post("/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feedbackDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.feedback").value("Great candidate"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getFeedbackById() throws Exception {
        mockMvc.perform(post("/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feedbackDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/feedbacks/{id}", feedbackDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.feedback").value("Great candidate"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateFeedback() throws Exception {
        mockMvc.perform(post("/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feedbackDTO)))
                .andExpect(status().isCreated());

        feedbackDTO.setFeedback("Updated feedback");

        mockMvc.perform(put("/feedbacks/{id}", feedbackDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feedbackDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.feedback").value("Updated feedback"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteFeedback() throws Exception {
        mockMvc.perform(post("/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feedbackDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/feedbacks/{id}", feedbackDTO.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getFeedbacksByCandidateId() throws Exception {
        mockMvc.perform(post("/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feedbackDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/feedbacks/candidate/{candidateId}", feedbackDTO.getCandidateId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getFeedbackByIdWithDetails() throws Exception {
        mockMvc.perform(post("/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feedbackDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/feedbacks/{id}/details", feedbackDTO.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getFeedbackByIdWithDetailsCriteria() throws Exception {
        mockMvc.perform(post("/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feedbackDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/feedbacks/{id}/details-criteria", feedbackDTO.getId()))
                .andExpect(status().isOk());
    }
}
