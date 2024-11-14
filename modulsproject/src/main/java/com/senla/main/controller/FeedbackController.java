package com.senla.main.controller;

import com.senla.main.dto.FeedbackDTO;
import com.senla.main.service.FeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {
    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<FeedbackDTO> createFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        FeedbackDTO createdFeedback = feedbackService.create(feedbackDTO);
        return new ResponseEntity<>(createdFeedback, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDTO> getFeedbackById(@PathVariable UUID id) {
        FeedbackDTO feedbackDTO = feedbackService.getById(id);
        return new ResponseEntity<>(feedbackDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<FeedbackDTO> updateFeedback(@PathVariable UUID id, @RequestBody FeedbackDTO feedbackDTO) {
        FeedbackDTO updatedFeedback = feedbackService.update(id, feedbackDTO);
        return new ResponseEntity<>(updatedFeedback, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable UUID id) {
        feedbackService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<Collection<FeedbackDTO>> getFeedbacksByCandidateId(@PathVariable UUID candidateId) {
        return new ResponseEntity<>(feedbackService.findByCandidateId(candidateId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}/details")
    public ResponseEntity<FeedbackDTO> getFeedbackByIdWithDetails(@PathVariable UUID id) {
        FeedbackDTO feedbackDTO = feedbackService.getByIdWithCandidateAndVacancy(id);
        return new ResponseEntity<>(feedbackDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}/details-criteria")
    public ResponseEntity<FeedbackDTO> getFeedbackByIdWithDetailsCriteria(@PathVariable UUID id) {
        FeedbackDTO feedbackDTO = feedbackService.getByIdWithCandidateAndVacancyCriteria(id);
        return new ResponseEntity<>(feedbackDTO, HttpStatus.OK);
    }
}
