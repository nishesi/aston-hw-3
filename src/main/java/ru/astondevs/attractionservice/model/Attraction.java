package ru.astondevs.attractionservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "attraction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attraction_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "settlement_id")
    private Settlement settlement;

    @ManyToMany(mappedBy = "attractions")
    private Set<Service> services;

    public enum Type {
        PALACE,
        PARK,
        MUSEUM,
        ARCHAEOLOGICAL_SITE,
        NATURE_RESERVE,
    }
}