package com.senla.main.controller;

import com.senla.main.dto.JobOfferDTO;
import com.senla.main.service.JobOfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/job-offers")
public class JobOfferController {
    private final JobOfferService jobOfferService;

    public JobOfferController(JobOfferService jobOfferService) {
        this.jobOfferService = jobOfferService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<JobOfferDTO> createJobOffer(@RequestBody JobOfferDTO jobOfferDTO) {
        JobOfferDTO createdJobOffer = jobOfferService.create(jobOfferDTO);
        return new ResponseEntity<>(createdJobOffer, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<JobOfferDTO> getJobOfferById(@PathVariable UUID id) {
        JobOfferDTO jobOfferDTO = jobOfferService.getById(id);
        return new ResponseEntity<>(jobOfferDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<JobOfferDTO> updateJobOffer(@PathVariable UUID id, @RequestBody JobOfferDTO jobOfferDTO) {
        JobOfferDTO updatedJobOffer = jobOfferService.update(id, jobOfferDTO);
        return new ResponseEntity<>(updatedJobOffer, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobOffer(@PathVariable UUID id) {
        jobOfferService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<Collection<JobOfferDTO>> getAllJobOffers() {
        return new ResponseEntity<>(jobOfferService.findAll(), HttpStatus.OK);
    }
}
