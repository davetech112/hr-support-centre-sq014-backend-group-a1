package com.hrsupportcentresq014.controllers;

import com.hrsupportcentresq014.dtos.request.EmployeeProfileRequest;
import com.hrsupportcentresq014.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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


    @PutMapping("/profile/{id}")
    public ResponseEntity<EmployeeProfileRequest> updateEmployeeProfile(@PathVariable("id") String id, @RequestBody @Valid EmployeeProfileRequest employeeProfileRequest) {
        log.info("Updating Employee Profile");
        EmployeeProfileRequest response = employeeService.updateEmployeeProfile(id, employeeProfileRequest);
        log.info("Your Profile has been successfully updated");
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
    @PostMapping("/add-pic")
    public String uploadProfilePic(@RequestParam("image") MultipartFile multipartFile, Model model) throws IOException {
        String imageURL = employeeService.uploadDocument(multipartFile);
        model.addAttribute("imageUrl", imageURL);
        return "gallery";
    }



//    @PutMapping("/profile/{id}")
//    public ResponseEntity<String> updateEmployeeProfileInfo(@PathVariable("id") String id,
//                                                            @RequestBody
//                                                            @Valid EmployeeProfileRequest employeeProfileRequest) {
//        log.info("Updating Employee Profile");
//        employeeService.updateEmployeeProfile(id, employeeProfileRequest);
//        log.info("Your Profile has been successfully updated");
//        return ResponseEntity.ok("Updated");
//
//    }
//    @PostMapping("/add-pic")
//    public ResponseEntity<String> uploadEmoloyeeProfilePic(@RequestParam("image")MultipartFile multipartFile) throws IOException {
//        employeeService.uploadDocument(multipartFile);
//        return ResponseEntity.ok("gallery");
//    }
}
