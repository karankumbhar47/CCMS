package com.iitbh.ccms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailService {

    private final JavaMailSender emailSender;

    @Autowired
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Async
    public void sendSimpleMessage(String to, String subject, String text) throws MailException {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("ccmsteam7@gmail.com"); // Set your email address here
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
        } catch (MailException e) {
            // Handle MessagingException (e.g., issues with the email message)
            System.out.println("Failed to send email. MailException: " + e.getMessage());
            System.out.println("" + e.getCause());
            throw e;
        }
    }
}
