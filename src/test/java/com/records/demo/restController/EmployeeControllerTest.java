package com.records.demo.restController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.records.demo.entity.Employee;
import com.records.demo.entity.Users;
import com.records.demo.security.SecurityConfigs;
import com.records.demo.service.EmployeeService;
import com.records.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EmployeeController.class)
@Import({EmployeeController.class, SecurityConfigs.class})
@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {
    @Value("${server.port}")
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CommandLineRunner commandLineRunner;

    @MockitoBean
    private DataSource dataSource;

    @MockitoBean
    private EmployeeService employeeService;

    @MockitoBean
    private UserService userService;

    @Test
    @WithMockUser(username = "test", roles = "MANAGER")
    void getEmployees() throws Exception {
        Users user = new Users();
        user.setDepartment("test");
        when(userService.findByEmail("test")).thenReturn(user);
        this.mockMvc.perform(get("http://localhost:"+port+"/api/employees")).andExpect(status().isOk());
        verify(employeeService).findByDepartment("test");
        verify(employeeService, times(0)).findAll();
    }

    @Test
    @WithMockUser(username = "test", roles = {"HRESOURCES","MANAGER"})
    void getEmployees_hr() throws Exception {

        this.mockMvc.perform(get("http://localhost:"+port+"/api/employees")).andExpect(status().isOk());
        verify(employeeService).findAll();
        verify(employeeService, times(0)).findByDepartment("test");
    }


    @Test
    @WithMockUser(username = "test", roles = {"MANAGER","HRESOURCES"})
    void getEmployeeById() throws Exception {
        Users user = new Users();
        user.setDepartment("test");
        Employee employee = new Employee();
        employee.setEmail("test");
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        when(employeeService.findById(1)).thenReturn(employee);
        when(employeeService.findByDepartment("test")).thenReturn(employeeList);
        when(userService.findByEmail("test")).thenReturn(user);
        this.mockMvc.perform(get("http://localhost:"+port+"/api/employees/id/1")).andExpect(status().isOk());
        verify(employeeService).findById(1);
    }
    @Test
    @WithMockUser(username = "test", roles = {"MANAGER"})
    void getEmployeeById_Manager() throws Exception {
        Users user = new Users();
        user.setDepartment("test");
        Employee employee = new Employee();
        employee.setEmail("test");
        employee.setDepartment("test");
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        when(employeeService.findById(1)).thenReturn(employee);
        when(employeeService.findByDepartment("test")).thenReturn(employeeList);
        when(userService.findByEmail("test")).thenReturn(user);
        this.mockMvc.perform(get("http://localhost:"+port+"/api/employees/id/1")).andExpect(status().isOk());
        verify(employeeService,times(2)).findById(1);
    }

    @Test
    @WithMockUser(username = "test", roles = {"MANAGER"})
    void getEmployeeByFirstName() throws Exception {
        Users user = new Users();
        user.setDepartment("test");
        Employee employee = new Employee();
        employee.setEmail("test");
        employee.setDepartment("test");
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        when(employeeService.findById(1)).thenReturn(employee);
        when(employeeService.findByFirstName("")).thenReturn(employeeList);
        when(userService.findByEmail("test")).thenReturn(user);
        this.mockMvc.perform(get("http://localhost:"+port+"/api/employees/id/1")).andExpect(status().isOk());
        verify(employeeService,times(2)).findById(1);
    }

    @Test
    @WithMockUser(username = "test", roles = {"MANAGER"})
    void getEmployeeByLastName() throws Exception {
        Users user = new Users();
        user.setDepartment("test");
        Employee employee = new Employee();
        employee.setEmail("test");
        employee.setDepartment("test");
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        when(employeeService.findById(1)).thenReturn(employee);
        when(employeeService.findByLastName("")).thenReturn(employeeList);
        when(userService.findByEmail("test")).thenReturn(user);
        this.mockMvc.perform(get("http://localhost:"+port+"/api/employees/id/1")).andExpect(status().isOk());
        verify(employeeService,times(2)).findById(1);
    }

    @Test
    @WithMockUser(username = "test", roles = {"MANAGER"})
    void getEmployeeByJobTitle() throws Exception {
        Users user = new Users();
        user.setDepartment("test");
        Employee employee = new Employee();
        employee.setEmail("test");
        employee.setDepartment("test");
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        when(employeeService.findById(1)).thenReturn(employee);
        when(employeeService.findByJoBTitle("")).thenReturn(employeeList);
        when(userService.findByEmail("test")).thenReturn(user);
        this.mockMvc.perform(get("http://localhost:"+port+"/api/employees/id/1")).andExpect(status().isOk());
        verify(employeeService,times(2)).findById(1);
    }

    @Test
    @WithMockUser(username = "test", roles = {"MANAGER"})
    void updateEmployee() throws Exception {
        Users user = new Users();
        user.setDepartment("test");
        Employee employee = new Employee();
        employee.setEmail("test");
        employee.setDepartment("test");
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        when(employeeService.findById(1)).thenReturn(employee);
        when(employeeService.findByDepartment("test")).thenReturn(employeeList);
        when(userService.findByEmail("test")).thenReturn(user);
        Map<String,Object> body = new HashMap<>();
        body.put("employeeId", 1);
        body.put("firstName", "test");
        body.put("lastName", "test");
        body.put("email","email@email.com");
        body.put("department","test");
        body.put("hireDate", "20/2/2024");

        this.mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:"+port+"/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", roles = {"MANAGER","HRESOURCES"})
    void addEmployee() throws Exception {
        Map<String,Object> body = new HashMap<>();
        body.put("firstName", "test");
        body.put("lastName", "test");
        body.put("email","email@email.com");
        body.put("department","test");
        body.put("hireDate", "20/2/2024");

        this.mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:"+port+"/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "test", roles = {"MANAGER","HRESOURCES"})
    void deleteById() throws Exception {
        this.mockMvc.perform(delete("http://localhost:"+port+"/api/employees/1")).andExpect(status().isOk());
        verify(employeeService).deleteById(1);
    }
}