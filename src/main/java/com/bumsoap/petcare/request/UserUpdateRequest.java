package com.bumsoap.petcare.request;

import com.bumsoap.petcare.model.Photo;
import lombok.Data;

@Data
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String gender;
    private String mobile;
    private String specialization;
    private Photo photo;
}
