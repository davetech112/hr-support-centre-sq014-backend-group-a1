package com.hrsupportcentresq014.entities;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class View extends BaseEntity{

    private String employeeId;
    private boolean hasView;
    @CreationTimestamp
    private LocalDateTime dateView;

}
