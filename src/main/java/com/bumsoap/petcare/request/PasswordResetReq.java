package com.bumsoap.petcare.request;

import lombok.Data;

@Data
public class PasswordResetReq {
    private String token;
    private String newPassword;
}
