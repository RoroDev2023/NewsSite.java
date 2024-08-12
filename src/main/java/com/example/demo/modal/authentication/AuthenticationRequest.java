package com.example.demo.modal.authentication;

import lombok.Data;

/**
 * Represents a request for authentication containing user credentials.
 */
@Data
public class AuthenticationRequest {

    /**
     * The username of the user trying to authenticate.
     */
    private String username;

    /**
     * The password of the user trying to authenticate.
     */
    private String password;
}
