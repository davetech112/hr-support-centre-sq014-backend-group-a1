package com.hrsupportcentresq014.services;

public interface PasswordResetRequestService {
    String resetPassword(String email);
    String completePasswordReset(String resetToken, String newPassword);
}
