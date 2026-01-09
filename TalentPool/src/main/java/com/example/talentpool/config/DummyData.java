package com.example.talentpool.config;

import com.example.talentpool.models.User;
import com.example.talentpool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DummyData {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initUsers() {
        return args -> {

            String username = "ojas";
            String rawPassword = "password123";
            String encodedPassword = passwordEncoder.encode(rawPassword);

            // 🔍 DEBUG OUTPUT
            System.out.println("=================================");
            System.out.println("DUMMY USER CREATED");
            System.out.println("Username       : " + username);
            System.out.println("Raw Password   : " + rawPassword);
            System.out.println("BCrypt Password: " + encodedPassword);
            System.out.println("=================================");

            User user = User.builder()
                    .username(username)
                    .password(encodedPassword)
                    .email("ojas@company.com")
                    .roles(List.of("ROLE_BASIC"))
                    .build();

            User adminUser = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .email("admin@company.com")
                    .roles(List.of("ROLE_ADMIN"))
                    .build();

            userRepository.save(user);
            userRepository.save(adminUser);

        };
    }
}
