package com.bumsoap.petcare.event.listener;

import com.bumsoap.petcare.email.EmailComponent;
import com.bumsoap.petcare.service.token.IServiceVerifToken;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

@RequiredArgsConstructor
public class NotiEventListener implements ApplicationListener<ApplicationEvent> {
    private final EmailComponent emailComponent;
    private final IServiceVerifToken serviceToken;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

    }
}
