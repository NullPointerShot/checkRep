package com.senla.main.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.main.config.TestConfig;
import com.senla.main.dto.VacancyDTO;
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
class VacancyControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private VacancyDTO vacancyDTO;

    @BeforeEach
    void setUp() {
        vacancyDTO = new VacancyDTO(UUID.randomUUID(), "Test Vacancy", UUID.randomUUID(), 60000);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createVacancy() throws Exception {
        mockMvc.perform(post("/vacancies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vacancyDTO))
                        .param("companyId", UUID.randomUUID().toString())
                        .param("skillIds", UUID.randomUUID().toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Vacancy"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getVacancyById() throws Exception {
        mockMvc.perform(post("/vacancies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vacancyDTO))
                        .param("companyId", UUID.randomUUID().toString())
                        .param("skillIds", UUID.randomUUID().toString()))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/vacancies/{id}", vacancyDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Vacancy"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateVacancy() throws Exception {
        mockMvc.perform(post("/vacancies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vacancyDTO))
                        .param("companyId", UUID.randomUUID().toString())
                        .param("skillIds", UUID.randomUUID().toString()))
                .andExpect(status().isCreated());

        vacancyDTO.setTitle("Updated Vacancy");

        mockMvc.perform(put("/vacancies/{id}", vacancyDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vacancyDTO))
                        .param("companyId", UUID.randomUUID().toString())
                        .param("skillIds", UUID.randomUUID().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Vacancy"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteVacancy() throws Exception {
        mockMvc.perform(post("/vacancies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vacancyDTO))
                        .param("companyId", UUID.randomUUID().toString())
                        .param("skillIds", UUID.randomUUID().toString()))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/vacancies/{id}", vacancyDTO.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAllVacancies() throws Exception {
        mockMvc.perform(post("/vacancies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vacancyDTO))
                        .param("companyId", UUID.randomUUID().toString())
                        .param("skillIds", UUID.randomUUID().toString()))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/vacancies"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getVacanciesByCompanyId() throws Exception {
        UUID companyId = UUID.randomUUID();
        mockMvc.perform(post("/vacancies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vacancyDTO))
                        .param("companyId", companyId.toString())
                        .param("skillIds", UUID.randomUUID().toString()))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/vacancies/company/{companyId}", companyId))
                .andExpect(status().isOk());
    }
}
