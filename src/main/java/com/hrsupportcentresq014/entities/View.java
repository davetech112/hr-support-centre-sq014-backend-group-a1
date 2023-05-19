package com.hrsupportcentresq014.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class View extends BaseEntity{

    private String employeeId;
    private boolean hasView;
    private LocalDateTime dateView = LocalDateTime.now();

}
