package com.hrsupportcentresq014.services;

import jakarta.mail.MessagingException;

public interface PasswordResetRequestService {
    String resetPassword(String email) throws MessagingException;
    String completePasswordReset(String resetToken, String newPassword);
}
