package com.hrsupportcentresq014.hrsupportcentresq014.entities;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {

    @Id
    private String id;

    @NotBlank(message = "Please provide the department name")
    private String departmentName;

    @DBRef
    private Employee departmentLead;

    @DBRef
    private List<Employee> listOfEmployee = new ArrayList<>();

    @DBRef
    private List<Team> listOfTeam = new ArrayList<>();

}
