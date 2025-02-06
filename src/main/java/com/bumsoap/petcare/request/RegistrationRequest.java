package com.bumsoap.petcare.request;

import lombok.Data;

@Data
public class RegistrationRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private String mobile;
    private String email;
    private String password;
    private String userType;
    private boolean enabled = false;
    private String specialization;
}
