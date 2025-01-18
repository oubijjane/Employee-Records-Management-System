package com.records.demo.restController;

import com.records.demo.dao.EmployeeDAO;
import com.records.demo.entity.Employee;
import com.records.demo.service.EmployeeService;
import com.records.demo.service.EmplyeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeDAO employeeDAO;

    private EmployeeService underTest;

    @BeforeEach
    void setUp() {
        underTest = new EmplyeeServiceImpl(employeeDAO);
    }

    @Test
    void getEmployees() {
        underTest.findAll();

        verify(employeeDAO).findAll();
    }

    @Test
    void getEmployeeById() {
        Employee employee = new Employee();
        employee.setFirstName("john");
        employee.setLastName("Doe");
        employee.setDepartment("test");
        employee.setHireDate("20/2/2023");
        employee.setEmail("email@email.com");

        int employeeId = employee.getEmployeeId();
        when(employeeDAO.findById(employeeId)).thenReturn(Optional.of(employee));
        Employee byId =underTest.findById(employeeId);


        verify(employeeDAO).findById(employeeId);
        assertThat(employeeDAO.findById(employeeId).isPresent()).isTrue();
        assertThat(byId).isEqualTo(employeeDAO.findById(employeeId).get());
    }

    @Test
    void getEmployeeByFirstName() {
        Employee employee = new Employee();
        employee.setFirstName("john");
        employee.setLastName("Doe");
        employee.setDepartment("test");
        employee.setHireDate("20/2/2023");
        employee.setEmail("email@email.com");

        String employeeFirstName = employee.getFirstName();
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        when(this.employeeDAO.findByFirstName(employeeFirstName)).thenReturn(employeeList);
        List<Employee> byFirstName =underTest.findByFirstName(employeeFirstName);

        verify(employeeDAO).findByFirstName(employeeFirstName);
        assertThat(byFirstName.size()).isEqualTo(1);
    }

    @Test
    void getEmployeeByLastName() {
        Employee employee = new Employee();
        employee.setFirstName("john");
        employee.setLastName("Doe");
        employee.setDepartment("test");
        employee.setHireDate("20/2/2023");
        employee.setEmail("email@email.com");

        String employeeLastName = employee.getLastName();
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        when(this.employeeDAO.findByLastName(employeeLastName)).thenReturn(employeeList);
        List<Employee> byLastName = underTest.findByLastName(employeeLastName);

        verify(employeeDAO).findByLastName(employeeLastName);
        assertThat(byLastName.size()).isEqualTo(1);
    }

    @Test
    void getEmployeeByJobTitle() {
        Employee employee = new Employee();
        employee.setFirstName("john");
        employee.setLastName("Doe");
        employee.setDepartment("test");
        employee.setHireDate("20/2/2023");
        employee.setEmail("email@email.com");

        String employeeJobTitle = employee.getJobTitle();
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        when(this.employeeDAO.findByJobTitle(employeeJobTitle)).thenReturn(employeeList);
        List<Employee> byJobTitle = underTest.findByJoBTitle(employeeJobTitle);

        verify(employeeDAO).findByJobTitle(employeeJobTitle);
        assertThat(byJobTitle.size()).isEqualTo(1);
    }

    @Test
    void SaveEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("john");
        employee.setLastName("Doe");
        employee.setDepartment("test");
        employee.setHireDate("20/2/2023");
        employee.setEmail("email@email.com");

        underTest.save(employee);

        ArgumentCaptor<Employee> employeeCapture = ArgumentCaptor.forClass(Employee.class);

        verify(employeeDAO).save(employeeCapture.capture());

        Employee capturedEmployee = employeeCapture.getValue();

        assertThat(capturedEmployee).isEqualTo(employee);
    }

    @Test
    void deleteById() {
        int id = 0;


        //when
        underTest.deleteById(id);

        //then
        verify(employeeDAO).deleteById(id);
    }
}