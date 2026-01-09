package com.example.talentpool.authController;

import com.example.talentpool.dto.LoginRequestDTO;
import com.example.talentpool.dto.LoginResponseDTO;
import com.example.talentpool.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO request) {
        String token = authService.login(request);
        return new LoginResponseDTO(token);
    }
}


