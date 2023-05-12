package com.hrsupportcentresq014.hrsupportcentresq014.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hrsupportcentresq014.hrsupportcentresq014.entities.entityUtil.Socials;
import com.hrsupportcentresq014.hrsupportcentresq014.enums.Role;
import com.mongodb.lang.NonNull;

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

    @NonNull
    private String firstname;

    private String middlename;

    private String phoneNo;

    @Indexed(unique = true)
    private String email;


    private String lastName;


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
