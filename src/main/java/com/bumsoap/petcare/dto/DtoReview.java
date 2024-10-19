package com.bumsoap.petcare.dto;

import lombok.Data;

@Data
public class DtoReview {
    private Long id;
    private int stars;
    private String comment;

    private Long vetId;
    private String vetName;
    private Long patientId;
    private String patientName;
    private byte[] vetImage;
    private byte[] patientImage;
}
