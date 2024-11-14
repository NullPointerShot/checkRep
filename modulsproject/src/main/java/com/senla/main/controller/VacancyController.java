package com.senla.main.controller;

import com.senla.main.dto.VacancyDTO;
import com.senla.main.model.Company;
import com.senla.main.model.Skill;
import com.senla.main.service.CompanyService;
import com.senla.main.service.SkillService;
import com.senla.main.service.VacancyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/vacancies")
public class VacancyController {
    private final VacancyService vacancyService;
    private final CompanyService companyService;
    private final SkillService skillService;

    public VacancyController(VacancyService vacancyService, CompanyService companyService, SkillService skillService) {
        this.vacancyService = vacancyService;
        this.companyService = companyService;
        this.skillService = skillService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<VacancyDTO> createVacancy(@RequestBody VacancyDTO vacancyDTO, @RequestParam UUID companyId, @RequestParam Set<UUID> skillIds) {
        Company company = companyService.getEntityById(companyId);
        Set<Skill> skills = skillService.getSkillsByIds(skillIds);
        VacancyDTO createdVacancy = vacancyService.create(vacancyDTO, company, skills);
        return new ResponseEntity<>(createdVacancy, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<VacancyDTO> getVacancyById(@PathVariable UUID id) {
        VacancyDTO vacancyDTO = vacancyService.getById(id);
        return new ResponseEntity<>(vacancyDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<VacancyDTO> updateVacancy(@PathVariable UUID id, @RequestBody VacancyDTO vacancyDTO, @RequestParam UUID companyId, @RequestParam Set<UUID> skillIds) {
        Company company = companyService.getEntityById(companyId);
        Set<Skill> skills = skillService.getSkillsByIds(skillIds);
        VacancyDTO updatedVacancy = vacancyService.update(id, vacancyDTO, company, skills);
        return new ResponseEntity<>(updatedVacancy, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVacancy(@PathVariable UUID id) {
        vacancyService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<Collection<VacancyDTO>> getAllVacancies() {
        Collection<VacancyDTO> vacancies = vacancyService.findAll();
        return new ResponseEntity<>(vacancies, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/company/{companyId}")
    public ResponseEntity<Collection<VacancyDTO>> getVacanciesByCompanyId(@PathVariable UUID companyId) {
        Collection<VacancyDTO> vacancies = vacancyService.findVacanciesByCompanyId(companyId);
        return new ResponseEntity<>(vacancies, HttpStatus.OK);
    }
}
