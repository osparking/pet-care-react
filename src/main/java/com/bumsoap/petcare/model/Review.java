package com.bumsoap.petcare.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Review {
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
