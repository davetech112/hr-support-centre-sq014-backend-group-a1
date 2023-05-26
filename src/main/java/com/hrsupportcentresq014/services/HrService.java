package com.hrsupportcentresq014.services;

import com.hrsupportcentresq014.dtos.request.CreateStaffRequest;
import com.hrsupportcentresq014.dtos.response.CreateStaffResponse;
import com.hrsupportcentresq014.dtos.response.StaffProfileDTO;
import com.hrsupportcentresq014.dtos.response.ViewStaffResponse;
import com.hrsupportcentresq014.entities.Employee;
import com.hrsupportcentresq014.exceptions.EmployeeNotFoundException;
import com.hrsupportcentresq014.exceptions.UserAlreadyExistsException;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;

public interface HrService {
    ResponseEntity<CreateStaffResponse> registerStaff(CreateStaffRequest staff) throws UserAlreadyExistsException, MessagingException;

    public StaffProfileDTO getStaffProfile(String id) throws EmployeeNotFoundException;


    ViewStaffResponse viewAllStaff(int pageNo, int pageSize, String sortBy, String sortDir);
}
