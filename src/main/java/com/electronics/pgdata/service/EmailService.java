package com.electronics.pgdata.service;

import com.electronics.pgdata.auth.entity.AccountUser;
import com.electronics.pgdata.model.Receipt;
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

    public void sendReceiptEmail(Receipt receipt) {
        try {
            String subject = "Your Purchase Receipt - Order #" + receipt.getReceiptUuid().substring(0, 8).toUpperCase();
            String senderName = "Eccomercews Support";

            // Build HTML receipt content
            StringBuilder mailContent = new StringBuilder();
            mailContent.append("<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;'>");
            mailContent.append("<h2 style='color: #333; text-align: center;'>Thank you for your purchase!</h2>");
            mailContent.append("<div style='background-color: #f8f9fa; padding: 20px; border-radius: 8px; margin: 20px 0;'>");
            mailContent.append("<h3 style='color: #495057; margin-top: 0;'>Order Details</h3>");
            mailContent.append("<p><strong>Receipt ID:</strong> " + receipt.getReceiptUuid() + "</p>");
            mailContent.append("<p><strong>Order Date:</strong> " + receipt.getCreatedOn().toString() + "</p>");
            mailContent.append("<p><strong>Customer Email:</strong> " + receipt.getUserEmail() + "</p>");
            mailContent.append("</div>");

            // Items table
            mailContent.append("<table style='width: 100%; border-collapse: collapse; margin: 20px 0;'>");
            mailContent.append("<thead>");
            mailContent.append("<tr style='background-color: #e9ecef;'>");
            mailContent.append("<th style='padding: 12px; text-align: left; border: 1px solid #dee2e6;'>Product</th>");
            mailContent.append("<th style='padding: 12px; text-align: center; border: 1px solid #dee2e6;'>Qty</th>");
            mailContent.append("<th style='padding: 12px; text-align: right; border: 1px solid #dee2e6;'>Unit Price</th>");
            mailContent.append("<th style='padding: 12px; text-align: right; border: 1px solid #dee2e6;'>Total</th>");
            mailContent.append("</tr>");
            mailContent.append("</thead>");
            mailContent.append("<tbody>");

            // Add each item
            if (receipt.getReceiptItems() != null) {
                for (var item : receipt.getReceiptItems()) {
                    mailContent.append("<tr>");
                    mailContent.append("<td style='padding: 12px; border: 1px solid #dee2e6;'>" + item.getProductName() + "</td>");
                    mailContent.append("<td style='padding: 12px; text-align: center; border: 1px solid #dee2e6;'>" + item.getQuantity() + "</td>");
                    mailContent.append("<td style='padding: 12px; text-align: right; border: 1px solid #dee2e6;'>$" + item.getUnitPrice() + "</td>");
                    mailContent.append("<td style='padding: 12px; text-align: right; border: 1px solid #dee2e6;'>$" + item.getTotalPrice() + "</td>");
                    mailContent.append("</tr>");
                }
            }

            mailContent.append("</tbody>");
            mailContent.append("</table>");

            // Totals section
            mailContent.append("<div style='background-color: #f8f9fa; padding: 20px; border-radius: 8px; margin: 20px 0;'>");
            if (receipt.getSubtotal() != null) {
                mailContent.append("<p style='margin: 5px 0; text-align: right;'><strong>Subtotal: $" + receipt.getSubtotal() + "</strong></p>");
            }
            if (receipt.getTaxAmount() != null) {
                mailContent.append("<p style='margin: 5px 0; text-align: right;'><strong>Tax: $" + receipt.getTaxAmount() + "</strong></p>");
            }
            mailContent.append("<p style='margin: 10px 0; text-align: right; font-size: 18px; color: #28a745;'><strong>Total: $" + receipt.getTotalAmount() + "</strong></p>");
            mailContent.append("</div>");

            // Footer
            mailContent.append("<div style='text-align: center; margin-top: 30px; padding-top: 20px; border-top: 1px solid #dee2e6;'>");
            mailContent.append("<p style='color: #6c757d; font-size: 14px;'>Thank you for shopping with Eccomercews!</p>");
            mailContent.append("<p style='color: #6c757d; font-size: 12px;'>If you have any questions, please contact our support team.</p>");
            mailContent.append("</div>");
            mailContent.append("</div>");

            // Send email
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(sender, senderName);
            helper.setTo(receipt.getUserEmail());
            helper.setSubject(subject);
            helper.setText(mailContent.toString(), true);

            javaMailSender.send(mimeMessage);

            System.out.println("Receipt email sent successfully to: " + receipt.getUserEmail());

        } catch (Exception e) {
            System.err.println("Error sending receipt email: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to send receipt email", e);
        }
    }

}
