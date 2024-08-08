package com.example.demo.controller;

import com.example.demo.annotation.CustomAnnotation;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

/**
 * Rest controller for managing users.
 */
@RestController
@Tag(name = "Users")
public class UserRestController {

    /**
     * The user service.
     */
    private final UserService userService;

    /**
     * Constructs a UserRestController with the provided user service.
     *
     * @param theUserService the user service
     */
    @Autowired
    public UserRestController(final UserService theUserService) {
        this.userService = theUserService;
    }

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    @Operation(
            description = "Get an endpoint for all users",
            summary = "Get all the users in JSON format"
    )
    @CustomAnnotation
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user
     * @return the user with the specified ID
     */
    @Operation(
            description = "Get an endpoint for a user with a unique ID",
            summary = "Get a specific user by providing "
                    +
                    "a unique ID that exists in the database"
    )
    @CustomAnnotation
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable final Long id) {
        return userService.findById(id);
    }

    /**
     * Retrieves a user by username.
     *
     * @param username the username of the user
     * @return the user with the specified username
     */
    @Operation(
            description = "Get an endpoint for a user/all users "
                    +
                    "with a specific name",
            summary = "Get the JSON format for a user/all "
                    +
                    "users with a specific name"
    )
    @CustomAnnotation
    @GetMapping("/users/name/{username}")
    public User getUserByFirstName(@PathVariable final String username) {
        return userService.findByUsername(username);
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to be deleted
     * @return a message indicating the
     * result of the deletion
     */
    @Operation(
            description = "Delete the user with specific ID",
            summary = "Delete the user by providing "
                    +
                    "a specific ID that is existent"
    )
    @CustomAnnotation
    @DeleteMapping("/users/delete/{id}")
    public String deleteUserById(@PathVariable final Long id) {
        User tempUser = userService.findById(id);
        userService.delete(id);
        return "User deleted - " + tempUser;
    }
}

