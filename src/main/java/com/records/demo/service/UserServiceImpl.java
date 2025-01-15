package com.records.demo.service;



import com.records.demo.dao.UserDAO;

import com.records.demo.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @Override
    public List<Users> findAll() {
        return userDAO.findAll();
    }

    @Override
    public Users findByEmail(String email) {
        Optional<Users> result = Optional.ofNullable(userDAO.findByEmail(email));
        Users user = null;
        if(result.isPresent()) {
            user = result.get();
        } else {
            throw new RuntimeException("Did not find user with email:  " + email);
        }
        return user;
    }

    @Override
    public Users findById(int id) {
        Optional<Users> result = userDAO.findById(id);
        Users user = null;
        if(result.isPresent()) {
            user = result.get();
        } else {
            throw new RuntimeException("Did not find user with id:  " + id);
        }
        return user;
    }

    @Override
    public Users save(Users user) {
        return userDAO.save(user);
    }

    @Override
    public void deleteById(int id) {
        userDAO.deleteById(id);
    }
}
