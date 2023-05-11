package com.hrsupportcentresq014.hrsupportcentresq014.entities;

import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Team {
    @Id
    private String id;
    @NonNull
    private String name;
    @DBRef
    private Employee teamLeader;

    @DBRef
    private List<Employee> listOfEmployee = new ArrayList<>();
}
