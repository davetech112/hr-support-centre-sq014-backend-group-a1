package com.hrsupportcentresq014.services.serviceImpl;

import com.cloudinary.Cloudinary;
import com.hrsupportcentresq014.dtos.request.EmployeeProfileRequest;
import com.hrsupportcentresq014.dtos.response.CreateHrResponseDTO;
import com.hrsupportcentresq014.entities.Employee;
import com.hrsupportcentresq014.exceptions.UserAlreadyExistsException;
import com.hrsupportcentresq014.repositories.EmployeeRepository;
import com.hrsupportcentresq014.repositories.RoleRepository;
import com.hrsupportcentresq014.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class EmployeeServiceImpl implements EmployeeService {

    private final RoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;

    private final Cloudinary cloudinary;
    private final ModelMapper modelMapper;
    private final MailServiceImpl mailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CreateHrResponseDTO createHr(CreateHrResponseDTO hrDTO) throws UserAlreadyExistsException {
        String password = UUID.randomUUID().toString();

        Optional<Employee> foundEmployee =
                employeeRepository.findByEmail(hrDTO.getEmail());

        if (foundEmployee.isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }

        Employee newEmployee1 = new Employee();
        BeanUtils.copyProperties(hrDTO, newEmployee1);
        newEmployee1.setRole(roleRepository.findRoleById("hr").get());
        newEmployee1.setPassword(passwordEncoder.encode(password));
//         newEmployee1.setCreatedOn();


        String message =   "   Welcome to Decagon!: " + hrDTO.getFirstName() + "Your password is : " + password + "  click here for a password Reset";
        mailService.sendAccountActivation(hrDTO.getEmail(),message);

//        Employee employee2 = employeeRepository.save(newEmployee1);


        return modelMapper.map(employeeRepository.save(newEmployee1), CreateHrResponseDTO.class);
//         CreateHrResponseDTO n = BeanUtils.copyProperties(employee2, CreateHrResponseDTO.class);
//        return newEmployee1;

    }

    @Override
    public EmployeeProfileRequest updateEmployeeProfile(String id, EmployeeProfileRequest employeeProfileRequest) {
        //log.info("Create staff method called>>>>>>>>>>>>>"+ employeeProfileRequest.getEmail());
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        //log.info("Create staff method called>>>>>>>>>>>>>"+ employeeProfileRequest.getEmail());
        if(existingEmployee.isPresent()){
            Employee employee = existingEmployee.get();
            BeanUtils.copyProperties(employeeProfileRequest, employee);
            return  mapToEmployeeProfileRequest(employeeRepository.save(employee));
        }
        return null;
    }

    @Override
    public String uploadDocument(MultipartFile multipartFile){
        try {
            LocalDate currentDate = LocalDate.now();
            return cloudinary.uploader()
                    .upload(multipartFile.getBytes(),
                            Map.of("public_id", UUID.randomUUID().toString()))
                    .get("url")
                    .toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public EmployeeProfileRequest mapToEmployeeProfileRequest(Employee employee) {
        return EmployeeProfileRequest.builder()
                .fileName(employee.getFileName())
                .email(employee.getEmail())
                .fileType(employee.getFileType())
                .nextOfKin(employee.getNextOfKin())
                .maritalStatus(employee.getMaritalStatus())
                .birthday(employee.getBirthday())
                .address(employee.getAddress())
                .lastUpdated(employee.getLastUpdated())
                .build();
    }
}

