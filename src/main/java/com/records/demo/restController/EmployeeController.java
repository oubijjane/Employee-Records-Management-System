package com.records.demo.restController;

import com.records.demo.entity.Employee;
import com.records.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/test")
    public List<Employee> getEmployees() {
        return employeeService.findAll();
    }
    @GetMapping("/get-employee-by-id/{id}")
    public Employee getEmployeeById(@PathVariable("id") int id) {
        return employeeService.findById(id);
    }

    @GetMapping("/get-employee-by-first-name/{firstName}")
    public List<Employee> getEmployeeByFirstName(@PathVariable("firstName") String firstName) {
        return employeeService.findByFirstName(firstName);
    }
    @GetMapping("/get-employee-by-last-name/{lastName}")
    public List<Employee> getEmployeeByLastName(@PathVariable("lastName") String lastName) {
        return employeeService.findByLastName(lastName);
    }

}
