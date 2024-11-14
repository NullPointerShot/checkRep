package com.senla.main.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.main.config.TestConfig;
import com.senla.main.dto.ProfileCreateDTO;
import com.senla.main.dto.ProfileResponseDTO;
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
class ProfileControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ProfileCreateDTO profileCreateDTO;
    private ProfileResponseDTO profileResponseDTO;

    @BeforeEach
    void setUp() {
        profileCreateDTO = new ProfileCreateDTO("Test Profile", "password123", UUID.randomUUID());
        profileResponseDTO = new ProfileResponseDTO(UUID.randomUUID(), "Test Profile", UUID.randomUUID());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createProfile() throws Exception {
        mockMvc.perform(post("/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Profile"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getProfileById() throws Exception {
        mockMvc.perform(post("/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileCreateDTO)))
                .andExpect(status().isCreated());



        mockMvc.perform(get("/profiles/{id}", profileResponseDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Profile"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateProfile() throws Exception {
        mockMvc.perform(post("/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileCreateDTO)))
                .andExpect(status().isCreated());

        profileCreateDTO.setName("Updated Profile");


        mockMvc.perform(put("/profiles/{id}", profileResponseDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileCreateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Profile"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteProfile() throws Exception {
        mockMvc.perform(post("/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileCreateDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/profiles/{id}", profileResponseDTO.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAllProfiles() throws Exception {
        mockMvc.perform(post("/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileCreateDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/profiles"))
                .andExpect(status().isOk());
    }
}
