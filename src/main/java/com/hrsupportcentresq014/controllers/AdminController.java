package com.hrsupportcentresq014.controllers;

import com.hrsupportcentresq014.dtos.response.CreateHrResponseDTO;
import com.hrsupportcentresq014.exceptions.UserAlreadyExistsException;
import com.hrsupportcentresq014.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController

@RequestMapping("/api")
//@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final EmployeeService employeeService;

    public AdminController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @PostMapping("/registration")
    public ResponseEntity<CreateHrResponseDTO> createHr(@RequestBody CreateHrResponseDTO hrDTO) throws UserAlreadyExistsException {
        return new ResponseEntity<>(employeeService.createHr(hrDTO), HttpStatus.CREATED);
    }
}
