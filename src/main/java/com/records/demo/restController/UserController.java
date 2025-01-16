package com.records.demo.restController;

import com.records.demo.entity.Roles;
import com.records.demo.entity.Users;
import com.records.demo.service.RolesService;
import com.records.demo.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;
    private RolesService rolesService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder, RolesService rolesService) {
        this.userService = userService;
        this.passwordEncoder= passwordEncoder;
        this.rolesService = rolesService;
    }

    @GetMapping("/users")
    public List<Users> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/id/{id}")
    public Users getUserById(@PathVariable("id") int id) {
        return userService.findById(id);
    }

    @GetMapping("/users/email/{email}")
    public Users getUserByEmail(@PathVariable("email") String email) {
        return userService.findByEmail(email);
    }

    @PutMapping("/users")
    public Users updateUser(@RequestBody Users user) {
        return userService.save(user);
    }

    @PostMapping("/users")
    public Users addUser(@RequestBody @NotNull Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.save(user);
    }

    @PutMapping("/users/{id}/{role}")
    public Users addRole(@PathVariable("id") int id, @PathVariable("role") String role) {
       Users user =  userService.findById(id);
       rolesService.save(new Roles(user, role));

        return  user;
    }

    @DeleteMapping("/users/{id}")
    public void deletById(@PathVariable("id") int id) {
        userService.deleteById(id);
    }
}
