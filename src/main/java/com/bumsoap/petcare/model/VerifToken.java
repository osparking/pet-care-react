package com.bumsoap.petcare.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Date;

public class VerifToken {
    private Long id;
    private String token;
    private Date expireDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
