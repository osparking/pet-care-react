package com.bumsoap.petcare.event;

import com.bumsoap.petcare.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class UserRegisteredEvent extends ApplicationEvent {
    private User user;

    public UserRegisteredEvent(User user) {
        super(user);
        this.user = user;
    }
}
