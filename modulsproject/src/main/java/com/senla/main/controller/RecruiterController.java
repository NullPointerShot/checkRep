package com.senla.main.controller;

import com.senla.main.dto.RecruiterDTO;
import com.senla.main.service.RecruiterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/recruiters")
public class RecruiterController {
    private final RecruiterService recruiterService;

    public RecruiterController(RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<RecruiterDTO> createRecruiter(@RequestBody RecruiterDTO recruiterDTO) {
        RecruiterDTO createdRecruiter = recruiterService.create(recruiterDTO);
        return new ResponseEntity<>(createdRecruiter, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<RecruiterDTO> getRecruiterById(@PathVariable UUID id) {
        RecruiterDTO recruiterDTO = recruiterService.getById(id);
        return new ResponseEntity<>(recruiterDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<RecruiterDTO> updateRecruiter(@PathVariable UUID id, @RequestBody RecruiterDTO recruiterDTO) {
        RecruiterDTO updatedRecruiter = recruiterService.update(id, recruiterDTO);
        return new ResponseEntity<>(updatedRecruiter, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecruiter(@PathVariable UUID id) {
        recruiterService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<Collection<RecruiterDTO>> getAllRecruiters() {
        return new ResponseEntity<>(recruiterService.findAll(), HttpStatus.OK);
    }
}
