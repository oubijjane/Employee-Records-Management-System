package com.records.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class EmployeeRecordsManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeRecordsManagementSystemApplication.class, args);
	}

}
