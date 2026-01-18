package com.example.weekndhomework.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfigUsers {


    // Define in-memory user details service bean with two users: librarian and user, each with their respective roles and passwords.
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails librarian = User.withUsername("librarian")
                .password("{noop}librarian@123")
                .roles("LIBRARIAN")
                .build();

        UserDetails user = User.withUsername("user")
                .password("{noop}user@123")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(librarian,user);
    }
}
