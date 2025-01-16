package com.records.demo;

import com.records.demo.audit.AuditorAwareImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


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

}
