package com.example.talentpool.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class User {
    private String username;
    private String password;
    private String email;
    private List<String> roles;
}
