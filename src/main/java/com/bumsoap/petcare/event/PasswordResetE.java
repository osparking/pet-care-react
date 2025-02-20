package com.bumsoap.petcare.event;

import com.bumsoap.petcare.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class PasswordResetE extends ApplicationEvent {
    private final User user;

    public PasswordResetE(Object source, User user) {
        super(source);
        this.user = user;
    }
}
