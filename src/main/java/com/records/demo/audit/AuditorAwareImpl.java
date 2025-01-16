package com.records.demo.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println("hello");
        return Optional.of(user.getUsername()) ;
    }

}
