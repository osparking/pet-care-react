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

    private void sendVerifEmail(User user, String verifUrl)
            throws MessagingException, UnsupportedEncodingException {
        String subject = "자신 이메일을 검증하세요.";
        String senderName = "팻 돌봄이";
        StringBuffer content = new StringBuffer("<p>안녕하세요? '");
        content.append(user.getLastName());
        content.append(user.getFirstName());
        content.append("' 고객님</p>");
        content.append("<p>저희 사이트에 등록하시는데 감사드립니다.");
        content.append("부디 다음 링크를 따르셔서 등록을 완료하십시오.</p>");
        content.append("<a href=\"");
        content.append(verifUrl);
        content.append("\">이메일 확인</a>");
        content.append("<p>고맙습니다.<br> 팻 케어 이메일 서비스");
        emailComponent.sendEmail(user.getEmail(), subject, senderName,
                content.toString());
    }
}
