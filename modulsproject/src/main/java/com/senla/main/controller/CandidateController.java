package com.senla.main.controller;

import com.senla.main.dto.CandidateDTO;
import com.senla.main.service.CandidateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidates")
public class CandidateController {
    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CandidateDTO> createCandidate(@RequestBody CandidateDTO candidateDTO) {
        CandidateDTO createdCandidate = candidateService.create(candidateDTO);
        return new ResponseEntity<>(createdCandidate, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<CandidateDTO> getCandidateById(@PathVariable UUID id) {
        CandidateDTO candidateDTO = candidateService.getById(id);
        return new ResponseEntity<>(candidateDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CandidateDTO> updateCandidate(@PathVariable UUID id, @RequestBody CandidateDTO candidateDTO) {
        CandidateDTO updatedCandidate = candidateService.update(id, candidateDTO);
        return new ResponseEntity<>(updatedCandidate, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCandidate(@PathVariable UUID id) {
        candidateService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Collection<CandidateDTO>> getAllCandidates() {
        Collection<CandidateDTO> candidates = candidateService.findAll();
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }

    @GetMapping("/experience/{experience}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<CandidateDTO>> getCandidatesByExperience(@PathVariable String experience) {
        List<CandidateDTO> candidates = candidateService.findByExperience(experience);
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }

    @GetMapping("/skill/{skillName}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<CandidateDTO>> getCandidatesBySkill(@PathVariable String skillName) {
        List<CandidateDTO> candidates = candidateService.findBySkill(skillName);
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }

    @GetMapping("/{id}/skills")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<CandidateDTO> getCandidateWithSkills(@PathVariable UUID id) {
        CandidateDTO candidateDTO = candidateService.getByIdWithSkills(id);
        return new ResponseEntity<>(candidateDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}/profile")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<CandidateDTO> getCandidateWithSkillsAndProfile(@PathVariable UUID id) {
        CandidateDTO candidateDTO = candidateService.getByIdWithSkillsAndProfile(id);
        return new ResponseEntity<>(candidateDTO, HttpStatus.OK);
    }
}
