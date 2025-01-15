package com.records.demo.restController;

import com.records.demo.entity.Users;
import com.records.demo.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
        user.setId(0);
        return userService.save(user);
    }

    @DeleteMapping("/users/{id}")
    public void deletById(@PathVariable("id") int id) {
        userService.deleteById(id);
    }
}
