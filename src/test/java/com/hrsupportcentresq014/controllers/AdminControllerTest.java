package com.hrsupportcentresq014.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrsupportcentresq014.dtos.request.AdminRequest;
import com.hrsupportcentresq014.dtos.response.AdminResponse;
import com.hrsupportcentresq014.entities.Permission;
import com.hrsupportcentresq014.entities.Role;
import com.hrsupportcentresq014.repositories.EmployeeRepository;
import com.hrsupportcentresq014.repositories.RoleRepository;
import com.hrsupportcentresq014.security_config.utils.JwtUtils;
import com.hrsupportcentresq014.services.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(controllers = AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class AdminControllerTest {




    @Autowired
    private MockMvc mockMvc;




    @MockBean
    private JwtUtils jwtUtils;




    @MockBean
    private UserService userService;




    @MockBean
    private EmployeeRepository employeeRepository;




    @InjectMocks
    private AdminController adminController;




    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RoleRepository roleRepository;








    private AdminResponse adminResponse;
    private AdminRequest adminRequest;
    Optional<Role> roleOfAdmin;




    Role adminRole;
    @BeforeEach
    void setup() {




        List<Permission> adminPermission = new ArrayList<>();
        adminPermission.add(new Permission(Permission.ADMIN_DELETE));
        adminPermission.add(new Permission(Permission.ADMIN_CREATE));
        adminPermission.add(new Permission(Permission.ADMIN_READ));
        adminPermission.add(new Permission(Permission.ADMIN_UPDATE));
        adminPermission.add(new Permission(Permission.HR_CREATE));
        adminPermission.add(new Permission(Permission.HR_DELETE));
        adminPermission.add(new Permission(Permission.HR_READ));
        adminPermission.add(new Permission(Permission.HR_UPDATE));




        adminRole = new Role();
        adminRole.setId("admin");
        adminRole.setName("ADMIN");
        adminRole.setPermissions(adminPermission);
        roleRepository.save(adminRole);












        adminRequest = AdminRequest.builder()
                .firstName("Timilehin")
                .nickName("Emmanuel")
                .lastName("Olowookere")
                .password("RabbitMQkAFKA")
                .phoneNumber("07050861774")
                .dateOfBirth("2000-21-11")
                .email("olowookeretimi79@gmail.com")
                .build();












        adminResponse = AdminResponse.builder()
                .firstName("Timilehin")
                .nickName("Emmanuel")
                .lastName("Olowookere")
                .dateOfBirth("2000-21-11")
                .email("olowookeretimi79@gmail.com")
                .phoneNumber("07050861774")
                .role(adminRole)
                .position("Manager")
                .build();
    }




    @Test
    @DisplayName("Test Admin Registration")
    void registerAdmin() throws Exception {
        Mockito.when(userService.register(adminRequest)).thenReturn(adminResponse);
        Mockito.when(roleRepository.findRoleById(any(String.class))).thenReturn(Optional.ofNullable(adminRole));




        MvcResult result = mockMvc.perform(post("/api/v1/admin/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(adminRequest))
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();




        //confirm that the userService is called at least once.
        Mockito.verify(userService, Mockito.times(1)).register(adminRequest);
        //confirm that registration is successfully done
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        String response = new ObjectMapper().writeValueAsString(adminResponse);
        //confirm that the response is uniform with what is requested
        assertEquals(response, result.getResponse().getContentAsString());
    }
}
