package com.Vikram.d45.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    // Generic method to send emails
    public void sendEmail(String toEmail, String subject, String message) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(toEmail);
        email.setSubject(subject);
        email.setText(message);
        javaMailSender.send(email);
    }

    // Send Approval Email
    public void sendApprovalEmail(String toEmail) {
        sendEmail(toEmail, "Leave Approved", "Your leave application has been approved!");
    }

    // Send Rejection Email
    public void sendRejectionEmail(String toEmail) {
        sendEmail(toEmail, "Leave Rejected", "Your leave application has been rejected!");
    }
}
