package com.senla.main.service;

import com.senla.main.dto.RoleDTO;
import com.senla.main.exception.InvalidRequestException;
import com.senla.main.exception.NotFoundException;
import com.senla.main.mapper.RoleMapper;
import com.senla.main.model.Role;
import com.senla.main.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Transactional
    public RoleDTO create(RoleDTO roleDTO) {
        if (Objects.isNull(roleDTO)) {
            throw new InvalidRequestException("RoleDTO cannot be null");
        }

        Role role = roleMapper.toEntity(roleDTO);
        roleRepository.save(role);
        return roleMapper.toDTO(role);
    }

    @Transactional(readOnly = true)
    public RoleDTO getById(UUID id) {
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID cannot be null");
        }

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role with ID " + id + " not found"));

        return roleMapper.toDTO(role);
    }

    @Transactional
    public RoleDTO update(UUID id, RoleDTO roleDTO) {
        if (Objects.isNull(id) || Objects.isNull(roleDTO)) {
            throw new InvalidRequestException("ID and RoleDTO cannot be null");
        }

        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role with ID " + id + " not found"));

        roleMapper.updateEntityFromDTO(roleDTO, existingRole);
        roleRepository.save(existingRole);

        return roleMapper.toDTO(existingRole);
    }

    @Transactional
    public void delete(UUID id) {
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID cannot be null");
        }

        roleRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public Collection<RoleDTO> findAll() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Collection<RoleDTO> findByName(String name) {
        return roleRepository.findByName(name).stream()
                .map(roleMapper::toDTO)
                .collect(Collectors.toList());
    }
}
