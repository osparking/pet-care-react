package com.bumsoap.petcare.response;

import lombok.Data;

@Data
public class JwtResponse {
    private String message;
    private Object data;
}
