package com.records.demo.dao;

import com.records.demo.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface RolesDAO extends JpaRepository<Roles, Integer> {
    List<Roles> findByEmail(String email);

}
