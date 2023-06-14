package com.hrsupportcentresq014.controllers;

import com.hrsupportcentresq014.dtos.request.ChangePasswordRequest;
import com.hrsupportcentresq014.dtos.request.EmployeeProfileRequest;
import com.hrsupportcentresq014.dtos.request.NominationApprovalRequest;
import com.hrsupportcentresq014.dtos.request.NominationRequest;
import com.hrsupportcentresq014.exceptions.AwardsNotFoundException;
import com.hrsupportcentresq014.exceptions.DuplicateProcessException;
import com.hrsupportcentresq014.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/staff")
@Log4j2
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PutMapping("/profile")
    public ResponseEntity<EmployeeProfileRequest> updateEmployeeProfile(@RequestBody @Valid EmployeeProfileRequest employeeProfileRequest) {
        log.info("Updating Employee Profile");
        EmployeeProfileRequest response = employeeService.updateEmployeeProfile(employeeProfileRequest);
        log.info("Your Profile has been successfully updated");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/add-pic")
    public ResponseEntity<String> uploadProfilePic(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        String imageURL = employeeService.uploadImage(multipartFile);
        return ResponseEntity.ok(imageURL);
    }

    @PostMapping("/add-resume")
        public ResponseEntity<String> uploadResume(@RequestParam("resume") MultipartFile multipartFile){
        String resumeUrl = employeeService.uploadResume(multipartFile);
        return ResponseEntity.ok(resumeUrl);
        }

    @PostMapping("/nominate")
    public ResponseEntity<String> nominateEmployee(@Valid @RequestBody NominationRequest request) throws AwardsNotFoundException, DuplicateProcessException {
        return ResponseEntity.ok(employeeService.nominate(request));
    }

    @PutMapping("/approve-nomination")
    public ResponseEntity<String> approveEmployeeNomination(@Valid @RequestBody NominationApprovalRequest request) throws Exception {
        return ResponseEntity.ok(employeeService.approveNomination(request));
    }
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequest changerPassword, Authentication auth) {
        return new ResponseEntity<>(employeeService.changePassword(changerPassword, auth), HttpStatus.OK);
    }

}
