package com.records.demo.service;

import com.records.demo.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();
    List<Employee> findByFirstName(String firstName);
    List<Employee> findByLastName(String lastName);
    Employee findById(int id);
}
