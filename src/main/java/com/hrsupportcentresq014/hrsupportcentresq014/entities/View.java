package com.hrsupportcentresq014.hrsupportcentresq014.entities;


import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class View {

    @Id
    private String id;

    @NonNull
    private String employeeId;


    private boolean hasView;

    @NonNull
    private LocalDateTime dateView;

}
