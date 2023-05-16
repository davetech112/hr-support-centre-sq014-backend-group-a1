package com.hrsupportcentresq014.hrsupportcentresq014.entities;

import com.mongodb.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Applicant {
    @Id
    private String id;


    @NotBlank(message = "The firstname field should not be blank")
    private String employeeFirstName;


    @NotBlank(message = "Please upload your application letter")
    private String applicationLeterUrl;


    @CreationTimestamp
    private LocalDateTime dateApply;


}
