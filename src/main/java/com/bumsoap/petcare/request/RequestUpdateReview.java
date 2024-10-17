package com.bumsoap.petcare.request;

import lombok.Data;

@Data
public class RequestUpdateReview {
    private int stars;
    private String comment;
}
