package com.senla.main.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;import java.util.UUID;

@Entity
@Table(name = "Job_offer")
@Getter
@Setter
public class JobOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", referencedColumnName = "id")
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_id", referencedColumnName = "id")
    private Vacancy vacancy;

    @Column(name = "salary")
    private Integer salary;

    @Column(name = "status")
    private String status;


}
