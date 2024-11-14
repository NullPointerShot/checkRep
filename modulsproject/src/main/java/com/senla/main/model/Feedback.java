package com.senla.main.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "Feedback")
@Getter
@Setter
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", referencedColumnName = "id")
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_id", referencedColumnName = "id")
    private Recruiter recruiter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_id", referencedColumnName = "id")
    private Vacancy vacancy;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "status")
    private String status;


}
