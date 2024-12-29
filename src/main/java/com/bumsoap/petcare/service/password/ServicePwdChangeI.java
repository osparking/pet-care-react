package com.bumsoap.petcare.service.password;

import com.bumsoap.petcare.request.RequestChangePwd;

public interface ServicePwdChangeI {
    void changePwd(Long userId, RequestChangePwd request);
}
