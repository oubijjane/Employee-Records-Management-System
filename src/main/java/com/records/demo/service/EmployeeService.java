package com.records.demo.service;

import com.records.demo.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();
    List<Employee> findByFirstName(String firstName);
    List<Employee> findByLastName(String lastName);
    List<Employee> findByJoBTitle(String jobTitle);
    List<Employee> findByDepartment(String department);
    Employee findById(int id);
    Employee save(Employee employee);
    void deleteById(int id);
}
