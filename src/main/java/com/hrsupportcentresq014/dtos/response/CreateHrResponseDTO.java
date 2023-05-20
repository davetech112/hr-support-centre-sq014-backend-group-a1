package com.hrsupportcentresq014.dtos.response;

import com.hrsupportcentresq014.entities.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
public class CreateHrResponseDTO {
    private String firstName;

    private String middleName;

    private String phoneNo;

    private String email;


    private String lastName;


    private String password;

    private Role role;


    private LocalDate dob;

    private String position;

    private String reportTo;
}
