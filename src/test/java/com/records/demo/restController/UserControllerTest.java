package com.records.demo.restController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.records.demo.entity.Users;
import com.records.demo.security.SecurityConfigs;
import com.records.demo.service.RolesService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.sql.DataSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@Import({UserController.class, SecurityConfigs.class})
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Value("${server.port}")
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DataSource dataSource;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private RolesService rolesService;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private CommandLineRunner commandLineRunner;

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void getUsers() throws Exception {
        this.mockMvc.perform(get("http://localhost:"+port+"/api/users")).andExpect(status().isOk());
        verify(userService).findAll();

    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void getUserById() throws Exception {
        this.mockMvc.perform(get("http://localhost:"+port+"/api/users/id/1")).andExpect(status().isOk());
        verify(userService).findById(1);
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void getUserByEmail() throws Exception {
        this.mockMvc.perform(get("http://localhost:"+port+"/api/users/email/email")).andExpect(status().isOk());
        verify(userService).findByEmail("email");
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void updateUser() throws Exception {
        Map<String,Object> body = new HashMap<>();
        body.put("email","email@email.com");
        body.put("password","test123");
        body.put("department","test");

        this.mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:"+port+"/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void addUser() throws Exception {
        Map<String,Object> body = new HashMap<>();
        body.put("email","email@email.com");
        body.put("password","test123");
        body.put("department","test");

        this.mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:"+port+"/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void addRole() throws Exception {
        Users user = new Users();
        user.setEmail("test@email.com");
        when(userService.findById(1)).thenReturn(user);
        this.mockMvc.perform(put("http://localhost:"+port+"/api/users/1/admin")).andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void deletById() throws Exception {

        this.mockMvc.perform(delete("http://localhost:"+port+"/api/users/1")).andExpect(status().isOk());
        verify(userService).deleteById(1);
    }
}