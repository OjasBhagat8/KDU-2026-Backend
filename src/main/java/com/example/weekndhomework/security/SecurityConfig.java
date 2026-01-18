package com.example.weekndhomework.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    // Define security filter chain bean that disables CSRF, sets up authorization rules based on roles, and enables HTTP Basic authentication.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers(HttpMethod.POST, "/api/books").hasRole("LIBRARIAN")
                                .requestMatchers(HttpMethod.GET, "/api/books").hasAnyRole("LIBRARIAN","USER")
                                .requestMatchers(HttpMethod.GET, "/api/analytics/audit").hasRole("LIBRARIAN").anyRequest().authenticated()
                                )
                .httpBasic(basic -> {});

        return http.build();
    }
}
