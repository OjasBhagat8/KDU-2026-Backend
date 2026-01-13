package com.example.quickship.controller;

import com.example.quickship.DTO.LoginRequestDTO;
import com.example.quickship.DTO.LoginResponseDTO;
import com.example.quickship.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class Login {

    private final UserService userService;

    public Login(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(userService.login(request));
    }
}
