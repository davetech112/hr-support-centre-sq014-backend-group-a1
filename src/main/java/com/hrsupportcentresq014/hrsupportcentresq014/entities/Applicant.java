package com.hrsupportcentresq014.hrsupportcentresq014.entities;

import com.mongodb.lang.NonNull;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class Applicant {
    @Id
    private String id;

    @NonNull
    private String employeeFirstName;

    @NonNull
    private String applicationLeterUrl;

    @NonNull
    private LocalDateTime dateApply;


}
