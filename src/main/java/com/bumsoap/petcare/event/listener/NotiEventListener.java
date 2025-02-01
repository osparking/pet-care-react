package com.bumsoap.petcare.event.listener;

import com.bumsoap.petcare.email.EmailComponent;
import com.bumsoap.petcare.service.token.IServiceVerifToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotiEventListener implements ApplicationListener<ApplicationEvent> {
    private final EmailComponent emailComponent;
    private final IServiceVerifToken serviceToken;
    @Value("${frontend.base.url}")
    private String frontendBaseUrl;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

    }
}
