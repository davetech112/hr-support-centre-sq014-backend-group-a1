package com.hrsupportcentresq014.services.serviceImpl;

import com.hrsupportcentresq014.dtos.response.CreateHrResponseDTO;
import com.hrsupportcentresq014.entities.Employee;
import com.hrsupportcentresq014.exceptions.EmployeeExistsException;
import com.hrsupportcentresq014.exceptions.UserAlreadyExistsException;
import com.hrsupportcentresq014.repositories.EmployeeRepository;
import com.hrsupportcentresq014.repositories.RoleRepository;
import com.hrsupportcentresq014.services.EmployeeService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class EmployeeServiceImpl implements EmployeeService {

    private final RoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final MailServiceImpl mailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CreateHrResponseDTO createHr(CreateHrResponseDTO hrDTO) throws EmployeeExistsException {
        String password = UUID.randomUUID().toString();

        Optional<Employee> foundEmployee =
                employeeRepository.findByEmail(hrDTO.getEmail());

        if (foundEmployee.isPresent()) {
            throw new EmployeeExistsException("User already exists");
        }

        Employee newEmployee1 = new Employee();
        BeanUtils.copyProperties(hrDTO, newEmployee1);
        newEmployee1.setRole(roleRepository.findRoleById("hr").get());
        newEmployee1.setPassword(passwordEncoder.encode(password));
//


        String message =   "   Welcome to Decagon!: " + hrDTO.getFirstName() + "Your password is : " + password + "  click here for a password Reset";
        mailService.sendAccountActivation(hrDTO.getEmail(),message);

//        Employee employee2 = employeeRepository.save(newEmployee1);


        return modelMapper.map(employeeRepository.save(newEmployee1), CreateHrResponseDTO.class);
//         CreateHrResponseDTO n = BeanUtils.copyProperties(employee2, CreateHrResponseDTO.class);
//        return newEmployee1;

    }
}