package com.example.quickship.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
    private String username;
    private String password;
    private String role;
}
