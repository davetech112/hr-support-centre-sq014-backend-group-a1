package com.hrsupportcentresq014.hrsupportcentresq014.entities;


import com.mongodb.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class View {

    @Id
    private String id;

    @NotBlank(message = "Please enter employee id")
    private String employeeId;


    private boolean hasView;

  @CreationTimestamp
    private LocalDateTime dateView;

}
