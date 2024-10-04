package com.bumsoap.petcare.service.user;

import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.request.RegistrationRequest;

public class UserAttributesMapper {
    public void setCommonAttributes(RegistrationRequest source,
                                    User target) {
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setGender(source.getGender());
        target.setPhoneNumber(source.getPhoneNumber());
        target.setEmail(source.getEmail());
        target.setPassword(source.getPassword());
        target.setEnabled(source.isEnabled());
        target.setUserType(source.getUserType());
    }
}
