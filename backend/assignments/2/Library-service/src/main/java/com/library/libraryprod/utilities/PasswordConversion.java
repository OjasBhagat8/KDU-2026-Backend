    package com.library.libraryprod.utilities;

    import com.library.libraryprod.dto.request.CreateUserRequest;
    import com.library.libraryprod.entities.User;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Component;


    @Component
    public class PasswordConversion {

        @Autowired
        private PasswordEncoder passwordEncoder;

        public User convert(CreateUserRequest userRequest){
            User user = new User();
            user.setUsername(userRequest.getUsername());
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            return user;
        }

    }
