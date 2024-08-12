package com.example.demo.config;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for loading user-specific data.
 * Implements the {@link UserDetailsService} interface.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * Service for managing user data.
     */
    @Autowired
    private UserService userService;

    /**
     * Loads the user by username.
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never {@code null})
     * @throws UsernameNotFoundException
     * if the user could not be found or the user has no granted authority
     */
    @Override
    public UserDetails loadUserByUsername(
            final String username) throws UsernameNotFoundException {
        Optional<User> userOptional =
                Optional.ofNullable(userService.findByUsername(username));
        if (userOptional.isPresent()) {
            User theUser = userOptional.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(theUser.getUsername())
                    .password(theUser.getPassword())
                    .authorities("EMPLOYEE", "MANAGER", "ADMIN")
                    .build();
        } else {
            throw new UsernameNotFoundException(
                    "User not found with username: " + username);
        }
    }
}

