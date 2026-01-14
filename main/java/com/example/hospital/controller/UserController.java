package com.example.hospital.controller;


import com.example.hospital.dto.CreateUserRequest;
import com.example.hospital.model.User;
import com.example.hospital.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public void create(@RequestBody CreateUserRequest req) {
        service.create(req);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<User> getByTenant(@PathVariable UUID tenantId) {
        return service.getByTenant(tenantId);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable UUID id,
                       @RequestParam String username,
                       @RequestParam String timezone) {
        service.update(id, username, timezone);
    }
}

