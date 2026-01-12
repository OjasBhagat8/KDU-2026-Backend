package com.example.quickship.service;

import com.example.quickship.DTO.LoginRequestDTO;
import com.example.quickship.DTO.LoginResponseDTO;
import com.example.quickship.model.User;
import com.example.quickship.repository.UserRepository;
import com.example.quickship.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwt;

    public UserService(UserRepository repository , PasswordEncoder encoder , JwtUtil jwt){
        this.repository = repository;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    public LoginResponseDTO login(LoginRequestDTO req){
        User user = repository.
                findByUsername(req.getUsername()).orElseThrow(() -> new RuntimeException("Invalid username"));

        if(!encoder.matches(req.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid Password");
        }
        String token = jwt.generateToken(user.getUsername(), user.getRole());
        return new LoginResponseDTO(token);
    }

}
