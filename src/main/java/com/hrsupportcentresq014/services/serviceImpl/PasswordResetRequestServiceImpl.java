package com.hrsupportcentresq014.services.serviceImpl;


import com.hrsupportcentresq014.entities.Employee;
import com.hrsupportcentresq014.entities.PasswordResetRequest;
import com.hrsupportcentresq014.repositories.EmployeeRepository;
import com.hrsupportcentresq014.repositories.PasswordResetRequestRepository;
import com.hrsupportcentresq014.services.PasswordResetRequestService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetRequestServiceImpl implements PasswordResetRequestService {
    private final PasswordResetRequestRepository resetRequestRepo;
    private final EmployeeRepository employeeRepo;

    public PasswordResetRequestServiceImpl(PasswordResetRequestRepository resetRequestRepo, EmployeeRepository employeeRepo) {
        this.resetRequestRepo = resetRequestRepo;
        this.employeeRepo = employeeRepo;
    }

    @Override
    public String resetPassword(String email) {
        String resetToken;
        resetToken = UUID.randomUUID().toString();
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(10);
        PasswordResetRequest resetRequest = new PasswordResetRequest();
        resetRequest.setEmail(email);
        resetRequest.setResetToken(resetToken);
        resetRequest.setExpirationDate(expirationDate);
        resetRequestRepo.save(resetRequest);
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