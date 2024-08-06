package com.example.demo.controller;

import com.example.demo.annotation.CustomAnnotation;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Tag(name = "Users")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService theUserService) {
        userService = theUserService;
    }


    @Operation(
            description = "Get an endpoint for all users",
            summary = "Get all the users in JSON format"
    )
    @CustomAnnotation
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }


    @Operation(
            description = "Get an endpoint for a user with a unique ID",
            summary = "Get a specific user by providing a unique ID that exists in the database"
    )
    @CustomAnnotation
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }


    @Operation(
            description = "Get an endpoint for a user/all users with a specific name",
            summary = "Get the JSON format for a user/all users with a specific name"
    )
    @CustomAnnotation
    @GetMapping("/users/name/{username}")
    public User getUserByFirstName(@PathVariable String username) {
        return userService.findByUsername(username);
    }


    @Operation(
            description = "Delete the user with specific ID",
            summary = "Delete the user from the database by providing a specific ID that exists in the database"
    )
    @CustomAnnotation
    @DeleteMapping("/users/delete/{id}")
    public String deleteUserById(@PathVariable Long id) {
        User tempUser = userService.findById(id);
        userService.delete(id);
        return "User deleted - " + tempUser;
    }
}
