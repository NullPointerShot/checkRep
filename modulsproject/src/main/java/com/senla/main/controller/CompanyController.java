package com.senla.main.controller;

import com.senla.main.dto.CompanyDTO;
import com.senla.main.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO companyDTO) {
        CompanyDTO createdCompany = companyService.create(companyDTO);
        return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable UUID id) {
        CompanyDTO companyDTO = companyService.getById(id);
        return new ResponseEntity<>(companyDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable UUID id, @RequestBody CompanyDTO companyDTO) {
        CompanyDTO updatedCompany = companyService.update(id, companyDTO);
        return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable UUID id) {
        companyService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/name/{name}")
    public ResponseEntity<Collection<CompanyDTO>> getCompaniesByName(@PathVariable String name) {
        return new ResponseEntity<>(companyService.findByName(name), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}/vacancies")
    public ResponseEntity<CompanyDTO> getCompanyByIdWithVacancies(@PathVariable UUID id) {
        CompanyDTO companyDTO = companyService.getByIdWithVacanciesAndRecruiters(id);
        return new ResponseEntity<>(companyDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}/vacancies-criteria")
    public ResponseEntity<CompanyDTO> getCompanyByIdWithVacanciesCriteria(@PathVariable UUID id) {
        CompanyDTO companyDTO = companyService.getByIdWithVacanciesAndRecruitersCriteria(id);
        return new ResponseEntity<>(companyDTO, HttpStatus.OK);
    }
}
