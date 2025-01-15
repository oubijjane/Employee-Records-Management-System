package com.records.demo.service;

import com.records.demo.dao.EmployeeDAO;
import com.records.demo.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        List<Employee> employee = null;
        if(result.isPresent()) {
            employee = result.get();
        } else {
            throw new RuntimeException("Did not find employee " + firstName);
        }
        return employee;
    }

    @Override
    public List<Employee> findByLastName(String lastName) {
        Optional<List<Employee>> result = Optional.ofNullable(employeeDAO.findByLastName(lastName));
        List<Employee> employee = null;
        if(result.isPresent()) {
            employee = result.get();
        } else {
            throw new RuntimeException("Did not find employee " + lastName);
        }
        return employee;
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

}
