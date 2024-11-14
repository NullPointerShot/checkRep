package com.senla.main.controller;

import com.senla.main.dto.ProfileCreateDTO;
import com.senla.main.dto.ProfileResponseDTO;
import com.senla.main.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProfileResponseDTO> createProfile(@RequestBody ProfileCreateDTO profileCreateDTO) {
        ProfileResponseDTO createdProfile = profileService.create(profileCreateDTO);
        return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponseDTO> getProfileById(@PathVariable UUID id) {
        ProfileResponseDTO profileDTO = profileService.getById(id);
        return new ResponseEntity<>(profileDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProfileResponseDTO> updateProfile(@PathVariable UUID id, @RequestBody ProfileCreateDTO profileCreateDTO) {
        ProfileResponseDTO updatedProfile = profileService.update(id, profileCreateDTO);
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable UUID id) {
        profileService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<Collection<ProfileResponseDTO>> getAllProfiles() {
        return new ResponseEntity<>(profileService.findAll(), HttpStatus.OK);
    }
}
