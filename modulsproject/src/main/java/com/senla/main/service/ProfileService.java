package com.senla.main.service;

import com.senla.main.repository.ProfileRepository;
import com.senla.main.dto.ProfileCreateDTO;
import com.senla.main.dto.ProfileResponseDTO;
import com.senla.main.exception.InvalidRequestException;
import com.senla.main.exception.NotFoundException;
import com.senla.main.mapper.ProfileCreateMapper;
import com.senla.main.mapper.ProfileResponseMapper;
import com.senla.main.model.Profile;
import com.senla.main.model.Role;
import com.senla.main.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Objects;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final RoleRepository roleRepository;
    private final ProfileCreateMapper profileCreateMapper;
    private final ProfileResponseMapper profileResponseMapper;

    public ProfileService(ProfileRepository profileRepository,
                          RoleRepository roleRepository,
                          ProfileCreateMapper profileCreateMapper,
                          ProfileResponseMapper profileResponseMapper) {
        this.profileRepository = profileRepository;
        this.roleRepository = roleRepository;
        this.profileCreateMapper = profileCreateMapper;
        this.profileResponseMapper = profileResponseMapper;
    }

    @Transactional
    public ProfileResponseDTO create(ProfileCreateDTO profileCreateDTO) {
        if (Objects.isNull(profileCreateDTO)) {
            throw new InvalidRequestException("ProfileCreateDTO cannot be null");
        }

        Role role = roleRepository.findById(profileCreateDTO.getRoleId())
                .orElseThrow(() -> new NotFoundException("Role with ID " + profileCreateDTO.getRoleId() + " not found"));

        Profile profile = profileCreateMapper.toEntity(profileCreateDTO, role);
        profileRepository.save(profile);
        return profileResponseMapper.toDTO(profile);
    }

    @Transactional(readOnly = true)
    public ProfileResponseDTO getById(UUID id) {
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID cannot be null");
        }

        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profile with ID " + id + " not found"));

        return profileResponseMapper.toDTO(profile);
    }

    @Transactional
    public ProfileResponseDTO update(UUID id, ProfileCreateDTO profileCreateDTO) {
        if (Objects.isNull(profileCreateDTO)) {
            throw new InvalidRequestException("ProfileCreateDTO cannot be null");
        }

        Profile existingProfile = profileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profile with ID " + id + " not found"));

        Role role = roleRepository.findById(profileCreateDTO.getRoleId())
                .orElseThrow(() -> new NotFoundException("Role with ID " + profileCreateDTO.getRoleId() + " not found"));

        profileCreateMapper.updateEntityFromDTO(profileCreateDTO, existingProfile, role);
        profileRepository.save(existingProfile);

        return profileResponseMapper.toDTO(existingProfile);
    }

    @Transactional
    public void delete(UUID id) {
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID cannot be null");
        }

        profileRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public Collection<ProfileResponseDTO> findAll() {
        return profileRepository.findAll().stream()
                .map(profileResponseMapper::toDTO)
                .collect(Collectors.toList());
    }
}
