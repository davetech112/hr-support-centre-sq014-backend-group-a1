package com.hrsupportcentresq014.dtos.request;

import com.hrsupportcentresq014.entities.NextOfKin;
import com.hrsupportcentresq014.utils.Social;
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
        @NotNull(message = "birthday must not be Blank")
        @Past(message = "Birthday must be in the past")
        private LocalDate birthday;
        private String nickName;
        private String address;
        private NextOfKin nextOfKin;
        private Social social;

        private String maritalStatus;
        private String nationality;
    }



