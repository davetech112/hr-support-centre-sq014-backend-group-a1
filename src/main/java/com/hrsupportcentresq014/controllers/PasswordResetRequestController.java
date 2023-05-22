package com.hrsupportcentresq014.controllers;

import com.hrsupportcentresq014.services.PasswordResetRequestService;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/password")
public class PasswordResetRequestController {
    private final PasswordResetRequestService resetRequestService;

    public PasswordResetRequestController(PasswordResetRequestService resetRequestService) {
        this.resetRequestService = resetRequestService;
    }

    @PostMapping("/forgot-password/")
    public ResponseEntity<String> resetPassword(@RequestParam String email) throws MessagingException {
        return resetRequestService.resetPassword(email);
    }

    @PostMapping("/password-reset-confirmation")
    public ResponseEntity<String> confirmPasswordReset(@RequestParam("resetToken") String resetToken,
                                                       @RequestParam("newPassword") String newPassword){
        return resetRequestService.completePasswordReset(resetToken, newPassword);
    }
}
