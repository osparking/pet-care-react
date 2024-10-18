package com.bumsoap.petcare.dto;

import lombok.Data;

@Data
public class DtoReview {
    private Long id;
    private int stars;
    private String comment;
}
