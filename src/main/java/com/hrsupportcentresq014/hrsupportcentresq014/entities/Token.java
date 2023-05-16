package com.hrsupportcentresq014.hrsupportcentresq014.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token  extends BaseEntity{
    private String jwtToken;
    private boolean isExpired;
    private boolean isRevoked;

    @DBRef
    private Employee employee;
}
