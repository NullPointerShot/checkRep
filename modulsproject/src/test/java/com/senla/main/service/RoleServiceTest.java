package com.senla.main.service;

import com.senla.main.dto.RoleDTO;
import com.senla.main.exception.InvalidRequestException;
import com.senla.main.exception.NotFoundException;
import com.senla.main.mapper.RoleMapper;
import com.senla.main.model.Role;
import com.senla.main.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RoleServiceTest {

    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    private Role role;
    private RoleDTO roleDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        UUID id = UUID.randomUUID();
        role = new Role();
        role.setId(id);
        roleDTO = new RoleDTO();
        roleDTO.setId(id);
    }

    @Test
    void create_ShouldCreateRole_WhenRoleDTOIsValid() {
        when(roleMapper.toEntity(roleDTO)).thenReturn(role);
        when(roleRepository.save(any(Role.class))).thenReturn(role);
        when(roleMapper.toDTO(role)).thenReturn(roleDTO);

        RoleDTO createdRole = roleService.create(roleDTO);

        assertEquals(roleDTO, createdRole);
        verify(roleRepository).save(any(Role.class));
    }

    @Test
    void create_ShouldThrowInvalidRequestException_WhenRoleDTOIsNull() {
        assertThrows(InvalidRequestException.class, () -> roleService.create(null));
    }

    @Test
    void getById_ShouldReturnRoleDTO_WhenRoleExists() {
        when(roleRepository.findById(any(UUID.class))).thenReturn(Optional.of(role));
        when(roleMapper.toDTO(role)).thenReturn(roleDTO);

        RoleDTO result = roleService.getById(role.getId());

        assertEquals(roleDTO, result);
    }

    @Test
    void getById_ShouldThrowNotFoundException_WhenRoleDoesNotExist() {
        when(roleRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> roleService.getById(UUID.randomUUID()));
    }

    @Test
    void update_ShouldUpdateRole_WhenRoleExists() {
        when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));
        when(roleMapper.toDTO(role)).thenReturn(roleDTO);

        RoleDTO updatedRole = roleService.update(role.getId(), roleDTO);

        assertEquals(roleDTO, updatedRole);
        verify(roleRepository).save(role);
    }

    @Test
    void update_ShouldThrowNotFoundException_WhenRoleDoesNotExist() {
        when(roleRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> roleService.update(UUID.randomUUID(), roleDTO));
    }

    @Test
    void delete_ShouldDeleteRole_WhenRoleExists() {
        UUID id = role.getId();
        when(roleRepository.findById(id)).thenReturn(Optional.of(role));

        roleService.delete(id);

        verify(roleRepository).delete(id);
    }

    @Test
    void delete_ShouldThrowInvalidRequestException_WhenIdIsNull() {
        assertThrows(InvalidRequestException.class, () -> roleService.delete(null));
    }

    @Test
    void findAll_ShouldReturnAllRoles() {
        when(roleRepository.findAll()).thenReturn(List.of(role));
        when(roleMapper.toDTO(role)).thenReturn(roleDTO);

        Collection<RoleDTO> result = roleService.findAll();

        assertEquals(1, result.size());
        assertEquals(roleDTO, result.iterator().next());
    }
}
