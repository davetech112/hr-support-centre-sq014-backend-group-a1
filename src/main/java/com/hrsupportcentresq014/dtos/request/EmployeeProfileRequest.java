package com.hrsupportcentresq014.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class EmployeeProfileRequest {

        @NotBlank(message = "email must not be Blank")
        private String email;
        @NotBlank(message = "filename must not be Blank")
        private String fileName;
        @NotBlank(message = "Image must not be Blank")
        private String imageUrl;
        @NotBlank(message = "filetype must not be Blank")
        private String fileType;
        @NotNull(message = "birthday must not be Blank")
        @Past(message = "Birthday must be in the past")
        private LocalDate birthday;
        private LocalDate lastUpdated = LocalDate.now();
        private String address;

        private String nextOfKin;

        private String maritalStatus;
    }



