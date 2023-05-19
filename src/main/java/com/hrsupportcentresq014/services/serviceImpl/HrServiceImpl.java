package com.hrsupportcentresq014.services.serviceImpl;

import com.hrsupportcentresq014.dtos.request.CreateStaffRequest;
import com.hrsupportcentresq014.dtos.response.CreateStaffResponse;
import com.hrsupportcentresq014.entities.Employee;
import com.hrsupportcentresq014.exceptions.UserAlreadyExistsException;
import com.hrsupportcentresq014.repositories.EmployeeRepository;
import com.hrsupportcentresq014.services.HrService;
import com.hrsupportcentresq014.services.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HrServiceImpl implements HrService {
    private final EmployeeRepository repository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<CreateStaffResponse> registerStaff(CreateStaffRequest staffRequest) throws UserAlreadyExistsException, MessagingException {
        Optional<Employee> staffDB = repository.findByEmail(staffRequest.getEmail());

        if(staffDB.isPresent()){
            throw new UserAlreadyExistsException("User with "+ staffRequest.getEmail()+" already exists");
        }

        //Password is generated for the user at the point of registration
        UUID uuid = UUID.randomUUID();
        String password = uuid.toString().replace("-", "").substring(0, 12);

        mailService.sendMailTest(staffRequest.getEmail(), "Employee account password", "Welcome, "+staffRequest.getFirstname()+" you have been onboarded. Here is your password: "+ password);

        Employee employee = Employee.builder()
                .password(passwordEncoder.encode(password))
                .build();
        BeanUtils.copyProperties(staffRequest, employee);

        return mappedToResponse(repository.save(employee));
    }
    public ResponseEntity<CreateStaffResponse> mappedToResponse(Employee employee){
        CreateStaffResponse response = CreateStaffResponse.builder()
                .contractType(employee.getContractType())
                .email(employee.getEmail())
                .firstName(employee.getFirstname())
                .lastName(employee.getLastName())
                .position(employee.getPosition())
                .teamManager(employee.getTeamManager())
                .startDate(employee.getStartDate())
                .password(employee.getPassword())
                .workLocation(employee.getWorkLocation())
                .build();

        return ResponseEntity.ok().body(response);
    }
}
