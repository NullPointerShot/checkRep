package com.senla.main.model;


import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Skill")
@Getter
@Setter
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Skill parent;

    @ManyToMany(mappedBy = "skills", fetch = FetchType.LAZY)
    private Set<Candidate> candidates = new HashSet<>();

    @ManyToMany(mappedBy = "skills", fetch = FetchType.LAZY)
    private Set<Vacancy> vacancies = new HashSet<>();


}
