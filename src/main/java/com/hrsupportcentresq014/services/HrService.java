package com.hrsupportcentresq014.services;


import com.hrsupportcentresq014.dtos.request.CreateStaffRequest;
import com.hrsupportcentresq014.dtos.request.JobPostingRequest;
import com.hrsupportcentresq014.dtos.request.JobUpdateRequest;
import com.hrsupportcentresq014.dtos.response.CreateStaffResponse;
import com.hrsupportcentresq014.dtos.response.JobPostingResponse;
import com.hrsupportcentresq014.exceptions.EmployeeNotFoundException;
import com.hrsupportcentresq014.exceptions.InvalidDateChoiceException;
import com.hrsupportcentresq014.exceptions.UserAlreadyExistsException;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;


public interface HrService {
    ResponseEntity<CreateStaffResponse> registerStaff(CreateStaffRequest staff) throws UserAlreadyExistsException, MessagingException;


    JobPostingResponse postJob(JobPostingRequest request) throws InvalidDateChoiceException, EmployeeNotFoundException;


    JobPostingResponse changeJobStatus(JobUpdateRequest request) throws InvalidDateChoiceException, EmployeeNotFoundException;
}
