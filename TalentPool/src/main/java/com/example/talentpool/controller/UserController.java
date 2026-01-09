package com.example.talentpool.controller;

import com.example.talentpool.models.User;
import com.example.talentpool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    // BASIC + ADMIN → VIEW DATA
    @PreAuthorize("hasAnyRole('BASIC','ADMIN')")
    @GetMapping("/users")
    public Collection<User> viewUsers() {
        return userRepository.findAll();
    }

    // ADMIN ONLY → ADD USERS
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users")
    public String addUser(@RequestBody User user) {
        userRepository.save(user);
        return "User added successfully";
    }
}
