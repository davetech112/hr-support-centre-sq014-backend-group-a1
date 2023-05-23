package com.hrsupportcentresq014.services.serviceImpl;

import com.hrsupportcentresq014.dtos.request.CreateStaffRequest;
import com.hrsupportcentresq014.dtos.response.CreateStaffResponse;
import com.hrsupportcentresq014.dtos.response.ViewStaffResponse;
import com.hrsupportcentresq014.dtos.response.ViewStaffResponseDTO;
import com.hrsupportcentresq014.entities.Employee;
import com.hrsupportcentresq014.entities.Role;
import com.hrsupportcentresq014.exceptions.UserAlreadyExistsException;
import com.hrsupportcentresq014.repositories.EmployeeRepository;
import com.hrsupportcentresq014.repositories.RoleRepository;
import com.hrsupportcentresq014.services.HrService;
import com.hrsupportcentresq014.services.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HrServiceImpl implements HrService {
    private final EmployeeRepository repository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

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
               .role(roleRepository.findRoleById("staff").get())
                .build();
        BeanUtils.copyProperties(staffRequest, employee);
        return mappedToResponse(repository.save(employee));
    }

    @Override
    public ViewStaffResponse viewAllStaff(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Role role = roleRepository.findRoleById("staff").get();

        Page<Employee> staffs = repository.findAllByRole(role, pageable);
        List<Employee> content = staffs.getContent();
        List<ViewStaffResponseDTO> contentDto = content.stream().map(cont -> mapToDto(cont)).collect(Collectors.toList());

        ViewStaffResponse viewStaff = new ViewStaffResponse();
        viewStaff.setContent(contentDto);
        viewStaff.setPageNo(staffs.getNumber());
        viewStaff.setPageSize(staffs.getSize());
        viewStaff.setTotalElements(staffs.getTotalElements());
        viewStaff.setTotalPages(staffs.getTotalPages());
        viewStaff.setLast(staffs.isLast());

        return viewStaff;
    }

    private ViewStaffResponseDTO mapToDto(Employee cont) {
        ViewStaffResponseDTO viewStaffDto = new ViewStaffResponseDTO();
        viewStaffDto.setFullName(cont.getFirstName() + " " + cont.getLastName());
        viewStaffDto.setPosition(cont.getPosition());
        viewStaffDto.setDepartment(cont.getDepartment());
        viewStaffDto.setContractType(cont.getContractType());
        viewStaffDto.setStatus("Worker");
        viewStaffDto.setTenure(23L);

        return viewStaffDto;
    }

    public ResponseEntity<CreateStaffResponse> mappedToResponse(Employee employee){
        CreateStaffResponse response = CreateStaffResponse.builder()
                .contractType(employee.getContractType())
                .email(employee.getEmail())
                .firstName(employee.getFirstName())
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
