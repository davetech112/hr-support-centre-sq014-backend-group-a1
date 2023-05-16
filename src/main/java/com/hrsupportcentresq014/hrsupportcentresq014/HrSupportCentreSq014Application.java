package com.hrsupportcentresq014.hrsupportcentresq014;

import com.hrsupportcentresq014.hrsupportcentresq014.entities.Employee;
import com.hrsupportcentresq014.hrsupportcentresq014.entities.Team;
import com.hrsupportcentresq014.hrsupportcentresq014.repositories.EmployeeRepository;
import com.hrsupportcentresq014.hrsupportcentresq014.repositories.TeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@SpringBootApplication

public class HrSupportCentreSq014Application {

	public static void main(String[] args) {
		SpringApplication.run(HrSupportCentreSq014Application.class, args);
	}



}
