package com.bumsoap.petcare.event.listener;

import com.bumsoap.petcare.email.EmailComponent;
import com.bumsoap.petcare.event.UserRegisteredEvent;
import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.service.token.IServiceVerifToken;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NotiEventListener implements ApplicationListener<ApplicationEvent> {
    private final EmailComponent emailComponent;
    private final IServiceVerifToken serviceToken;
    @Value("${frontend.base.url}")
    private String frontendBaseUrl;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        Object source = event.getSource();
        if (source instanceof User) {
            UserRegisteredEvent usrRegEvent = (UserRegisteredEvent) event;
            handleSendVerifEmail(usrRegEvent);
        }
    }

    private void handleSendVerifEmail(UserRegisteredEvent event) {
        User user = event.getUser();
        String vToken = UUID.randomUUID().toString();
        serviceToken.saveUserVerifToken(vToken, user);
        String verifUrl = frontendBaseUrl + "/email_verify?token=" + vToken;
        try {
            sendVerifEmail(user, verifUrl);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
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

    private void newAppointmentBooked(User user, String url)
            throws MessagingException, UnsupportedEncodingException {
        String subject = "새 진료 예약 등록";
        String senderName = "팻 돌봄이";
        StringBuffer content = new StringBuffer("<p>안녕하세요? '");
        content.append(user.getLastName());
        content.append(user.getFirstName());
        content.append("' 유저님</p>");
        content.append("<p>당신에게 새 예약 일정이 잡혔습니다:</p>");
        content.append("<a href=\"");
        content.append(url);
        content.append("\">자세한 내용은 병원 포털에 접속하여 열람하십시오.</a><br/>");
        content.append("<p>행운을 빕니다.<br> 팻 케어 이메일 서비스");
        emailComponent.sendEmail(user.getEmail(), subject, senderName,
                content.toString());
    }
}
