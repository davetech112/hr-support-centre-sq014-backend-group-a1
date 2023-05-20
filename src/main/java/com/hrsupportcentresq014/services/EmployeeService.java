package com.hrsupportcentresq014.services;

import com.hrsupportcentresq014.dtos.response.CreateHrResponseDTO;
import com.hrsupportcentresq014.exceptions.UserAlreadyExistsException;

public interface EmployeeService {
    CreateHrResponseDTO createHr (CreateHrResponseDTO hrDTO) throws UserAlreadyExistsException;
}
