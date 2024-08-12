package com.example.demo.controller;

import com.example.demo.config.util.JwtUtil;
import com.example.demo.modal.authentication.AuthenticationRequest;
import com.example.demo.modal.authentication.AuthenticationResponse;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling authentication requests.
 */
@Tag(name = "Authentication")
@RestController
public class AuthController {

    /**
     * The user service.
     */
    private final UserService userService;

    /**
     * The authentication manager.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * The user details service.
     */
    private final UserDetailsService userDetailsService;

    /**
     * The JWT utility.
     */
    private final JwtUtil jwtUtil;

    /**
     * Constructs an AuthController with the provided services and utilities.
     *
     * @param userServiceParam              the user service
     * @param authenticationManagerParam    the authentication manager
     * @param userDetailsServiceParam       the user details service
     * @param jwtUtilParam                  the JWT utility
     */
    @Autowired
    public AuthController(
            final UserService userServiceParam,
            final AuthenticationManager
                    authenticationManagerParam,
            final UserDetailsService
                    userDetailsServiceParam,
            final JwtUtil jwtUtilParam) {
        this.userService = userServiceParam;
        this.authenticationManager = authenticationManagerParam;
        this.userDetailsService = userDetailsServiceParam;
        this.jwtUtil = jwtUtilParam;
    }

    /**
     * Registers a new user.
     *
     * @param user the user to register
     * @return a ResponseEntity indicating the result of the registration
     */
    @Operation(
            description = "Post a user by providing needed details",
            summary = "Post a user by providing the "
                    +
                    "firstName, lastName, and userRole consecutively."
    )
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody final User user) {
        if (userService.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User already exists");
        }
        if (user.getPassword() == null
                || user.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Password cannot be empty");
        }
        user.setPassword(new BCryptPasswordEncoder()
                .encode(user.getPassword()));
        userService.save(user);
        return ResponseEntity.status(HttpStatus.OK)
                .body("User registered successfully");
    }

    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param authRequest the authentication
     *       request containing username and password
     * @return a ResponseEntity containing
     *      the authentication response with the JWT token
     */
    @Operation(
            summary = "Login by inserting the jwt "
                    +
                    "token that was provided upon registration"
    )
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(
            @RequestBody final AuthenticationRequest authRequest) {
        try {
            if (authRequest.getUsername() == null
                    || authRequest.getUsername().isEmpty()
                    || authRequest.getPassword() == null
                    || authRequest.getPassword().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Username or password cannot be empty");
            }
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword())
                );
            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(authRequest.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new AuthenticationResponse(jwt));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during authentication");
        }
    }
}


