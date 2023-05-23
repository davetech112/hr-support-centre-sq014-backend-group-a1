package com.hrsupportcentresq014.dtos.response;

import lombok.*;

import java.time.LocalDate;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeProfileResponse {
    private String email;

    private String fileName;

    private String imageUrl;

    private String fileType;

    private LocalDate birthday;
    private LocalDate lastUpdated = LocalDate.now();
    private String address;

    private String nextOfKin;

    private String maritalStatus;
}
