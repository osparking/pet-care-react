package com.bumsoap.petcare.request;

import lombok.Data;

@Data
public class RequestChangePwd {
    private String currentPwd;
    private String newPwd;
    private String confirmPwd;
}
