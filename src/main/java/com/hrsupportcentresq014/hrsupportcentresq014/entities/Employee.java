package com.hrsupportcentresq014.hrsupportcentresq014.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hrsupportcentresq014.hrsupportcentresq014.entities.entityUtil.Socials;
import com.hrsupportcentresq014.hrsupportcentresq014.enums.Role;
import com.mongodb.lang.NonNull;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
@Builder
public class Employee {
    @Id
    private String id;

    @NotBlank(message = "The firstname field should not be blank")
    private String firstname;

    private String middlename;

    private String phoneNo;

    @Indexed(unique = true)
    private String email;

    @NotBlank(message = "The lasname field should not be blank")
    private String lastName;

    @NotBlank(message="The password field should not be blank")
    private String password;

    private Role role;


    private Socials social;

    private String pictureUrl;

    private String resumeUrl;


    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    private String position;

    private String reportTo;

}
