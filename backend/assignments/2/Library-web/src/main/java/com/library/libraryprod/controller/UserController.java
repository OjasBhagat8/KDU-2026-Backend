package com.library.libraryprod.controller;

import com.library.libraryprod.dto.request.CreateUserRequest;
import com.library.libraryprod.dto.response.UserResponse;
import com.library.libraryprod.libraryservice.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/library/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(
            summary = "Used to add a user"
    )
    @PostMapping("/user")
    public ResponseEntity<UserResponse> addUser(@RequestBody CreateUserRequest createUserRequest){
        return new ResponseEntity<>(userService.addUser(createUserRequest), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Create a admin user"
    )
    @PostMapping("/user/admin")
    public ResponseEntity<UserResponse> addUserAdmin(@RequestBody CreateUserRequest createUserRequest){
        return new ResponseEntity<>(userService.addUserAdmin(createUserRequest), HttpStatus.CREATED);
    }

}
