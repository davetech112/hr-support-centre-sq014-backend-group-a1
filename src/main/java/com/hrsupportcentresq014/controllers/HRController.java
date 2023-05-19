package com.hrsupportcentresq014.controllers;

import com.hrsupportcentresq014.dtos.request.CreateStaffRequest;
import com.hrsupportcentresq014.dtos.response.CreateStaffResponse;
import com.hrsupportcentresq014.exceptions.UserAlreadyExistsException;
import com.hrsupportcentresq014.services.HrService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hr")
//@PreAuthorize("hasRole('HR')")
public class HRController {
  //todo This is a must read for guys working on these end points read below
// todo: if you want to mark any endpoint for fine-grained authorization refer to the Enum roles to see the specific roles
//  and the Enum permissions that the HR has for that particular endpoint then you can mark with @PreAuthorize("hasAuthority('hr:create')")
    private final HrService hrService;
    @PostMapping("/register")
    public ResponseEntity<CreateStaffResponse> registerStaffByHR(@Valid @RequestBody CreateStaffRequest staff) throws UserAlreadyExistsException, MessagingException {
        return hrService.registerStaff(staff);
    }
}
