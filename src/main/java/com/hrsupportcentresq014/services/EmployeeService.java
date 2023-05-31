package com.hrsupportcentresq014.services;

import com.hrsupportcentresq014.dtos.request.EmployeeProfileRequest;
import com.hrsupportcentresq014.dtos.response.CreateHrResponseDTO;
import com.hrsupportcentresq014.exceptions.UserAlreadyExistsException;
import org.springframework.web.multipart.MultipartFile;

public interface EmployeeService {
    CreateHrResponseDTO createHr (CreateHrResponseDTO hrDTO) throws UserAlreadyExistsException;

    EmployeeProfileRequest updateEmployeeProfile(EmployeeProfileRequest employeeProfileRequest);

    String uploadDocument(MultipartFile multipartFile);

    String uploadImage(MultipartFile multipartFile);

    String uploadResume(MultipartFile multipartFile);
}
