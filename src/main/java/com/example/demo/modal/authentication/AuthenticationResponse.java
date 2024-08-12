package com.example.demo.modal.authentication;

import lombok.Data;

/**
 * Represents the response containing the JWT token for authentication.
 */
@Data
public class AuthenticationResponse {

    /**
     * The JSON Web Token (JWT) used for authentication.
     */
    private final String jwt;

    /**
     * Constructs a new AuthenticationResponse with the specified JWT.
     *
     * @param jwtToken the JWT to be set in the response
     */
    public AuthenticationResponse(final String jwtToken) {
        this.jwt = jwtToken;
    }
}
