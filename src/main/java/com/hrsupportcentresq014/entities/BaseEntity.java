package com.hrsupportcentresq014.entities;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
public abstract class BaseEntity {
    @Id
    private String id;
    @CreatedDate
    private LocalDateTime createdOn;
    @LastModifiedDate
    private LocalDateTime updatedOn;
}





