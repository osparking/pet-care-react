package com.bumsoap.petcare.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DtoUserAppo {
    private String lastName;
    private String firstName;
    private String gender;
    private String mobile;
    private String email;
}
