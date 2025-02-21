package com.bumsoap.petcare.event.listener;

import com.bumsoap.petcare.email.EmailComponent;
import com.bumsoap.petcare.event.*;
import com.bumsoap.petcare.model.Appointment;
import com.bumsoap.petcare.model.User;
import com.bumsoap.petcare.service.token.IServiceVerifToken;
import com.bumsoap.petcare.utils.FeedbackMessage;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
        switch (event) {
            case UserRegisteredEvent registerE -> {
                handleSendVerifEmail( registerE);
            }
            case AppointmentBooked booked-> {
                try {
                    handleAppointBookedNoti(booked);
                } catch (MessagingException | UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
            case AppointApprovedE approveE -> {
                try {
                    handleAppointApprovedNoti(approveE);
                } catch (MessagingException | UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
            case AppointDeclinedE declineE -> {
                try {
                    handleAppointDeclinedNoti(declineE);
                } catch (MessagingException | UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
            default ->  {
                break;
            }
        }
    }

    private void handleSendVerifEmail(UserRegisteredEvent event) {
        saveToken_sendEmail(event.getUser());
    }

    /**
     * 유저에게 만료 시점이 지정된 토큰을 부여하고 그 정보를 DB에 저장하고,
     * 유저가 등록한 이메일을 검증할 수 있도록, 부여된 토큰을 이메일로 보낸다.
     * @param user 토큰을 부여받을 유저
     */
    @Transactional
    public void saveToken_sendEmail(User user) {
        String vToken = UUID.randomUUID().toString();
        Long vtId = serviceToken.saveUserVerifToken(vToken, user);
        serviceToken.deleteVeToken(vtId, user.getId());
        String verifUrl = frontendBaseUrl + "/email_verify?token=" + vToken;
        try {
            sendVerifEmail(user, verifUrl);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendVerifEmail(User vet, String verifUrl)
            throws MessagingException, UnsupportedEncodingException {
        String subject = "자신 이메일을 검증하세요.";
        String senderName = "팻 돌봄이";
        StringBuffer content = new StringBuffer("<p>안녕하세요? '");
        content.append(vet.getLastName());
        content.append(vet.getFirstName());
        content.append("' 수의사님</p>");
        content.append("<p>저희 사이트에 등록하시는데 감사드립니다.");
        content.append("부디 다음 링크를 따르셔서 등록을 완료하십시오.</p>");
        content.append("<a href=\"");
        content.append(verifUrl);
        content.append("\">이메일 확인</a>");
        content.append("<p>고맙습니다.<br> 팻 케어 서비스");
        emailComponent.sendEmail(vet.getEmail(), subject, senderName,
                content.toString());
    }

    private void newAppointmentBooked(User vet, String url)
            throws MessagingException, UnsupportedEncodingException {
        String subject = "새 진료 예약 등록";
        String senderName = "팻 돌봄이";
        StringBuffer content = new StringBuffer("<p>안녕하세요? '");
        content.append(vet.getLastName());
        content.append(vet.getFirstName());
        content.append("' 수의사님</p>");
        content.append("<p>당신에게 새 예약 일정이 잡혔습니다:</p>");
        content.append("<a href=\"");
        content.append(url);
        content.append("\">자세한 내용은 병원 포털에 접속하여 열람하십시오.</a><br/>");
        content.append("<p>행운을 빕니다.<br> 팻 케어 서비스");
        emailComponent.sendEmail(vet.getEmail(), subject, senderName,
                content.toString());
    }

    private void handleAppointBookedNoti(AppointmentBooked apoBooked)
            throws MessagingException, UnsupportedEncodingException {
        Appointment appointment = apoBooked.getAppointment();
        User vet = appointment.getVeterinarian();
        newAppointmentBooked(vet, frontendBaseUrl);
    }

    private void sendAppointApprovedNoti(User user, String url)
            throws MessagingException, UnsupportedEncodingException {
        String subject = "진료 예약 승인 통지";
        String senderName = "팻 돌봄이";
        StringBuffer content = new StringBuffer("<p>안녕하세요? '");
        content.append(user.getLastName());
        content.append(user.getFirstName());
        content.append("' 고객님</p>");
        content.append("<p>신청하신 진료 예약이 승인되었습니다:</p>");
        content.append("<a href=\"");
        content.append(url);
        content.append("\">자세한 내용 및 수의사 정보</a>");
        content.append("는 병원 포털에 접속하여 열람하실 수 있습니다.<br/>");
        content.append("<p>행운을 빕니다.<br> 팻 케어 서비스");
        emailComponent.sendEmail(user.getEmail(), subject, senderName,
                content.toString());
    }

    private void handleAppointApprovedNoti(AppointApprovedE approved)
            throws MessagingException, UnsupportedEncodingException {
        Appointment appointment = approved.getAppointment();
        User patient = appointment.getPatient();
        sendAppointApprovedNoti(patient, frontendBaseUrl);
    }

    private void sendAppointDeclinedNoti(User user, String url)
            throws MessagingException, UnsupportedEncodingException {
        String subject = "진료 예약 거절 통지";
        String senderName = "팻 돌봄이";
        StringBuffer content = new StringBuffer("<p>안녕하세요? '");
        content.append(user.getLastName());
        content.append(user.getFirstName());
        content.append("' 고객님</p>");
        content.append("<p>죄송스럽게도 진료 예약이 거절되었음을 알려드립니다.<br/> ");
        content.append("불편하시더라도 다른 일정을 잡아 재 예약해 주십시오.</p>");
        content.append("<a href=\"");
        content.append(url);
        content.append("\">자세한 내용 및 수의사 정보</a>");
        content.append("는 병원 포털에 접속하여 열람하실 수 있습니다.<br/>");
        content.append("<p>행운을 빕니다.<br> 팻 케어 서비스");
        emailComponent.sendEmail(user.getEmail(), subject, senderName,
                content.toString());
    }

    private void handleAppointDeclinedNoti(AppointDeclinedE declined)
            throws MessagingException, UnsupportedEncodingException {
        Appointment appointment = declined.getAppointment();
        User patient = appointment.getPatient();
        sendAppointDeclinedNoti(patient, frontendBaseUrl);
    }

    private void sendPwdResetEmail(User user, String url) throws
            MessagingException, UnsupportedEncodingException {
        String subject = "비밀번호 리셋 요청 인증";
        String senderName = "팻 돌봄이";
        StringBuffer content = new StringBuffer("<p>안녕하세요? '");
        content.append(user.getLastName());
        content.append(user.getFirstName());
        content.append("' 유저님</p>");
        content.append("<p>유저님은 자기의 비밀번호를 재설정해 달라고 요구하셨습니다.");
        content.append("<br/>이를 진행하려면, 아래 링크를 클릭하십시오.</p>");
        content.append("<a href=\"");
        content.append(url);
        content.append("\">패스워드 재설정</a>");
        content.append(". 만일 재설정을 요청하지 않았으면 이 메일은 무시하세요.<br/>");
        content.append("<p>당신 애완동물의 후원자.<br> 팻 케어 서비스 올림.");
        emailComponent.sendEmail(user.getEmail(), subject, senderName,
                content.toString());
    }
}
