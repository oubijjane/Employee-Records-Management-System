package com.records.demo.dao;

import com.records.demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<Users, Integer> {
    Users findByEmail(String email);
}
