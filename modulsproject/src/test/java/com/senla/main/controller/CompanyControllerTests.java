package com.senla.main.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.main.config.TestConfig;
import com.senla.main.dto.CompanyDTO;
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
class CompanyControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private CompanyDTO companyDTO;

    @BeforeEach
    void setUp() {
        companyDTO = new CompanyDTO(UUID.randomUUID(), "Test Company", "http://testcompany.com");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createCompany() throws Exception {
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(companyDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Company"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getCompanyById() throws Exception {
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(companyDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/companies/{id}", companyDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Company"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateCompany() throws Exception {
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(companyDTO)))
                .andExpect(status().isCreated());

        companyDTO.setName("Updated Company");

        mockMvc.perform(put("/companies/{id}", companyDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(companyDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Company"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteCompany() throws Exception {
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(companyDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/companies/{id}", companyDTO.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getCompaniesByName() throws Exception {
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(companyDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/companies/name/{name}", "Test Company"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Company"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getCompanyByIdWithVacancies() throws Exception {
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(companyDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/companies/{id}/vacancies", companyDTO.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getCompanyByIdWithVacanciesCriteria() throws Exception {
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(companyDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/companies/{id}/vacancies-criteria", companyDTO.getId()))
                .andExpect(status().isOk());
    }
}
