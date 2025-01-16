package com.records.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfigs {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(config -> config
                .requestMatchers(HttpMethod.GET,"/api/employees").hasRole("MANAGER")
                .requestMatchers(HttpMethod.GET,"/api/employees/**").hasRole("MANAGER")
                .requestMatchers(HttpMethod.POST,"/api/employees").hasRole("HR")
                .requestMatchers(HttpMethod.PUT,"/api/employees").hasRole("HR")
                .requestMatchers(HttpMethod.DELETE,"/api/employees/**","/api/users/**",
                        "/api/users").hasRole("ADMIN")

        ).csrf(AbstractHttpConfigurer::disable).httpBasic(Customizer.withDefaults());


        return httpSecurity.build();
    }

//    @Bean
//    public UserDetailsManager userDetailsManagerDataBase(DataSource dataSource){
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//
//        jdbcUserDetailsManager.setUsersByUsernameQuery(
//                "select email, password, enabled from customer where email=?"
//        );
//        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
//                "select email, role from roles where email=?"
//        );
//
//        return jdbcUserDetailsManager;
//    }
      @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails john = User.builder()
                .username("john")
                .password("{noop}test123")
                .roles("MANAGER")
                .build();

        UserDetails mary = User.builder()
                .username("mary")
                .password("{noop}test123")
                .roles("HR","MANAGER")
                .build();

        UserDetails susan = User.builder()
                .username("susan")
                .password("{noop}test123")
                .roles("HR","MANAGER","ADMIN")
                .build();

        return new InMemoryUserDetailsManager(john,mary,susan);
    }


}
