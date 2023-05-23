package com.hrsupportcentresq014.controllers;


import com.hrsupportcentresq014.dtos.request.AdminRequest;
import com.hrsupportcentresq014.dtos.response.AdminResponse;
import com.hrsupportcentresq014.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final UserService userService;
    @PostMapping(name = "RegisterAdmin", value = "/register")
    public ResponseEntity<AdminResponse> registerAdmin(@Valid @RequestBody AdminRequest adminRequest){
        log.info("Registering Admin with payload {}", adminRequest);
        AdminResponse adminResponse = userService.register(adminRequest);
        return new ResponseEntity<>(adminResponse, HttpStatus.CREATED);
    }
}
