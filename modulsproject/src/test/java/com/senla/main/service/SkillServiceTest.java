package com.senla.main.service;

import com.senla.main.dto.SkillDTO;
import com.senla.main.exception.InvalidRequestException;
import com.senla.main.exception.NotFoundException;
import com.senla.main.mapper.SkillMapper;
import com.senla.main.model.Skill;
import com.senla.main.repository.SkillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SkillServiceTest {

    @InjectMocks
    private SkillService skillService;

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private SkillMapper skillMapper;

    private SkillDTO skillDTO;
    private Skill skill;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        skillDTO = new SkillDTO();
        skill = new Skill();
        skillDTO.setId(UUID.randomUUID());
        skillDTO.setName("Java");
        skill.setId(skillDTO.getId());
        skill.setName(skillDTO.getName());
    }

    @Test
    void create_ShouldThrowInvalidRequestException_WhenSkillDTOIsNull() {
        assertThrows(InvalidRequestException.class, () -> skillService.create(null, null));
    }

    @Test
    void create_ShouldReturnSkillDTO_WhenSuccessful() {
        when(skillMapper.toEntity(skillDTO, null)).thenReturn(skill);
        when(skillRepository.save(any(Skill.class))).thenReturn(skill);
        when(skillMapper.toDTO(skill)).thenReturn(skillDTO);

        SkillDTO result = skillService.create(skillDTO, null);

        assertNotNull(result);
        verify(skillRepository).save(any(Skill.class));
    }

    @Test
    void getById_ShouldThrowInvalidRequestException_WhenIdIsNull() {
        assertThrows(InvalidRequestException.class, () -> skillService.getById(null));
    }

    @Test
    void getById_ShouldThrowNotFoundException_WhenSkillNotFound() {
        UUID id = UUID.randomUUID();
        when(skillRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> skillService.getById(id));
    }

    @Test
    void getById_ShouldReturnSkillDTO_WhenSuccessful() {
        UUID id = UUID.randomUUID();
        when(skillRepository.findById(id)).thenReturn(Optional.of(skill));
        when(skillMapper.toDTO(skill)).thenReturn(skillDTO);

        SkillDTO result = skillService.getById(id);

        assertNotNull(result);
        verify(skillRepository).findById(id);
    }

    @Test
    void getSkillById_ShouldThrowInvalidRequestException_WhenIdIsNull() {
        assertThrows(InvalidRequestException.class, () -> skillService.getSkillById(null));
    }

    @Test
    void getSkillById_ShouldThrowNotFoundException_WhenSkillNotFound() {
        UUID id = UUID.randomUUID();
        when(skillRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> skillService.getSkillById(id));
    }

    @Test
    void getSkillsByIds_ShouldReturnSetOfSkills_WhenSuccessful() {
        Set<UUID> ids = Set.of(skillDTO.getId());
        when(skillRepository.findById(skillDTO.getId())).thenReturn(Optional.of(skill));

        Set<Skill> result = skillService.getSkillsByIds(ids);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void update_ShouldThrowInvalidRequestException_WhenIdOrSkillDTOIsNull() {
        assertThrows(InvalidRequestException.class, () -> skillService.update(null, skillDTO, null));
        assertThrows(InvalidRequestException.class, () -> skillService.update(UUID.randomUUID(), null, null));
    }

    @Test
    void update_ShouldThrowNotFoundException_WhenSkillNotFound() {
        UUID id = UUID.randomUUID();
        when(skillRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> skillService.update(id, skillDTO, null));
    }

    @Test
    void update_ShouldReturnSkillDTO_WhenSuccessful() {
        UUID id = UUID.randomUUID();
        when(skillRepository.findById(id)).thenReturn(Optional.of(skill));
        when(skillMapper.toDTO(skill)).thenReturn(skillDTO);
        doNothing().when(skillMapper).updateEntityFromDTO(any(SkillDTO.class), any(Skill.class), any());
        when(skillRepository.save(any(Skill.class))).thenReturn(skill);

        SkillDTO result = skillService.update(id, skillDTO, null);

        assertNotNull(result);
        verify(skillRepository).save(any(Skill.class));
    }

    @Test
    void delete_ShouldThrowInvalidRequestException_WhenIdIsNull() {
        assertThrows(InvalidRequestException.class, () -> skillService.delete(null));
    }

    @Test
    void delete_ShouldCallRepositoryDelete_WhenSuccessful() {
        UUID id = UUID.randomUUID();
        skillService.delete(id);
        verify(skillRepository).delete(id);
    }

    @Test
    void findAll_ShouldReturnCollectionOfSkillDTOs_WhenSuccessful() {
        when(skillRepository.findAll()).thenReturn(List.of(skill));
        when(skillMapper.toDTO(skill)).thenReturn(skillDTO);

        Collection<SkillDTO> result = skillService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findByName_ShouldReturnCollectionOfSkillDTOs_WhenSuccessful() {
        when(skillRepository.findByName(anyString())).thenReturn(List.of(skill));
        when(skillMapper.toDTO(skill)).thenReturn(skillDTO);

        Collection<SkillDTO> result = skillService.findByName("Java");

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
