package com.records.demo.restController;

import com.records.demo.entity.Roles;
import com.records.demo.entity.Users;
import com.records.demo.service.RolesService;
import com.records.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

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

    @Operation(summary = "Get list of all users ", description = "Retrieve all users with roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of users"),
            @ApiResponse(responseCode = "403", description = "Access denied due to insufficient role")
    })
    @GetMapping("/users")
    public List<Users> getUsers() {
        return userService.findAll();
    }
    @Operation(summary = "Get a user from the id", description = "Retrieve a user with the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the user"),
            @ApiResponse(responseCode = "403", description = "Access denied due to insufficient role")
    })
    @GetMapping("/users/id/{id}")
    public Users getUserById(@PathVariable("id") int id) {
        return userService.findById(id);
    }

    @Operation(summary = "Get a user from the email", description = "Retrieve a user with the email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the user"),
            @ApiResponse(responseCode = "403", description = "Access denied due to insufficient role")
    })
    @GetMapping("/users/email/{email}")
    public Users getUserByEmail(@PathVariable("email") String email) {
        return userService.findByEmail(email);
    }

    @Operation(summary = "update a user with id", description = "update a user with id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the user"),
            @ApiResponse(responseCode = "403", description = "Access denied due to insufficient role")
    })
    @PutMapping("/users")
    public Users updateUser(@Valid @RequestBody Users user) {
        Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
        if(!BCRYPT_PATTERN.matcher(user.getPassword()).matches()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userService.save(user);
    }

    @Operation(summary = "add a new user", description = "add a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added the user"),
            @ApiResponse(responseCode = "403", description = "Access denied due to insufficient role")
    })
    @PostMapping("/users")
    public Users addUser(@RequestBody @NotNull Users user) {
        System.out.println(passwordEncoder.encode(user.getPassword()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.save(user);
    }

    @Operation(summary = "add a role to an existing user", description = "add a role to an existing user roles = ([ROLE_ADMIN],[ROLE_HRESOURCES],[ROLE_MANAGER])")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the employee"),
            @ApiResponse(responseCode = "403", description = "Access denied due to insufficient role")
    })
    @PutMapping("/users/{id}/{role}")
    public Users addRole(@PathVariable("id") int id, @PathVariable("role") String role) {
       Users user =  userService.findById(id);
       @Valid Roles newRole = new Roles(user, role);
       rolesService.save(newRole);

        return  user;
    }

    @Operation(summary = "delete a user with an id", description = "delete a user with an id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the employee"),
            @ApiResponse(responseCode = "403", description = "Access denied due to insufficient role")
    })
    @DeleteMapping("/users/{id}")
    public void deletById(@PathVariable("id") int id) {
        userService.deleteById(id);
    }
}
