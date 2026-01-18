package com.library.libraryprod.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponse {
    private String username;
    private Role role;
}
