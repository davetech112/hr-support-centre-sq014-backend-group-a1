package com.hrsupportcentresq014.entities;


import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class BaseEntity {

    @Id
    private String id;

    @CreationTimestamp
    private LocalDateTime createdOn;
}
