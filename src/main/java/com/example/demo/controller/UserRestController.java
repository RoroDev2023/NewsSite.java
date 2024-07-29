package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.userservice.UserService;
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
            summary = "Get the JSON format of all the users"
    )
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @Operation(
            description = "Get an endpoint for a user with a unique ID",
            summary = "Get the JSON format for a user providing a unique ID that exists in the database"
    )
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }

    @Operation(
            description = "Get an endpoint for a user/all users with a specific name",
            summary = "Get the JSON format for a user/all users with a specific name"
    )
    @GetMapping("/users/name/{username}")
    public User getUserByFirstName(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }

    @Operation(
            description = "Delete the user with specific ID",
            summary = "Delete the user from the database by providing a specific ID that exists in the database"
    )
    @DeleteMapping("/users/delete/{id}")
    public String deleteUserById(@PathVariable Long id) {
        User tempUser = userService.findById(id);

        if (tempUser == null) {
            throw new RuntimeException("User not found");
        }

        userService.delete(id);

        return "User deleted - " + tempUser;
    }


    @Operation(
            description = "Post a user by providing needed details",
            summary="Post a user by providing the firstName, lastName, and userRole consecutively."
    )
    @PostMapping("users/create")
    public String createUser(@RequestBody User userRequest) {
        User user = new User(userRequest.getUsername(), userRequest.getPassword());
        //user.setId(12l);
        userService.save(user);
        return "User created - " + user;
    }


}
