package com.bumsoap.petcare.dto;

import lombok.Data;

@Data
public class DtoUser {
    private long id;
    private String firstName;
    private String lastName;
    private String gender;
    private String phoneNumber;
    private String email;
    private String userType;
    private Boolean enabled;
    private String specialization;
}
