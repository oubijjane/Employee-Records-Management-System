package com.records.demo.dao;

import com.records.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmployeeDAO extends JpaRepository<Employee, Integer> {
   List<Employee> findAll();
   List<Employee> findByFirstName(String firstName);
   List<Employee> findByLastName(String lastName);
   List<Employee> findByJobTitle(String jobTitle);
   List<Employee> findByDepartment(String department);

}
