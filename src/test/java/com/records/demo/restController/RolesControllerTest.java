package com.records.demo.restController;

import com.records.demo.security.SecurityConfigs;
import com.records.demo.service.RolesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RolesController.class)
@Import({RolesController.class, SecurityConfigs.class})
@ExtendWith(MockitoExtension.class)
class RolesControllerTest {

    @Value("${server.port}")
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DataSource dataSource;

    @MockitoBean
    private CommandLineRunner commandLineRunner;

    @MockitoBean
    private RolesService rolesService;

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void getRoles_Authorized() throws Exception {

        this.mockMvc.perform(get("http://localhost:"+port+"/api/roles")).andExpect(status().isOk());
        verify(rolesService).findAll();
    }

    @Test
    @WithMockUser(username = "test", roles = "MANAGER")
    void getRoles_Unauthorized() throws Exception {
        this.mockMvc.perform(get("http://localhost:"+port+"/api/roles")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void getEmployeeById() throws Exception {
        this.mockMvc.perform(get("http://localhost:"+port+"/api/roles/id/1")).andExpect(status().isOk());
        verify(rolesService).findById(1);
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void getRolesByEmail() throws Exception {
        this.mockMvc.perform(get("http://localhost:"+port+"/api/roles/email/email")).andExpect(status().isOk());
        verify(rolesService).findByEmail("email");
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void deletById() throws Exception {
        this.mockMvc.perform(delete("http://localhost:"+port+"/api/roles/1")).andExpect(status().isOk());
        verify(rolesService).deleteById(1);
    }
}