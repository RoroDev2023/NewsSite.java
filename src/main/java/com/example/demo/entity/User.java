package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a User.
 */
@Entity
@Table(name = "users")
@NoArgsConstructor
@Data
public class User {

    /**
     * The unique identifier for a user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The username of the user.
     */
    @Column(name = "username")
    private String username;

    /**
     * The password of the user.
     */
    @Column(name = "password")
    private String password;

    /**
     * Indicates whether the user is enabled.
     */
    @Column(name = "enabled")
    private String enabled;

    /**
     * Constructs a new User with the specified username,
     * password, and enabled status.
     *
     * @param userUsername the username of the user
     * @param userPassword the password of the user
     * @param userEnabled the enabled parameter of the user
     */
    public User(final String userUsername, final String userPassword,
                final Integer userEnabled) {
        this.username = userUsername;
        this.password = userPassword;
        this.enabled = userEnabled.toString();
    }

    /**
     * Returns a string representation of the User.
     *
     * @return a string representation of the User
     */
    @Override
    public String toString() {
        return "User{"
                +
                "id=" + id
                +
                ", username='" + username + '\''
                +
                ", password='" + password + '\''
                +
                '}';
    }
}

