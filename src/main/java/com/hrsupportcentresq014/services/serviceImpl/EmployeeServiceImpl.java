package com.hrsupportcentresq014.services.serviceImpl;

import com.cloudinary.Cloudinary;
import com.hrsupportcentresq014.dtos.request.EmployeeProfileRequest;
import com.hrsupportcentresq014.dtos.request.NominationApprovalRequest;
import com.hrsupportcentresq014.dtos.request.NominationRequest;
import com.hrsupportcentresq014.dtos.response.CreateHrResponseDTO;
import com.hrsupportcentresq014.entities.Award;
import com.hrsupportcentresq014.entities.Employee;
import com.hrsupportcentresq014.entities.Nominee;
import com.hrsupportcentresq014.exceptions.*;
import com.hrsupportcentresq014.repositories.AwardRepository;
import com.hrsupportcentresq014.repositories.EmployeeRepository;
import com.hrsupportcentresq014.repositories.NomineeRepository;
import com.hrsupportcentresq014.repositories.RoleRepository;
import com.hrsupportcentresq014.security_config.utils.SecurityUtils;
import com.hrsupportcentresq014.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class EmployeeServiceImpl implements EmployeeService {

    private final RoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;
    private final SecurityUtils securityUtils;
    private final Cloudinary cloudinary;
    private final ModelMapper modelMapper;
    private final MailServiceImpl mailService;
    private final PasswordEncoder passwordEncoder;
    private final AwardRepository awardRepository;
    private final NomineeRepository nomineeRepository;


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


        String message = "   Welcome to Decagon!: " + hrDTO.getFirstName() + "Your password is : " + password + "  click here for a password Reset";
        mailService.sendAccountActivation(hrDTO.getEmail(), message);

//        Employee employee2 = employeeRepository.save(newEmployee1);


        return modelMapper.map(employeeRepository.save(newEmployee1), CreateHrResponseDTO.class);
//         CreateHrResponseDTO n = BeanUtils.copyProperties(employee2, CreateHrResponseDTO.class);
//        return newEmployee1;

    }

    @Override
    public EmployeeProfileRequest updateEmployeeProfile(EmployeeProfileRequest employeeProfileRequest) {
        String userEmail = securityUtils.getCurrentUserDetails().getUsername();
        Optional<Employee> existingEmployee = employeeRepository.findByEmail(userEmail);
        if (existingEmployee.isPresent()) {
            Employee employee = existingEmployee.get();
            BeanUtils.copyProperties(employeeProfileRequest, employee);
            return mapToEmployeeProfileRequest(employeeRepository.save(employee));
        }
        return null;
    }

    @Override
    public String uploadDocument(MultipartFile multipartFile) {
        try {
            long fileSize = multipartFile.getSize();
            if (fileSize > 1000 * 1024) {
                throw new IllegalArgumentException("File size cannot be more than 1MB");
            }
            return cloudinary.uploader()
                    .upload(multipartFile.getBytes(),
                            Map.of("public_id", UUID.randomUUID().toString()))
                    .get("url")
                    .toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Employee getEmployee(String email) {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Employee with email: " + email + " not found"));
    }

    @Override
    public String uploadImage(MultipartFile multipartFile) {
        String userEmail = securityUtils.getCurrentUserDetails().getUsername();
        Employee existingEmployee = getEmployee(userEmail);
        String imageUrl = uploadDocument(multipartFile);
        existingEmployee.setImageUrl(imageUrl);
        existingEmployee.setUpdatedOn(LocalDateTime.now());
        employeeRepository.save(existingEmployee);
        return imageUrl;
    }

    @Override
    public String uploadResume(MultipartFile multipartFile) {
        String userEmail = securityUtils.getCurrentUserDetails().getUsername();
        Employee existingEmployee = getEmployee(userEmail);
        String fileType = multipartFile.getContentType();
        if (
                !fileType.equals("application/pdf")
                        && !fileType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                        && !fileType.equals("application/msword")
        ) {
            throw new IllegalArgumentException("Unsupported file type");
        }
        String resumeUrl = uploadDocument(multipartFile);
        existingEmployee.setResumeUrl(resumeUrl);
        existingEmployee.setUpdatedOn(LocalDateTime.now());
        employeeRepository.save(existingEmployee);
        return resumeUrl;
    }

    @Override
    public String nominate(NominationRequest request) throws DuplicateProcessException {

        Award award = awardRepository.findAwardByTitleAndYear(request.getAwardTitle(), LocalDateTime.now().getYear()).orElseThrow(()->new AwardsNotFoundException("Award category "+ request.getAwardTitle() +" for year "+ LocalDateTime.now() +" does not exist"));

        String nominatorEmail = securityUtils.getCurrentUserDetails().getUsername();

        Employee nominator = employeeRepository.findByEmail(nominatorEmail).orElseThrow(()->new EmailNotFoundException("Employee with email "+ nominatorEmail +" not found"));

        Employee nominated = employeeRepository.findByEmail(request.getNomineeEmail()).orElseThrow(()->new EmailNotFoundException("Employee with email "+ request.getNomineeEmail() +" not found"));

        Optional <Nominee> existingNominee =  nomineeRepository.findByNomineeAndAward(nominated, award);

        //Check if no one has been nominated or if one of the nominees has not been nominated yet
        if(award.getUnapprovedNominees().isEmpty() || existingNominee.isEmpty()){
            //Instantiate a hashmap and put the email and reason

            HashMap<String, String> nominatorsMap = new HashMap<>();
            nominatorsMap.put(nominator.getId(), request.getReason());

            //Create an instance of nominee
            Nominee nominee = Nominee.builder()
                    .nominee(nominated)
                    .award(award)
                    .nominators(nominatorsMap)
                    .isApproved(false)
                    .build();

            //Add nominator email and reason to the hashMap
            award.getUnapprovedNominees().add(nomineeRepository.save(nominee));

            awardRepository.save(award);
            return "Nomination successful awaiting approval";
        }

        //If the list of nominees is not empty and the nominee has been nominated
        Optional<String> checkNominatorEmail = award.getUnapprovedNominees()
                .stream()
                .flatMap(nominee -> nominee.getNominators().keySet().stream())
                .filter(nominatorId -> nominatorId.equals(nominator.getId()))
                .findFirst();

        if (checkNominatorEmail.isPresent()) {
            throw new DuplicateProcessException("You already nominated an employee in "+ award.getTitle()+" category");
        }

        //Since nominee already exists in database and nomination is not duplicate, just add the new nominator to the Hashmap of nominators
        Nominee updateNominee = existingNominee.get();
        updateNominee.getNominators().put(nominator.getId(), request.getReason());
        nomineeRepository.save(updateNominee);

        return "Nomination successful awaiting approval";

    }

    @Override
    public String approveNomination(NominationApprovalRequest request) throws Exception {
        //Category and year
        Award award = awardRepository.findAwardByTitleAndYear(request.getAwardTitle(), LocalDateTime.now().getYear()).orElseThrow(()-> new AwardsNotFoundException("Award Category not found"));

        String teamManagerEmail = securityUtils.getCurrentUserDetails().getUsername();

        Employee teamManager = employeeRepository.findByEmail(teamManagerEmail).orElseThrow(()-> new EmailNotFoundException("Email not found"));

        Nominee unapprovedNominee = nomineeRepository.findByNomineeAndAward(employeeRepository.findByEmail(request.getNomineeEmail()).get(), award).get();

        if(teamManager.getDepartment().equals(unapprovedNominee.getNominee().getDepartment()) && teamManager.getPosition().equals("Department Lead")) {

            if(!award.getApprovedNominees().isEmpty()) {
                Optional<Nominee> duplicateApproval = award.getApprovedNominees().stream().filter(nominee -> nominee.getId().equals(unapprovedNominee.getId())).findFirst();
                if(duplicateApproval.isPresent()) throw new DuplicateProcessException(unapprovedNominee.getNominee().getFirstName()+" nomination already approved");
            }

            unapprovedNominee.setApproved(true);

            award.getApprovedNominees().add(nomineeRepository.save(unapprovedNominee));

            awardRepository.save(award);

            return "Nomination Approved";
        }
        throw new Exception("Cannot approve nomination");

    }



        public EmployeeProfileRequest mapToEmployeeProfileRequest (Employee employee){
            return EmployeeProfileRequest.builder()
                    .nextOfKin(employee.getNextOfKin())
                    .maritalStatus(employee.getMaritalStatus())
                    .nationality(employee.getNationality())
                    .social(employee.getSocial())
                    .nickName(employee.getNickName())
                    .birthday(employee.getBirthday())
                    .address(employee.getAddress())
                    .build();
        }

}

