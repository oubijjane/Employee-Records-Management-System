package com.records.demo;

import com.records.demo.security.SecurityConfigs;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {SecurityConfigs.class})
@AutoConfigureMockMvc
class EmployeeRecordsManagementSystemApplicationTests {


}
