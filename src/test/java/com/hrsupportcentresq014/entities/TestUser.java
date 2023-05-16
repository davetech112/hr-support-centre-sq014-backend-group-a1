package com.hrsupportcentresq014.entities;


import com.hrsupportcentresq014.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class TestUser {


    @MockBean
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp(){

        Employee employee = new Employee();
        employee.setLastName("Shola");
        employee.setFirstname("adebisi");
        employee.setEmail("aadebisi11@yahoo.com");

        when(employeeRepository.findEmployeeByEmail("aadebisi11@yahoo.com")).thenReturn(Optional.of(employee));

    }



    @Test
    void saveUser(){
       Employee employee =  employeeRepository.findEmployeeByEmail("aadebisi11@yahoo.com").get();
       assertEquals("adebisi", employee.getFirstname());
    }
}
