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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Job {
    @Id
    private String id;

    private String title;

    private String description;

    // in mango, there is nothing like manytoone mapping. the best way to associate a particular post with a department
    // is by saving the id of the department in the job class.
   @NonNull
    private String departmentName;

   @DBRef
   private List<View> listOfView = new ArrayList<>();

   @DBRef
   private List<Applicant> listOfApplicant = new ArrayList<>();


}
