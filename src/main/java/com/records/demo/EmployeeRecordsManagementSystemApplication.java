package com.records.demo;

import com.records.demo.audit.AuditorAwareImpl;
import com.records.demo.dao.RolesDAO;
import com.records.demo.dao.UserDAO;
import com.records.demo.entity.Roles;
import com.records.demo.entity.Users;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;


@SpringBootApplication
@EnableJpaAuditing
public class EmployeeRecordsManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeRecordsManagementSystemApplication.class, args);
	}
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2B);
	}

	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAwareImpl();
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Employee Management API")
						.version("1.0")
						.description("API documentation for managing employee and users records."));
	}
	@Bean
	public CommandLineRunner commandLineRunner(UserDAO userDAO, PasswordEncoder passwordEncoder, RolesDAO rolesDAO) {
		return runner -> {
			createDefaultUser(userDAO,passwordEncoder,rolesDAO);
		};
	}

	public void createDefaultUser(UserDAO userDAO, PasswordEncoder passwordEncoder, RolesDAO rolesDAO) {
		Users user = new Users();
		user.setPassword(passwordEncoder.encode("123"));
		user.setEmail("admin@email.com");
		user.setDepartment("dev");
		Optional<Users> result = Optional.ofNullable(userDAO.findByEmail("admin@email.com"));
		if(!result.isPresent()) {
			userDAO.save(user);
			rolesDAO.save(new Roles(userDAO.findByEmail("admin@email.com"), "ROLE_ADMIN"));
			rolesDAO.save(new Roles(userDAO.findByEmail("admin@email.com"), "ROLE_MANAGER"));
			rolesDAO.save(new Roles(userDAO.findByEmail("admin@email.com"), "ROLE_HRESOURCES"));
		}
	}

}
