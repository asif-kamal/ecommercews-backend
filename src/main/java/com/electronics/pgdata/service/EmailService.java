package com.electronics.pgdata.service;

import com.electronics.pgdata.auth.entity.AccountUser;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(sender);
            helper.setTo(accountUser.getEmail());
            helper.setSubject(subject);
            helper.setText(mailContent, true); // true enables HTML content

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            return "Error while sending mail ..";

        }
        return "Mail Sent Success!";
    }
}
