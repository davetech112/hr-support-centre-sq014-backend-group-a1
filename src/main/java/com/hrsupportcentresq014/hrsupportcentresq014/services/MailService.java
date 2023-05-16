package com.hrsupportcentresq014.hrsupportcentresq014.services;

import jakarta.mail.MessagingException;

public interface MailService {
    void sendMailTest(String fullName, String subject, String email, String text,String messageSubject) throws MessagingException;
    void sendAccountActivation(String firstname, String lastname, String email, String activated_password) throws MessagingException;
    void passwordReset(String fullName, String resetUrl, String altEmail) throws MessagingException;
}
