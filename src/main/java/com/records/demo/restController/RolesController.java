package com.records.demo.restController;

import com.records.demo.entity.Employee;
import com.records.demo.entity.Roles;
import com.records.demo.entity.Users;
import com.records.demo.service.RolesService;
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

    @GetMapping("/roles")
    public List<Roles> getEmployees() {
        return rolesService.findAll();
    }

    @GetMapping("/roles/id/{id}")
    public Roles getEmployeeById(@PathVariable("id") int id) {
        return rolesService.findById(id);
    }

    @GetMapping("/roles/email/{email}")
    public List<Roles> getUserByEmail(@PathVariable("email") String email) {
        return rolesService.findByEmail(email);
    }
    @DeleteMapping("/roles/{id}")
    public void deletById(@PathVariable("id") int id) {
        rolesService.deleteById(id);
    }

    @PostMapping("/roles")
    public Roles addRole(@Valid @RequestBody  Roles role) {
        return rolesService.save(role);
    }
    @PutMapping("/roles")
    public Roles updateRole(@Valid @RequestBody Roles role) {
        return rolesService.save(role);
    }
}
