package com.senla.main.service;

import com.senla.main.dto.ProfileCreateDTO;
import com.senla.main.dto.ProfileResponseDTO;
import com.senla.main.exception.InvalidRequestException;
import com.senla.main.exception.NotFoundException;
import com.senla.main.mapper.ProfileCreateMapper;
import com.senla.main.mapper.ProfileResponseMapper;
import com.senla.main.model.Profile;
import com.senla.main.model.Role;
import com.senla.main.repository.ProfileRepository;
import com.senla.main.repository.RoleRepository;
import com.senla.main.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ProfileCreateMapper profileCreateMapper;

    @Mock
    private ProfileResponseMapper profileResponseMapper;

    @InjectMocks
    private ProfileService profileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProfile_Success() {
        ProfileCreateDTO profileCreateDTO = new ProfileCreateDTO();

        UUID roleId = UUID.randomUUID();
        profileCreateDTO.setRoleId(roleId);

        Role role = new Role();
        Profile profile = new Profile();

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
        when(profileCreateMapper.toEntity(profileCreateDTO, role)).thenReturn(profile);
        when(profileRepository.save(profile)).thenReturn(profile);
        when(profileResponseMapper.toDTO(profile)).thenReturn(new ProfileResponseDTO());

        ProfileResponseDTO result = profileService.create(profileCreateDTO);

        assertNotNull(result);
        verify(profileRepository, times(1)).save(profile);
    }

    @Test
    void testCreateProfile_InvalidDTO() {
        assertThrows(InvalidRequestException.class, () -> profileService.create(null));
    }

    @Test
    void testGetById_Success() {
        UUID profileId = UUID.randomUUID();
        Profile profile = new Profile();

        when(profileRepository.findById(profileId)).thenReturn(Optional.of(profile));
        when(profileResponseMapper.toDTO(profile)).thenReturn(new ProfileResponseDTO());

        ProfileResponseDTO result = profileService.getById(profileId);

        assertNotNull(result);
        verify(profileRepository, times(1)).findById(profileId);
    }

    @Test
    void testGetById_NotFound() {
        UUID profileId = UUID.randomUUID();
        when(profileRepository.findById(profileId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> profileService.getById(profileId));
    }

    @Test
    void testUpdateProfile_Success() {
        UUID profileId = UUID.randomUUID();
        ProfileCreateDTO profileCreateDTO = new ProfileCreateDTO();

        Role role = new Role();
        Profile existingProfile = new Profile();

        when(profileRepository.findById(profileId)).thenReturn(Optional.of(existingProfile));
        when(roleRepository.findById(profileCreateDTO.getRoleId())).thenReturn(Optional.of(role));
        doNothing().when(profileCreateMapper).updateEntityFromDTO(profileCreateDTO, existingProfile, role);
        when(profileResponseMapper.toDTO(existingProfile)).thenReturn(new ProfileResponseDTO());

        ProfileResponseDTO result = profileService.update(profileId, profileCreateDTO);

        assertNotNull(result);
        verify(profileRepository, times(1)).save(existingProfile);
    }

    @Test
    void testDeleteProfile_Success() {
        UUID profileId = UUID.randomUUID();
        profileService.delete(profileId);
        verify(profileRepository, times(1)).delete(profileId);
    }
}
