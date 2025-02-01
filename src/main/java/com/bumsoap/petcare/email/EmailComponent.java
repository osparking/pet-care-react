package com.bumsoap.petcare.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailComponent {
    private JavaMailSender mailSender;
    public void sendEmail(String to, String subject, String senderName,
                          String mailContent) {

    }
}
