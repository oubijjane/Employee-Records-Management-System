package com.records.demo.restController;

import com.records.demo.entity.Employee;
import com.records.demo.entity.Roles;
import com.records.demo.entity.Users;
import com.records.demo.service.RolesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RolesController {

    private RolesService rolesService;

    @Autowired
    public RolesController(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @Operation(summary = "Get list of all roles of the users", description = "Get list of all roles of the users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of roles"),
            @ApiResponse(responseCode = "403", description = "Access denied due to insufficient role")
    })
    @GetMapping("/roles")
    public List<Roles> getRoles() {
        return rolesService.findAll();
    }

    @Operation(summary = "Get a role with id", description = "Get a role with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the role"),
            @ApiResponse(responseCode = "403", description = "Access denied due to insufficient role")
    })
    @GetMapping("/roles/id/{id}")
    public Roles getRoleById(@PathVariable("id") int id) {
        return rolesService.findById(id);
    }

    @Operation(summary = "Get list of all roles of a user with email", description = "Get list of all roles of a user with email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of user roles"),
            @ApiResponse(responseCode = "403", description = "Access denied due to insufficient role")
    })
    @GetMapping("/roles/email/{email}")
    public List<Roles> getRolesByEmail(@PathVariable("email") String email) {
        return rolesService.findByEmail(email);
    }

    @Operation(summary = "delete a role of a user with the id", description = "delete a role of a user with the id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the"),
            @ApiResponse(responseCode = "403", description = "Access denied due to insufficient role")
    })
    @DeleteMapping("/roles/{id}")
    public void deletById(@PathVariable("id") int id) {
        rolesService.deleteById(id);
    }

}
