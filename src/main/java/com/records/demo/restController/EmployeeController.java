package com.records.demo.restController;

import com.records.demo.entity.Employee;
import com.records.demo.service.EmployeeService;
import com.records.demo.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private EmployeeService employeeService;
    private UserService userService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, UserService userService) {
        this.employeeService = employeeService;
        this.userService = userService;
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        if(checkRole("ROLE_MANAGER") && !checkRole("ROLE_HRESOURCES") && !checkRole("ROLE_ADMIN")) {
            return employeeService.findByDepartment(getUserDepartment());
        }
        return employeeService.findAll();
    }
//    @GetMapping("/employees/department")
//    public List<Employee> getEmployeesByDepartment() {
//        return employeeService.findAll();
//    }
    @GetMapping("/employees/id/{id}")
    public Employee getEmployeeById(@PathVariable("id") int id) {
        if(checkRole("ROLE_MANAGER") && !checkRole("ROLE_HRESOURCES") && !checkRole("ROLE_ADMIN")) {
            if(!employeeService.findById(id).getDepartment().equals(getUserDepartment())) {
                throw new AccessDeniedException("You are not authorized to access this employee's record.");
            };
        }
        return employeeService.findById(id);
    }

    @GetMapping("/employees/first-name/{firstName}")
    public List<Employee> getEmployeeByFirstName(@PathVariable("firstName") String firstName) {
        if(checkRole("ROLE_MANAGER") && !checkRole("ROLE_HRESOURCES") && !checkRole("ROLE_ADMIN")) {
            List<Employee> employees = new ArrayList<>();
            employeeService.findByFirstName(firstName).forEach(employee -> {
                if(employee.getDepartment().equals(getUserDepartment())) {
                    employees.add(employee);
                }
            });
            return employees;
        }
        return employeeService.findByFirstName(firstName);
    }
    @GetMapping("/employees/last-name/{lastName}")
    public List<Employee> getEmployeeByLastName(@PathVariable("lastName") String lastName) {
        if(checkRole("ROLE_MANAGER") && !checkRole("ROLE_HRESOURCES") && !checkRole("ROLE_ADMIN")) {
            List<Employee> employees = new ArrayList<>();
            employeeService.findByLastName(lastName).forEach(employee -> {
                if(employee.getDepartment().equals(getUserDepartment())) {
                    employees.add(employee);
                }
            });
            return employees;
        }
        return employeeService.findByLastName(lastName);
    }

    @GetMapping("/employees/job-title/{jobTitle}")
    public List<Employee> getEmployeeByJobTitle(@PathVariable("jobTitle") String jobTitle) {
        if(checkRole("ROLE_MANAGER") && !checkRole("ROLE_HRESOURCES") && !checkRole("ROLE_ADMIN")) {
            List<Employee> employees = new ArrayList<>();
            employeeService.findByJoBTitle(jobTitle).forEach(employee -> {
                if(employee.getDepartment().equals(getUserDepartment())) {
                    employees.add(employee);
                }
            });
            return employees;
        }
        return employeeService.findByJoBTitle(jobTitle);
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee) {
        if(checkRole("ROLE_MANAGER") && !checkRole("ROLE_HRESOURCES") && !checkRole("ROLE_ADMIN")) {

            if(!employeeService.findById(employee.getEmployeeId()).getDepartment().equals(getUserDepartment())) {
                throw new AccessDeniedException("You are not authorized to update this employee's record.");
            };
//            employeeService.updateEmployee(employee.getEmployeeId(),employee.getPhoneNumber(),employee.getAdress(),
//                    employee.getEmail(),employee.getFirstName(),employee.getLastName(),employee.getStatus());
            Employee updatedEmployee = employeeService.findById(employee.getEmployeeId());
            updatedEmployee.setFirstName(employee.getFirstName());
            return employeeService.save(updatedEmployee);
        }
        return employeeService.save(employee);
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody @NotNull Employee employee) {
        return employeeService.save(employee);
    }
    @DeleteMapping("/employees/{id}")
    public void deleteById(@PathVariable("id") int id) {
        employeeService.deleteById(id);
    }

    private Boolean checkRole(String role) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> roles = new ArrayList<>();
        user.getAuthorities().stream()
                .map(authority -> authority.getAuthority()) // Correctly use getAuthority() method to get the role name
                .forEach(roles::add);

        return roles.contains(role);
    }
    private String getUserEmail() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return user.getUsername();
    }
    private String getUserDepartment() {
        String department = userService.findByEmail(getUserEmail()).getDepartment();

        return department;
    }
}
