package com.records.demo.service;

import com.records.demo.dao.EmployeeDAO;
import com.records.demo.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmplyeeServiceImpl implements EmployeeService{
    private EmployeeDAO employeeDAO;

    @Autowired
    public EmplyeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public List<Employee> findAll() {
        return employeeDAO.findAll();
    }

    @Override
    public List<Employee> findByFirstName(String firstName) {
        Optional<List<Employee>> result = Optional.ofNullable(employeeDAO.findByFirstName(firstName));
        List<Employee> employees = null;
        if(result.isPresent()) {
            employees = result.get();
        } else {
            throw new RuntimeException("Did not find employee " + firstName);
        }
        return employees;
    }

    @Override
    public List<Employee> findByLastName(String lastName) {
        Optional<List<Employee>> result = Optional.ofNullable(employeeDAO.findByLastName(lastName));
        List<Employee> employees = null;
        if(result.isPresent()) {
            employees = result.get();
        } else {
            throw new RuntimeException("Did not find employee " + lastName);
        }
        return employees;
    }

    @Override
    public List<Employee> findByJoBTitle(String jobTitle) {
        Optional<List<Employee>> result = Optional.ofNullable(employeeDAO.findByJobTitle(jobTitle));
        List<Employee> employees = null;
        if(result.isPresent()) {
            employees = result.get();
        } else {
            throw new RuntimeException("Did not find employee with job title: " + jobTitle);
        }
        return employees;
    }

    @Override
    public List<Employee> findByDepartment(String department) {
        return    employeeDAO.findByDepartment(department);
    }

    @Override
    public int updateEmployee(int employeeId, String phoneNumber, String address, String email, String firstName,
                                   String lastName, String status) {
        return employeeDAO.saveEmployee(employeeId, phoneNumber, address, email,
                firstName, lastName, status);
    }

    @Override
    public Employee findById(int id) {
        Optional<Employee> result = employeeDAO.findById(id);
        Employee employee = null;
        if(result.isPresent()) {
            employee = result.get();
        } else {
            throw new RuntimeException("Did not find employee id - " + id);
        }
        return employee;
    }

    @Transactional
    @Override
    public Employee save(Employee employee) {
       return employeeDAO.save(employee);
    }

    @Override
    public void deleteById(int id) {
        employeeDAO.deleteById(id);
    }

}
