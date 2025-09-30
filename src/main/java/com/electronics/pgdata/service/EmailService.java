package com.electronics.pgdata.service;

import com.electronics.pgdata.auth.entity.AccountUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String sender;
    @Autowired
    private JavaMailSender javaMailSender;

    public String sendEmail(AccountUser accountUser) {
        String subject = "Welcome to Eccomercews. Please verify your email.";
        String senderName = "Eccomercews Support";
        String mailContent = "<p>Dear " + accountUser.getFirstName() + ", your verification code is: "
                + accountUser.getVerificationCode()+ "</p>";
        mailContent += "<br />";
        mailContent += "<p>Please enter this code to verify your account.</p>";

        try {

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(accountUser.getEmail());
            mailMessage.setText(mailContent);
            mailMessage.setSubject(subject);
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            return "Error while sending mail ..";

        }
        return "Mail Sent Success!";
    }
}
