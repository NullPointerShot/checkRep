package com.senla.main.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.main.config.TestConfig;
import com.senla.main.dto.SkillDTO;
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
class SkillControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private SkillDTO skillDTO;

    @BeforeEach
    void setUp() {
        skillDTO = new SkillDTO(UUID.randomUUID(), "Test Skill", null);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createSkill() throws Exception {
        mockMvc.perform(post("/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(skillDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Skill"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getSkillById() throws Exception {
        mockMvc.perform(post("/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(skillDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/skills/{id}", skillDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Skill"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateSkill() throws Exception {
        mockMvc.perform(post("/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(skillDTO)))
                .andExpect(status().isCreated());

        skillDTO.setName("Updated Skill");

        mockMvc.perform(put("/skills/{id}", skillDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(skillDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Skill"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteSkill() throws Exception {
        mockMvc.perform(post("/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(skillDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/skills/{id}", skillDTO.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAllSkills() throws Exception {
        mockMvc.perform(post("/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(skillDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/skills"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getSkillsByName() throws Exception {
        mockMvc.perform(post("/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(skillDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/skills/search?name={name}", "Test Skill"))
                .andExpect(status().isOk());
    }
}
