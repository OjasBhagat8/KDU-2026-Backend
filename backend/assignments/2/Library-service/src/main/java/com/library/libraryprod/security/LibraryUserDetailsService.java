package com.library.libraryprod.security;

import com.library.libraryprod.entities.User;
import com.library.libraryprod.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@link org.springframework.security.core.userdetails.UserDetailsService} implementation
 * that loads users from the application repository and adapts them to Spring security's UserDetails.
 * Grants authorities in the form ROLE_{USER_ROLE}.
 */
@Service
public class LibraryUserDetailsService implements UserDetailsService{

    private final UserRepository userRepository;

    public LibraryUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a user by username and maps it to Spring security's User.
     *
     * @param username the unique username
     * @return UserDetails with credentials and authorities
     * @throws UsernameNotFoundException if no user exists for the given username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->new UsernameNotFoundException("User not found: " + username));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getUserRole().name()))
        );
    }
}
