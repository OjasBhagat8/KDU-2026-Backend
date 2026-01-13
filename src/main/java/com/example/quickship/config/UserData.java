package com.example.quickship.config;

import com.example.quickship.model.User;
import com.example.quickship.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserData {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserData(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @PostConstruct
    public void loadUsers() {
        repo.save(new User("manager", encoder.encode("manager@123"), "MANAGER"));
        repo.save(new User("driver", encoder.encode("driver@123"), "DRIVER"));
    }
}
