package com.library.libraryprod.libraryservice;

import com.library.libraryprod.dto.request.CreateUserRequest;
import com.library.libraryprod.dto.response.UserResponse;
import com.library.libraryprod.enums.UserRole;
import com.library.libraryprod.entities.User;
import com.library.libraryprod.repository.UserRepository;
import com.library.libraryprod.utilities.PasswordConversion;
import com.library.libraryprod.dto.response.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordConversion passwordConversion;

    public UserResponse addUser(CreateUserRequest createUserRequest){
        User user = passwordConversion.convert(createUserRequest);
        user.setUserRole(UserRole.MEMBER);
        userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setRole(Role.MEMBER);
        userResponse.setUsername(user.getUsername());
        return userResponse;
    }

    public UserResponse addUserAdmin(CreateUserRequest createUserRequest) {
        User user = passwordConversion.convert(createUserRequest);
        user.setUserRole(UserRole.LIBRARIAN);
        userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setRole(Role.LIBRARIAN);
        userResponse.setUsername(user.getUsername());
        return userResponse;
    }
}