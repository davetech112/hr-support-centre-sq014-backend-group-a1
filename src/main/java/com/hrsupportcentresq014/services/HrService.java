package com.hrsupportcentresq014.services;

import com.hrsupportcentresq014.dtos.request.CreateStaffRequest;
import com.hrsupportcentresq014.dtos.response.CreateStaffResponse;
import com.hrsupportcentresq014.exceptions.UserAlreadyExistsException;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;

public interface HrService {
    ResponseEntity<CreateStaffResponse> registerStaff(CreateStaffRequest staff) throws UserAlreadyExistsException, MessagingException;
}
