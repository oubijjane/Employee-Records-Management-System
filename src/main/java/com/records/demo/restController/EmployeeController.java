package com.records.demo.restController;

import com.records.demo.entity.Employee;
import com.records.demo.service.EmployeeService;
import com.records.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
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

    @Operation(summary = "Get list of all employees", description = "Retrieve all employees or employees from a specific department for managers.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of employees"),
            @ApiResponse(responseCode = "403", description = "Access denied due to insufficient role")
    })
    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        if(checkRole("ROLE_MANAGER") && !checkRole("ROLE_HRESOURCES") && !checkRole("ROLE_ADMIN")) {
            return employeeService.findByDepartment(getUserDepartment());
        }
        return employeeService.findAll();
    }

@Operation(summary = "Get an employee from the id", description = "Retrieve an employee with the id or an employee from a specific department for managers.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the employee"),
        @ApiResponse(responseCode = "403", description = "Access denied due to insufficient role or the employee not in the same " +
                "department as the manager")
})
    @GetMapping("/employees/id/{id}")
    public Employee getEmployeeById(@PathVariable("id") int id) {
        if(checkRole("ROLE_MANAGER") && !checkRole("ROLE_HRESOURCES") && !checkRole("ROLE_ADMIN")) {
            if(!employeeService.findById(id).getDepartment().equals(getUserDepartment())) {
                throw new AccessDeniedException("You are not authorized to access this employee's record.");
            };
        }
        return employeeService.findById(id);
    }


    @Operation(summary = "Get an employee from the first name", description = "Retrieve an employee with the first name or an employee from a specific department for managers.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the employee"),
            @ApiResponse(responseCode = "403", description = "Access denied due to insufficient role or the employee not in the same " +
                    "department as the manager")
    })
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

    @Operation(summary = "Get an employee from the last name", description = "Retrieve an employee with the last name or an employee from a specific department for managers.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the employee"),
            @ApiResponse(responseCode = "403", description = "Access denied due to insufficient role or the employee not in the same " +
                    "department as the manager")
    })
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

    @Operation(summary = "Get an employee from the job title", description = "Retrieve an employee with the job title or an employee from a specific department for managers.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the employee"),
            @ApiResponse(responseCode = "403", description = "Access denied due to insufficient role or the employee not in the same " +
                    "department as the manager")
    })
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

    @Operation(summary = "Update an employee", description = "Update an existing employee's details. some fields and" +
            " employee outside of the manager department are restricted ")
    @PutMapping("/employees")
    public Employee updateEmployee(@Validated @RequestBody Employee employee) {
        if(checkRole("ROLE_MANAGER") && !checkRole("ROLE_HRESOURCES") && !checkRole("ROLE_ADMIN")) {

            if(!employeeService.findById(employee.getEmployeeId()).getDepartment().equals(getUserDepartment())) {
                throw new AccessDeniedException("You are not authorized to update this employee's record.");
            };
            Employee updatedEmployee = employeeService.findById(employee.getEmployeeId());
            updatedEmployee.setFirstName(employee.getFirstName());
            return employeeService.save(updatedEmployee);
        }
        return employeeService.save(employee);
    }

    @Operation(summary = "add a new employee", description = "add a new employee, Restricted for the managers")
    @PostMapping("/employees")
    public Employee addEmployee(@Validated @RequestBody @NotNull Employee employee) {
        return employeeService.save(employee);
    }
    @Operation(summary = "delete an employee", description = "delete an employee, Restricted for the managers")
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
