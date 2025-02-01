package com.bumsoap.petcare.email;

import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Component
public class EmailComponent {
    private JavaMailSender mailSender = null;
    public void sendEmail(String to, String subject, String senderName,
                          String mailContent) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom(EmailProperties.DEFAULT_USERNAME, senderName);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }

    private JavaMailSender createMailSender() {
        JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost(EmailProperties.DEFAULT_HOST);
        mailSenderImpl.setPort(EmailProperties.DEFAULT_PORT);
        mailSenderImpl.setUsername(EmailProperties.DEFAULT_USERNAME);
        mailSenderImpl.setPassword(EmailProperties.DEFAULT_PASSWORD);
        Properties props = mailSenderImpl.getJavaMailProperties();
        props.put("mail.smtp.auth", EmailProperties.DEFAULT_AUTH);
        props.put("mail.smtp.starttls.enable", EmailProperties.DEFAULT_STARTTLS);
        return mailSenderImpl;
    }

    @PostConstruct
    private void init() {
        mailSender = createMailSender();
    }
}
