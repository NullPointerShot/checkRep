package com.senla.main.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.main.config.TestConfig;
import com.senla.main.dto.RoleDTO;
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
class RoleControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private RoleDTO roleDTO;

    @BeforeEach
    void setUp() {
        roleDTO = new RoleDTO(UUID.randomUUID(), "Test Role");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createRole() throws Exception {
        mockMvc.perform(post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Role"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getRoleById() throws Exception {
        mockMvc.perform(post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/roles/{id}", roleDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Role"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateRole() throws Exception {
        mockMvc.perform(post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleDTO)))
                .andExpect(status().isCreated());

        roleDTO.setName("Updated Role");

        mockMvc.perform(put("/roles/{id}", roleDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Role"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteRole() throws Exception {
        mockMvc.perform(post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/roles/{id}", roleDTO.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getRolesByName() throws Exception {
        mockMvc.perform(post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/roles/search?name={name}", "Test Role"))
                .andExpect(status().isOk());
    }
}
