package com.hrsupportcentresq014.controllers;


import com.hrsupportcentresq014.dtos.request.CreateStaffRequest;
import com.hrsupportcentresq014.dtos.request.JobPostingRequest;
import com.hrsupportcentresq014.dtos.request.JobUpdateRequest;
import com.hrsupportcentresq014.dtos.response.CreateStaffResponse;
import com.hrsupportcentresq014.dtos.response.JobPostingResponse;
import com.hrsupportcentresq014.entities.Employee;
import com.hrsupportcentresq014.exceptions.EmployeeNotFoundException;
import com.hrsupportcentresq014.exceptions.UnauthorizedUserException;
import com.hrsupportcentresq014.exceptions.UserAlreadyExistsException;
import com.hrsupportcentresq014.repositories.EmployeeRepository;
import com.hrsupportcentresq014.repositories.RoleRepository;
import com.hrsupportcentresq014.services.HrService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/hr")
@PreAuthorize("hasRole('HR')")
public class HRController {
  // todo This is a must read for guys working on these end points read below
  // todo: if you want to mark any endpoint for fine-grained authorization refer to the Enum roles to see the specific roles
  // and the Enum permissions that the HR has for that particular endpoint then you can mark with @PreAuthorize("hasAuthority('hr:create')")
  private final HrService hrService;
  private RoleRepository roleRepository;
  private final EmployeeRepository employeeRepository;




  @PostMapping("/register")
  public ResponseEntity<CreateStaffResponse> registerStaffByHR(@Valid @RequestBody CreateStaffRequest staff) throws UserAlreadyExistsException, MessagingException {
    return hrService.registerStaff(staff);
  }


  @PostMapping("/create-job")
  public ResponseEntity<JobPostingResponse> createJobPosting(@Valid @RequestBody JobPostingRequest request) throws EmployeeNotFoundException {
    log.info("Creating a Job posting with {}", request);
    JobPostingResponse response = hrService.postJob(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }




  @PutMapping("/jobs/update-job")
  public ResponseEntity<JobPostingResponse> changeJobStatus(@Valid @RequestBody JobUpdateRequest request) throws EmployeeNotFoundException {
    log.info("Changing job status");
    JobPostingResponse result = hrService.changeJobStatus(request);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
