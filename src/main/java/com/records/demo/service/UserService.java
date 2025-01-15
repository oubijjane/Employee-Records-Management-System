package com.records.demo.service;


import com.records.demo.entity.Users;

import java.util.List;

public interface UserService {
    List<Users> findAll();
    Users findByEmail(String email);
    Users findById(int id);
    Users save(Users user);
    void deleteById(int id);
}
