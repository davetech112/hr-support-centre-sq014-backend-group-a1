package com.hrsupportcentresq014.hrsupportcentresq014.services;

import jakarta.mail.MessagingException;

public interface MailService {
    void sendMailTest(String senderEmail,String messageSubject, String messageBody) throws MessagingException;
    void sendAccountActivation(String receiverEmail, String activationUrl) throws MessagingException;
    void passwordReset(String resetUrl, String receiverEmail) throws MessagingException;
}
