package com.hrsupportcentresq014.controllers;

import com.hrsupportcentresq014.services.PasswordResetRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/password")
public class PasswordResetRequestController {
    private PasswordResetRequestService resetRequestService;

    public PasswordResetRequestController(PasswordResetRequestService resetRequestService) {
        this.resetRequestService = resetRequestService;
    }

    @PostMapping("/forgot-password/{id}")
    public ResponseEntity<String> resetPassword(@RequestParam String email){
        return ResponseEntity.ok(resetRequestService.resetPassword(email));
    }

    @PostMapping("/password-reset-confirmation")
    public ResponseEntity<String> confirmPasswordReset(@RequestParam("resetToken") String resetToken,
                                                       @RequestParam("newPassword") String newPassword){
        return ResponseEntity.ok(resetRequestService.completePasswordReset(resetToken, newPassword));
    }
}
