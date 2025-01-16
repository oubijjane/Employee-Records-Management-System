package com.records.demo.restController;

import com.records.demo.entity.Employee;
import com.records.demo.service.EmployeeService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return employeeService.findAll();
    }
//    @GetMapping("/employees/department")
//    public List<Employee> getEmployeesByDepartment() {
//        return employeeService.findAll();
//    }
    @GetMapping("/employees/id/{id}")
    public Employee getEmployeeById(@PathVariable("id") int id) {
        return employeeService.findById(id);
    }

    @GetMapping("/employees/first-name/{firstName}")
    public List<Employee> getEmployeeByFirstName(@PathVariable("firstName") String firstName) {
        return employeeService.findByFirstName(firstName);
    }
    @GetMapping("/employees/last-name/{lastName}")
    public List<Employee> getEmployeeByLastName(@PathVariable("lastName") String lastName) {
        return employeeService.findByLastName(lastName);
    }

    @GetMapping("/employees/job-title/{jobTitle}")
    public List<Employee> getEmployeeByJobTitle(@PathVariable("jobTitle") String jobTitle) {
        return employeeService.findByJoBTitle(jobTitle);
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody @NotNull Employee employee) {
        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update
        employee.setEmployeeId(0);
        return employeeService.save(employee);
    }
    @DeleteMapping("/employees/{id}")
    public void deleteById(@PathVariable("id") int id) {
        employeeService.deleteById(id);
    }
}
