package com.senla.main.controller;

import com.senla.main.dto.SkillDTO;
import com.senla.main.model.Skill;
import com.senla.main.service.SkillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/skills")
public class SkillController {
    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<SkillDTO> createSkill(@RequestBody SkillDTO skillDTO, @RequestParam(required = false) UUID parentId) {
        Skill parent = parentId != null ? skillService.getSkillById(parentId) : null;
        SkillDTO createdSkill = skillService.create(skillDTO, parent);
        return new ResponseEntity<>(createdSkill, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<SkillDTO> getSkillById(@PathVariable UUID id) {
        SkillDTO skillDTO = skillService.getById(id);
        return new ResponseEntity<>(skillDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<SkillDTO> updateSkill(@PathVariable UUID id, @RequestBody SkillDTO skillDTO, @RequestParam(required = false) UUID parentId) {
        Skill parent = parentId != null ? skillService.getSkillById(parentId) : null;
        SkillDTO updatedSkill = skillService.update(id, skillDTO, parent);
        return new ResponseEntity<>(updatedSkill, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable UUID id) {
        skillService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<Collection<SkillDTO>> getAllSkills() {
        Collection<SkillDTO> skills = skillService.findAll();
        return new ResponseEntity<>(skills, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/search")
    public ResponseEntity<Collection<SkillDTO>> getSkillsByName(@RequestParam String name) {
        Collection<SkillDTO> skills = skillService.findByName(name);
        return new ResponseEntity<>(skills, HttpStatus.OK);
    }
}
