package com.hrsupportcentresq014.repositories;

import com.hrsupportcentresq014.entities.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {


    Optional<Employee> findByEmail(String username);


    Optional<Employee> findEmployeeByEmail(String email);

}
