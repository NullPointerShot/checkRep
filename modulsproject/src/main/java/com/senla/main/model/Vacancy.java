package com.senla.main.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Vacancy")
@Getter
@Setter
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @Column(name = "salary")
    private Integer salary;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Vacancy_to_skill",
            joinColumns = @JoinColumn(name = "vacancy_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills = new HashSet<>();

    @OneToMany(mappedBy = "vacancy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Feedback> feedbacks = new HashSet<>();

    @OneToMany(mappedBy = "vacancy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<JobOffer> jobOffers = new HashSet<>();

}
