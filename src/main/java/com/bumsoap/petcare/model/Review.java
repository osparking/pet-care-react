package com.bumsoap.petcare.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private int stars;

    @ManyToOne
    @JoinColumn(name = "vet_id")
    private User veterinarian;

    @ManyToOne
    @JoinColumn(name = "pat_id")
    private User patient;
}
