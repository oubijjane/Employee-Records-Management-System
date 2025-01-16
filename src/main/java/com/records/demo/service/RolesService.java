package com.records.demo.service;



import com.records.demo.entity.Roles;

import java.util.List;

public interface RolesService {
    List<Roles> findAll();

    Roles findById(int id);

    Roles save(Roles role);

    void deleteById(int id);

    List<Roles> findByEmail(String email);

}
