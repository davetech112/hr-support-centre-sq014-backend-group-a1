package com.hrsupportcentresq014.services.serviceImpl;


import com.hrsupportcentresq014.dtos.request.CreateStaffRequest;
import com.hrsupportcentresq014.dtos.request.JobPostingRequest;
import com.hrsupportcentresq014.dtos.request.JobUpdateRequest;
import com.hrsupportcentresq014.dtos.response.CreateStaffResponse;
import com.hrsupportcentresq014.dtos.response.JobPostingResponse;
import com.hrsupportcentresq014.entities.Employee;
import com.hrsupportcentresq014.entities.Job;
import com.hrsupportcentresq014.enums.JobStatus;
import com.hrsupportcentresq014.exceptions.*;
import com.hrsupportcentresq014.repositories.EmployeeRepository;
import com.hrsupportcentresq014.repositories.JobRepository;
import com.hrsupportcentresq014.services.HrService;
import com.hrsupportcentresq014.services.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;


import static java.lang.String.valueOf;


@Service
@Slf4j
@RequiredArgsConstructor
public class HrServiceImpl implements HrService {
    private final EmployeeRepository repository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final Mapper mapper;
    private final JobRepository jobRepository;


    @Override
    public ResponseEntity<CreateStaffResponse> registerStaff(CreateStaffRequest staffRequest) throws UserAlreadyExistsException, MessagingException {
        Optional<Employee> staffDB = repository.findByEmail(staffRequest.getEmail());


        if (staffDB.isPresent()) {
            throw new UserAlreadyExistsException("User with " + staffRequest.getEmail() + " already exists");
        }


        //Password is generated for the user at the point of registration
        UUID uuid = UUID.randomUUID();
        String password = uuid.toString().replace("-", "").substring(0, 12);


        mailService.sendMailTest(staffRequest.getEmail(), "Employee account password", "Welcome, " + staffRequest.getFirstname() + " you have been onboarded. Here is your password: " + password);


        Employee employee = new Employee();


        BeanUtils.copyProperties(staffRequest, employee);


        employee = Employee.builder()
                .password(passwordEncoder.encode(password))
                .build();


        return mappedToResponse(repository.save(employee));
    }


    //create job posting
    @Override
    public JobPostingResponse postJob(JobPostingRequest request) throws InvalidDateChoiceException, EmployeeNotFoundException {
        if(userIsHr()) {
            if (jobExists(request))
                throw new JobPostingExistsException("Similar Job Posting exists");
            if (!validateClosingDate(request.getClosingDate())) {
                throw new InvalidDateChoiceException("closing date cannot be in the past");
            }
            Job jobToSave = mapper.requestToJob(request);
            jobToSave.setIsActive(true);
            Job job = jobRepository.save(jobToSave);
            log.info("Job Posting created with payload {}", job);
            return mapper.jobToResponse(job);
        }
        else {
            throw new UnauthorizedUserException("User is not authorized to access this resource");
        }
    }




    @Override
    public JobPostingResponse changeJobStatus(JobUpdateRequest request) throws InvalidDateChoiceException, EmployeeNotFoundException {
        if(userIsHr()) {
            Job job = jobRepository.findById(request.getId()).orElseThrow(() -> new JobPostingNotFoundException("Job posting not found"));
            if (request.getJobStatus().equals(valueOf(JobStatus.ACTIVATE))) {
                Job updatedJob = updateJobPost(job.getId(), request);
                return mapper.jobToResponse(updatedJob);
            }
            job.setIsActive(false);
            job.setClosingDate(LocalDate.now().toString());
            Job savedJob = jobRepository.save(job);
            return mapper.jobToResponse(savedJob);
        }
        else {
            throw new UnauthorizedUserException("User is not authorized to access the resources");
        }
    }


    private Job updateJobPost(String id, JobUpdateRequest request) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new JobPostingNotFoundException("Job posting not found"));
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setDepartmentName(request.getDepartmentName());
        job.setRequirements(request.getRequirements());
        job.setIsActive(true);
        job.setClosingDate(request.getClosingDate());
        return jobRepository.save(job);
    }


    //validate provided closing date
    private boolean validateClosingDate(String date) throws InvalidDateChoiceException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate closingDate = LocalDate.parse(date, formatter);
        LocalDate currentDate = LocalDate.now();


        int result = currentDate.compareTo(closingDate);


        if(result > 0){
            log.warn("The closing date provided is behind the current date");
            return false;
        } else if (result < 0) {
            return true;
        } else{
            log.warn("The closing date provided is invalid");
            return false;
        }
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


    private boolean jobExists(JobPostingRequest request) {
        log.info("Checking if Job posting already exists");
        Job existingJob = jobRepository.findByTitle(request.getTitle());
        if (existingJob == null) {
            return false;
        } else {
            return existingJob.getTitle().equals(request.getTitle()) &&
                    existingJob.getDescription().equals(request.getDescription())
                    && existingJob.getRequirements().equals(request.getRequirements())
                    && existingJob.getClosingDate().equals(request.getClosingDate())
                    && existingJob.getIsActive()
                    && existingJob.getDepartmentName().equals(request.getDepartmentName());
        }
    }

    public boolean userIsHr() throws EmployeeNotFoundException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        Employee employee = repository.findByEmail(email).orElseThrow(()-> new EmployeeNotFoundException("User not found"));
        return employee.getRole().getName().equals("HR");
    }
}
