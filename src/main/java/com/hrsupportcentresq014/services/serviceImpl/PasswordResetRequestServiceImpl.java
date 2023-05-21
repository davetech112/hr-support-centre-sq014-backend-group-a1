package com.hrsupportcentresq014.services.serviceImpl;


import com.hrsupportcentresq014.entities.Employee;
import com.hrsupportcentresq014.entities.PasswordResetRequest;
import com.hrsupportcentresq014.repositories.EmployeeRepository;
import com.hrsupportcentresq014.repositories.PasswordResetRequestRepository;
import com.hrsupportcentresq014.services.MailService;
import com.hrsupportcentresq014.services.PasswordResetRequestService;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetRequestServiceImpl implements PasswordResetRequestService {
    private final PasswordResetRequestRepository resetRequestRepo;
    private final EmployeeRepository employeeRepo;
    private final MailService mailService;

    public PasswordResetRequestServiceImpl(PasswordResetRequestRepository resetRequestRepo, EmployeeRepository employeeRepo, MailService mailService) {
        this.resetRequestRepo = resetRequestRepo;
        this.employeeRepo = employeeRepo;
        this.mailService = mailService;
    }

    @Override
    public String resetPassword(String email) throws MessagingException {
        String resetToken;
        resetToken = UUID.randomUUID().toString();
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(30);
        PasswordResetRequest resetRequest = new PasswordResetRequest();
        resetRequest.setEmail(email);
        resetRequest.setResetToken(resetToken);
        resetRequest.setExpirationDate(expirationDate);
        resetRequestRepo.save(resetRequest);
        String resetEmailUrl = """
                <div>
                <a href="http://localhost:8080/api/password/password-reset-confirmation?resetToken=%s"> Click the following link to reset your password</a>
                </div>
                """.formatted(resetToken);

        mailService.passwordReset(resetEmailUrl, email);
        return "Password reset process initiated";
    }

    @Override
    public String completePasswordReset(String resetToken, String newPassword) {
        PasswordResetRequest resetRequest = resetRequestRepo.findPasswordResetRequestByResetToken(resetToken);
        if((resetRequest == null) || resetRequest.getExpirationDate().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Invalid or expired token");
        }
        else {
            Employee employeeToResetPassword = employeeRepo.findByEmail(resetRequest.getEmail()).orElseThrow(() -> new RuntimeException("Employee not found"));
            employeeToResetPassword.setPassword(newPassword);
            employeeRepo.save(employeeToResetPassword);
        }
        return "Password reset successful";
    }
}